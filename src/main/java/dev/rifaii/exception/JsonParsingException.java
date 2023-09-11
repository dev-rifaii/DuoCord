package dev.rifaii.exception;

public class JsonParsingException extends RuntimeException {

    public JsonParsingException() {
        super();
    }

    public JsonParsingException(String message) {
        super(message);
    }

    public JsonParsingException(String message, String json) {
        super("Failed to parse json String with value %s. %s)".formatted(json, message));
    }

    public JsonParsingException(String message, String json, Class<?> clazz) {
        super("Failed to parse json String with value %s into class %s. %s)".formatted(clazz.getSigners(), json, message));
    }

    public JsonParsingException(String json, Class<?> clazz) {
        super("Failed to parse json String with value %s into class %s)".formatted(json, clazz.getSimpleName()));
    }

}
