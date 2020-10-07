package activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.ap.cheerupdiary.R;

import fragment.DatePickerFragment;

public class DateActivity extends AppCompatActivity {

    Button saveBtn;
    Button dateBtn;

    String month;
    String day;
    String year;

    // 일정 종류
    Spinner kind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        saveBtn = findViewById(R.id.btnSave);
        dateBtn = findViewById(R.id.btnDate);

        SettingSpinner();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    // datePicker를 보여주는 함수
    void showDatePicker(View view){
        DialogFragment frag = new DatePickerFragment();
        frag.show(getSupportFragmentManager(),"datePicker");
    }

    public void datePickerResult(int year, int month, int day){
        this.year = Integer.toString(year);
        this.month = Integer.toString(month+1);
        this.day = Integer.toString(day);

        Toast.makeText(this, "date = " + this.month + "/" + this.day,Toast.LENGTH_SHORT).show();
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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
