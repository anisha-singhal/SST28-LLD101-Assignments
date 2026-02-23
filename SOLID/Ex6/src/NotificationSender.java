public abstract class NotificationSender {
    protected final AuditLog audit;

    protected NotificationSender(AuditLog audit) {
        this.audit = audit;
    }

    // Contract: given a non-null notification, always return a SendResult. Never throw.
    public SendResult send(Notification n) {
        if (n == null) {
            return SendResult.error("Notification must not be null");
        }
        return doSend(n);
    }

    protected abstract SendResult doSend(Notification n);
}
