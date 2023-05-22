import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class LeftPanel extends JPanel {
    JScrollPane scrollPane;

    VBDLayer senders;
    public LeftPanel(VBDLayer senders) {
        this.senders = senders;

        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(204, 720));



        scrollPane = new JScrollPane(getSenderPanels());
        // Set the content panel as the viewport view

        scrollPane.setPreferredSize(new Dimension(180, 650));


        JButton button = new JButton("Add Sender");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = JOptionPane.showInputDialog("Enter message:");
                senders.addSender(2500, new Random().nextInt(8999999)+1000000, message);
                scrollPane.setVisible(false);

                repaintScrollPane();

            }
        });
        button.setPreferredSize(new Dimension(200, 55));

        add(scrollPane, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
    }
    public ArrayList<JPanel> createSenderPanels(){
        ArrayList<JPanel> panels = new ArrayList<>();
        for (VBD sender : senders.getSenders()){
            JPanel temp = new JPanel();
            temp.setPreferredSize(new Dimension(200, 80));
            temp.setBackground(Color.WHITE);
            temp.setLayout(new GridLayout(0, 2));

            JTextField deviceNumberText = new JTextField();
            deviceNumberText.setText("Number:");
            deviceNumberText.setEditable(false);
            JTextField numberText = new JTextField();
            numberText.setEditable(false);
            numberText.setText(String.valueOf(sender.getNumber()));

            JButton terminateButton = new JButton();
            terminateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    senders.removeSender(sender);
                    repaintScrollPane();
                }
            });
            terminateButton.setText("Terminate");
            JTextField messageText = new JTextField();
            messageText.setText(sender.getMessageText());
            messageText.setEditable(false);

            JTextField frequencyText = new JTextField();
            frequencyText.setEditable(false);
            frequencyText.setText("Frequency:");
            JSlider frequencySlider = new JSlider(1000, 5000);
            frequencySlider.setValue((int) sender.getFrequency());
            frequencySlider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    sender.setFrequency(frequencySlider.getValue());
                }
            });

            JTextField stateText = new JTextField();
            stateText.setEditable(false);
            stateText.setText("State:");
            JComboBox activeBox = new JComboBox<>(new String[]{"ACTIVE", "WAITING"});
            activeBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if ((((JComboBox) e.getSource()).getSelectedItem()).equals("WAITING")) {
                        sender.setActive(false);
                    }
                    else{
                        sender.setActive(true);
                    }
                }
            });

            temp.add(deviceNumberText);
            temp.add(numberText);
            temp.add(terminateButton);
            temp.add(messageText);
            temp.add(frequencyText);
            temp.add(frequencySlider);
            temp.add(stateText);
            temp.add(activeBox);

            panels.add(temp);
        }
        return panels;
    }
    public JPanel getSenderPanels(){
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
        ArrayList<JPanel> panels = createSenderPanels();
        JPanel contentPanel = (JPanel) scrollPane.getViewport().getView();
        contentPanel.removeAll();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;

        for (int i = 0; i < panels.size(); i++) {
            JPanel panel = panels.get(i);
            constraints.gridy = i;
            contentPanel.add(panel, constraints);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
        scrollPane.setVisible(true);
    }
}
