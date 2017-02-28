package klop.propagate.com.au.klop.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import klop.propagate.com.au.klop.Model.Common;
import klop.propagate.com.au.klop.Model.NewGame;
import klop.propagate.com.au.klop.Model.NewPlayers;
import klop.propagate.com.au.klop.Model.NewTotalGame;

/**
 * Created by trung on 09/12/2016.
 */
public class DatabasePlayers extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "DatabaseKlop";

    //TABLE1
    private static final String TABLE_PLAYER = "players";
    private static final String KEY_ID ="id";
    private static final String KEY_FIRSTNAME ="fname";
    private static final String KEY_LASTNAME = "lname";
    private static final String KEY_IMAGE = "image";
    public static final String CREATE_PLAYER_TABLE = "CREATE TABLE " + TABLE_PLAYER +"("
            + KEY_ID +" INTEGER PRIMARY KEY,"
            + KEY_FIRSTNAME +" TEXT,"
            + KEY_IMAGE +" BLOB"
            +")";

    //TABLE2
    private static final String TABLE_GAME = "Game";
    private static final String KEY_GAMEID ="gameid";
    private static final String KEY_ROUND ="round";
    private static final String KEY_NAME = "name";
    private static final String KEY_SCORE = "score";
    private static final String KEY_TOTALSCORE = "ttscore";
    private static final String KEY_IMAGE2 = "image";
    private static final String KEY_TIBREK = "tibrek";
    public static final String CREATE_GAME_TABLE = "CREATE TABLE " + TABLE_GAME +"("
            + KEY_GAMEID +" INTEGER,"
            + KEY_ROUND +" INTEGER,"
            + KEY_NAME +" TEXT,"
            + KEY_SCORE +" TEXT,"
            + KEY_TOTALSCORE +" TEXT,"
            + KEY_IMAGE2 +" BLOB,"
            + KEY_TIBREK +" BOOLEAN"
            +")";

    //TABLE3
    private static final String TABLE_TOTAL_GAME = "totalgame";
    private static final String KEY_TOTALID = "totalid";
    private static final String KEY_TOTALPLAYER = "totalplayer";
    private static final String KEY_DATE = "date";
    private static final String KEY_STATUS = "status";
    private static final String KEY_TIBREK2 = "tibrek2";
    public static final String CREATE_TOTALGAME_TABLE = "CREATE TABLE " + TABLE_TOTAL_GAME+"("
            +KEY_TOTALID+" INTEGER,"
            +KEY_TOTALPLAYER+" INTEGER,"
            +KEY_DATE+ " TEXT,"
            +KEY_STATUS+" TEXT,"
            +KEY_TIBREK2+" BOOLEAN"
            +")";


    public DatabasePlayers(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_GAME_TABLE);
        sqLiteDatabase.execSQL(CREATE_PLAYER_TABLE);
        sqLiteDatabase.execSQL(CREATE_TOTALGAME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXITS "+TABLE_GAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXITS "+TABLE_PLAYER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXITS "+TABLE_TOTAL_GAME);
        onCreate(sqLiteDatabase);
    }

    public void addTotalGame (NewTotalGame newTotalGame) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TOTALID, newTotalGame.getID());
        values.put(KEY_TOTALPLAYER, newTotalGame.getTotalPlayer());
        values.put(KEY_DATE, newTotalGame.getDate());
        values.put(KEY_STATUS, newTotalGame.getStatus());
        values.put(KEY_TIBREK2,newTotalGame.isTibreak());
        db.insert(TABLE_TOTAL_GAME, null, values);
        db.close();
    }

    public List<NewTotalGame> getallTotalGame (){
        List<NewTotalGame> totalGameList = new ArrayList<NewTotalGame>();
        String selectQuery = "SELECT  * FROM "+ TABLE_TOTAL_GAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                NewTotalGame newTotalGame = new NewTotalGame();
                newTotalGame.setID(Integer.parseInt(cursor.getString(0)));
                newTotalGame.setDate(cursor.getString(2));
                newTotalGame.setStatus(cursor.getString(3));
                newTotalGame.setTotalPlayer(Integer.parseInt(cursor.getString(1)));
                newTotalGame.setTibreak(cursor.getInt(4) > 0);
                totalGameList.add(newTotalGame);
            }while (cursor.moveToNext());
        }
        return totalGameList;
    }

    public void addPlayers (NewPlayers newPlayers){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME,newPlayers.getFirstName());
        values.put(KEY_IMAGE,newPlayers.getImg());
        db.insert(TABLE_PLAYER,null,values);
        db.close();
    }
    public List<NewPlayers> getallPlayers (){
        List<NewPlayers> worksList = new ArrayList<NewPlayers>();
        String selectQuery = "SELECT  * FROM "+TABLE_PLAYER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                NewPlayers newItems = new NewPlayers();
                newItems.setId(Integer.parseInt(cursor.getString(0)));
                newItems.setFirstName(cursor.getString(1));
                newItems.setImg(cursor.getBlob(2));
                worksList.add(newItems);
            }while (cursor.moveToNext());
        }
        return worksList;
    }
    public boolean getNumberinPlayer(){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean havePlayer = false;
        Cursor cursor = db.rawQuery("SELECT  * FROM "+TABLE_PLAYER,null);
        if (cursor.moveToLast()) {
            int count = Integer.parseInt(cursor.getString(0));
            if (count>1){
                havePlayer =true;
            }
        }
        return havePlayer;
    }
    public void deletePlayers(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYER,KEY_ID+" = ?",new String[] { String.valueOf(id) });
        db.close();
    }
    public void deletePlayerinGame(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GAME,KEY_NAME+" = ?",new String[] { String.valueOf(name) });
        db.close();
    }
    public void deleteTotalGame(int gameid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TOTAL_GAME,KEY_TOTALID +" =?",new String[]{String.valueOf(gameid)});
        db.delete(TABLE_GAME,KEY_GAMEID+" =?",new String[]{String.valueOf(gameid)});
        db.close();
    }
    public void updatePlayers(NewPlayers newPlayers) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, newPlayers.getFirstName());
        values.put(KEY_IMAGE,newPlayers.getImg());

        db.update(TABLE_PLAYER, values, KEY_ID + " = ?",
                new String[] { String.valueOf(newPlayers.getId()) });


    }

    public void updateStatusofTotalGame(NewTotalGame newTotalGame){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STATUS,newTotalGame.getStatus());

        db.update(TABLE_TOTAL_GAME,values,KEY_TOTALID + " = ?",new  String[]{String.valueOf(newTotalGame.getID())});
    }

    public void updateTibreakofTotalGame(int gameid, boolean isTieBreak){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TIBREK2,isTieBreak);

        int dbUpdate = db.update(TABLE_TOTAL_GAME,values,KEY_TOTALID + " = ?",new  String[]{String.valueOf(gameid)});
    }

    public void addGame(NewGame newGame){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_GAMEID,newGame.getGameid());
        values.put(KEY_ROUND,newGame.getRound());
        values.put(KEY_NAME,newGame.getName());
        values.put(KEY_SCORE,newGame.getScore());
        values.put(KEY_TOTALSCORE,newGame.getTotalScore());
        values.put(KEY_IMAGE,newGame.getImg2());
        values.put(KEY_TIBREK,newGame.isTibreak());
        db.insert(TABLE_GAME,null,values);
        db.close();
    }

    public List<NewGame> getallGames (){
        List<NewGame> worksList = new ArrayList<NewGame>();
        String selectQuery = "SELECT  * FROM "+TABLE_GAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                NewGame newGame = new NewGame();
                newGame.setGameid(Integer.parseInt(cursor.getString(0)));
                newGame.setRound(Integer.parseInt(cursor.getString(1)));
                newGame.setName(cursor.getString(2));
                newGame.setScore(Integer.parseInt(cursor.getString(3)));
                newGame.setTotalScore(Integer.parseInt(cursor.getString(4)));
                newGame.setImg2(cursor.getBlob(5));
                worksList.add(newGame);
            }while (cursor.moveToNext());
        }
        return worksList;
    }


    public List<NewGame> getGameIDforGame(int gameid, int roundid) {
        List<NewGame> gameList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_GAME, new String[]{KEY_GAMEID, KEY_ROUND,
                        KEY_NAME, KEY_SCORE, KEY_TOTALSCORE, KEY_IMAGE,KEY_TIBREK}, KEY_GAMEID + "=? AND " + KEY_ROUND + "=?",
                new String[]{String.valueOf(gameid), String.valueOf(roundid)}, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                NewGame newGame = new NewGame(
                        Integer.parseInt(cursor.getString(0))
                        , Integer.parseInt(cursor.getString(1))
                        , cursor.getString(2)
                        , Integer.parseInt(cursor.getString(3))
                        , Integer.parseInt(cursor.getString(4))
                        , cursor.getBlob(5)
                        , Boolean.valueOf(cursor.getString(6)));
                gameList.add(newGame);
            } while (cursor.moveToNext());
        }
        return gameList;
    }

    public int getGameID(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_GAME;
        int returnvalue = 1 ;
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToLast()) {
            int test = cursor.getInt(0);
            returnvalue = cursor.getInt(0) + 1;
        }
        return returnvalue;
    }

    public byte[] getImageOfPlayer(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        byte[] imageOfPlayer = new byte[0];
            String query = "SELECT * FROM " + TABLE_PLAYER + " WHERE " + KEY_FIRSTNAME + " = '" + name +"'";
            Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                imageOfPlayer = cursor.getBlob(2);
            }
        } finally {
            cursor.close();
        }
        return imageOfPlayer;
    }
    public int getRound(int gameid) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_GAME + " WHERE " + KEY_GAMEID + " = ";
        int returnvalue = 1;
        String queryd = query + gameid;
        Cursor cursor = db.rawQuery(queryd, null);
        try {
            if (cursor.moveToLast()) {
                returnvalue = Integer.parseInt(cursor.getString(1));
            }
        } finally {
            cursor.close();
        }
        return returnvalue;
    }


    public boolean checkPlayer (String name){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean checkPlayer = false;
        String query = "SELECT * FROM " + TABLE_PLAYER + " WHERE " + KEY_FIRSTNAME + " like " + "'%"+name+"%'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()){
            String  status = cursor.getString(1);
            if(status != null)
                checkPlayer = true;
        }
        return  checkPlayer;
    }

    public boolean checkTotalScoreofGame(int gameID, int round) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean check = false;
        String query = "SELECT * FROM " + TABLE_GAME + " WHERE " + KEY_GAMEID + " = " + gameID + " AND " + KEY_ROUND + " = " + round;
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            do {
                if (Integer.parseInt(cursor.getString(4)) == 50) {
                    count++;
                }
            }while (cursor.moveToNext());
        }

        if (count == 1)
            check = true;
        else {

        }

        return check;
    }
    public boolean checkStatus(int gameID){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean checkstatus = false;
        String query = "SELECT * FROM " + TABLE_TOTAL_GAME + " WHERE " + KEY_TOTALID + " = " + gameID;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()){
          String  status = cursor.getString(3);
            if(!status.equals("In Progress"))
                checkstatus = true;
        }

        return checkstatus;
    }

    public boolean checkTibrek(int gameID) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean tibrek = false;
        int count = 0;
        String query = "SELECT * FROM " + TABLE_TOTAL_GAME + " WHERE " + KEY_TOTALID + " = " + gameID;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                tibrek = cursor.getInt(4) > 0;
