import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Graphics extends JFrame {
    public Graphics(VRDLayer receivers, BTSLayer btsReceivers, BSCLayerGroup bscGroup , BTSLayer btsSenders, VBDLayer senders){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SMSSaver.saveInfo(senders);
                System.exit(0);
            }
        });
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
