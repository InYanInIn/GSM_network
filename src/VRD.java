import java.util.ArrayList;

public class VRD extends Station implements Runnable{

    private int receivedMessages;
    private long number;
    private boolean deleteMessagesEvery10Sec;

    private ArrayList<SMS> receivedSMS = new ArrayList<>();

    public VRD(long number, boolean deleteMessagesEvery10Sec) {
        super();
        receivedMessages = 0;
        this.number = number;
        this.deleteMessagesEvery10Sec = deleteMessagesEvery10Sec;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(10000);
                if (deleteMessagesEvery10Sec){
                    receivedSMS.clear();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public synchronized long getNumber() {
        return number;
    }

    @Override
    public void addMessage(SMS message) {
        System.out.println("Message received! Message: "+message.getSenderNumber()+"|"+message.getReceiverNumber());
        receivedSMS.add(message);
        receivedMessages+=1;
    }

    public int getReceivedMessages() {
        return receivedMessages;
    }

    public boolean isDeleteMessagesEvery10Sec() {
        return deleteMessagesEvery10Sec;
    }

    public void setDeleteMessagesEvery10Sec(boolean deleteMessagesEvery10Sec) {
        this.deleteMessagesEvery10Sec = deleteMessagesEvery10Sec;
    }
}
