package methods;

import javafx.collections.ObservableList;

public interface GetData {

    public ObservableList<String> getNameDoctorFilter(String nameDepartment);

    public ObservableList<String> choiceDoctorsList();

    public ObservableList<String> choiceDepartmentList();

}