import java.io.*;
import java.util.ArrayList;

public class SMSSaver {
    public static void saveInfo(VBDLayer vbdLayer){
        ArrayList<VBD> senders = vbdLayer.getSenders();

        String file = "sentSMS.bin";

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (VBD sender : senders){
                bw.write(sender.getNumber() + " : " + sender.getSentSMS()+"\n");
                bw.write(sender.getMessageText()+"\n\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
