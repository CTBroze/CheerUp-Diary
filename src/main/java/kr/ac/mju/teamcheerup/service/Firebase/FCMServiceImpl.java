package kr.ac.mju.teamcheerup.service.Firebase;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.mju.teamcheerup.FirebaseInitialize;
import kr.ac.mju.teamcheerup.modle.FCMMessage;
import kr.ac.mju.teamcheerup.modle.Message;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.codehaus.jackson.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Service
public class FCMServiceImpl implements FCMService{
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/cheerupdiary/messages:send";
    private final ObjectMapper objectMapper;
    private final FirebaseInitialize firebaseInitialize;

    @Override
    public void sendMessageTo(String targetToken, Message msg) throws IOException {
        String message = makeMessage(targetToken, msg);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer" + firebaseInitialize.getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());
    }

    private String makeMessage(String targetToken, Message message) throws JsonProcessingException, com.fasterxml.jackson.core.JsonProcessingException {
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

        return objectMapper.writeValueAsString(fcmMessage);
    }
}
