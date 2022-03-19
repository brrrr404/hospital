package com.example.hospital;

import Entity.*;
import Entity.Record;
import exception.ControllerException;
import lombok.SneakyThrows;
import methods.User;
import methods.DateAndTime;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Date;


public class BD {
    private final String HOST = "127.0.0.1";
    private final String PORT = "3306";
    private final String DB_NAME = "hospital";
    private final String LOGIN = "root";
    private final String PASS = "root";
    private final String NO_NEW_RECORD_IN_BD = "Ошибка записи в базу данных";

    public static User user;

    private Connection dbConn = null;

    // Метод для подключения к БД с использованием значений выше
    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    //авторизация
    public User auth(String login, String password) throws SQLException, ClassNotFoundException {
        String auth = "SELECT * FROM autorization WHERE login =? AND password=?";

        Patient patient = new Patient();
        Doctor doctor = new Doctor();

        PreparedStatement statement = getDbConnection().prepareStatement(auth);
        statement.setString(1,login);
        statement.setString(2,password);
        ResultSet res = statement.executeQuery();

        while (res.next()) {
            doctor.setId(res.getInt("id_doctor"));
            patient.setId(res.getInt("id_patient"));
        }
        if(doctor.getID()>0){
            return user = getDoctor(doctor.getID());
        }
        else if(patient.getID()>0){
            return user = userHello(patient.getID());
        }
        return null;
    }

    //поиск пользователя в бд и заполнение его данных по входным данным
    public Patient userHello(Integer newUser) throws SQLException, ClassNotFoundException {
            Patient patient = new Patient();
            String request = "SELECT * FROM patients WHERE id_patient = " + newUser;

            Statement statement = getDbConnection().createStatement();
            ResultSet newResult = statement.executeQuery(request);
            while (newResult.next()) {
                patient.setId(newResult.getInt("id_patient"));
                patient.setFio(newResult.getString("fio_patient"));
                patient.setPlace(newResult.getString("place_of_residence"));
                patient.setNumber(newResult.getInt("phone"));
                patient.setMail(newResult.getString("email"));
                patient.setMale(newResult.getString("male"));
            }
            return patient;
    }


@SneakyThrows
    public Department getDepartment(String nameDepartment){
        String request = "SELECT * FROM `department` WHERE name_department= ?";
        PreparedStatement statement = getDbConnection().prepareStatement(request);
        statement.setString(1,nameDepartment);

        ResultSet getDepartment = statement.executeQuery();

        Department department = new Department();
        while(getDepartment.next()){
            department.setName(nameDepartment);
            department.setHeadOfDepartment(getDepartment.getString("head_department"));
        }
        return department;
    }

    // Метод для регистрации нового пользователя
    public int registNewUser(String name, String surname, String middleName, String email, int number, String place, String login, String password, String male) throws SQLException, ClassNotFoundException {
        String newUser = "INSERT INTO `patients` (fio_patient, phone, email, place_of_residence, male) VALUES (?, ?, ?, ?, ?)";
        String userReg = "INSERT INTO `autorization` (id_patient, login, password) VALUES (?, ?, ?)";
        String idUser = "SELECT * FROM patients WHERE id_patient=(SELECT MAX(id_patient) FROM patients)";

        //передаем информацию о новом пользователе без пароля и логина
        PreparedStatement prStUser = getDbConnection().prepareStatement(newUser);
        prStUser.setString(1, surname + " " + name + " " + middleName);
        prStUser.setInt(2, number);
        prStUser.setString(3, email);
        prStUser.setString(4, place);
        prStUser.setString(5, male);

        if(prStUser.executeUpdate()<=0){
            new ControllerException("Ошибка регистрации!");
        }

        //получаем новый id пользователя из БД
        Statement statement = getDbConnection().createStatement();
        //создаем массив результатов
        ResultSet res = statement.executeQuery(idUser);

        //достаем из массива новый id
        int newID = 0;
        while (res.next()) {
            newID = Integer.parseInt(res.getString("id_patient"));
        }

        //создаем аккаунт в авторизации
        PreparedStatement prStReg = getDbConnection().prepareStatement(userReg);
        prStReg.setInt(1, newID);
        prStReg.setString(2, login);
        prStReg.setString(3, password);

        user = auth(login, password);

        return prStReg.executeUpdate();

    }

    //проверка на свободный логин
    public boolean existLogin(String login) throws SQLException, ClassNotFoundException {
        String sql = "SELECT login FROM autorization WHERE login=?";

        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, login);
        ResultSet res = statement.executeQuery();

