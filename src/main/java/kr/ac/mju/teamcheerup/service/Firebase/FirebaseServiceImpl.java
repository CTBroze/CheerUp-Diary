package kr.ac.mju.teamcheerup.service.Firebase;

import kr.ac.mju.teamcheerup.modle.Event;
import kr.ac.mju.teamcheerup.modle.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirebaseServiceImpl implements FirebaseService{

    @Override
    public Message getMesseage() {
        return null;
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
