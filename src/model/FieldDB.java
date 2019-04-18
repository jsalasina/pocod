package model;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;

//database handler
public class FieldDB{
    private static String userName = "root";
    private static String password = "qwertyui";
    private static String url = "jdbc:mysql://localhost/test?serverTimezone=Europe/Moscow&useSSL=false&allowPublicKeyRetrieval=true";

    public static void dropTable() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        try(Connection connection = DriverManager.getConnection(url, userName, password)){
            PreparedStatement preparedStatement = connection.prepareStatement("drop table if exists mines ");
            preparedStatement.execute();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTable() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        try(Connection connection = DriverManager.getConnection(url, userName, password)){
            PreparedStatement preparedStatement = connection.prepareStatement("create table mines (id integer auto_increment not null,\n" +
                    "                   value integer not null, status boolean not null default false, flag boolean not null default false, primary key (id))");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insert(Field field) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException{
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        try(Connection connection = DriverManager.getConnection(url, userName, password)){

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO mines(value) VALUES (?)");

            for (int i = 0; i < field.getField().length; i++) {
                for (int j = 0; j < field.getField()[i].length ; j++) {
                    int val = field.getField()[i][j].getValue();
                    preparedStatement.setInt(1, val);
                    preparedStatement.executeUpdate();
                }
                System.out.println("done");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Cell getCell(int id)throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        Cell cell = new Cell();
        try(Connection connection = DriverManager.getConnection(url, userName, password)){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from mines where id = ? and flag = 0");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cell.setId(resultSet.getInt("id"));
                cell.setValue(resultSet.getInt("value"));
                cell.setStatus(resultSet.getBoolean("status"));
                cell.setFlag(resultSet.getBoolean("flag"));
            }
            System.out.println(cell);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cell;
    }

    public static void setFlag(int id, boolean flag) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        try(Connection connection = DriverManager.getConnection(url, userName, password)){
            PreparedStatement preparedStatement = connection.prepareStatement("update mines set flag = ? where id =?");
            preparedStatement.setBoolean(1, flag);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean getFlag(int id) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        boolean flag = false;
        try(Connection connection = DriverManager.getConnection(url, userName, password)){
            PreparedStatement preparedStatement = connection.prepareStatement("select flag from mines where id =?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                flag = resultSet.getBoolean("flag");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void setCellChecked(int id) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        try(Connection connection = DriverManager.getConnection(url, userName, password)){
            PreparedStatement preparedStatement = connection.prepareStatement("update mines set status = true where id =?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Cell> allBombs() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        ArrayList<Cell> bombs = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url, userName, password)){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from mines where value = -1 and status = 0");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Cell cell = new Cell();
                cell.setId(resultSet.getInt("id"));
                cell.setValue(resultSet.getInt("value"));
                cell.setStatus(resultSet.getBoolean("status"));
                FieldDB.setCellChecked(cell.getId());
                bombs.add(cell);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bombs;
    }
    public static boolean cellStatus(int id) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

        try(Connection connection = DriverManager.getConnection(url, userName, password)){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT status from mines where id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                if(resultSet.getBoolean("status")) return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}