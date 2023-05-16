import java.util.ArrayList;

public class BTSLayer extends StationLayer{
    ArrayList<BTS> bts = new ArrayList<>();
    public StationLayer stationLayer;

    public BTSLayer(StationLayer stationLayer) {
        bts.add(new BTS(stationLayer));
        this.stationLayer = stationLayer;
    }

    @Override
    public void receiveMessage(SMS message){
        BTS minStation = bts.get(0);
        for (BTS st : bts){
            if (st.getMessagesAmount() == 5){
                minStation = new BTS(stationLayer);
                bts.add(minStation);
            }
            if (st.getMessagesAmount() < minStation.getMessagesAmount()){
                minStation = st;
            }
        }
        minStation.addMessage(message);
    }
}
