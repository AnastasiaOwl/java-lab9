package main.server;
import lombok.AllArgsConstructor;
import main.entities.Customer;

import java.sql.*;
import java.sql.Connection;
import java.sql.Date;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class CustomerServer {
    private  Connection connection;

    public List<Customer> findAll() {
        ArrayList<Customer> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("select * from customer")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String surname = rs.getString("surname");
                String name = rs.getString("name");
                String patronymic = rs.getString("patronymic");
                String address = rs.getString("address");
                long cardNumber = rs.getLong("cardNumber");
                double cardBalance = rs.getDouble("cardBalance");
                Date birthDate = rs.getDate("birthDate");
                list.add(new Customer(id, surname, name, patronymic,address,cardNumber,cardBalance,birthDate));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Customer> searchByName(String nameSearch, Scanner scanner) {
        System.out.println("Please, enter name:");
         nameSearch=scanner.nextLine();
        ArrayList<Customer> list = new ArrayList<>();
        try (PreparedStatement ps1 = connection.prepareStatement("select*from customer WHERE name=?")) {
            ps1.setString(1, nameSearch);
            ResultSet rs = ps1.executeQuery();
            {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String surname = rs.getString("surname");
                    String name = rs.getString("name");
                    String patronymic = rs.getString("patronymic");
                    String address = rs.getString("address");
                    long cardNumber = rs.getLong("cardNumber");
                    double cardBalance = rs.getDouble("cardBalance");
                    Date birthDate = rs.getDate("birthDate");
                    list.add(new Customer(id, surname, name, patronymic,address,cardNumber,cardBalance,birthDate));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<Customer> searchByCardNumber( Scanner scanner){
        System.out.println("from:");
        long fromCardNumber = scanner.nextLong();
        System.out.println("to:");
        long toCardNumber = scanner.nextLong();
        ArrayList<main.entities.Customer> list = new ArrayList<>();
        try (PreparedStatement ps1 = connection.prepareStatement("select*from customer WHERE cardNumber>? && cardNumber<?")) {
            ps1.setString(1, String.valueOf(fromCardNumber));
            ps1.setString(2, String.valueOf(toCardNumber));
            ResultSet rs = ps1.executeQuery();
            {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String surname = rs.getString("surname");
                    String name = rs.getString("name");
                    String patronymic = rs.getString("patronymic");
                    String address = rs.getString("address");
                    long cardNumber = rs.getLong("cardNumber");
                    double cardBalance = rs.getDouble("cardBalance");
                    Date birthDate = rs.getDate("birthDate");
                    list.add(new main.entities.Customer(id, surname, name, patronymic,address,cardNumber,cardBalance,birthDate));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public  List<Customer> sortByCardBalance() {
        ArrayList<main.entities.Customer> list = new ArrayList<>();
        try (PreparedStatement ps1 = connection.prepareStatement(" select *from customer WHERE cardBalance<0  ORDER BY cardBalance DESC")) {
            ResultSet rs = ps1.executeQuery();
            {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String surname = rs.getString("surname");
                    String name = rs.getString("name");
                    String patronymic = rs.getString("patronymic");
                    String address = rs.getString("address");
                    long cardNumber = rs.getLong("cardNumber");
                    double cardBalance = rs.getDouble("cardBalance");
                    Date birthDate = rs.getDate("birthDate");
                    list.add(new main.entities.Customer(id, surname, name, patronymic,address,cardNumber,cardBalance,birthDate));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public  int numberOfDebtors(){
       List<Customer> q = sortByCardBalance();
       int count = q.size();
       return count;
    }
    public  List<Customer> sortByCardBalanceAndCardNumber(){
        ArrayList<main.entities.Customer> list = new ArrayList<>();
        try (PreparedStatement ps1 = connection.prepareStatement(" select *from customer ORDER BY cardBalance,cardNumber")) {
            ResultSet rs = ps1.executeQuery();
            {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String surname = rs.getString("surname");
                    String name = rs.getString("name");
                    String patronymic = rs.getString("patronymic");
                    String address = rs.getString("address");
                    long cardNumber = rs.getLong("cardNumber");
                    double cardBalance = rs.getDouble("cardBalance");
                    Date birthDate = rs.getDate("birthDate");
                    list.add(new main.entities.Customer(id, surname, name, patronymic,address,cardNumber,cardBalance,birthDate));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public  List<Integer> getOnlyYear(){
        ArrayList<Integer> list = new ArrayList<>();
        try (PreparedStatement ps1 = connection.prepareStatement(" SELECT DISTINCT year(birthDate) as birthYear FROM customer")) {
            ResultSet rs = ps1.executeQuery();
            {
                while (rs.next()) {
                    int birthYear = rs.getInt("birthYear");
                    list.add(birthYear);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public  Map<Integer,Customer> sortByCardBalanceForEveryYear ()
    {
        Map<Integer, Customer> map = new HashMap<>();
        try (PreparedStatement ps2 = connection.prepareStatement("SELECT year(birthDate) AS birthYear,id,surname,name,patronymic,address,cardNumber,birthDate, MAX(cardBalance) AS maxCardBalance FROM customer GROUP BY birthYear")) {
            ResultSet rs = ps2.executeQuery();
            {
                while (rs.next()) {
                    int birthYear = rs.getInt("birthYear");
                    double maxCardBalance = rs.getDouble("maxCardBalance");
                    int id = rs.getInt("id");
                    String surname = rs.getString("surname");
                    String name = rs.getString("name");
                    String patronymic = rs.getString("patronymic");
                    String address = rs.getString("address");
                    long cardNumber = rs.getLong("cardNumber");
                    Date birthDate = rs.getDate("birthDate");
                    map.put(birthYear, new main.entities.Customer(id, surname, name, patronymic,address,cardNumber, maxCardBalance,birthDate));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}