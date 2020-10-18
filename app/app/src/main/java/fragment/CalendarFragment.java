package fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ap.cheerupdiary.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import activity.DateActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {

    private View v;

    Button btn;

    MaterialCalendarView calendar;
    ListView scheduleList;



    // 나중에 일정클릭시 schedule list 들만 가져와서 string 형으로 저장
    String scheduleLStringList[] = {"일정1", "일정2", "일정3"};


    public CalendarFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_calendar, container,false);

        calendar = v.findViewById(R.id.calendarView);
        calendar.addDecorator(new SundayDecorator());
        calendar.addDecorator(new SaturdayDecorator());
        calendar.addDecorator(new OneDateDecorator());



        // adapter 설정
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, scheduleLStringList) ;
        scheduleList = (ListView) v.findViewById(R.id.scheduleList);
        scheduleList.setAdapter(adapter);


        // click event
        scheduleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });



        // calendar 이벤트처리
        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                //String txt = calendar.getSelectedDate().getDate().toString();
                //Toast myToast = Toast.makeText(v.getContext(),txt, Toast.LENGTH_SHORT);
                //myToast.show();
            }
        });

//        btn = v.findViewById(R.id.testbtn);
//
//        btn.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent( getActivity(), DateActivity.class);
//                startActivity(intent);
//            }
//        });

        return v;
        //return inflater.inflate(R.layout.fragment_calendar, container, false);
    }
}
