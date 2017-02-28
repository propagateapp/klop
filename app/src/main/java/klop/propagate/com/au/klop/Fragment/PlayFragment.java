package klop.propagate.com.au.klop.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import klop.propagate.com.au.klop.Adapter.TotalGameAdapter;
import klop.propagate.com.au.klop.AddPlayerActivity;
import klop.propagate.com.au.klop.Database.DatabasePlayers;
import klop.propagate.com.au.klop.Model.Common;
import klop.propagate.com.au.klop.Model.NewTotalGame;
import klop.propagate.com.au.klop.NewGameActivity;
import klop.propagate.com.au.klop.R;
import klop.propagate.com.au.klop.RoundActivity;

public class PlayFragment extends Fragment {

    private List<NewTotalGame> listdata = new ArrayList<>();
    private TotalGameAdapter totalGameAdapter;
    private Button btnnew;
    private ListView lv;
    boolean checkPlayer;

    public static PlayFragment newInstance() {
        PlayFragment fragment = new PlayFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_play, container, false);

        lv = (ListView)rootView.findViewById(R.id.lVPlayKlop);
        final DatabasePlayers db = new DatabasePlayers(getActivity());
        listdata.addAll(db.getallTotalGame());
        totalGameAdapter = new TotalGameAdapter(listdata,getActivity());
        lv.setAdapter(totalGameAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewTotalGame newTotalGame = listdata.get(i);
                Intent intent = new Intent(getActivity(),RoundActivity.class);
                intent.putExtra("id",newTotalGame.getID());
                startActivity(intent);
            }
        });
        checkPlayer = db.getNumberinPlayer();
        btnnew = (Button)rootView.findViewById(R.id.btnNewGame);
        btnnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPlayer == false){
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
                    builder3.setTitle("Cannot start a new game");
                    builder3.setMessage("To start a new game, you will need at least two players. Currently,there is no player. Please add more players before starting a new game");
                    builder3.setNegativeButton("Add Player", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getActivity(), AddPlayerActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder3.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });


                    builder3.show();
                }else {
                    if (Common.listplayer != null && Common.listplayer.size() > 0)
                        Common.listplayer.clear();
                    Intent i = new Intent(getActivity(), NewGameActivity.class);
                    startActivity(i);
                }
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                final NewTotalGame newTotalGame = listdata.get(pos);
                AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
                builder3.setTitle("DELETE");
                builder3.setMessage("Do you want delete this game?");
                builder3.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteTotalGame(newTotalGame.getID());
                        onResume();
                    }
                });
                builder3.show();

                return true;
            }
        });
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();

        DatabasePlayers db = new DatabasePlayers(getActivity());
        listdata.clear();
        listdata.addAll(db.getallTotalGame());
        lv.setAdapter(totalGameAdapter);
        checkPlayer = db.getNumberinPlayer();
        totalGameAdapter.notifyDataSetChanged();
    }
}
