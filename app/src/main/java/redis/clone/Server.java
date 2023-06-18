package redis.clone;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.Objects;

public class Server {
    public static String host;
    public static int port;

    public static void start(HttpHandler router) throws Exception {
        if (Objects.equals(host, "") || Objects.equals(port, 0)) {
            throw new RuntimeException("host and port can't be empty");
        }
        HttpServer server = HttpServer.create(new InetSocketAddress(host, port), 0);
        server.createContext("/", router);
        server.start();
        System.out.printf("server running on %s:%d", host, port);
    }
}
