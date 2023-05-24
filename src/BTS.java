public class BTS extends Station{

    public StationLayer stationLayer;

    public BTS(StationLayer stationLayer) {
        super();
        this.stationLayer = stationLayer;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            sendMessage(messages.remove(0));



        } catch (InterruptedException|IndexOutOfBoundsException e) {
            try {
                throw new NoSuchNumberException("No receiver found with number: ");
            } catch (NoSuchNumberException ignored) {}
        }
    }

    @Override
    public synchronized void addMessage(String message) {
        messages.add(message);
        Thread mess = new Thread(this);
        mess.start();
    }

    public synchronized void sendMessage(String message){
        stationLayer.receiveMessage(message);
    }

    public synchronized void refresh(StationLayer stationLayer){
        this.stationLayer = stationLayer;
    }
}
