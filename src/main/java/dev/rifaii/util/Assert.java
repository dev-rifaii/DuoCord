package dev.rifaii.util;

public class Assert {

    public static void requireNotNull(String messageIfNull, Object... args) throws Exception {
        for (Object o : args) {
            if (o == null) throw new Exception(messageIfNull);
        }
    }
}
