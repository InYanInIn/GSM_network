import java.util.ArrayList;

public class Encoder {

    public synchronized static String encodeSMS(SMS sms){
        String sender = String.valueOf(sms.getSenderNumber());
        String receiver = String.valueOf(sms.getReceiverNumber());
        String message = sms.getMessage();

        String encodedSMS = "";
        String lengthOfServiceCenterAddress;
        String lengthOfOriginatingCenterAddress;
        int senderLength;
        int receiverLength;
        String serviceCenterAddress = "";
        String serviceOriginatingAddress = "";


        //The Service Center Address field
        senderLength = (sender.length()%2==1?sender.length()/2+1:sender.length()/2)+1;
        lengthOfServiceCenterAddress = Long.toHexString(senderLength).length()==1?
                "0"+Long.toHexString(senderLength).toUpperCase():
                Long.toHexString(senderLength).toUpperCase();

        encodedSMS+=lengthOfServiceCenterAddress;
        encodedSMS+="91";

        for (int i = 0; i < sender.length()-1; i+=2){
            serviceCenterAddress += sender.charAt(i+1) + "" + sender.charAt(i);
        }
        if (sender.length()%2==1){
            serviceCenterAddress+="F"+sender.charAt(sender.length()-1);
        }

        encodedSMS+=serviceCenterAddress;

        //The First Octet field
        encodedSMS+="01";

        //The Originating Address
        receiverLength = receiver.length();
        lengthOfOriginatingCenterAddress = Long.toHexString(receiverLength).length()==1?
                "0"+Long.toHexString(receiverLength).toUpperCase() :
                Long.toHexString(receiverLength).toUpperCase();

        encodedSMS+=lengthOfOriginatingCenterAddress;
        encodedSMS+="91";

        for (int i = 0; i < receiver.length()-1; i+=2){
            serviceOriginatingAddress += receiver.charAt(i+1) + "" + receiver.charAt(i);
        }
        if (receiver.length()%2==1){
            serviceOriginatingAddress+="F"+receiver.charAt(receiver.length()-1);
        }

        encodedSMS+=serviceOriginatingAddress;

        //The Protocol Identifier field
        encodedSMS+="00";
        //The Data Coding Scheme
        encodedSMS+="00";
        //The Date
        encodedSMS+="00000000000000";

        //The message
        String messageLength = Long.toHexString(message.length()).length()==1?
                "0"+ Long.toHexString(message.length()).toUpperCase():
                Long.toHexString(message.length()).toUpperCase();

        ArrayList<Integer> smsCharArr = new ArrayList<>();
//        int index = 0;
//        for (int i = 1; i < message.length(); i++) {
//            int prevChar = smsCharArr.get(i-1-index);
//            int test = ((message.charAt(i-index)+0) << (8 - (i%8)))&0b11111111;
//            prevChar += test;
//            smsCharArr.remove(i-1-index);
//            smsCharArr.add(i-1-index, prevChar);
//
//            if (i % 8 != 0) {
//                smsCharArr.add(
//                        (message.charAt(i-index) + 0) >> (i % 8)
//                );
//            }else{
//                index++;
//            }
//        }
        for (int i = 0; i < message.length(); i+=8) {
            smsCharArr.add(message.charAt(i)+0);

            for (int j = 1; j < 7 && i+j<message.length(); j++) {
                int prevChar = smsCharArr.get(smsCharArr.size()-1);
                int shiftingPart = ((message.charAt(j+i)+0) << (8 - (j%8)))&0b11111111;
                prevChar = prevChar | shiftingPart;
                smsCharArr.add(smsCharArr.size()-1, prevChar);
                smsCharArr.remove(smsCharArr.size()-1);


                smsCharArr.add((message.charAt(i+j) + 0) >> (j % 8));
            }
            if(i+7<message.length()) {
                smsCharArr.add(
                        smsCharArr.get(smsCharArr.size()-1) |
                                ((message.charAt(i + 7) + 0) << 1)
                );
                smsCharArr.remove(smsCharArr.size() - 2);
            }

        }

        String shiftedMessage = "";
        for (int i = 0; i < smsCharArr.size(); i++) {
            shiftedMessage+=Integer.toHexString(smsCharArr.get(i)).length()==1?
                    "0"+ Integer.toHexString(smsCharArr.get(i)).toUpperCase():
                    Integer.toHexString(smsCharArr.get(i)).toUpperCase();
        }

        encodedSMS+=messageLength;
        encodedSMS+=shiftedMessage;

        return encodedSMS;
    }

}