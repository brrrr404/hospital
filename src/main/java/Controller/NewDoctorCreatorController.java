package Controller;

import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Department;
import Entity.Doctor;
import com.example.hospital.BD;
import exception.ControllerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.SneakyThrows;
import methods.Base;
import methods.GetData;

public class NewDoctorCreatorController extends Base implements GetData {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> division;

    @FXML
    private ChoiceBox<String> doctors;

    @FXML
    private Spinner<String> bgTime;

    @FXML
    private DatePicker date;

    @FXML
    private Spinner<String> endTime;

    @FXML
    private Button ready;

    BD bd = new BD();

    @SneakyThrows
    @FXML
    void initialize() {

        setSpinner(endTime);
        setSpinner(bgTime);

        ready.setOnAction(actionEvent -> {
            validate();

            Time newTimeBegin = Time.valueOf(bgTime.getValue() + ":00");
            Time newTimeEnd = Time.valueOf(endTime.getValue() + ":00");
            LocalDate newDate = newDate();

            bd.addJob(doctors.getValue(), newTimeBegin, newTimeEnd, newDate);
            closeModalWindow(ready);
        });


        doctors.setItems(choiceDoctorsList());
        division.setItems(choiceDepartmentList());

        division.setOnAction(event -> doctors.setItems(getNameDoctorFilter(division.getValue()))
        );

    }

    private LocalDate newDate() {
        return date.getValue();
    }


    @SneakyThrows
    private void validate() {
        if (date.getValue() == null) {
            new ControllerException("Дата не выбрана");
        }
        if (bgTime.getValue() == null) {
            new ControllerException("Время начала смены не выбрано");
        }
        if (endTime.getValue() == null) {
            new ControllerException("Время окончания смены не выбрано");
        }

        Time newTimeBegin = Time.valueOf(bgTime.getValue() + ":00");
        Time newTimeEnd = Time.valueOf(endTime.getValue() + ":00");
        LocalDate newDate = date.getValue();

        if (newTimeBegin.after(newTimeEnd)) {
            new ControllerException("Время начала не может быть позже времени окончания смены");
        }
        if (doctors.getValue() == null || division.getValue() == null) {
            new ControllerException("Врач или департамент не выбраны");
        }
        if (newDate.isBefore(LocalDate.now()) || newDate.isEqual(LocalDate.now())) {
            new ControllerException("Неверно указана дата");
        }
    }

    private void setSpinner(Spinner spinner) {
        spinner.setEditable(true);

        List<String> times = new ArrayList<>();
        for (int i = 8; i < 20; i++) {
            for (int j = 0; j < 60; j += 15) {
                times.add(i + ":" + j);
            }
        }

        ObservableList<String> items = FXCollections.observableArrayList(times);
        SpinnerValueFactory<String> valueFactory = //
                new SpinnerValueFactory.ListSpinnerValueFactory<String>(items);


        spinner.setValueFactory(valueFactory);
        spinner.getEditor().setOnAction(actionEvent -> {
        });
    }


    @SneakyThrows
    public ObservableList<String> getNameDoctorFilter(String nameDepartment) {
        ObservableList<String> doctorsName
                = FXCollections.observableArrayList();

        for (Doctor i : bd.getDoctors(nameDepartment)) {
            doctorsName.add(i.getName());
        }

        return doctorsName;
    }

    //все департаменты
    @Override
    public ObservableList<String> choiceDepartmentList() {
        ObservableList<String> doctorDepartment
                = FXCollections.observableArrayList();

        for (Department i : bd.getDepartments()) {
            doctorDepartment.add(i.getName());
        }

        return doctorDepartment;
    }

    @Override
    //все врачи
    public ObservableList<String> choiceDoctorsList() {
        ObservableList<String> doctorsName
                = FXCollections.observableArrayList();

        for (Doctor i : bd.getAllDoctors()) {
            doctorsName.add(i.getName());
        }

        return doctorsName;
    }
}
