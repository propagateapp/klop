package klop.propagate.com.au.klop.Adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import klop.propagate.com.au.klop.Database.DatabasePlayers;
import klop.propagate.com.au.klop.Model.Common;
import klop.propagate.com.au.klop.Model.NewGame;
import klop.propagate.com.au.klop.Model.NewPlayers;
import klop.propagate.com.au.klop.R;

/**
 * Created by trung on 12/12/2016.
 */
public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {
    private List<NewPlayers> listdata;
    private Activity activity;
    private ArrayList arrayList = new ArrayList();

    public GamesAdapter(List<NewPlayers> listdata,Activity activity){
        this.activity = activity;
        this.listdata = listdata;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_list_addplay, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (listdata != null && listdata.size() > 0) {
            final NewPlayers newPlayers = listdata.get(position);

            holder.tvName.setText(newPlayers.getFirstName().replace('_',' '));

            final byte[] outImage = newPlayers.getImg();
            ByteArrayInputStream imgStream = new ByteArrayInputStream(outImage);
            final Bitmap theimage = BitmapFactory.decodeStream(imgStream);
            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(null, theimage);
            dr.setCornerRadius(Math.max(theimage.getWidth(), theimage.getHeight()) / 2.0f);

            holder.imvPhoto.setImageDrawable(dr);

            final DatabasePlayers db = new DatabasePlayers(activity);

            final String name = newPlayers.getFirstName();
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.imvOk.getVisibility() == view.INVISIBLE) {
                        holder.imvOk.setVisibility(View.VISIBLE);
                        Common.listplayer.add(new NewGame(1, name, 0, 0,false));
                    } else if (holder.imvOk.getVisibility() == view.VISIBLE) {
                        holder.imvOk.setVisibility(View.INVISIBLE);
                        removePlayerfromlist(name);
                    }
                }
            });
        }
    }

    private void removePlayerfromlist(String name){
        if (Common.listplayer != null && Common.listplayer.size()>0){
            for (int i =0; i<Common.listplayer.size();i++){
                NewGame newGame = Common.listplayer.get(i);
                if (newGame.getName() == name){
                    Common.listplayer.remove(newGame);
                    break;
                }
            }

        }
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public ImageView imvPhoto, imvOk;
        public LinearLayout linearLayout;

        public ViewHolder(final View itemView) {
            super(itemView);

            linearLayout = (LinearLayout)itemView.findViewById(R.id.content);
            tvName = (TextView)itemView.findViewById(R.id.tv_name);
            imvPhoto = (ImageView)itemView.findViewById(R.id.imVphoto);
            imvOk = (ImageView)itemView.findViewById(R.id.btn_add);


        }
    }
}
