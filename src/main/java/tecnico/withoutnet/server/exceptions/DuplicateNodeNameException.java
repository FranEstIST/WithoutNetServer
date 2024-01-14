package tecnico.withoutnet.server.exceptions;

public class DuplicateNodeNameException extends RuntimeException {
    public DuplicateNodeNameException(String message) {
        super(message);
    }
}
