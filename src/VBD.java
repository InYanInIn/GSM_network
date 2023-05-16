import java.util.Random;

public class VBD extends Station implements Runnable{

    private long frequency;
    boolean active;
    long number;
    public StationLayer stationLayer;
    public VRDLayer receiverLayer;

    public VBD(long frequency, boolean active, long number, StationLayer stationLayer, VRDLayer receiverLayer) {
        this.frequency = frequency;
        this.active = active;
        this.number = number;
        this.stationLayer = stationLayer;
        this.receiverLayer = receiverLayer;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(frequency);
                sendMessage(receiverLayer);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessage(VRDLayer vrdLayer){
        System.out.println("Message sent!");
        long randomNumber = vrdLayer.receivers.get(new Random().nextInt(vrdLayer.receivers.size())).getNumber();
        stationLayer.receiveMessage(new SMS("Hello World!", randomNumber));
    }

    public void refreshReceivers(VRDLayer receiverLayer){
        this.receiverLayer = receiverLayer;
    }
}
