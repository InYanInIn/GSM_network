import java.util.ArrayList;

public class VRDLayer extends StationLayer{
    private ArrayList<VRD> receivers = new ArrayList<>();

    @Override
    public synchronized void receiveMessage(SMS message) {
        try {
            long number = message.getReceiverNumber();
            for(VRD r : receivers){
                if (r.getNumber() == number){
                    r.addMessage(message);
                    return;
                }
            }
            throw new NoSuchNumberException("No receiver found with number: " + number);
        } catch (NoSuchNumberException ignored) {}
    }

    public synchronized void addReceiver(long number, VBDLayer vbdLayer){
        receivers.add(new VRD(number, false));
        vbdLayer.refreshReceivers(this);
    }
    public synchronized void removeReceiver(VRD vrd){
        receivers.remove(vrd);

    }
    public synchronized int getReceiversAmount(){
        return receivers.size();
    }

    public synchronized VRD getReceiverByIndex(int index) {
        return receivers.get(index);
    }

    public synchronized ArrayList<VRD> getReceivers() {
        return receivers;
    }
}
