package Entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.Statement;

@Setter
@Getter
public class Roles {
    private int idRegistration;
    private int idPatient;
    private int idDoctors;
    private String login;
    private String password;

    public Roles(String idRegistration, String idPatient, String idDoctors, String login, String password) {
        this.idRegistration = Integer.parseInt(idRegistration);
        this.idPatient = Integer.parseInt(idPatient);
        this.idDoctors = Integer.parseInt(idDoctors);
        this.login = login;
        this.password = password;
    }

    public Roles() {
    }
}
