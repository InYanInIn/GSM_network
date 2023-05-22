import java.util.Random;

public class VBD extends Station implements Runnable{

    private long frequency;
    boolean active;
    long number;
    public StationLayer stationLayer;
    public VRDLayer receiverLayer;
    public String messageText;

    public VBD(long frequency, boolean active, long number, StationLayer stationLayer, VRDLayer receiverLayer, String messageText) {
        super();
        this.frequency = frequency;
        this.active = active;
        this.number = number;
        this.stationLayer = stationLayer;
        this.receiverLayer = receiverLayer;
        this.messageText =messageText;
    }

    @Override
    public void run() {
        while (true){
            try {
                if (active) {
                    Thread.sleep(frequency);
                    sendMessage(receiverLayer);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void sendMessage(VRDLayer vrdLayer){

        if (vrdLayer.getReceiversAmount()!=0){
            System.out.println("Message sent!");
            long randomNumber = vrdLayer.getReceiverByIndex(new Random().nextInt(vrdLayer.getReceiversAmount())).getNumber();
            stationLayer.receiveMessage(new SMS("Hello World!", number, randomNumber));
        }else {
            System.out.println(number+" List of contacts is empty. Message was not sent.");
        }
    }

    public void refreshReceivers(VRDLayer receiverLayer){
        this.receiverLayer = receiverLayer;
    }

    public long getFrequency() {
        return frequency;
    }

    public boolean isActive() {
        return active;
    }

    public long getNumber() {
        return number;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }
}
