public class BTS extends Station{

    public StationLayer stationLayer;

    public BTS(StationLayer stationLayer) {
        this.stationLayer = stationLayer;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            sendMessage(messages.get(0));
            messages.remove(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addMessage(SMS message) {
        super.addMessage(message);
        Thread mess = new Thread(this);
        mess.start();
    }

    public void sendMessage(SMS message){
        stationLayer.receiveMessage(message);
    }
}
