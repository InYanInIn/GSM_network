import java.util.ArrayList;
import java.util.List;

public class BTSLayer extends StationLayer{
    volatile ArrayList<BTS> bts = new ArrayList<>();
    public StationLayer stationLayer;

    public BTSLayer(StationLayer stationLayer) {
        bts.add(new BTS(stationLayer));
        this.stationLayer = stationLayer;
    }

    @Override
    public synchronized void receiveMessage(SMS message) {
        BTS minStation = bts.get(0);
        List<BTS> stationsToAdd = new ArrayList<>();

        for (BTS st : bts) {
            if (st.getMessagesAmount() < minStation.getMessagesAmount()) {
                minStation = st;
            }
        }
        if (minStation.getMessagesAmount() == 5){
            minStation = new BTS(stationLayer);
            stationsToAdd.add(minStation);
            System.out.println("STATION BTS CREATED");
            bts.addAll(stationsToAdd);
            minStation.addMessage(message);
            return;
        }
        minStation.addMessage(message);
    }

    public synchronized ArrayList<BTS> getBts() {
        return bts;
    }

    public synchronized void removeBTS(BTS btsStation){
        bts.remove(btsStation);
    }
    public synchronized void refreshBTSes(StationLayer stationLayer){
        for (BTS bts1 : bts){
            bts1.refresh(stationLayer);
        }
    }
}
