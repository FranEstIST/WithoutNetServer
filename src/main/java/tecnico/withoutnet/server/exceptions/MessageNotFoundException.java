package tecnico.withoutnet.server.exceptions;

public class MessageNotFoundException extends RuntimeException{
    public MessageNotFoundException(String message) {
        super(message);
    }
}
