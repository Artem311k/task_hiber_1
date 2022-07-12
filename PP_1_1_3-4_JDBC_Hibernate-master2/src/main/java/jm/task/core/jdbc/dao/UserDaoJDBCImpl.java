package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection JDBC_Connection = Util.getConnection();




    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Statement statement = JDBC_Connection.createStatement()){
                statement.executeUpdate(
                        "create table if not exists User(id bigint not null auto_increment, " +
                                "name varchar(100)," +
                                "lastname varchar(100)," +
                                "age tinyint," +
                                "PRIMARY KEY (id))");
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        try (Statement statement = JDBC_Connection.createStatement()) {
            statement.executeUpdate("drop table if exists User");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void saveUser(String name, String lastName, byte age) {

        try (Statement statement = JDBC_Connection.createStatement()) {
            statement.executeUpdate(String.format(
                    "insert into User(name, lastname, age) values ('%s', '%s', %d)", name, lastName, age));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        try (Statement statement = JDBC_Connection.createStatement()) {
            statement.executeUpdate(String.format("delete from User where id=%d", id));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<>();
        try (Statement statement = JDBC_Connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from user");
            while(resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        (byte) resultSet.getInt("age"));
                user.setId((long) resultSet.getInt("id"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {

        try (Statement statement = JDBC_Connection.createStatement()) {
            statement.executeUpdate("truncate table User");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}
