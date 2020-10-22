package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ap.cheerupdiary.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import data.ScheduleData;
import fragment.DatePickerFragment;



public class DateActivity extends AppCompatActivity {
    Button saveBtn;
    Button dateBtn;

    public String month;
    public String day;
    public String year;

    EditText title;
    EditText desc;
    int type;

    int manyOfData;

    // 일정 종류
    Spinner kind;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Event/" + auth.getUid());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        //saveBtn : SaveButton, dateBtn : DateSelectButton
        saveBtn = findViewById(R.id.btnSave);
        dateBtn = findViewById(R.id.btnDate);

        SettingSpinner();

        //title data and description data
        title = findViewById(R.id.titleEditText);
        desc = findViewById(R.id.descEditText);

        //SaveButton Click Event
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Map<String, ScheduleData> m = new HashMap<>();

                //FCM Token Init
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task) {
                                if (!task.isSuccessful()) {
                                    Log.w("FCM", "getInstanceId failed", task.getException());
                                    return;
                                }
                                // Get new Instance ID token
                                String token = task.getResult();
                                FCMService fcmService = new FCMService();
                                fcmService.onNewToken(token);
                            }
                        });

                myRef.child("manyofdata").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //First Event Create
                       if(snapshot.getValue() == null){
                            manyOfData = 1;
                            m.put("data", getData());
                            //Toast.makeText(getApplicationContext(), Integer.toString(manyOfData),Toast.LENGTH_SHORT).show();
                            myRef.child(Integer.toString(manyOfData)).setValue(m);
                            myRef.child("manyofdata").setValue(manyOfData);
                        }
                        //used
                        else{
                        manyOfData = Integer.parseInt(snapshot.getValue().toString()) + 1;
                        m.put("data", getData());
                        //Toast.makeText(getApplicationContext(), Integer.toString(manyOfData),Toast.LENGTH_SHORT).show();
                        myRef.child(Integer.toString(manyOfData)).setValue(m);
                        myRef.child("manyofdata").setValue(manyOfData);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                finish();
            }
        });

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(view);
            }
        });
    }
//
//    String getDate(){
//        long now = System.currentTimeMillis();
//        Date date = new Date(now);
//        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
//        String time = mFormat.format(date);
//        String result = time;
//        return result;
//    }

    ScheduleData getData(){
        ScheduleData result = new ScheduleData("","","",1);
        result.title = this.title.getText().toString();
        result.description = this.desc.getText().toString();
        result.scheduleType=type;
        result.date = year + "-" + month + "-" + day;

        return result;
    }

    void checkManyOfData(String many){
        this.manyOfData = Integer.parseInt(many) + 1;
       // Toast.makeText(getApplicationContext(), many,Toast.LENGTH_SHORT).show();
    }

    // datePicker를 보여주는 함수
    void showDatePicker(View view){
        DialogFragment frag = new DatePickerFragment();
        frag.show(getSupportFragmentManager(),"datePicker");
    }

    @SuppressLint("RestrictedApi")
    public void datePickerResult(int year, int month, int day){
        this.year = Integer.toString(year);
        this.month = Integer.toString(month+1);
        this.day = Integer.toString(day);


    }

    // spinner 타입 종류를 지역변수에 mapping
    void setType(int pos){
        this.type=pos;
    }

    void SettingSpinner(){
        kind = findViewById(R.id.kindSpinner);
        kind.setPrompt("일정을 선택하세요");

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.kind, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kind.setAdapter(adapter);

        kind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
