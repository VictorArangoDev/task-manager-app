package api.exception;

import java.util.Map;

public class DuplicateResourceException extends RuntimeException{

    private Map<String, String> errors;

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(Map<String, String> errors) {
        super("Datos duplicados");
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
