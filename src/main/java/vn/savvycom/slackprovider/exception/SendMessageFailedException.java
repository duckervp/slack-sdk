package vn.savvycom.slackprovider.exception;

public class SendMessageFailedException extends RuntimeException {
    public SendMessageFailedException(String message) {
        super(message);
    }
}
