public class SMS {
    private String message;
    private long senderNumber;
    private long receiverNumber;

    public SMS(String message, long senderNumber, long receiverNumber) {
        this.message = message;
        this.senderNumber = senderNumber;
        this.receiverNumber = receiverNumber;
    }

    public SMS() {
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

    public synchronized void setMessage(String message) {
        this.message = message;
    }

    public synchronized void setSenderNumber(long senderNumber) {
        this.senderNumber = senderNumber;
    }

    public synchronized void setReceiverNumber(long receiverNumber) {
        this.receiverNumber = receiverNumber;
    }
}
