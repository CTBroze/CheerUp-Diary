package kr.ac.mju.teamcheerup.controller;

import kr.ac.mju.teamcheerup.CheckEvent;
import kr.ac.mju.teamcheerup.modle.Message;
import kr.ac.mju.teamcheerup.service.Firebase.FCMService;
import kr.ac.mju.teamcheerup.service.Firebase.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class FCMController {
    //FCM메시징을 위한 Service
    @Autowired
    FCMService fcmService;

    //데이터베이스 접근을 위한 서비스
    @Autowired
    FirebaseService firebaseService;

    @Autowired
    CheckEvent checkEvent;

    //요청받는 이벤트의 종류와 보낼 사용자의 토큰값을 받아온다.
    @PostMapping("/send/{token}/{event}")
    public void sendMessage(@PathVariable String token,@PathVariable int event){
        try {
            //메시지 전송 시도
            fcmService.sendMessageTo(token, firebaseService.getMesseage(event));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //GetMapping미사용시 해당 메서드가 호출되지 않음
    @PutMapping("/insert/{title}/{data}")
    public void insertMessage(@PathVariable String title,@PathVariable String data){
        //경로를 통해 받아온 제목과 내용을 Message로 변환하여 반환
        firebaseService.insertMessage(Message.builder()
                .token(null)
                .notification(Message.Notification.builder()
                        .title(title)
                        .body(data)
                        .image(null)
                        .build())
                .build());
    }

    //Service시작용
    @GetMapping("/start")
    public void scheduleStart(){
            checkEvent.checkEvent();
    }

    //ServiceTest
    @GetMapping("/test")
    public void test(){
        try {
            fcmService.sendMessageTo("OuQHCXo2ELbkrUwMzWfj9lmCHUm2", firebaseService.getMesseage(0));
            fcmService.sendMessageTo("OuQHCXo2ELbkrUwMzWfj9lmCHUm2", firebaseService.getMesseage(1));
            fcmService.sendMessageTo("OuQHCXo2ELbkrUwMzWfj9lmCHUm2", firebaseService.getMesseage(2));
            fcmService.sendMessageTo("OuQHCXo2ELbkrUwMzWfj9lmCHUm2", firebaseService.getMesseage(3));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
