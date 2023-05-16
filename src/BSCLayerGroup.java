import java.util.ArrayList;

public class BSCLayerGroup extends StationLayer{
    private ArrayList<BSCLayer> group = new ArrayList<>();

    public BSCLayerGroup(BTSLayer BTSLayer) {
        group.add(new BSCLayer(BTSLayer));
    }

    public void addBSCLayer(){
        group.add(0, new BSCLayer(group.get(0)));
    }

    public BSCLayer getFirstBSCLayer(){
        return group.get(0);
    }
}
