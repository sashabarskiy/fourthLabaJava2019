package service;

import entity.GroupEntity;
import entity.StudentEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

@Service
@PropertySource("application.yml")
public class DataBaseService {

    @Value("${url}")
    private String DB_URL;

    @Value("${username}")
    private String USER;

    @Value("${password}")
    private String PASS;

    private Connection getConnection() {
        Connection connection = null;
        try {
            DriverManager.deregisterDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return connection;
    }

    // CCC

    public void createGroup(GroupEntity groupEntity){
        Connection connection = getConnection();
        try{
            connection.createStatement().executeUpdate("insert into \"group\" (name) values ('" + groupEntity.getName() + "');" );
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public void createStudent(StudentEntity studentEntity){
        Connection connection = getConnection();
        try{
            String query = "insert into student (name,surname,\"group\",mark) values ('" + studentEntity.getName() + "','" + studentEntity.getSurname() + "'," + getGroupId(studentEntity.getGroup()) + ","+ studentEntity.getMark() +");";
            connection.createStatement().executeUpdate(query);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    // RRR

    public List<GroupEntity> getAllGroups(){
        Connection connection = getConnection();
        List<GroupEntity> groupEntities = new LinkedList<GroupEntity>();
        try{
            ResultSet resultSet = connection.createStatement().executeQuery("select * from \"group\";");
            while(resultSet.next())
                groupEntities.add(new GroupEntity(resultSet.getLong("id"),
                        resultSet.getString("name")));
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        return groupEntities;
    }

    public List<StudentEntity> getAllStudents(){
        Connection connection = getConnection();
        List<StudentEntity> studentEntities = new LinkedList<StudentEntity>();
        try{
            ResultSet resultSet = connection.createStatement().executeQuery("select * from student");
            while(resultSet.next())
                studentEntities.add(new StudentEntity(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        getGroup(resultSet.getLong("group")).getName(),
                        resultSet.getInt("mark")));
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        return studentEntities;
    }

    public List<StudentEntity> getAllGoodStudents(){
        Connection connection = getConnection();
        List<StudentEntity> studentEntities = new LinkedList<StudentEntity>();
        try{
            ResultSet resultSet = connection.createStatement().executeQuery("select * from student where mark >= 4");
            while(resultSet.next())
                studentEntities.add(new StudentEntity(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        getGroup(resultSet.getLong("group")).getName(),
                        resultSet.getInt("mark")));
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        return studentEntities;
    }

    public GroupEntity getGroup(Long id){
        Connection connection = getConnection();
        try{
            ResultSet resultSet = connection.createStatement().executeQuery("select * from \"group\" where id = "+id+";");
            resultSet.next();
            return new GroupEntity(resultSet.getLong("id"),
                    resultSet.getString("name"));
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }

    public Long getGroupId(String name){
        Connection connection = getConnection();
        try{
            String query = "select * from \"group\" where name = '"+name+"';";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            resultSet.next();
            return resultSet.getLong("id");
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }

    // UUU

    public void updateGroup(GroupEntity groupEntity){
        Connection connection = getConnection();
        try{
            connection.createStatement().executeUpdate("update \"group\" set name = '" + groupEntity.getName() + "' where id = "+groupEntity.getId()+";" );
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public void updateStudent(StudentEntity studentEntity){
        Connection connection = getConnection();
        try{
            String query = "update student set name = '" + studentEntity.getName() + "', surname = '" + studentEntity.getSurname() + "', \"group\" = " + getGroupId(studentEntity.getGroup()) + ", mark = " + studentEntity.getMark() +" where id = "+studentEntity.getId()+";";
            connection.createStatement().executeUpdate(query);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    // DDD

    public void deleteGroup(Long id){
        Connection connection = getConnection();
        try{
            connection.createStatement().executeUpdate("delete from \"group\" where id = "+id+";");
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    public void deleteStudent(Long id){
        Connection connection = getConnection();
        try{
            connection.createStatement().executeUpdate("delete from student where id = "+id+";");
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }



}
