package Entity;

import lombok.Getter;
import lombok.Setter;
import methods.DateAndTime;

import java.sql.Time;
import java.util.Date;

@Setter
@Getter
public class Record implements Comparable <Record> {
    private int id;
    private int idDoctor;
    private int idPatient;
    private Date date;
    private DateAndTime time;
    private boolean archive;


    public Record(){
    }

    public void setTime(Time time) {
        this.time = newTime(time);
    }

    public Record(int id, int id_doctor, int id_patient, Date date, Time time, boolean archive) {
        this.id = id;
        this.idDoctor = id_doctor;
        this.idPatient = id_patient;
        this.date = date;
        this.time = newTime(time);
        this.archive = archive;
    }


    public DateAndTime newTime(Time time){
       return new DateAndTime(time.toLocalTime().getHour(),
               time.toLocalTime().getMinute(),
               time.toLocalTime().getSecond());
    }

    @Override
    public int compareTo(Record o) {

        return this.time.compareTo(o.getTime());

    }
}
