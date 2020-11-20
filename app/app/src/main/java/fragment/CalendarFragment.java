package fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.renderscript.Sampler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ap.cheerupdiary.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javax.security.auth.callback.Callback;

import activity.DateActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {

    private View v;

    Button btn;

    ListView scheduleList;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference myRef = database.getReference("Event/"+auth.getUid());
    valueEventListener val = new valueEventListener();
    int manyofdate;


    // 나중에 일정클릭시 schedule list 들만 가져와서 string 형으로 저장
    String scheduleLStringList[] = {""};
    Vector<String> vector = new Vector<String>();

    String selectday = new String("");

    MaterialCalendarView calendar;

    public CalendarFragment() {
        // Required empty public constructor

    }

    @Override
    public void onResume() {
        super.onResume();
        calendar = v.findViewById(R.id.calendarView);;

        calendar.addDecorator(new SundayDecorator());
        calendar.addDecorator(new SaturdayDecorator());
        calendar.addDecorator(new OneDateDecorator());
        calendar.addDecorator(new EventDecorator(Color.RED));
        onDateChangeListener dayListen = new onDateChangeListener();
        calendar.setOnDateChangedListener(dayListen);

        btn = v.findViewById(R.id.showBtn);
        ShowButtonListener show = new ShowButtonListener();
        btn.setOnClickListener(show);

        // adapter 설정
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, scheduleLStringList) ;
        scheduleList = (ListView) v.findViewById(R.id.scheduleList);
        scheduleList.setAdapter(adapter);

        setOnItemClickListener listListen = new setOnItemClickListener();
        scheduleList.setOnItemClickListener(listListen);

        inItEventListener init = new inItEventListener();
        myRef.child("manyofdata").addListenerForSingleValueEvent(init);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_calendar, container,false);


        return v;
        //return inflater.inflate(R.layout.fragment_calendar, container, false);
    }


    // 일정 목록 보기위한 listener
    class ShowButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            Object objArr[] = vector.toArray();
            scheduleLStringList = Arrays.copyOf(objArr,
                    objArr.length,
                    String[].class);
            ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, scheduleLStringList);
            scheduleList.setAdapter(adapter);
        }
    }

    // schedule list listener
    class setOnItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Toast.makeText(getContext(), vector.toString(),Toast.LENGTH_SHORT).show();


        }
    }

    // calendar select Listener
    class onDateChangeListener implements OnDateSelectedListener{
        @Override
        public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
            vector.clear();
            //Toast.makeText(getContext(), "해당 날짜를 찾습니다",Toast.LENGTH_SHORT).show();
            int temp = date.getYear();
            selectday  = "";
            selectday += Integer.toString(temp) + "-";
            temp = date.getMonth();
            selectday += Integer.toString(temp+1) + "-";
            temp = date.getDay();
            selectday += Integer.toString(temp);

            for(int i = 0; i < manyofdate; i++){
                String index = new String(Integer.toString(i+1));
                myRef = database.getReference("Event/"+auth.getUid());
                myRef.child(index).child("data/date").addListenerForSingleValueEvent(val);
            }

        }
    }

    // 해당하는 날짜 찾는 listener
    class valueEventListener implements ValueEventListener{
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            String result = new String(snapshot.getValue().toString());
            if(selectday.equals(result)){
                //Toast.makeText(getContext(),  snapshot.getRef().getParent().getParent().getKey().toString(),Toast.LENGTH_SHORT).show();
                insertListener insert = new insertListener();
                snapshot.getRef().getParent().child("title").addListenerForSingleValueEvent(insert);
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }

    class insertListener implements  ValueEventListener{
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            //Toast.makeText(getContext(), snapshot.getValue().toString(),Toast.LENGTH_SHORT).show();
            vector.add(snapshot.getValue().toString());
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }

    class inItEventListener implements ValueEventListener{
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.getValue() != null){
                manyofdate = Integer.parseInt(snapshot.getValue().toString());
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }

}
