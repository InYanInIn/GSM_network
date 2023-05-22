import javax.swing.*;
import java.awt.*;

public class Graphics extends JFrame {
    public Graphics(VRDLayer receivers, BTSLayer btsReceivers, BSCLayerGroup bscGroup , BTSLayer btsSenders, VBDLayer senders){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        LeftPanel leftPanel = new LeftPanel(senders);
        RightPanel rightPanel = new RightPanel(receivers, senders);
        MiddlePanel middlePanel = new MiddlePanel(btsSenders, btsReceivers, bscGroup);



        add(middlePanel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        setPreferredSize(new Dimension(1800, 800));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
