package Entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Department {
    private String name;
    private String headOfDepartment;

    public Department(String name, String head_of_department) {
        this.name = name;
        this.headOfDepartment = head_of_department;
    }

    public Department(){

    }
}
