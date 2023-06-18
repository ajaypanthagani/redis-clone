package redis.clone;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public abstract class Router implements HttpHandler {
    protected Map<String, CheckedConsumer<HttpExchange, IOException>> routes;

    protected void handlePageNotFound(HttpExchange exchange) throws IOException {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("message", "page not found");
        jsonResponse.put("code", "404");
        String response = jsonResponse.toString();
        exchange.sendResponseHeaders(404, response.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath();
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        if (routes != null && routes.containsKey(requestPath)) {
            routes.get(requestPath).accept(exchange);
        } else {
            handlePageNotFound(exchange);
        }
    }

    @FunctionalInterface
    public interface CheckedConsumer<T, E extends Exception> {
        void accept(T t) throws E;
    }
}
