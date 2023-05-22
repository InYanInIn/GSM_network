import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MiddlePanel extends JPanel implements Runnable {

    BTSLayer senderBTSLayer;
    BTSLayer receiverBTSLayer;
    BSCLayerGroup bscLayerGroup;
    JScrollPane scrollPaneLeft;
    JScrollPane scrollPaneRight;
    JScrollPane middleScrollPane;
    JPanel bscGroup;

        public MiddlePanel(BTSLayer senderBTSLayer, BTSLayer receiverBTSLayer, BSCLayerGroup bscLayerGroup) {
            this.senderBTSLayer = senderBTSLayer;
            this.receiverBTSLayer = receiverBTSLayer;
            this.bscLayerGroup = bscLayerGroup;

            setPreferredSize(new Dimension(648, 720));
            setLayout(new BorderLayout());

            JButton createBSCLayer = new JButton("Create new BSC Layer");
            createBSCLayer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    bscLayerGroup.addBSCLayer();
                    senderBTSLayer.refreshBTSes(bscLayerGroup.getFirstBSCLayer());
                    repaintScrollPane();
                }
            });

            JButton deleteBSCLayer = new JButton("Delete BSC Layer");
            deleteBSCLayer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    bscLayerGroup.removeBSCLayer();
                    senderBTSLayer.refreshBTSes(bscLayerGroup.getFirstBSCLayer());
                    repaintScrollPane();
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout());
            buttonPanel.add(createBSCLayer, BorderLayout.WEST);
            buttonPanel.add(deleteBSCLayer, BorderLayout.EAST);

            scrollPaneLeft = new JScrollPane(getBTSPanels(senderBTSLayer));
            scrollPaneLeft.setPreferredSize(new Dimension(180, 650));

            scrollPaneRight = new JScrollPane(getBTSPanels(receiverBTSLayer));
            scrollPaneRight.setPreferredSize(new Dimension(180, 650));

            bscGroup = new JPanel(new GridLayout(1, 0));



            // Create a scroll pane for the bscGroup
            JScrollPane bscGroupScrollPane = new JScrollPane(bscGroup);
            bscGroupScrollPane.setPreferredSize(new Dimension(1000, 650));

            JPanel contentPanel = new JPanel(new GridBagLayout());

            // Left Scroll Pane
            contentPanel.add(scrollPaneLeft);

            // Middle Scroll Pane
            contentPanel.add(bscGroupScrollPane);

            // Right Scroll Pane
            contentPanel.add(scrollPaneRight);

            middleScrollPane = new JScrollPane(contentPanel);
            add(middleScrollPane, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);

            new Thread(this).start();
        }

    public synchronized JPanel getBSCPanels(BSCLayer bscLayer){
        ArrayList<JPanel> panels = createBSCPanels(bscLayer);
        JPanel contentPanel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        contentPanel.setLayout(gridBagLayout);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST; // Align components to the left within their cells

        for (int i = 0; i < panels.size(); i++) {
            JPanel panel = panels.get(i);
            constraints.gridy = i; // Set the row index for each component
            contentPanel.add(panel, constraints);
        }

        return contentPanel;
    }

    public synchronized JPanel getBTSPanels(BTSLayer btsLayer){
        ArrayList<JPanel> panels = createBTSPanels(btsLayer);
        JPanel contentPanel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        contentPanel.setLayout(gridBagLayout);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST; // Align components to the left within their cells

        for (int i = 0; i < panels.size(); i++) {
            JPanel panel = panels.get(i);
            constraints.gridy = i; // Set the row index for each component
            contentPanel.add(panel, constraints);
        }

        return contentPanel;
    }

    public synchronized ArrayList<JPanel> createBSCPanels(BSCLayer bscLayer){
        ArrayList<BSC> bscList = new ArrayList<>(bscLayer.getBscs());
        ArrayList<JPanel> panels = new ArrayList<>();
        for (BSC bsc : bscList){
            JPanel temp = new JPanel();
            temp.setPreferredSize(new Dimension(150, 60));
            temp.setBackground(Color.WHITE);
            temp.setLayout(new GridLayout(0, 2));

            JTextField btsUniqueNumber = new JTextField();
            btsUniqueNumber.setText("Number:");
            btsUniqueNumber.setEditable(false);
            JTextField numberText = new JTextField();
            numberText.setEditable(false);
            numberText.setText(String.valueOf(bsc.getID()));

            JButton terminateButton = new JButton();
            terminateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    bscLayer.removeBSC(bsc);
                    repaintScrollPane();
                }
            });
            terminateButton.setText("Terminate");
            JPanel condition = new JPanel();
            Color colorCondition;
            if (bsc.getMessagesAmount() < 3)
                colorCondition = Color.GREEN;
            else if(bsc.getMessagesAmount() < 5)
                colorCondition = Color.ORANGE;
            else
                colorCondition = Color.RED;

            condition.setBackground(colorCondition);


            temp.add(btsUniqueNumber);
            temp.add(numberText);
            temp.add(terminateButton);
            temp.add(condition);

            panels.add(temp);
        }
        return panels;
    }
    public synchronized ArrayList<JPanel> createBTSPanels(BTSLayer btsLayer){
        ArrayList<BTS> btsList = new ArrayList<>(btsLayer.getBts());
        ArrayList<JPanel> panels = new ArrayList<>();
        for (BTS bts : btsList){
            JPanel temp = new JPanel();
            temp.setPreferredSize(new Dimension(200, 60));
            temp.setBackground(Color.WHITE);
            temp.setLayout(new GridLayout(0, 2));

            JTextField btsUniqueNumber = new JTextField();
            btsUniqueNumber.setText("Number:");
            btsUniqueNumber.setEditable(false);
            JTextField numberText = new JTextField();
            numberText.setEditable(false);
            numberText.setText(String.valueOf(bts.getID()));

            JButton terminateButton = new JButton();
            terminateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    btsLayer.removeBTS(bts);
                    repaintScrollPane();
                }
            });
            terminateButton.setText("Terminate");
            JPanel condition = new JPanel();
            Color colorCondition;
            if (bts.getMessagesAmount() < 2)
                colorCondition = Color.GREEN;
            else if(bts.getMessagesAmount() < 4)
                colorCondition = Color.yellow;
            else if(bts.getMessagesAmount() < 5)
                colorCondition = Color.ORANGE;
            else
                colorCondition = Color.RED;

            condition.setBackground(colorCondition);

            temp.add(btsUniqueNumber);
            temp.add(numberText);
            temp.add(terminateButton);
            temp.add(condition);

            panels.add(temp);
        }
        return panels;
    }

    public synchronized void repaintScrollPane(){
        scrollPaneLeft.setViewportView(getBTSPanels(senderBTSLayer));
        scrollPaneLeft.revalidate();
        scrollPaneLeft.repaint();

        scrollPaneRight.setViewportView(getBTSPanels(receiverBTSLayer));
        scrollPaneRight.revalidate();
        scrollPaneRight.repaint();
        // Clear existing BSC Layer panels
        bscGroup.removeAll();

        // Add new BSC Layer panels
        for (int i = 0; i < bscLayerGroup.getGroup().size(); i++) {
            BSCLayer bscLayer = bscLayerGroup.getElementByIndex(i);
            JScrollPane bscLayerScrollPane = new JScrollPane(getBSCPanels(bscLayer));
            bscLayerScrollPane.setPreferredSize(new Dimension(175, 150));
            bscGroup.add(bscLayerScrollPane);
        }

        // Refresh the bscGroup panel
        bscGroup.revalidate();
        bscGroup.repaint();

        // Repaint all scroll panes within bscGroup
        Component[] components = bscGroup.getComponents();
        for (Component component : components) {
            if (component instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) component;
                scrollPane.getViewport().getView().revalidate();
                scrollPane.getViewport().getView().repaint();
            }
        }
    }
    @Override
    public void run() {
        while (true) {

            repaintScrollPane();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
