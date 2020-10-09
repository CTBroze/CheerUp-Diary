package kr.ac.mju.teamcheerup.service.Firebase;

import jdk.dynalink.NamedOperation;
import kr.ac.mju.teamcheerup.modle.Event;
import kr.ac.mju.teamcheerup.modle.Message;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

@Service
public class FirebaseServiceImpl implements FirebaseService{
    final int messageCount = 1;

    @Override
    public Message getMesseage(int event) {
        Message msg = null;
        Random random = new Random();
        try {
            Firebase firebase = new Firebase("https://cheerupdiary.firebaseio.com/Message/Message"+(random.nextInt(messageCount+1)));
            FirebaseResponse response = firebase.get();
            if(response.getSuccess()){
                String[] temp = (response.getBody().toString()).replace("{","").replace("}","").split(",");
                msg = Message.builder()
                        .notification(Message.Notification.builder()
                                .title((temp[2].split("=")[1]))
                                .image((temp[1].split("=")[1]))
                                .body((temp[0].split("=")[1]))
                                .build())
                        .token(null)
                        .build();
            }
        }
        catch (FirebaseException e){
            System.out.println(e.getMessage());
        }
        catch (UnsupportedEncodingException e){
            System.out.println(e.getMessage());
        }

        return msg;
    }

    @Override
    public void insertMessage(Message msg) {

    }
    @Override
    public Event getEvent(String key) {
        return null;
    }

    @Override
    public List<String> getAllUser() {
        return null;
    }
}
