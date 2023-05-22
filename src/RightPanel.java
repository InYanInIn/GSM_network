import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class RightPanel extends JPanel implements Runnable{
    JScrollPane scrollPane;

    VRDLayer receivers;
    VBDLayer senders;
    public RightPanel(VRDLayer receivers, VBDLayer senders) {

        this.receivers = receivers;
        this.senders = senders;

        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(200, 720));



        scrollPane = new JScrollPane(getReceiverPanels()); // Set the content panel as the viewport view

        scrollPane.setPreferredSize(new Dimension(200, 650));


        JButton button = new JButton("Add Receiver");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receivers.addReceiver(new Random().nextInt(8000000)+1000000, senders);
                scrollPane.setVisible(false);

                repaintScrollPane();

            }
        });
        button.setPreferredSize(new Dimension(200, 55));

        add(scrollPane, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
        new Thread(this).start();
    }
    public ArrayList<JPanel> createSenderPanels(){
        ArrayList<JPanel> panels = new ArrayList<>();
        for (VRD receiver : receivers.getReceivers()){
            JPanel temp = new JPanel();
            temp.setPreferredSize(new Dimension(200, 60));
            temp.setBackground(Color.WHITE);
            temp.setLayout(new GridLayout(0, 2));

            JTextField deviceNumberText = new JTextField();
            deviceNumberText.setText("Number:");
            deviceNumberText.setEditable(false);
            JTextField numberText = new JTextField();
            numberText.setEditable(false);
            numberText.setText(String.valueOf(receiver.getNumber()));

            JButton terminateButton = new JButton();
            terminateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    receivers.removeReceiver(receiver);
                    repaintScrollPane();
                }
            });
            terminateButton.setText("Terminate");
            JLabel messageCount = new JLabel();
            messageCount.setText(String.valueOf(receiver.getReceivedMessages()));


            JTextField stateText = new JTextField();
            stateText.setEditable(false);
            stateText.setText("Remove messages:");
            JCheckBox activeBox = new JCheckBox();

            activeBox.addActionListener(e -> {
                if (activeBox.isSelected()){

                }
                else {

                }
            });

            temp.add(deviceNumberText);
            temp.add(numberText);
            temp.add(terminateButton);
            temp.add(messageCount);
            temp.add(stateText);
            temp.add(activeBox);

            panels.add(temp);
        }
        return panels;
    }
    public JPanel getReceiverPanels(){
        ArrayList<JPanel> panels = createSenderPanels();
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


    public void repaintScrollPane(){
        JViewport viewport = scrollPane.getViewport();

// Get the current view component of the viewport
        Component viewComponent = viewport.getView();

// Check if the view component is a JPanel
        if (viewComponent instanceof JPanel) {
            JPanel contentPanel = (JPanel) viewComponent;

            // Check if the content panel contains any components
            if (contentPanel.getComponentCount() > 0) {
                // Remove the first component from the content panel
                Component firstElement = contentPanel.getComponent(0);
                contentPanel.remove(firstElement);

                // Repaint the content panel
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        }
        scrollPane.getViewport().setView(getReceiverPanels());
        scrollPane.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        repaintScrollPane();
    }

    @Override
    public void run() {
        while (true) {
            repaintScrollPane();
            System.out.println("0000000000000000000000");
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


