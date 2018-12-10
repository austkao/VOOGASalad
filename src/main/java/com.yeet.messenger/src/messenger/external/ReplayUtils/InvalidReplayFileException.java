package messenger.external.ReplayUtils;

public class InvalidReplayFileException extends Exception {

    private String message;

    public InvalidReplayFileException(String msg){
        message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
