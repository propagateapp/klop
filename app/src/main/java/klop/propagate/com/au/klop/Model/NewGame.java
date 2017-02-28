package klop.propagate.com.au.klop.Model;

/**
 * Created by trung on 13/12/2016.
 */
public class NewGame {

    private int id;
    private int gameid;
    private int round;
    private String name;
    private int score;
    private int totalScore;
    private byte[] img2;
    private boolean tibreak;

    public boolean isTibreak() {
        return tibreak;
    }

    public void setTibreak(boolean tibreak) {
        this.tibreak = tibreak;
    }

    public NewGame() {
    }

    public NewGame(int round, String name, int score, int totalScore, boolean tibreak) {
        this.round = round;
        this.name = name;
        this.score = score;
        this.totalScore = totalScore;
        this.tibreak = tibreak;
    }

    public NewGame(int gameid, int round, String name, int score, int totalScore, boolean tibreak) {
        this.gameid = gameid;
        this.round = round;
        this.name = name;
        this.score = score;
        this.totalScore = totalScore;
        this.tibreak = tibreak;
    }

    public NewGame(int gameid, int round, String name, int score, int totalScore, byte[] img2, boolean tibreak) {
        this.gameid = gameid;
        this.round = round;
        this.name = name;
        this.score = score;
        this.totalScore = totalScore;
        this.img2 = img2;
        this.tibreak = tibreak;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public byte[] getImg2() {
        return img2;
    }

    public void setImg2(byte[] img2) {
        this.img2 = img2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
}}
