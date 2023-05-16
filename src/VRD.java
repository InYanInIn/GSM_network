import java.util.ArrayList;

public class VRD extends Station{

    private int receivedMessages;
    private long number;
    private boolean deleteMessagesEvery10Sec;

    private ArrayList<SMS> receivedSMS = new ArrayList<>();

    public VRD(long number, boolean deleteMessagesEvery10Sec) {
        receivedMessages = 0;
        this.number = number;
        this.deleteMessagesEvery10Sec = deleteMessagesEvery10Sec;
    }

    @Override
    public void run() {

    }

    public long getNumber() {
        return number;
    }

    @Override
    public void addMessage(SMS message) {
        System.out.println("Message received! Message: "+message.getMessage());
        receivedSMS.add(message);
        receivedMessages++;
    }

    public int getReceivedMessages() {
        return receivedMessages;
    }
}
