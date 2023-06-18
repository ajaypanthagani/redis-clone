package redis.clone.router;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RouterTest {

    void testRoutHandler(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, 10L);
        exchange.close();
    }

    @Test
    void testHandleExistingRoute() throws IOException {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Router router = new Router(Map.of(
                "/test", this::testRoutHandler
        ));

        Headers responseHeaders = Mockito.mock(Headers.class);
        when(exchange.getResponseHeaders()).thenReturn(responseHeaders);
        when(exchange.getRequestURI()).thenReturn(URI.create("/test"));
        router.handle(exchange);

        verify(exchange).sendResponseHeaders(Mockito.eq(200), Mockito.eq(10L));
        verify(exchange).close();
    }

    @Test
    public void testHandlePageNotFound() throws IOException {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        OutputStream outputStream = Mockito.mock(OutputStream.class);
        Router router = new Router();

        when(exchange.getResponseBody()).thenReturn(outputStream);
        Headers responseHeaders = Mockito.mock(Headers.class);
        when(exchange.getResponseHeaders()).thenReturn(responseHeaders);
        when(exchange.getRequestURI()).thenReturn(URI.create("/test"));
        router.handle(exchange);

        verify(exchange).sendResponseHeaders(Mockito.eq(404), Mockito.anyLong());
        verify(outputStream).write(Mockito.any(byte[].class));
        verify(outputStream).close();
    }
}
