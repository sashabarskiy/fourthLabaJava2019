package controller;

import entity.GroupEntity;
import entity.StudentEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.DataBaseService;

import java.util.List;

public class Controller {

    DataBaseService dataBaseService;

    @FXML
    private TableView<GroupEntity> tableViewGroups;

    @FXML
    private TableColumn<GroupEntity, Long> idGroupColumn;

    @FXML
    private TableColumn<GroupEntity, String> nameGroupColumn;

    @FXML
    private TableView<StudentEntity> tableViewStudents;

    @FXML
    private TableColumn<StudentEntity, Long> idStudentColumn;

    @FXML
    private TableColumn<StudentEntity, String> nameStudentColumn;

    @FXML
    private TableColumn<StudentEntity, String> surnameStudentColumn;

    @FXML
    private TableColumn<StudentEntity, String> groupStudentColumn;

    @FXML
    private TableColumn<StudentEntity, Integer> markStudentColumn;

    @FXML
    private TextField nameGroupTextField;

    @FXML
    private TextField idGroupTextField;

    @FXML
    private TextField nameStudentTextField;

    @FXML
    private TextField idStudentTextField;

    @FXML
    private TextField surnameStudentTextField;

    @FXML
    private TextField markStudentTextField;

    @FXML
    private ComboBox groupStudentComboBox;

    @FXML
    private TableView<StudentEntity> tableGoodViewStudents;

    @FXML
    private TableColumn<StudentEntity, Long> idGoodStudentColumn;

    @FXML
    private TableColumn<StudentEntity, String> nameGoodStudentColumn;

    @FXML
    private TableColumn<StudentEntity, String> surnameGoodStudentColumn;

    @FXML
    private TableColumn<StudentEntity, String> groupGoodStudentColumn;

    @FXML
    private TableColumn<StudentEntity, Integer> markGoodStudentColumn;

    ObservableList<GroupEntity> convertGroups(List<GroupEntity> list){
        ObservableList<GroupEntity> observable = FXCollections.observableArrayList();
        observable.addAll(list);
        return observable;
    }

    ObservableList<String> convertGroupsNames(List<GroupEntity> list){
        ObservableList<String> observable = FXCollections.observableArrayList();
        for(GroupEntity groupEntity: list)
            observable.add(groupEntity.getName());
        return observable;
    }

    ObservableList<StudentEntity> convertStudents(List<StudentEntity> list){
        ObservableList<StudentEntity> observable = FXCollections.observableArrayList();
        observable.addAll(list);
        return observable;
    }

    @FXML
    public void initialize(){

        System.out.println("start!!!");
        ApplicationContext context = new AnnotationConfigApplicationContext("service");
        dataBaseService = context.getBean(DataBaseService.class);

        // groups
        idGroupColumn.setCellValueFactory(new PropertyValueFactory<GroupEntity, Long>("id"));
        nameGroupColumn.setCellValueFactory(new PropertyValueFactory<GroupEntity, String>("name"));
        List<GroupEntity> groupEntities = dataBaseService.getAllGroups();
        tableViewGroups.setItems(convertGroups(groupEntities));

        groupStudentComboBox.setItems(convertGroupsNames(groupEntities));

        // students

        idStudentColumn.setCellValueFactory(new PropertyValueFactory<StudentEntity, Long>("id"));
        nameStudentColumn.setCellValueFactory(new PropertyValueFactory<StudentEntity, String>("name"));
        surnameStudentColumn.setCellValueFactory(new PropertyValueFactory<StudentEntity, String>("surname"));
        groupStudentColumn.setCellValueFactory(new PropertyValueFactory<StudentEntity, String>("group"));
        markStudentColumn.setCellValueFactory(new PropertyValueFactory<StudentEntity, Integer>("mark"));
        tableViewStudents.setItems(convertStudents(dataBaseService.getAllStudents()));

        // good students

        idGoodStudentColumn.setCellValueFactory(new PropertyValueFactory<StudentEntity, Long>("id"));
        nameGoodStudentColumn.setCellValueFactory(new PropertyValueFactory<StudentEntity, String>("name"));
        surnameGoodStudentColumn.setCellValueFactory(new PropertyValueFactory<StudentEntity, String>("surname"));
        groupGoodStudentColumn.setCellValueFactory(new PropertyValueFactory<StudentEntity, String>("group"));
        markGoodStudentColumn.setCellValueFactory(new PropertyValueFactory<StudentEntity, Integer>("mark"));
        tableGoodViewStudents.setItems(convertStudents(dataBaseService.getAllGoodStudents()));

    }

    @FXML
    private void createGroupHandleButtonAction(ActionEvent event) {
        dataBaseService.createGroup(new GroupEntity(0L,nameGroupTextField.getText().trim()));
        List<GroupEntity> groupEntities = dataBaseService.getAllGroups();
        tableViewGroups.setItems(convertGroups(groupEntities));
        groupStudentComboBox.setItems(convertGroupsNames(groupEntities));
    }

    @FXML
    private void deleteGroupHandleButtonAction(ActionEvent event) {
        dataBaseService.deleteGroup(Long.parseLong(idGroupTextField.getText().trim()));
        List<GroupEntity> groupEntities = dataBaseService.getAllGroups();
        tableViewGroups.setItems(convertGroups(groupEntities));
        groupStudentComboBox.setItems(convertGroupsNames(groupEntities));
    }

    @FXML
    private void updateGroupHandleButtonAction(ActionEvent event) {
        dataBaseService.updateGroup(new GroupEntity(Long.parseLong(idGroupTextField.getText().trim()),nameGroupTextField.getText().trim()));
        List<GroupEntity> groupEntities = dataBaseService.getAllGroups();
        tableViewGroups.setItems(convertGroups(groupEntities));
        groupStudentComboBox.setItems(convertGroupsNames(groupEntities));
    }

    @FXML
    private void deleteStudentHandleButtonAction(ActionEvent event) {
        dataBaseService.deleteStudent(Long.parseLong(idStudentTextField.getText().trim()));
        tableViewStudents.setItems(convertStudents(dataBaseService.getAllStudents()));
        tableGoodViewStudents.setItems(convertStudents(dataBaseService.getAllGoodStudents()));
    }

    @FXML
    private void updateStudentHandleButtonAction(ActionEvent event) {
        dataBaseService.updateStudent(new StudentEntity(Long.parseLong(idStudentTextField.getText().trim()),
                nameStudentTextField.getText().trim(),
                surnameStudentTextField.getText().trim(),
                groupStudentComboBox.getValue().toString(),
                Integer.parseInt(markStudentTextField.getText().trim())
                ));
        tableViewStudents.setItems(convertStudents(dataBaseService.getAllStudents()));
        tableGoodViewStudents.setItems(convertStudents(dataBaseService.getAllGoodStudents()));
    }

    @FXML
    private void createStudentHandleButtonAction(ActionEvent event) {
        dataBaseService.createStudent(new StudentEntity(0L,
                nameStudentTextField.getText().trim(),
                surnameStudentTextField.getText().trim(),
                groupStudentComboBox.getValue().toString(),
                Integer.parseInt(markStudentTextField.getText().trim())
        ));
        tableViewStudents.setItems(convertStudents(dataBaseService.getAllStudents()));
        tableGoodViewStudents.setItems(convertStudents(dataBaseService.getAllGoodStudents()));
    }


}
