package Entity;

import lombok.Getter;
import lombok.Setter;
import methods.User;

@Setter
@Getter
public class Patient implements User {
    private int id;
    private String fio;
    private String login;
    private String password;
    private String place;
    private int number;
    private String male;
    private String mail;

    public static Treatment treatment;


    public Patient(int id, String name, String surname, String middleName, String login, String password, String place, int number, String male) {
        this.id = id;
        this.fio = name + " " + surname + " " + middleName;
        this.login = login;
        this.password = password;
        this.place = place;
        this.number = number;
        this.male = male;
    }

    public Patient(){
    }

    @Override
    public int getID(){
        return id;
    }

    @Override
    public String getFIO(){
        return fio;
    }
}
