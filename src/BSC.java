public class BSC extends Station{
    public StationLayer stationLayer;

    public BSC(StationLayer stationLayer) {
        this.stationLayer = stationLayer;
    }

    @Override
    public void run() {
        try {
            int randTime = (int)(Math.random()*11+5)*1000;
            Thread.sleep(randTime);
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
