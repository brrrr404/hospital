package Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import methods.User;

@AllArgsConstructor
@Getter
@Setter
public class Doctor implements User {
    private int id;
    private String name;
    private Department department;
    private String appointment;

    public static Doctor doctor;

    public Doctor(){
    }

    @Override
    public int getID(){
        return id;
    }

    @Override
    public String getFIO(){
        return name;
    }
}
