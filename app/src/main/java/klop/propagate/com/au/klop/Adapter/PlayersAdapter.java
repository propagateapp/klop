package klop.propagate.com.au.klop.Adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.List;

import klop.propagate.com.au.klop.Model.NewPlayers;
import klop.propagate.com.au.klop.R;

/**
 * Created by trung on 09/12/2016.
 */
public class PlayersAdapter extends BaseAdapter {
    private List<NewPlayers> listdata;
    private Activity activity;
    public PlayersAdapter(List<NewPlayers> listdata,Activity activity){
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
            view = activity.getLayoutInflater().inflate(R.layout.item_list, null);
            objectsHolder.tvName = (TextView) view.findViewById(R.id.tvNamePlayer);
            objectsHolder.image = (ImageView)view.findViewById(R.id.imageViewPlay);
        }

        view.setTag(objectsHolder);

        NewPlayers works = listdata.get(i);

        byte[] outImage = works.getImg();
        ByteArrayInputStream imgStream = new ByteArrayInputStream(outImage);
        Bitmap theimage = BitmapFactory.decodeStream(imgStream);
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(null,theimage);
        dr.setCornerRadius(Math.max(theimage.getWidth(),theimage.getHeight())/2.0f);

        objectsHolder.tvName.setText(works.getFirstName().replace('_',' '));
        objectsHolder.image.setImageDrawable(dr);
        return view;
    }
    private class ObjectsHolder {
        TextView tvName;
        ImageView image;
    }
}
