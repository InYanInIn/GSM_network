import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        VRDLayer receivers = new VRDLayer();
        BTSLayer btsReceivers = new BTSLayer(receivers);
        BSCLayerGroup bscGroup = new BSCLayerGroup(btsReceivers);
        BTSLayer btsSenders = new BTSLayer(bscGroup.getFirstBSCLayer());
        VBDLayer senders = new VBDLayer(btsSenders, receivers);

        receivers.addReceiver(2345678, senders);
        senders.addSender(2000, 1234567);

    }
}