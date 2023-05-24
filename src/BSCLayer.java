import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BSCLayer extends StationLayer{
    volatile ArrayList<BSC> bscs = new ArrayList<>();
    public StationLayer stationLayer;

    public BSCLayer(StationLayer stationLayer) {
        bscs.add(new BSC(stationLayer));
        this.stationLayer = stationLayer;
    }

    @Override
    public synchronized void receiveMessage(String message) {
        BSC minStation = bscs.get(0);
        List<BSC> stationsToAdd = new ArrayList<>();

        for (BSC st : bscs) {
            if (st.getMessagesAmount() < minStation.getMessagesAmount()) {
                minStation = st;
            }
        }
        if (minStation.getMessagesAmount() == 5) {
            minStation = new BSC(stationLayer);
            minStation.addMessage(message);
            stationsToAdd.add(minStation);
            System.out.println("Station BSC created!!!");
            bscs.addAll(stationsToAdd);
            return;
        }


        minStation.addMessage(message);
    }

    public ArrayList<BSC> getBscs() {
        return bscs;
    }

    public synchronized void removeBSC(BSC bscStation){
        bscs.remove(bscStation);
    }

    public synchronized void sentAllMessagesImmideatly(){
        for (BSC bsc : bscs){
            bsc.setActive(false);
            for (String message : bsc.getMessages()) {
                bsc.sendMessage(message);
            }
        }
    }
}
