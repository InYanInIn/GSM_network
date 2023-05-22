import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        VRDLayer receivers = new VRDLayer();
        BTSLayer btsReceivers = new BTSLayer(receivers);
        BSCLayerGroup bscGroup = new BSCLayerGroup(btsReceivers);
        BTSLayer btsSenders = new BTSLayer(bscGroup.getFirstBSCLayer());
        VBDLayer senders = new VBDLayer(btsSenders, receivers);
//
//
//        Thread.sleep(21);
//        senders.addSender(500, 8);
//        Thread.sleep(21);
//        receivers.addReceiver(1, senders);
//        receivers.addReceiver(2, senders);
//        senders.addSender(500, 7);
//        Thread.sleep(21);
//        senders.addSender(500, 6);
//        Thread.sleep(21);
//        receivers.addReceiver(3, senders);
//        receivers.addReceiver(4, senders);

        new Graphics(receivers, btsReceivers, bscGroup, btsSenders, senders);

    }
}