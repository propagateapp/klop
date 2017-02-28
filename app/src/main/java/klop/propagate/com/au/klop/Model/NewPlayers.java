package klop.propagate.com.au.klop.Model;

/**
 * Created by trung on 09/12/2016.
 */
public class NewPlayers {
    private String firstName;
    private int Id;
    private byte[] img;


    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public NewPlayers(String firstName, byte[] img) {
        this.firstName = firstName;
        this.img = img;
    }

    public NewPlayers() {
    }

    public NewPlayers(String firstName) {
        this.firstName = firstName;
    }

    public NewPlayers(String firstName, int id) {
        this.firstName = firstName;
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
