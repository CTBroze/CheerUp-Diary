package kr.ac.mju.teamcheerup;

import kr.ac.mju.teamcheerup.modle.Event;
import kr.ac.mju.teamcheerup.service.Firebase.FCMService;
import kr.ac.mju.teamcheerup.service.Firebase.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class CheckEvent {
    @Autowired
    FirebaseService firebaseService;

    @Autowired
    FCMService fcmService;

    //1분에 한번씩 검색한다.(60000ms / 1000 = 60s)
    @Scheduled(fixedRate = 60000)
    public void checkEvent(){
        LocalDateTime now = LocalDateTime.now();
        //유저목록 가져오기
        List<String> userToken = firebaseService.getAllUser();
        try {
            //Event검색
            for (String user : userToken) {
                List<Event> events = firebaseService.getEvent(user);
                for (Event event : events) {
                    if (event.getDateTime().getYear() == now.getYear()) {
                        if (event.getDateTime().getMonth() == now.getMonth()) {
                            if (event.getDateTime().getDayOfMonth() == now.getDayOfMonth()) {
                                if (event.getDateTime().getHour() == now.getHour()) {
                                    if (event.getDateTime().getMinute() == now.getMinute()) {
                                        //호출(바로 호출) 가능하면 HTTP Requset로 수정(localhost:8080/send/{userToken}/{eventCode})
                                        fcmService.sendMessageTo(user, firebaseService.getMesseage(eventClassification(event)));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private int eventClassification(Event event){
        //이벤트 분류
        if(event.getEvent().equals("생일")) return 1;
        if(event.getEvent().equals("시험")) return 2;
        //분류되지 않은 이벤트
        return 0;
    }
}
