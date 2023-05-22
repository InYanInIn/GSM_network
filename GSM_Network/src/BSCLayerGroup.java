import java.util.ArrayList;

public class BSCLayerGroup extends StationLayer{
    volatile private ArrayList<BSCLayer> group = new ArrayList<>();

    public BSCLayerGroup(BTSLayer BTSLayer) {
        group.add(new BSCLayer(BTSLayer));
    }

    public synchronized void addBSCLayer(){
        group.add(0, new BSCLayer(group.get(0)));
    }

    public synchronized BSCLayer getFirstBSCLayer(){
        return group.get(0);
    }

    public synchronized ArrayList<BSCLayer> getGroup() {
        return group;
    }
    public synchronized BSCLayer getElementByIndex(int n){
        return group.get(n);
    }
    public synchronized void removeBSCLayer(){
        group.remove(0);
    }
}
