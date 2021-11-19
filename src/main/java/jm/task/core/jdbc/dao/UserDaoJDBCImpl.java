package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.postgresql.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    //создаем таблицу User
    @Override
    public void createUsersTable() {
        Class<Driver> driverClass = Driver.class;
        try (Connection connection = Util.getInstance().open(); Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS public.\"User\" (id serial NOT NULL PRIMARY KEY, " +
                    "name character varying(30) NOT NULL, lastName character varying(30) NOT NULL, age smallint NOT NULL)";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //удаляем таблицу User
    @Override
    public void dropUsersTable() {
        Class<Driver> driverClass = Driver.class;
        try (Connection connection = Util.getInstance().open(); Statement statement = connection.createStatement()) {
            String sql = "DROP TABLE IF EXISTS public.\"User\"";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //поиск user-а в таблице
    @Override
    public User findUserInTable(String name1, String lastName1, byte age1) {
        Class<Driver> driverClass = Driver.class;
        User resultFind = null;
        String sql = "SELECT * FROM public.\"User\"";
        try (Connection connection = Util.getInstance().open(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (name1.equals(resultSet.getString("name")) && lastName1.equals(resultSet.getString("lastName"))
                        && age1 == resultSet.getByte("age")) {
                    resultFind = new User(name1, lastName1, age1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultFind;
    }

    //сохраняем User
    @Override
    public void saveUser(String name1, String lastName1, byte age1) {
        Class<Driver> driverClass = Driver.class;
        String sql = "INSERT INTO public.\"User\" (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getInstance().open(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name1);
            statement.setString(2, lastName1);
            statement.setByte(3, age1);
            statement.executeUpdate();
            System.out.println("User с именем – " + name1 + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //удаляем User по его Id
    @Override
    public void removeUserById(long id) {
        Class<Driver> driverClass = Driver.class;
        String sql = "DELETE FROM public.\"User\" WHERE id = ?";
        try (Connection connection = Util.getInstance().open(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //возвращаем всех Users
    @Override
    public List<User> getAllUsers() {
        Class<Driver> driverClass = Driver.class;
        List<User> list = new ArrayList<>();
        try (Connection connection = Util.getInstance().open(); Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM public.\"User\"";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User(resultSet.getString(2), resultSet.getString(3), (Byte) resultSet.getByte(4));
                user.setId(resultSet.getLong(1));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (User user : list) {
            System.out.println(user.toString());
        }
        return list;
    }

    //очищаем таблицу User
    @Override
    public void cleanUsersTable() {
        Class<Driver> driverClass = Driver.class;
        try (Connection connection = Util.getInstance().open(); Statement statement = connection.createStatement()) {
            String sql = "TRUNCATE TABLE public.\"User\"";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
