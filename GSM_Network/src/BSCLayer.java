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
    public synchronized void receiveMessage(SMS message) {
        BSC minStation = bscs.get(0);
        List<BSC> stationsToAdd = new ArrayList<>();

        for (BSC st : bscs) {
            if (st.getMessagesAmount() == 5) {
                minStation = new BSC(stationLayer);
                stationsToAdd.add(minStation);
                break;
            }
            if (st.getMessagesAmount() < minStation.getMessagesAmount()) {
                minStation = st;
            }
        }

        bscs.addAll(stationsToAdd);
        minStation.addMessage(message);
    }

    public ArrayList<BSC> getBscs() {
        return bscs;
    }

    public synchronized void removeBSC(BSC bscStation){
        bscs.remove(bscStation);
    }
}
