public class SendResult {
    public final boolean success;
    public final String errorMessage;

    public SendResult() {
        this.success = true;
        this.errorMessage = null;
    }

    public static SendResult error(String message) {
        return new SendResult(message);
    }

    private SendResult(String errorMessage) {
        this.success = false;
        this.errorMessage = errorMessage;
    }
}
