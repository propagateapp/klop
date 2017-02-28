package klop.propagate.com.au.klop.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import klop.propagate.com.au.klop.Model.NewTotalGame;
import klop.propagate.com.au.klop.R;

/**
 * Created by trung on 14/12/2016.
 */
public class TotalGameAdapter extends BaseAdapter {

    private List<NewTotalGame> listdata;
    private Activity activity;
    public static final DateFormat FILE_DATE_FORMAT = new SimpleDateFormat("HHmmddMMyyyy");


    public TotalGameAdapter(List<NewTotalGame> listdata,Activity activity){
        this.activity = activity;
        this.listdata = listdata;
    }
    @Override
    public int getCount() {
        return listdata.size();
    }

    @Override
    public Object getItem(int i) {
        return listdata.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ObjectsHolder objectsHolder;
        if (view != null && view.getTag() != null) {
            objectsHolder = (ObjectsHolder) view.getTag();
        } else {
            objectsHolder = new ObjectsHolder();
            view = activity.getLayoutInflater().inflate(R.layout.item_list_totalgame, null);
            objectsHolder.tvStatus = (TextView) view.findViewById(R.id.tvstatus);
            objectsHolder.tvDate = (TextView) view.findViewById(R.id.tvdate);
            objectsHolder.tvtotalPlayers = (TextView) view.findViewById(R.id.tvtotalplayer);
        }

        view.setTag(objectsHolder);
        NewTotalGame newTotalGame = listdata.get(i);
        Date start = null, end = null;
        try {
            start = FILE_DATE_FORMAT.parse(newTotalGame.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date = new Date();
        if (newTotalGame.getStatus().equals("In Progress")){
                view.setBackgroundColor(Color.parseColor("#33ffffff"));
        }

        objectsHolder.tvStatus.setText(newTotalGame.getStatus());
        objectsHolder.tvDate.setText(printDifference(start, date));
        objectsHolder.tvtotalPlayers.setText(String.valueOf(newTotalGame.getTotalPlayer()) + " players");
        return view;
    }
    private class ObjectsHolder {
        TextView tvStatus,tvDate,tvtotalPlayers;
    }
    public String printDifference(Date startDate, Date endDate) {

        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;
        String time = "";
        if (elapsedDays > 0)
            time += elapsedDays + " days";
        else if (elapsedHours>0)
            time +=elapsedHours + " hours";
        else if (elapsedMinutes>0)
            time += elapsedMinutes + " min";
        else if (elapsedSeconds>0)
            time += elapsedSeconds + " seconds";

        return time;
    }
}
