import java.util.ArrayList;

public class VRDLayer extends StationLayer{
    ArrayList<VRD> receivers = new ArrayList<>();

    @Override
    public void receiveMessage(SMS message) {
        long number = message.getNumber();
        for(VRD r : receivers){
            if (r.getNumber() == number){
                r.addMessage(message);
                return;
            }
        }
    }

    public void addReceiver(long number, VBDLayer vbdLayer){
        receivers.add(new VRD(number, false));
        vbdLayer.refreshReceivers(this);
    }
}
