package kr.ac.mju.teamcheerup.service.Firebase;

import kr.ac.mju.teamcheerup.modle.Event;
import kr.ac.mju.teamcheerup.modle.Message;

import java.util.List;

public interface FirebaseService {
    Message getMesseage();
    void insertMessage(Message msg);
    //key값에 해당하는 유저의 이벤트에서 이벤트를 찾아서 전달함(없을경우 null전달)
    Event getEvent(String key);
    //모든 유저의 key값을 list에 담아 전달
    List<String> getAllUser();
}