        res.next();

        return res.getRow()!= 0;
    }

    //получаем доктора по id в момент авторизации
    @SneakyThrows
    public Doctor getDoctor(Integer idDoctor){
        Doctor doctor = new Doctor();
            String request = "SELECT * FROM `doctors` WHERE id_doctor=?";
            PreparedStatement statement = getDbConnection().prepareStatement(request);
            statement.setInt(1,idDoctor);

            ResultSet getDoctor = statement.executeQuery();
            while (getDoctor.next()) {
                doctor.setId(getDoctor.getInt("id_doctor"));
                doctor.setName(getDoctor.getString("fio_doctor"));
                doctor.setDepartment(getDepartment(getDoctor.getString("name_department")));
                doctor.setAppointment(getDoctor.getString("appointment"));
            }
            return doctor;
    }

  //получаем доктора по ФИО в момент выбора врача пациентом
    @SneakyThrows
    public Doctor getDoctor(String fioDoctor) {
        String sql = "SELECT * FROM doctors WHERE fio_doctor=?";

        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, fioDoctor);
        ResultSet res = statement.executeQuery();

        Doctor doctor = new Doctor();

        while (res.next()) {
            doctor.setId(res.getInt("id_doctor"));
            doctor.setName(res.getString("fio_doctor"));
            doctor.setDepartment(getDepartment(res.getString("name_department")));
            doctor.setAppointment(res.getString("appointment"));
        }
        return doctor;
    }

    //получить докторов определнных специализаций
    @SneakyThrows
    public List<Doctor> getDoctors(String departmentName) {
        String sql = "SELECT * FROM doctors WHERE name_department=?";
        PreparedStatement statement = getDbConnection().prepareStatement(sql);
        statement.setString(1, departmentName);

        ResultSet res = statement.executeQuery();

        ArrayList<Doctor> doctors = new ArrayList<>();
        while (res.next()) {
            Doctor doctor = new Doctor();
            doctor.setId(res.getInt("id_doctor"));
            doctor.setName(res.getString("fio_doctor"));
            doctor.setDepartment(getDepartment(res.getString("name_department")));
            doctor.setAppointment(res.getString("appointment"));
            doctors.add(doctor);
        }
        return doctors;

    }

    //получить всех врачей что есть в бд
    @SneakyThrows
    public List<Doctor> getAllDoctors() {

        String sql = "SELECT * FROM doctors";
        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<Doctor> doctors = new ArrayList<>();
        while (res.next()) {
            Doctor doctor = new Doctor();
            doctor.setId(res.getInt("id_doctor"));
            doctor.setName(res.getString("fio_doctor"));
            doctor.setDepartment(getDepartment(res.getString("name_department")));
            doctor.setAppointment(res.getString("appointment"));
            doctors.add(doctor);
        }
        return doctors;
    }

    //получить все департаменты что есть в бд
    @SneakyThrows
    public List<Department> getDepartments() {
        String sql = "SELECT * FROM department";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<Department> departments = new ArrayList<>();

        while (res.next()) {
            Department department = new Department();
            department.setName(res.getString("name_department"));
            department.setHeadOfDepartment(res.getString("head_department"));
            departments.add(department);
        }
        return departments;
    }

    //получаем все доступное время для записи для определенного врача
    @SneakyThrows
    public List<Record> getAllRecords(String name, LocalDate date) {
        String recordsRequest = "SELECT * FROM `recordsession` WHERE `id_doctor`=? AND `date`=? AND `archive`=?";
        String doctorRequest = "SELECT `id_doctor` FROM `doctors` WHERE fio_doctor = ?";
        PreparedStatement statementDoctors = getDbConnection().prepareStatement(doctorRequest);
        statementDoctors.setString(1, name);
        ResultSet resDoctors = statementDoctors.executeQuery();
        resDoctors.next();

        PreparedStatement statementRecords = getDbConnection().prepareStatement(recordsRequest);
        statementRecords.setInt(1, resDoctors.getInt("id_doctor"));
        statementRecords.setDate(2, java.sql.Date.valueOf(date));
        statementRecords.setInt(3, 0);

        ResultSet resRecords = statementRecords.executeQuery();

        ArrayList<Record> records = new ArrayList<>();

        while (resRecords.next()) {
            Record record = new Record();
            record.setId(resRecords.getInt("id_record"));
            record.setIdPatient(resRecords.getInt("id_patient"));
            record.setIdDoctor(resRecords.getInt("id_doctor"));
            record.setDate(resRecords.getDate("date"));
            record.setTime(resRecords.getTime("time"));
            record.setArchive(resRecords.getBoolean("archive"));

            records.add(record);
        }

        Collections.sort(records);

        return records;
    }

    //создаем запись и закрываем к ней доступ для новых записей на это время
    //возвращаем записанное значение
    @SneakyThrows(value = {SQLException.class, ClassNotFoundException.class})
    public Treatment setRecords(String time, LocalDate date, int doctorID) {
        String updRecord = "UPDATE `recordsession` SET id_patient=?  WHERE time=? AND date=? AND id_doctor=?";
        String trueNewRecord = "UPDATE `recordsession` SET archive=?  WHERE time=? AND date=? AND id_doctor=?";
        String newRecord = "SELECT * FROM `recordsession` WHERE time=? AND date=? AND id_doctor=?";



        PreparedStatement statement = getDbConnection().prepareStatement(updRecord);
        statement.setInt(1, user.getID());
        statement.setTime(2, java.sql.Time.valueOf(time));
        statement.setDate(3, java.sql.Date.valueOf(date));
        statement.setInt(4, doctorID);


            if (statement.executeUpdate() <= 0) {
                new ControllerException(NO_NEW_RECORD_IN_BD);

            } else {
                PreparedStatement statementNewRecord = getDbConnection().prepareStatement(trueNewRecord);
                statementNewRecord.setInt(1, 1);
                statementNewRecord.setTime(2, Time.valueOf(time));
                statementNewRecord.setDate(3, java.sql.Date.valueOf(date));
                statementNewRecord.setInt(4, doctorID);

                statementNewRecord.executeUpdate();

            }

        PreparedStatement newRecordStatement = getDbConnection().prepareStatement(newRecord);
        newRecordStatement.setTime(1, Time.valueOf(time));
        newRecordStatement.setDate(2, java.sql.Date.valueOf(date));
        newRecordStatement.setInt(3, doctorID);

        ResultSet newRecordResult = newRecordStatement.executeQuery();

        Record newRecordObject = new Record();

        newRecordResult.next();

            newRecordObject.setId(newRecordResult.getInt("id_record"));
            newRecordObject.setIdPatient(newRecordResult.getInt("id_patient"));
            newRecordObject.setIdDoctor(newRecordResult.getInt("id_doctor"));
            newRecordObject.setTime(newRecordResult.getTime("time"));
            newRecordObject.setDate(newRecordResult.getDate("date"));
            newRecordObject.setArchive(newRecordResult.getBoolean("archive"));


        return setTreatments(newRecordObject);
    }

    //создаем обращенение и возвращаем его
    @SneakyThrows
    public Treatment setTreatments(Record record){
        String setTreatment = "INSERT INTO `treatments` (id_record, id_patient, id_doctor, name_department) VALUES (?, ?, ?, ?)";
        String getDepartment = "SELECT name_department FROM `doctors` WHERE id_doctor=?";
        String getTreatment = "SELECT * FROM `treatments` WHERE id_record=?";

        PreparedStatement getDepartmentStatement = getDbConnection().prepareStatement(getDepartment);
        getDepartmentStatement.setInt(1, record.getIdDoctor());

        ResultSet getDepartmentResult = getDepartmentStatement.executeQuery();
        getDepartmentResult.next();

        PreparedStatement setTreatmentStatement = getDbConnection().prepareStatement(setTreatment);

        setTreatmentStatement.setInt(1, record.getId());
        setTreatmentStatement.setInt(2, record.getIdPatient());
        setTreatmentStatement.setInt(3, record.getIdDoctor());
        setTreatmentStatement.setString(4, getDepartmentResult.getString("name_department"));

       setTreatmentStatement.executeUpdate();

        PreparedStatement getTreatmentStatement = getDbConnection().prepareStatement(getTreatment);

        getTreatmentStatement.setInt(1, record.getId());
        ResultSet getTreatmentResult = getTreatmentStatement.executeQuery();

        Treatment treatment = new Treatment();

       while(getTreatmentResult.next()){
           treatment.setId(getTreatmentResult.getInt("id_treatment"));
           treatment.setIdPatient(getTreatmentResult.getInt("id_patient"));
           treatment.setIdDoctor(getTreatmentResult.getInt("id_doctor"));
           treatment.setNameDepartment(getTreatmentResult.getString("name_department"));
       }

       return Patient.treatment=treatment;
    }

    @SneakyThrows
    public List <Treatment> getHistory(String fioPatient){
        String getIDPatient = "SELECT id_patient FROM patients WHERE fio_patient=?";
        String getTreatment = "SELECT * FROM treatments WHERE id_patient=?";
        String getDoctor = "SELECT fio_doctor FROM doctors WHERE id_doctor=?";
        String getTimeAndDate = "SELECT * FROM recordsession WHERE id_record=?";

        //подключаемся к таблице пациентов
        PreparedStatement getIDPatientStatement = getDbConnection().prepareStatement(getIDPatient);

        getIDPatientStatement.setString(1, fioPatient);
        ResultSet getIDPatientResult = getIDPatientStatement.executeQuery();

        getIDPatientResult.next();

        //подключаемся к таблице записей
        PreparedStatement getTreatmentStatement = getDbConnection().prepareStatement(getTreatment);
        getTreatmentStatement.setString(1, getIDPatientResult.getString("id_patient"));

        ResultSet getTreatmentResult = getTreatmentStatement.executeQuery();

        List<Treatment> treatments = new ArrayList<>();

        while (getTreatmentResult.next()){
            Treatment treatment = new Treatment();
            treatment.setId(getTreatmentResult.getInt("id_treatment"));
            treatment.setIdPatient(getTreatmentResult.getInt("id_patient"));
            treatment.setIdDoctor(getTreatmentResult.getInt("id_doctor"));
            treatment.setIdRecord(getTreatmentResult.getInt("id_record"));
            treatment.setNameDepartment(getTreatmentResult.getString("name_department"));
            treatment.setComment(getTreatmentResult.getString("comment"));

            treatments.add(treatment);
        }

        PreparedStatement getDoctorStatement = getDbConnection().prepareStatement(getDoctor);
        PreparedStatement getTimeAndDateStatement = getDbConnection().prepareStatement(getTimeAndDate);
        for(Treatment i : treatments){
            getDoctorStatement.setInt(1, i.getIdDoctor());
            getTimeAndDateStatement.setInt(1, i.getIdRecord());

            ResultSet getDoctorResult = getDoctorStatement.executeQuery();
            ResultSet getTimeAndDateResult = getTimeAndDateStatement.executeQuery();

            getDoctorResult.next();
            getTimeAndDateResult.next();

            i.setFioDoctor(getDoctorResult.getString("fio_doctor"));
            i.setDate(modifyDateLayout(getTimeAndDateResult.getDate("date"), getTimeAndDateResult.getTime("time")));
        }

        return treatments;
    }

    @SneakyThrows
    public void addComment(Treatment treatment){
        String setNewComment = "UPDATE treatments SET comment=? WHERE id_record=?";

        PreparedStatement setNewCommentStatement = getDbConnection().prepareStatement(setNewComment);
        setNewCommentStatement.setString(1, treatment.getComment());
        setNewCommentStatement.setInt(2, treatment.getIdRecord());

       if(setNewCommentStatement.executeUpdate()<=0){
           new ControllerException(NO_NEW_RECORD_IN_BD);
       }
    }

    private String modifyDateLayout(java.sql.Date oldDate, java.sql.Time time) throws ParseException {

        Date newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldDate.toString() + " " + time.toString());
        return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(newDate);
    }

  @SneakyThrows
  public void addJob(String fioDoctor, Time bgTime, Time endTime, LocalDate date){
        String newJob = "INSERT INTO `recordsession` (id_doctor, date, time) VALUES (?, ?, ?)";

      LocalTime endLocalTime = endTime.toLocalTime();
      LocalTime beginLocalTime = bgTime.toLocalTime();
      long duration = Duration.between(beginLocalTime, endLocalTime).toMinutes();

      PreparedStatement newJobStatement = getDbConnection().prepareStatement(newJob);

      for(int i = 0; i<duration; i=i+15){
          newJobStatement.setInt(1, getDoctor(fioDoctor).getID());
          newJobStatement.setDate(2, java.sql.Date.valueOf(date));

          long hoursInMinute = beginLocalTime.getHour()*60;
          long newMinute = beginLocalTime.getMinute()+hoursInMinute+i;
          newJobStatement.setTime(3, Time.valueOf(parseHours(newMinute)));
          newJobStatement.executeUpdate();
      }
  }


  private LocalTime parseHours(long ing){
        int newInt;
        if(ing%60==0){
            newInt=(int)ing/60;
            return LocalTime.of(newInt,00);
        }
        else{
           newInt=(int)Math.floor(ing/60);
           return LocalTime.of(newInt,(int) (ing-(newInt*60)));
        }
  }
}

