package fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ap.cheerupdiary.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class EventDecorator implements DayViewDecorator {
    private int color;
    int manyofdata;
    String today;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference myRef = database.getReference("Event/"+auth.getUid());
    final Vector<String> list = new Vector<String>();

    public interface ManyOfDataListener{
        void onDataLoaded(String data);
    }

    public EventDecorator(String date) {
        this.color = Color.RED;
        this.manyofdata = manyofdata;
        this.today = date;
        Log.e("startvector","asd");
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {

        String date = "";
        date += Integer.toString(day.getYear()) + "-" +
                Integer.toString(day.getMonth()+1) + "-" +
                Integer.toString(day.getDay());

        //myRef.addValueEventListener(new printListener());
        if(today.equals(date))
            return true;

        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        //view.setSelectionDrawable(drawable);
        //

        view.addSpan(new DotSpan(5, color)); // 날자밑에 점
    }


}
