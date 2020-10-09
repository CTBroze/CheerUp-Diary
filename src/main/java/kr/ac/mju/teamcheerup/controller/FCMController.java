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
    @Autowired
    FCMService fcmService;

    @Autowired
    FirebaseService firebaseService;

    @GetMapping("/send/{token}/{event}")
    public void sendMessage(@PathVariable String token,@PathVariable int event){
        try {
            fcmService.sendMessageTo(token, firebaseService.getMesseage(event));
        }
        catch (IOException e){

        }
    }

    @GetMapping("/test")
    public void test(){
        firebaseService.getAllUser();
    }
}
