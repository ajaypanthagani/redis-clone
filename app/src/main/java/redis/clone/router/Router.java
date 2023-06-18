package redis.clone.router;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

public class Router extends redis.clone.Router {
    public Router() {
    }

    public Router(Map<String, CheckedConsumer<HttpExchange, IOException>> routes) {
        this.routes = routes;
    }
}
