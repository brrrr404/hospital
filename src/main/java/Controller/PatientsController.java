package Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Department;
import Entity.Doctor;
import Entity.Record;
import com.example.hospital.BD;
import exception.ControllerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import lombok.SneakyThrows;
import methods.Base;
import methods.GetData;

public class PatientsController extends Base implements GetData {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dateChange;

    @FXML
    private ChoiceBox<String> choiceDepartment;

    @FXML
    private ChoiceBox<String> choiceDoctor;

    @FXML
    private Label hello;

    @FXML
    private Button timeButton1;

    @FXML
    private Button timeButton10;

    @FXML
    private Button timeButton11;

    @FXML
    private Button timeButton12;

    @FXML
    private Button timeButton13;

    @FXML
    private Button timeButton14;

    @FXML
    private Button timeButton15;

    @FXML
    private Button timeButton2;

    @FXML
    private Button timeButton3;

    @FXML
    private Button timeButton4;

    @FXML
    private Button timeButton5;

    @FXML
    private Button timeButton6;

    @FXML
    private Button timeButton7;

    @FXML
    private Button timeButton8;

    @FXML
    private Button timeButton9;

    @FXML
    private Pane div1;


    public BD bd = new BD();

    @FXML
    @SneakyThrows
    void initialize() {

        hello.setText("Добро пожаловать, " + BD.user.getFIO());

        List<Button> buttons = allButton();
        for (Button i : buttons) {
            i.setDisable(true);
        }

        dateChange.setValue(LocalDate.now());
        dateChange.setOnAction(event -> {
            validate();
            newNameButtonTime(choiceDoctor.getValue(), dateChange.getValue());
        });

        choiceDoctor.setItems(choiceDoctorsList());
        choiceDepartment.setItems(choiceDepartmentList());

        choiceDepartment.setOnAction(event -> choiceDoctor.setItems(getNameDoctorFilter(choiceDepartment.getValue()))
        );

        choiceDoctor.setOnAction(event -> {
            validate();
            newNameButtonTime(choiceDoctor.getValue(), dateChange.getValue());
        });

        for(Button i : buttons){
             i.setOnAction(event ->{ bd.setRecords(i.getText(),dateChange.getValue(), bd.getDoctor(choiceDoctor.getValue()).getID());
                 i.setDisable(true);
             });
        }


    }

    private void validate(){
        if(dateChange.getValue()==null){
            new ControllerException("Не выбрана дата");
        }
        if(choiceDoctor.getValue()==null){
            new ControllerException("Не выбран специалист");
        }
    }

    //получаем новый список врачей определенной специальности
    @SneakyThrows
    public ObservableList<String> getNameDoctorFilter(String nameDepartment) {
        ObservableList<String> doctorsName
                = FXCollections.observableArrayList();

        for (Doctor i : bd.getDoctors(nameDepartment)) {
            doctorsName.add(i.getName());
        }

        return doctorsName;
    }


    //все врачи
    public ObservableList<String> choiceDoctorsList() {
        ObservableList<String> doctorsName
                = FXCollections.observableArrayList();

        for (Doctor i : bd.getAllDoctors()) {
            doctorsName.add(i.getName());
        }

        return doctorsName;
    }

    //все департаменты
    public ObservableList<String> choiceDepartmentList() {
        ObservableList<String> doctorDepartment
                = FXCollections.observableArrayList();

        for (Department i : bd.getDepartments()) {
            doctorDepartment.add(i.getName());
        }

        return doctorDepartment;
    }

    //привязываем время записи к кнопкам
    private void newNameButtonTime(String name, LocalDate date) {
        List<Record> records = bd.getAllRecords(name, date);
        List<Button> buttons = allButton();
        for (Button k : buttons) {
            k.setText("");
            k.setDisable(true);
        }

        for (int i = 0; i < records.size(); i++) {
            buttons.get(i).setText(records.get(i).getTime().toString());
            buttons.get(i).setDisable(false);
        }
    }

    //заносим все кнопки в один список
    private List<Button> allButton() {
        List<Button> buttons = new ArrayList<>();

        buttons.add(timeButton1);
        buttons.add(timeButton2);
        buttons.add(timeButton3);
        buttons.add(timeButton4);
        buttons.add(timeButton5);
        buttons.add(timeButton6);
        buttons.add(timeButton7);
        buttons.add(timeButton8);
        buttons.add(timeButton9);
        buttons.add(timeButton10);
        buttons.add(timeButton11);
        buttons.add(timeButton12);
        buttons.add(timeButton13);
        buttons.add(timeButton14);
        buttons.add(timeButton15);

        return buttons;
    }

}
