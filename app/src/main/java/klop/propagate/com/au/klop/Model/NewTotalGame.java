package klop.propagate.com.au.klop.Model;

/**
 * Created by trung on 14/12/2016.
 */
public class NewTotalGame {
    private int ID;
    private int TotalPlayer;
    private String Date;
    private String Status;
    private boolean tibreak;



    public NewTotalGame() {
    }
    public NewTotalGame(int ID,int totalPlayer, String date, String status, boolean tibreak) {
        this.ID = ID;
        TotalPlayer = totalPlayer;
        Date = date;
        Status = status;
        this.tibreak = tibreak;
    }

    public NewTotalGame(int totalPlayer, String date, String status) {
        TotalPlayer = totalPlayer;
        Date = date;
        Status = status;
    }

    public NewTotalGame(int ID, int totalPlayer, String date, String status) {
        this.ID = ID;
        TotalPlayer = totalPlayer;
        Date = date;
        Status = status;
    }

    public boolean isTibreak() {
        return tibreak;
    }

    public void setTibreak(boolean tibreak) {
        this.tibreak = tibreak;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getTotalPlayer() {
        return TotalPlayer;
    }

    public void setTotalPlayer(int totalPlayer) {
        TotalPlayer = totalPlayer;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
