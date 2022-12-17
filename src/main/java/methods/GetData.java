package methods;

import javafx.collections.ObservableList;

public interface GetData {

     ObservableList<String> getNameDoctorFilter(String nameDepartment);

     ObservableList<String> choiceDoctorsList();

     ObservableList<String> choiceDepartmentList();

}