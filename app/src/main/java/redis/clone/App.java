package redis.clone;

import redis.clone.ctx.Config;
import redis.clone.router.Router;

public class App {

    private static void boot() {
        Config.init();
    }

    public static void main(String[] args) throws Exception {
        boot();
        Server.host = (String) Config.get("HOST");
        Server.port = (Integer) Config.get("PORT");
        Server.start(new Router());
    }
}
