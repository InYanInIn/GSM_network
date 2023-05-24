import java.util.ArrayList;

public class Decoder{
    public synchronized static SMS decodeSMS(String encodedSMS){

        int index = 0;

        long sender = 0;
        long receiver = 0;
        String message = "";

        // sender number
        int senderNumLength = Integer.parseInt(encodedSMS.substring(0,2), 16)*2;
        index+=4;

        String senderNum="";
        for (int i = 0; i < senderNumLength-3; i+=2) {
            senderNum+=encodedSMS.charAt(i+1+index)+""+encodedSMS.charAt(i+index);
        }
        if (senderNum.charAt(senderNum.length()-1)=='F'){
            senderNum=senderNum.substring(0,senderNum.length()-1);
        }

        sender = Long.parseLong(senderNum);
        index+=senderNumLength;

        // receiver number
        int receiverNumLength = Integer.parseInt(encodedSMS.substring(index,index+2), 16);
        index+=4;

        String receiverNum="";
        for (int i = 0; i < receiverNumLength; i+=2) {
            receiverNum+=encodedSMS.charAt(i+1+index)+""+encodedSMS.charAt(i+index);
        }
        if (receiverNum.charAt(receiverNum.length()-1)=='F'){
            receiverNum=receiverNum.substring(0,receiverNum.length()-1);
        }

        receiver = Long.parseLong(receiverNum);
        index+=receiverNumLength+1;

        //Skipping zeros
        index+=18;

        int charCount = Integer.parseInt(encodedSMS.substring(index,index+2), 16);
        index+=2;

        ArrayList<Integer> smsIntArr = new ArrayList<>();

        for (int i = 0; i < charCount - charCount/8; i++) {
            smsIntArr.add(Integer.parseInt(encodedSMS.substring(index+i*2,index+2+i*2), 16));
        }

        ArrayList<Integer> smsCharArr = new ArrayList<>();

//        smsCharArr.add(smsIntArr.get(0)&0b01111111);
//        int j = 0;
//        for (int i = 1; i < smsIntArr.size(); i++) {
//            int firstHalf = smsIntArr.get(i-j-1)>>(8-i%8);
//            int secondHalf = (smsIntArr.get(i-j)<<(i%8))&0b01111111;
//            smsCharArr.add(firstHalf|secondHalf);
//            if ((i+1)%7==0) {
//                smsCharArr.add((smsIntArr.get(i - j) & 0b11111110)>>1);
//                j++;
//            }
//        }
        for (int i = 0; i < smsIntArr.size(); i+=7) {
            smsCharArr.add(smsIntArr.get(i)&0b01111111);

            for (int j = 1; j <= 7 && i+j < smsIntArr.size(); j++) {
                int firstHalf = smsIntArr.get(i+j-1)>>(8-j);
                int secondHalf = (smsIntArr.get(i+j)<<(j))&0b01111111;
                smsCharArr.add(firstHalf|secondHalf);
            }
//
            if(i+6 < smsIntArr.size() && i+7>=smsIntArr.size())
                smsCharArr.add((smsIntArr.get(i+6)&0b11111110)>>1);
        }

        for (int i = 0; i < smsCharArr.size(); i++) {
            int val = smsCharArr.get(i);
            message+=(char)val;
        }

        return new SMS(message, sender, receiver);
    }

}