package klop.propagate.com.au.klop.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import klop.propagate.com.au.klop.Database.DatabasePlayers;
import klop.propagate.com.au.klop.Model.Common;
import klop.propagate.com.au.klop.Model.NewGame;
import klop.propagate.com.au.klop.Model.NewTotalGame;
import klop.propagate.com.au.klop.R;
import klop.propagate.com.au.klop.RoundActivity;

/**
 * Created by trung on 13/12/2016.
 */
public class RoundAdapter extends RecyclerView.Adapter<RoundAdapter.ViewHolder> {
    public List<NewGame> listdata;
    private RoundActivity activity;
    private int count = 0 ;
    private DatabasePlayers db;
    private int gameID;
    private String tibreak;
    public int newRound = 0;
    private String recentInputName = "";
    private boolean isFinish = false,isTibrek = false ;

    public RoundAdapter(List<NewGame> listdata, RoundActivity activity, int gameid){
        this.activity = activity;
        this.listdata = listdata;
        this.gameID = gameid;
        this.db = new DatabasePlayers(activity);
    }
    @Override
    public RoundAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_list_round, parent, false);
        isFinish = db.checkStatus(this.gameID);
        isTibrek = db.checkTibrek(this.gameID);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RoundAdapter.ViewHolder holder, final int position) {
        final NewGame newGame = listdata.get(position);
        String a = newGame.getName();
        holder.tvName.setText(a.replace('_', ' '));
        final byte[] outImage = db.getImageOfPlayer(a);
        ByteArrayInputStream imgStream = new ByteArrayInputStream(outImage);
        final Bitmap theimage = BitmapFactory.decodeStream(imgStream);
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(null, theimage);
        dr.setCornerRadius(Math.max(theimage.getWidth(), theimage.getHeight()) / 2.0f);
        int totalScore = 0, score = 0;
        int roundcurrent = db.getRound(newGame.getGameid());
        int totalScoreWin = db.getTotalScoreofRound(newGame.getGameid(), activity.currentRound, newGame.getName());
        if (totalScoreWin == 50) {
            isFinish = true;
        }
        if (isTibrek == true) {
            tibreak = "(Tibreak)";
            if (roundcurrent == activity.currentRound) {
                totalScore = 0;
                score = db.getScoreofRound(newGame.getGameid(),activity.currentRound,newGame.getName());
                holder.btnScore.setBackgroundResource(R.drawable.btnscore);
            } else if (activity.currentRound == newRound) {
                totalScore = db.getTotalScoreofRound(newGame.getGameid(), newRound - 1, newGame.getName());
                score = db.getScoreofRound(newGame.getGameid(),activity.currentRound,newGame.getName());
                holder.btnScore.setBackgroundResource(R.drawable.btnscore);
            } else {
                totalScore = db.getTotalScoreofRound(newGame.getGameid(), activity.currentRound - 1, newGame.getName());
                score = db.getScoreofRound(newGame.getGameid(), activity.currentRound, newGame.getName());
            }
        } else if (roundcurrent > 0) {
            tibreak = "";
            if (isFinish) {
                totalScore = totalScoreWin;
                score = db.getScoreofRound(newGame.getGameid(), activity.currentRound, newGame.getName());
            } else if (roundcurrent == activity.currentRound) {
                score = db.getScoreofRound(newGame.getGameid(), activity.currentRound, newGame.getName());
                if (score > 0 ){
                    totalScore = db.getTotalScoreofRound(newGame.getGameid(), activity.currentRound, newGame.getName());
                    score = db.getScoreofRound(newGame.getGameid(),activity.currentRound,newGame.getName());
                }else {
                    totalScore = db.getTotalScoreofRound(newGame.getGameid(), activity.currentRound - 1, newGame.getName());
                    holder.btnScore.setBackgroundResource(R.drawable.btnscore);
                }
            } else if (activity.currentRound == newRound) {
                    totalScore = db.getTotalScoreofRound(newGame.getGameid(), newRound - 1, newGame.getName());
                    holder.btnScore.setBackgroundResource(R.drawable.btnscore);
            } else {
                totalScore = db.getTotalScoreofRound(newGame.getGameid(), activity.currentRound - 1, newGame.getName());
                score = db.getScoreofRound(newGame.getGameid(), activity.currentRound, newGame.getName());
            }
        }


        activity.tvRound.setText("Round " + activity.currentRound +" "+tibreak );
        holder.btnttScore.setText(String.valueOf(totalScore));
        if (activity.currentRound != roundcurrent) {
            if (score == 13) {
                holder.btnScore.setBackgroundResource(R.drawable.btnbust);
                holder.btnScore.setText("");
                holder.btnScore.setEnabled(false);
            } else {
                holder.btnScore.setText(String.valueOf(score));
                holder.btnScore.setEnabled(false);
            }
        } else {
            if (score == 13) {
                holder.btnScore.setBackgroundResource(R.drawable.btnbust);
                holder.btnScore.setText("");
                holder.btnScore.setEnabled(false);
            } else if (isFinish) {
                holder.btnScore.setText(String.valueOf(score));
                holder.btnScore.setEnabled(false);
            } else {
                score = db.getScoreofRound(newGame.getGameid(), activity.currentRound, newGame.getName());
                if (score > 0){
                    holder.btnScore.setEnabled(true);
                    holder.btnScore.setText(String.valueOf(score));
                }else {
                    holder.btnScore.setEnabled(true);
                    holder.btnScore.setText("+");
                }
            }
        }
        holder.imvPhoto.setImageDrawable(dr);

        final int finalTotalScore = totalScore;
        final int finalScore = score;
        holder.btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Score : ");
                final EditText input = new EditText(activity);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);

                final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                input.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        input.requestFocus();
                        imm.showSoftInput(input,0);
                    }
                },100);


                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String test = String.valueOf(input.getText());
                        if (test.equals("")) {
                            dialog.cancel();
                        } else {
                            if (Integer.parseInt(input.getText().toString()) <= 12) {
                                holder.btnScore.setText(input.getText().toString());
                                int a = finalTotalScore;
                                int inputVal = Integer.parseInt(input.getText().toString());
                                a = a - finalScore + inputVal;
                                holder.btnttScore.setText(String.format("%d", a));
                                if (a > 50) {
                                    a = 25;
                                    inputVal = 13;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        holder.btnScore.setBackgroundResource(R.drawable.btnbust);
                                        holder.btnScore.setText("");
                                    }
                                    holder.btnttScore.setText(String.format("%d", a));
                                }
                                listdata.get(position).setScore(inputVal);
                                listdata.get(position).setTotalScore(a);
                                String name = listdata.get(position).getName();
                                if (name != recentInputName) {
                                    count++;
                                    recentInputName = name;
                                }
                                int isok = 0;
                                boolean nextok = false;
                                for (int i = 0; i < listdata.size(); i++) {
                                    NewGame updateGame = listdata.get(i);
                                    db.updatePlayerinGame(updateGame);
                                    if (listdata.get(i).getScore() > 0 ){
                                        isok += 1;
                                    }
                                    if (isok == listdata.size()){
                                        nextok = true;
                                        isok = 0;
                                    }
                                }

                                if (nextok == true) {
//                                    for (int i = 0; i < listdata.size(); i++) {
//                                        NewGame updateGame = listdata.get(i);
//                                        db.updatePlayerinGame(updateGame);
//                                    }
                                    if (isTibrek) {
                                        List<NewGame> listMaxScore = db.getPeopleMaxScore(newGame.getGameid(), newGame.getRound());
                                        if (listMaxScore.size() == 1) {
                                            NewTotalGame updateTotalGame = new NewTotalGame();
                                            updateTotalGame.setID(gameID);
                                            String namewin = listMaxScore.get(0).getName();
                                            updateTotalGame.setStatus(namewin.replace('_', ' ') + " Win ");
                                            db.updateStatusofTotalGame(updateTotalGame);
                                            isFinish = db.checkStatus(gameID);
                                            AlertDialog.Builder builder3 = new AlertDialog.Builder(activity);
                                            builder3.setTitle("Congratulations, " + namewin.replace('_', ' '));
                                            builder3.setMessage("You have won this game");
                                            builder3.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                }
                                            });
                                            builder3.show();
                                        } else {
                                            for (int i = 0; i < listMaxScore.size(); i++) {
                                                NewGame updateGame = listMaxScore.get(i);
                                                db.addGame(new NewGame(updateGame.getGameid(), updateGame.getRound() + 1, updateGame.getName(), 0, 0, true));
                                                newRound = updateGame.getRound() + 1;
                                            }
                                        }

                                    } else {
                                        // check if isTieBreak
                                        //   get max score -> finish

                                        List<NewGame> listPeopleMax = db.getPeopleMaxTotalScore(newGame.getGameid(), newGame.getRound());
                                        if (listPeopleMax.size() == 1) {
                                            NewTotalGame updateTotalGame = new NewTotalGame();
                                            updateTotalGame.setID(gameID);
                                            String namewin = listPeopleMax.get(0).getName();
                                            updateTotalGame.setStatus(namewin.replace('_', ' ') + " Win ");
                                            db.updateStatusofTotalGame(updateTotalGame);
                                            AlertDialog.Builder builder3 = new AlertDialog.Builder(activity);
                                            builder3.setTitle("Congtatulation, " + namewin.replace('_', ' '));
                                            builder3.setMessage("You have won this game");
                                            builder3.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                }
                                            });
                                            builder3.show();
                                        } else if (listPeopleMax.size() > 1) {
                                            isTibrek = true;
                                            db.updateTibreakofTotalGame(gameID, isTibrek);
                                            // new round
                                            for (int i = 0; i < listPeopleMax.size(); i++) {
                                                NewGame updateGame = listPeopleMax.get(i);
                                                db.addGame(new NewGame(updateGame.getGameid(), updateGame.getRound() + 1, updateGame.getName(), 0, 0, true));
                                                newRound = updateGame.getRound() + 1;
                                            }

                                        } else {
                                            for (int i = 0; i < listdata.size(); i++) {
                                                NewGame updateGame = listdata.get(i);
                                                db.addGame(new NewGame(updateGame.getGameid(), updateGame.getRound() + 1, updateGame.getName(), 0, 0, false));
                                                newRound = updateGame.getRound() + 1;
                                            }
                                        }
                                    }


                                    if (listdata != null && listdata.size() > 0) {
                                        listdata.clear();
                                        count = 0;
                                        activity.tvRound.setText("Round " + newRound);
                                        listdata = db.getGameIDforGame(gameID, newRound);
                                        activity.currentRound = newRound;
                                        notifyDataSetChanged();
                                    }
                                }
                            } else {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                                builder1.setTitle("ERROR");
                                builder1.setMessage("Score cannot be greater than 12");
                                builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                builder1.show();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public ImageView imvPhoto;
        public Button btnScore, btnttScore;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name2);
            imvPhoto = (ImageView) itemView.findViewById(R.id.imVphoto2);
            btnScore = (Button) itemView.findViewById(R.id.btn_score);
            btnttScore = (Button) itemView.findViewById(R.id.btn_totalscore);
        }
    }

}
