package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ap.cheerupdiary.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import fragment.CalendarFragment;
import fragment.ScheduleListFragment;
import fragment.SettingFragment;

public class MainMenu extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    CalendarFragment calendarFrag;
    ScheduleListFragment scheduleListFrag;
    SettingFragment settingFrag;

    // 메뉴 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // 액션바 메뉴 클릭시 이벤트 처리


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.addDateBtn){
            Intent intent = new Intent( this, DateActivity.class);
            startActivity(intent);
            calendarFrag.onDestroy();
            settingFrag.onDestroy();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main_menu);

        // make fragment
        calendarFrag = new fragment.CalendarFragment();
        scheduleListFrag = new ScheduleListFragment();
        settingFrag = new SettingFragment();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, settingFrag).commitAllowingStateLoss();

        // add listener for setting first view when user start
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                    case R.id.tab1: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, calendarFrag).commitAllowingStateLoss();

                        return true;
                    }
//                    case R.id.tab2: {
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, scheduleListFrag).commitAllowingStateLoss();
//                        return true;
//                    }
                    case R.id.tab3: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, settingFrag).commitAllowingStateLoss();

                        return true;
                    }
                    default:
                        return false;
                }
            }
        });

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_menu);
//
//        // make fragment
//        calendarFrag = new fragment.CalendarFragment();
//        scheduleListFrag = new ScheduleListFragment();
//        settingFrag = new SettingFragment();
//
//        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, calendarFrag).commitAllowingStateLoss();
//
//
//        // add listener for setting first view when user start
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
//                    case R.id.tab1: {
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, calendarFrag).commitAllowingStateLoss();
//                        onResume();
//                        return true;
//                    }
////                    case R.id.tab2: {
////                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, scheduleListFrag).commitAllowingStateLoss();
////                        return true;
////                    }
//                    case R.id.tab3: {
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, settingFrag).commitAllowingStateLoss();
//                        onResume();
//                        return true;
//                    }
//                    default:
//                        return false;
//                }
//            }
//        });
//
//    }
}
