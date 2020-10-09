package kr.ac.mju.teamcheerup.controller;

import kr.ac.mju.teamcheerup.service.Firebase.FCMService;
import kr.ac.mju.teamcheerup.service.Firebase.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FCMController {
    //FCM메시징을 위한 Service
    @Autowired
    FCMService fcmService;

    //데이터베이스 접근을 위한 서비스
    @Autowired
    FirebaseService firebaseService;

    //요청받는 이벤트의 종류와 보낼 사용자의 토큰값을 받아온다.
    @GetMapping("/send/{token}/{event}")
    public void sendMessage(@PathVariable String token,@PathVariable int event){
        try {
            //메시지 전송 시도
            fcmService.sendMessageTo(token, firebaseService.getMesseage(event));
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    //Service테스트용
    @GetMapping("/test")
    public void test(){
        firebaseService.getAllUser();
    }
}
