package fragment;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.ap.cheerupdiary.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {
 //   private final Drawable drawable;
    private HashSet<CalendarDay> dates;



    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        //view.setSelectionDrawable();
    }
}
