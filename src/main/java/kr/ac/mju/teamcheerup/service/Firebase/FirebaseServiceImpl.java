package kr.ac.mju.teamcheerup.service.Firebase;

import kr.ac.mju.teamcheerup.modle.Event;
import kr.ac.mju.teamcheerup.modle.Message;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FirebaseServiceImpl implements FirebaseService{
    //총 메시지 개수
    private int messageCount = 6;
    final String BASE_URL = "https://cheerupdiary.firebaseio.com/";

    @Override
    public Message getMesseage(int event) {
        Message msg = null;
        Random random = new Random();
        //이벤트 종류에 따라 메시지를 가져오도록 변경 예정(현재 메시지개수 부족)
        try {
            //시험 0, 생일 1, 대회 2, 기타 3
            switch(event){
                case 0:
                    break;
                case 1:
                case 2:
                    event += 2;
                    break;
                default:
                    return null;
            }
            //Firebase접근을 위한 객체(Firebase4j 이용)
            Firebase firebase = new Firebase(BASE_URL +"Message/Message"+(random.nextInt(1) + event));
            //주소에 해당하는 데이터값을 가져온다
            FirebaseResponse response = firebase.get();
            //성공적으로 통신했을경우
            if(response.getSuccess()){
                /*
                    Json형태의 String데이터를 분리
                    "{ key=data,key=data }" 형태를
                    String[0] = "key1=data1", String[1] = "key2=data2" 로 분리
                    이후 "="로 문자열을 분리하면 String[1]의 값이 Data값
                 */
                String[] temp = (response.getBody().toString()).replace("{","").replace("}","").split(",");
                msg = Message.builder()
                        .notification(Message.Notification.builder()
                                .title((temp[2].split("=")[1]))
                                .image((temp[1].split("=")[1]))
                                .body((temp[0].split("=")[1]))
                                .build())
                        .token(null) //이후 메시지 전송시 토큰값을 넣어준다.
                        .build();
            }
        }
        catch (FirebaseException e){ //FirebaseException 처리(Firebase 생성자에서 필요)
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e){ //UnsupportedEncodingException 처리(Firebase.get()에서 필요)
            e.printStackTrace();
        }
        return msg;
    }

    @Override
    public List<Event> getEvent(String key) {
        //이벤트 목록을 배열로 반환
        List<Event> events = new ArrayList<>();
        try {
            //이벤트를 가져올 유저를 baseUrl지정
            Firebase firebase = new Firebase(BASE_URL + "Event/"+key);
            //이벤트ID코드순으로 반복 GET
            int index = 1;
            FirebaseResponse response = firebase.get(index+"/data/");
            //데이터가 없다면 rawBody가 문자열null이 반환됨
            while(!(response.getRawBody().equals("null"))){
                //데이터 삽입과정
                String[] temp = (response.getBody().toString()).replace("{","").replace("}","").split(",");
                //Array에 저장
                events.add(Event.builder()
                        .event(Integer.parseInt((temp[2].split("="))[1]))
                        //LocalDateTime.of와 Integer.parseInt를 통해 String > int > LocalDateTime으로 변환하여 저장
                        .dateTime(LocalDateTime.of(Integer.parseInt(((temp[0].split("="))[1]).split("-")[0]), //year
                                Integer.parseInt(((temp[0].split("="))[1]).split("-")[1]), //month
                                Integer.parseInt(((temp[0].split("="))[1]).split("-")[2]), //day
                                Integer.parseInt(((temp[3].split("="))[1]).split(":")[0]), //hour
                                Integer.parseInt(((temp[3].split("="))[1]).split(":")[1]), //min
                                0, //sec
                                0)) //nanosec
                        .data((temp[1].split("="))[1])
                        .title((temp[4].split("="))[1])
                        .build());
                index++;
                response = firebase.get(index+"/data/");
            }
        }
        catch(FirebaseException e){ //FirebaseException 처리(Firebase 생성자에서 필요)
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e){ //UnsupportedEncodingException 처리(Firebase.get()에서 필요)

            e.printStackTrace();
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }

        return events;
    }

    @Override
    public List<String> getAllUser() {
        List<String> userList = new ArrayList<>();
        try {
            //UID가 저장된 트리 선택
            Firebase firebase = new Firebase(BASE_URL + "UID/");
            //전체 목록을 가져옴
            FirebaseResponse response = firebase.get();
            if(response.getSuccess()){
                String[] temp = response.getBody().toString().replace("{","").replace("}","").replace(" ","").split(",");
                //가져온 목록을 List로 저장
                for(int i = 0;i<temp.length;i++){
                    userList.add(temp[i].split("=")[0]);
                }
            }
        }
        catch (FirebaseException e){ //FirebaseException 처리(Firebase 생성자에서 필요)
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e){ //UnsupportedEncodingException 처리(Firebase.get()에서 필요)
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void insertMessage(Message msg) {
        try {
            Firebase firebase = new Firebase(BASE_URL + "Message/Message"+messageCount);
            HashMap<String, Object> dataMap = new LinkedHashMap<String, Object>();
            dataMap.put("제목",msg.getNotification().getTitle());
            dataMap.put("내용",msg.getNotification().getBody());
            dataMap.put("이미지",msg.getNotification().getImage());
            FirebaseResponse response = firebase.put(dataMap);
            messageCount++;
        }
        catch (FirebaseException e){
            e.printStackTrace();
        } catch (JacksonUtilityException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFCMToken(String token){
        try {
            Firebase firebase = new Firebase(BASE_URL + "FCM/" + token);
            FirebaseResponse response = firebase.get();
            if(response.getSuccess()){
                String fcmToken = response.getRawBody().replace("\"","");
                return fcmToken;
            }
        }
        catch (FirebaseException e){
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }
}
