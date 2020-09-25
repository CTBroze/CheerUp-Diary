package kr.ac.mju.teamcheerup.service.Firebase;

import kr.ac.mju.teamcheerup.modle.Message;

import java.io.IOException;

public interface FCMService {
    void sendMessageTo(String targetToken, Message msg) throws IOException;
}
