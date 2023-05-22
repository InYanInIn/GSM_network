import java.util.ArrayList;

public abstract class Station implements Runnable{

    private static int GENERALID = 0;
    volatile private int ID = 0;

    volatile ArrayList<SMS> messages = new ArrayList<>();


    public Station() {
        this.ID = GENERALID++;
    }

    public synchronized int getID() {
        return ID;
    }

    public synchronized int getMessagesAmount(){
        return messages.size();
    }

    public synchronized void addMessage(SMS message){
        messages.add(message);
    }



}