//                if (Boolean.valueOf(cursor.getString(4)) == true) {
//                    count += 1;
//                }
            } while (cursor.moveToNext());
        }
//        if (count >= 2) {
//            tibrek = true;
//        }
        return tibrek;
    }
    public boolean checkTibrekGame(int gameID, int round) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean tibrek = false;
        String query = "SELECT * FROM " + TABLE_GAME + " WHERE " + KEY_GAMEID + " = " + gameID +" AND "+KEY_ROUND+" = "+round;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                tibrek = cursor.getInt(6) > 0;
            } while (cursor.moveToNext());
        }
        return tibrek;
    }

    public List<NewGame> getPeopleMaxTotalScore(int gameid, int round){
        SQLiteDatabase db = this.getWritableDatabase();
        int max = 0;
        List<NewGame> listPeopleMax = new ArrayList<>();
        String query = "SELECT * FROM "+TABLE_GAME+" WHERE "+KEY_GAMEID+" = "+gameid +" AND "+KEY_ROUND+" = "+round;
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                int score = Integer.parseInt(cursor.getString(4));
                if(max < score)
                    max = score;
            }while (cursor.moveToNext());
        }
        if(max == 50 && cursor.moveToFirst())
        {
            do {
                if (Integer.parseInt(cursor.getString(4))== max){
                    NewGame newGame = new NewGame(
                            Integer.parseInt(cursor.getString(0))
                            , Integer.parseInt(cursor.getString(1))
                            , cursor.getString(2)
                            , Integer.parseInt(cursor.getString(3))
                            , Integer.parseInt(cursor.getString(4))
                            , cursor.getBlob(5)
                            , Boolean.valueOf(cursor.getString(6)));
                    listPeopleMax.add(newGame);
                }
            }while (cursor.moveToNext());

        }
        return listPeopleMax;
    }

    public List<NewGame> getPeopleMaxScore(int gameid, int round){
        SQLiteDatabase db = this.getWritableDatabase();
        int max = 0;
        List<NewGame> listPeopleMax = new ArrayList<>();
        String query = "SELECT * FROM "+TABLE_GAME+" WHERE "+KEY_GAMEID+" = "+gameid +" AND "+KEY_ROUND+" = "+round;
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                int score = Integer.parseInt(cursor.getString(4));
                if(max < score)
                    max = score;
            }while (cursor.moveToNext());
        }
        if(cursor.moveToFirst())
        {
            do {
                if (Integer.parseInt(cursor.getString(3))== max){
                    NewGame newGame = new NewGame(
                            Integer.parseInt(cursor.getString(0))
                            , Integer.parseInt(cursor.getString(1))
                            , cursor.getString(2)
                            , Integer.parseInt(cursor.getString(3))
                            , Integer.parseInt(cursor.getString(4))
                            , cursor.getBlob(5)
                            , Boolean.valueOf(cursor.getString(6)));
                    listPeopleMax.add(newGame);
                }
            }while (cursor.moveToNext());

        }
        return listPeopleMax;
    }

    public int getTotalScoreofRound(int gameId,int round,String name){
        SQLiteDatabase db = this.getWritableDatabase();
        int totalScore = 0;
        String query = "SELECT * FROM "+TABLE_GAME+" WHERE "+KEY_GAMEID+" = "+gameId +" AND "+KEY_ROUND+" = "+round+" AND "+KEY_NAME+" = '"+name+"'";
        Cursor cursor = db.rawQuery(query,null);
        try {
            if (cursor.moveToNext()) {
                totalScore = Integer.parseInt(cursor.getString(4));
            }
        } finally {
            cursor.close();
        }
        return totalScore;
    }
    public int getScoreofRound(int gameId,int round,String name){
        SQLiteDatabase db = this.getWritableDatabase();
        int Score = 0;
        String query = "SELECT * FROM "+TABLE_GAME+" WHERE "+KEY_GAMEID+" = "+gameId +" AND "+KEY_ROUND+" = "+round+" AND "+KEY_NAME+" = '"+name+"'";
        Cursor cursor = db.rawQuery(query,null);
        try {
            if (cursor.moveToNext()){
                Score = Integer.parseInt(cursor.getString(3));
            }
        } finally {
            cursor.close();
        }
        return Score;
    }

    public void updatePlayerinGame(NewGame newGame) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SCORE,newGame.getScore());
        values.put(KEY_TOTALSCORE,newGame.getTotalScore());

        db.update(TABLE_GAME, values,KEY_GAMEID+"=? AND " + KEY_ROUND + "=? AND "+KEY_NAME + "=?",

                new String[] { String.valueOf(newGame.getGameid())
                        ,String.valueOf(newGame.getRound())
                        ,newGame.getName()}
     );
    }
}
