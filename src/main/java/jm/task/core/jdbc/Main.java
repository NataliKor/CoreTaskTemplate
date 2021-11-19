package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        User user1 = new User("Петр", "Табуреткин", (byte) 29);
        User user2 = new User("Дмитрий", "Рыжиков", (byte) 30);
        User user3 = new User("Анна", "Розочкина", (byte) 25);
        User user4 = new User("Екатерина", "Шапочкина", (byte) 27);

        User user5 = new User("Дмитрий", "Рыжиков", (byte) 30);

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        userService.saveUser(user5.getName(), user5.getLastName(), user5.getAge());

        userService.getAllUsers();
        userService.removeUserById(2);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
