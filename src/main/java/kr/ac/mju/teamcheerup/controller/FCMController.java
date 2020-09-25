package kr.ac.mju.teamcheerup.controller;

import kr.ac.mju.teamcheerup.service.Firebase.FCMService;
import kr.ac.mju.teamcheerup.service.Firebase.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FCMController {
    @Autowired
    FCMService fcmService;
    FirebaseService firebaseService;




}
