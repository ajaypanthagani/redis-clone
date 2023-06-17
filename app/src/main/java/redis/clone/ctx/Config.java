package redis.clone.ctx;

import org.yaml.snakeyaml.Yaml;
import redis.clone.App;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Config {
    private static Map<String, String> configFromFile;
    private static Map<String, String> configFromEnv;

    public static void init(){
        Yaml yaml = new Yaml();
        try (InputStream inputStream = App.class.getResourceAsStream("/application.yaml")) {
            configFromEnv = System.getenv();
            configFromFile = yaml.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void init(Map<String, String> env, Map<String, String> file){
        configFromEnv = env;
        configFromFile = file;
    }

    public static String get(String key) {
        String valueFromEnv = configFromEnv.get(key);
        if(valueFromEnv == null){
            return configFromFile.get(key);
        }
        return valueFromEnv;
    }
}
