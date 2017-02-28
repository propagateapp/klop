package klop.propagate.com.au.klop;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import klop.propagate.com.au.klop.Adapter.RoundAdapter;
import klop.propagate.com.au.klop.Database.DatabasePlayers;
import klop.propagate.com.au.klop.Model.Common;
import klop.propagate.com.au.klop.Model.NewGame;
import klop.propagate.com.au.klop.Model.NewPlayers;

public class RoundActivity extends AppCompatActivity {

    private RecyclerView rv;
    private RelativeLayout layout;
    public TextView tvback, tvRound;
    private ImageView imvNext,imvPre;
    private List<NewGame> listdata = new ArrayList<>();
    private RoundAdapter gamesAdapter;
    private DatabasePlayers db;
    private Boolean a;
    private Integer gameID;
    public int roundNumber,currentRound;
    boolean isTibreak;
    private String tibreak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        layout = (RelativeLayout)findViewById(R.id.layout);
        setupUI(layout);

        tvRound = (TextView) findViewById(R.id.tvRoundName);
        tvback = (TextView) findViewById(R.id.tvback);
        tvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        a = intent.getBooleanExtra("update", false);
        gameID = intent.getIntExtra("id", 0);
        db = new DatabasePlayers(this);
        rv = (RecyclerView) findViewById(R.id.rvRound);
        roundNumber = db.getRound(gameID);
        currentRound = roundNumber;
        listdata.addAll(db.getGameIDforGame(gameID, roundNumber));
        gamesAdapter = new RoundAdapter(listdata, this, gameID);
        tvRound.setText("Round " + roundNumber);
        rv.setAdapter(gamesAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        imvNext = (ImageView) findViewById(R.id.nextRound);
        imvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentRound < roundNumber || currentRound < gamesAdapter.newRound) {
                    listdata.clear();
                    currentRound = currentRound + 1;
                    listdata.addAll(db.getGameIDforGame(gameID, currentRound));
                    isTibreak = db.checkTibrekGame(gameID,currentRound);
                    if (isTibreak == true){
                        tibreak = "(Tibreak)";
                    }else {
                        tibreak = "";
                    }
                    tvRound.setText("Round" + currentRound +tibreak);
                    rv.setAdapter(gamesAdapter);
                    rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            }
        });

        imvPre = (ImageView)findViewById(R.id.preRound);
        imvPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentRound > 1) {
                    listdata.clear();
                    currentRound = currentRound - 1;
                    listdata.addAll(db.getGameIDforGame(gameID, currentRound));
//                    if (currentRound == db.getRound(gameID) - 1){
//                        for (int i = 0; i < listdata.size(); i++) {
//                            NewGame updateGame = listdata.get(i);
//                            db.updatePlayerinGame(updateGame);
//                        }
//                    }
                    isTibreak = db.checkTibrekGame(gameID,currentRound);
                    if (isTibreak == true){
                        tibreak = "(Tibreak)";
                    }else {
                        tibreak = "";
                    }
                    tvRound.setText("Round" + currentRound+tibreak);
                    gamesAdapter.listdata = listdata;
                    rv.setAdapter(gamesAdapter);
                    rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
            }
        });

    }
    public void setupUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(RoundActivity.this);
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
