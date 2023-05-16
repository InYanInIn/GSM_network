public class SMS {
    private String message;
    private long number;

    public SMS(String message, long number) {
        this.message = message;
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public long getNumber() {
        return number;
    }
}
