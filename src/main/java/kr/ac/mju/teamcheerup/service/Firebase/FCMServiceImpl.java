package kr.ac.mju.teamcheerup.service.Firebase;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.mju.teamcheerup.FirebaseInitialize;
import kr.ac.mju.teamcheerup.modle.FCMMessage;
import kr.ac.mju.teamcheerup.modle.Message;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Service
public class FCMServiceImpl implements FCMService{
    //API_URL과 사용할 객체들(RequiredArgsConstructor 이용)
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/cheerupdiary/messages:send";
    private final ObjectMapper objectMapper;
    private final FirebaseInitialize firebaseInitialize;

    @Autowired
    private final FirebaseService firebaseService;

    /*
        FirebaseMessage객체를 통한 전송이 아닌 HttpRequset를 보내는 형식으로 전달
        private메서드인 makeMessage를 이용한 메시지 제작
        OkHttpClient 라이브러리 이용하여 Request를 보낸다
        Request를 받은 FCM서버측은 Message에 포함된 Token의 유저에게 메시지를 보냄
     */
    @Override
    public void sendMessageTo(String targetToken, Message msg) throws IOException {
        //null 메시지면 반환(지정 이벤트가 아님)
        if(msg == null){
            return;
        }

        //FCM에서 사용하기 알맞은 JSON String형태로 만들어 저장
        String message = makeMessage(firebaseService.getFCMToken(targetToken), msg);

        //HTTP 통신을 위한 객체
        OkHttpClient client = new OkHttpClient();

        //Request
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + firebaseInitialize.getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();
        //Request를 보내고 결과를 response에 저장
        Response response = client.newCall(request).execute();

        //결과값 출력
        System.out.println(response.body().string());
    }

    private String makeMessage(String targetToken, Message message) throws com.fasterxml.jackson.core.JsonProcessingException { //Json 변환과정에서 예외가 발생가능
        //FCM에서 사용하는 메시지 형태로 전환
        FCMMessage fcmMessage = FCMMessage.builder()
                .message(Message.builder()
                        .token(targetToken)
                        .notification(Message.Notification.builder()
                                .title(message.getNotification().getTitle())
                                .body(message.getNotification().getBody())
                                .image(message.getNotification().getImage())
                                .build()
                        ).build()
                ).validate_only(false)
                .build();

        //Jackson의 ObjectMapper를 이용한 Json형태로 전환.
        return objectMapper.writeValueAsString(fcmMessage);
    }
}
