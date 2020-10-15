package kr.ac.mju.teamcheerup.service.Firebase;

import kr.ac.mju.teamcheerup.modle.Event;
import kr.ac.mju.teamcheerup.modle.Message;

import java.util.List;

public interface FirebaseService {
    //이벤트에 따른 메시지 반환
    Message getMesseage(int event);

    //key값에 해당하는 유저의 이벤트에서 이벤트 목록 전달
    List<Event> getEvent(String key);

    //모든 유저의 key값을 list에 담아 전달
    List<String> getAllUser();
    void insertMessage(Message msg);
}
