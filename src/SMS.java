public class SMS {
    private String message;
    private long senderNumber;
    private long receiverNumber;

    public SMS(String message, long senderNumber, long receiverNumber) {
        this.message = message;
        this.senderNumber = senderNumber;
        this.receiverNumber = receiverNumber;
    }

    public synchronized String getMessage() {
        return message;
    }

    public synchronized long getSenderNumber() {
        return senderNumber;
    }
    public synchronized long getReceiverNumber() {
        return receiverNumber;
    }
}
