import java.util.ArrayList;

public class VBDLayer extends StationLayer{
    ArrayList<VBD> senders = new ArrayList<>();

    private StationLayer stationLayer;
    private VRDLayer receiverLayer;


    public VBDLayer(StationLayer stationLayer, VRDLayer receiverLayer) {
        this.stationLayer = stationLayer;
        this.receiverLayer = receiverLayer;
    }

    public void addSender(long frequency, long number){
        senders.add(new VBD(frequency, true, number, stationLayer, receiverLayer));
        Thread mess = new Thread(senders.get(senders.size()-1));
        mess.start();
    }

    public void refreshReceivers(VRDLayer receiverLayer){
        this.receiverLayer = receiverLayer;
        for (VBD sender : senders){

        }
    }
}
