package tech.hljzj.activiti.manager;

public class ActException extends RuntimeException {
    public ActException(String message) {
        super(message);
    }

    public ActException(String message, Exception e) {
        super(message, e);
    }
}
