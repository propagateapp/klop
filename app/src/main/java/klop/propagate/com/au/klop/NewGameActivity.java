package klop.propagate.com.au.klop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import klop.propagate.com.au.klop.Adapter.GamesAdapter;
import klop.propagate.com.au.klop.Database.DatabasePlayers;
import klop.propagate.com.au.klop.Model.Common;
import klop.propagate.com.au.klop.Model.NewGame;
import klop.propagate.com.au.klop.Model.NewPlayers;
import klop.propagate.com.au.klop.Model.NewTotalGame;

public class NewGameActivity extends AppCompatActivity {
    TextView btnCancel;
    private ImageView imv;
    private  RecyclerView  rv;
    private List<NewPlayers> listdata = new ArrayList<>();
    private GamesAdapter  gamesAdapter;
    private DatabasePlayers db;
    private Context context;
    public static final DateFormat FILE_DATE_FORMAT = new SimpleDateFormat("HHmmddMMyyyy");

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Common.listplayer.clear();

        btnCancel = (TextView) findViewById(R.id.tvCanceladd);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imv = (ImageView) findViewById(R.id.imgvPlay);
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Common.listplayer.size() >= 2) {
                    int gameID = SavePlayer();
                    Intent i = new Intent(NewGameActivity.this, RoundActivity.class);
                    i.putExtra("id", gameID);
                    startActivity(i);
                    finish();
                } else {
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(NewGameActivity.this);
                    builder3.setTitle("Error");
                    builder3.setMessage("Sorry! You must select at least 2 players to start a new game");
                    builder3.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder3.show();
                }
            }
        });

        db = new DatabasePlayers(this);
        listdata.addAll(db.getallPlayers());
        rv = (RecyclerView) findViewById(R.id.rvPlayers);
        gamesAdapter = new GamesAdapter(listdata, this);
        rv.setAdapter(gamesAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private int SavePlayer() {
        Date date = new Date();
        String dateNow = FILE_DATE_FORMAT.format(date);
        int gameID = db.getGameID();
        db.addTotalGame(new NewTotalGame(gameID, Common.listplayer.size(), dateNow, "In Progress",false));

        if (Common.listplayer != null && Common.listplayer.size() > 0) {
            for (int i = 0; i < Common.listplayer.size(); i++) {
                NewGame newGame = Common.listplayer.get(i);
                newGame.setGameid(gameID);
                db.addGame(newGame);
            }
        }

        return gameID;
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = new DatabasePlayers(this);
        listdata.clear();
        listdata.addAll(db.getallPlayers());
        rv.setAdapter(gamesAdapter);
        gamesAdapter.notifyDataSetChanged();
    }
}
