public class BSC extends Station{
    public StationLayer stationLayer;
    private boolean active = true;

    public BSC(StationLayer stationLayer) {
        super();
        this.stationLayer = stationLayer;
    }

    @Override
    public void run() {
        try {
            int randTime = (int)(Math.random()*11+5)*1000;
            Thread.sleep(randTime);
            if(active)
                sendMessage(messages.remove(0));

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void  addMessage(String message) {
        messages.add(message);
        Thread mess = new Thread(this);
        mess.start();
    }

    public void sendMessage(String message){
        stationLayer.receiveMessage(message);
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
