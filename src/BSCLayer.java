import java.util.ArrayList;

public class BSCLayer extends StationLayer{
    ArrayList<BSC> bscs = new ArrayList<>();
    public StationLayer stationLayer;

    public BSCLayer(StationLayer stationLayer) {
        bscs.add(new BSC(stationLayer));
        this.stationLayer = stationLayer;
    }

    @Override
    public void receiveMessage(SMS message){
        BSC minStation = bscs.get(0);
        for (BSC st : bscs){
            if (st.getMessagesAmount() == 5){
                minStation = new BSC(stationLayer);
                bscs.add(minStation);
            }
            if (st.getMessagesAmount() < minStation.getMessagesAmount()){
                minStation = st;
            }
        }
        minStation.addMessage(message);
    }
}
