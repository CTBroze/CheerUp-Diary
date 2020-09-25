package kr.ac.mju.teamcheerup.service.Firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Service
public class FirebaseInitialize {
    //serviceAccount파일 경로(.json)
    final private String  firebaseConfigPath = "./cheerupdiary-firebase-adminsdk-qyjmj-29a5cb9012.json";

    //데이터베이스 접근 인증
    @PostConstruct
    public void initialize(){
        try{
            FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://cheerupdiary.firebaseio.com/")
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    //토큰을 얻기 위한 코드
    public String getAccessToken() throws IOException {
        List<String> list = Arrays.asList("https://www.googleapis.com/auth/cloud-platform");
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(list);

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
