package fragment;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.ap.cheerupdiary.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {
    //private final Drawable drawable;
    private int color;
    private CalendarDay date;

    public EventDecorator(int color) {
        //drawable = context.getResources().getDrawable(R.drawable.more);
        this.color = color;
        //this.dates = new HashSet<>(dates);
        date = CalendarDay.today();
    }


    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        //view.setSelectionDrawable(drawable);
        view.addSpan(new DotSpan(5, color));
    }
}
