package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.MakingConnection;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl() {
        this.userDao = MakingConnection.returnSpecificDao();
    }

    //создаем таблицу User
    public void createUsersTable() {
        userDao.createUsersTable();
    }

    //удаляем таблицу User
    public void dropUsersTable() throws SQLException {
        userDao.dropUsersTable();
    }

    //сохраняем User
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        if (userDao.findUserInTable(name, lastName, age) == null) {
            userDao.saveUser(name, lastName, age);
        } else {
            System.out.println("User с именем – " + name + " не добавлен в базу данных, так как данный User уже есть в базе данных");
        }
    }

    //удаляем User по его Id
    public void removeUserById(long id) throws SQLException {
        userDao.removeUserById(id);
    }

    //возвращаем всех Users
    public List<User> getAllUsers() throws SQLException {
        return userDao.getAllUsers();
    }

    //очищаем таблицу User
    public void cleanUsersTable() throws SQLException {
        userDao.cleanUsersTable();
    }
}
