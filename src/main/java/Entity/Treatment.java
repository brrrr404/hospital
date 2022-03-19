package Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Treatment {
    private int id;
    private int idPatient;
    private int idDoctor;
    private int idRecord;
    private String nameDepartment;
    private String date;
    private String comment;
    private String fioDoctor;

    public Treatment() {
    }

    public Treatment(int id, int idPatient, int idDoctor, int idRecord, String nameDepartment, String date, String comment, String fioDoctor) {
        this.id = id;
        this.idPatient = idPatient;
        this.idDoctor = idDoctor;
        this.idRecord = idRecord;
        this.nameDepartment = nameDepartment;
        this.date = date;
        this.comment = comment;
        this.fioDoctor = fioDoctor;
    }
}


