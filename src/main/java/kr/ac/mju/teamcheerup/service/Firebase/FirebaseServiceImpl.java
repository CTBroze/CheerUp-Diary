package kr.ac.mju.teamcheerup.service.Firebase;

import kr.ac.mju.teamcheerup.modle.Event;
import kr.ac.mju.teamcheerup.modle.Message;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class FirebaseServiceImpl implements FirebaseService{
    //총 메시지 개수
    final int messageCount = 1;

    @Override
    public Message getMesseage(int event) {
        Message msg = null;
        Random random = new Random();
        //이벤트 종류에 따라 메시지를 가져오도록 변경 예정(현재 메시지개수 부족)
        try {
            //Firebase접근을 위한 객체(Firebase4j 이용)
            Firebase firebase = new Firebase("https://cheerupdiary.firebaseio.com/Message/Message"+(random.nextInt(messageCount+1)));
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
            System.out.println(e.getMessage());
        }
        catch (UnsupportedEncodingException e){ //UnsupportedEncodingException 처리(Firebase.get()에서 필요)
            System.out.println(e.getMessage());
        }
        return msg;
    }

    @Override
    public List<Event> getEvent(String key) {
        //이벤트 목록을 배열로 반환
        List<Event> events = new ArrayList<>();
        try {
            Firebase firebase = new Firebase("https://cheerupdiary.firebaseio.com/Event/"+key+"/");
            int index = 0;
            FirebaseResponse response = firebase.get(index+"/");
            while(!(response.getRawBody().equals("null"))){
                //데이터 삽입과정
                String[] temp = (response.getBody().toString()).replace("{","").replace("}","").split(",");
                //Array에 저장
                events.add(Event.builder()
                        .event((temp[0].split("="))[1])
                        .dateTime(LocalDateTime.of(Integer.parseInt(((temp[1].split("="))[1]).split(" ")[0]), //year
                                Integer.parseInt(((temp[1].split("="))[1]).split(" ")[1]), //month
                                Integer.parseInt(((temp[1].split("="))[1]).split(" ")[2]), //day
                                Integer.parseInt(((temp[1].split("="))[1]).split(" ")[3]), //hour
                                Integer.parseInt(((temp[1].split("="))[1]).split(" ")[4]), //min
                                0, //sec
                                0)) //nanosec
                        .data((temp[2].split("="))[1])
                        .title((temp[3].split("="))[1])
                        .build());
                index++;
                response = firebase.get(index+"/");
            }
        }
        catch(FirebaseException e){ //FirebaseException 처리(Firebase 생성자에서 필요)
            System.out.println(e.getMessage());
        }
        catch (UnsupportedEncodingException e){ //UnsupportedEncodingException 처리(Firebase.get()에서 필요)

            System.out.println(e.getMessage());
        }
        catch (NumberFormatException e){
            System.out.println(e.getMessage());
        }

        return events;
    }

    @Override
    public List<String> getAllUser() {
        List<String> userList = null;
        try {
            //UID가 저장된 트리 선택
            Firebase firebase = new Firebase("https://cheerupdiary.firebaseio.com/UID/");
            //전체 목록을 가져옴
            FirebaseResponse response = firebase.get();
            if(response.getSuccess()){
                //가져온 목록을 List로 저장
                userList = Arrays.asList((response.getRawBody().toString()).replace("[","").replace("]","").split(","));
            }
        }
        catch (FirebaseException e){ //FirebaseException 처리(Firebase 생성자에서 필요)
            System.out.println(e.getMessage());
        }
        catch (UnsupportedEncodingException e){ //UnsupportedEncodingException 처리(Firebase.get()에서 필요)
            System.out.println(e.getMessage());
        }
        return userList;
    }

    @Override
    public void insertMessage(Message msg) {

    }
}
