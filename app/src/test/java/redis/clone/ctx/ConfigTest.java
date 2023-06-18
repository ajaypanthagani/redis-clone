package redis.clone.ctx;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ConfigTest {

    @BeforeAll
    static void init() {
        Map<String, String> env = new HashMap<>();
        Map<String, String> file = new HashMap<>();

        env.put("SOME-COMMON-KEY", "SOME-VALUE-ENV");
        env.put("SOME-KEY-ENV", "SOME-VALUE-ENV");
        file.put("SOME-COMMON-KEY", "SOME-VALUE-FILE");
        file.put("SOME-KEY-FILE", "SOME-VALUE-FILE");

        Config.init(env, file);
    }

    @Test
    void shouldGetConfigValueFromEnvironment() {
        String expectedConfigValue = "SOME-VALUE-ENV";
        String actualConfigValue = (String) Config.get("SOME-COMMON-KEY");
        assertEquals(expectedConfigValue, actualConfigValue);
    }

    @Test
    void shouldGetConfigValueFromFile() {
        String expectedConfigValue = "SOME-VALUE-FILE";
        String actualConfigValue = (String) Config.get("SOME-KEY-FILE");
        assertEquals(expectedConfigValue, actualConfigValue);
    }
}
