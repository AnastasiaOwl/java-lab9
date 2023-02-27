package org.pack;

import org.mariadb.jdbc.client.column.YearColumn;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        try {
            Connection owl = DriverManager.getConnection("jdbc:mariadb://localhost:3306/lab9", "Owl", "1");
            showAll(owl);
            System.out.println("\nSearch by name:");
            searchByName(owl,scanner);
            System.out.println("\nSearch by cardNumber:");
            searchByCardNumber(owl,scanner);
            System.out.println("\nSort by cardBalance:");
            sortByCardBalance(owl);
            System.out.println("\nSort by cardNumber and cardBalance:");
            sortByCardBalanceAndCardNumber(owl);
            System.out.println("\nOnly birthYear:");
            getOnlyYear(owl);
            System.out.println("\nSort by cardBalance for every year:");
            sortByCardBalanceForEveryYear (owl);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void showAll(Connection owl) {
        try (PreparedStatement ps = owl.prepareStatement("select*from customer");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String surname = rs.getString("surname");
                String name = rs.getString("name");
                String patronymic = rs.getString("patronymic");
                String address = rs.getString("address");
                long cardNumber = rs.getLong("cardNumber");
                double cardBalance = rs.getDouble("cardBalance");
                Date birthDate = rs.getDate("birthDate");
                System.out.println(id + ". " + surname + ", " + name + ", " + patronymic + ", " + address + ", " + cardNumber + ", " + cardBalance + ", " + birthDate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void searchByName(Connection owl, Scanner scanner) {
        String nameSearch = scanner.next();
        try (PreparedStatement ps1 = owl.prepareStatement("select*from customer WHERE name=?")) {
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
                    System.out.println(+id + ". " + surname + ", " + name + ", " + patronymic + ", " + address + ", " + cardNumber + ", " + cardBalance + ", " + birthDate);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void searchByCardNumber(Connection owl, Scanner scanner){
        System.out.println("from:");
        long fromCardNumber = scanner.nextLong();
        System.out.println("to:");
        long toCardNumber = scanner.nextLong();
        try (PreparedStatement ps1 = owl.prepareStatement("select*from customer WHERE cardNumber>? && cardNumber<?")) {
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
                    System.out.println(+id + ". " + surname + ", " + name + ", " + patronymic + ", " + address + ", " + cardNumber + ", " + cardBalance + ", " + birthDate);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void sortByCardBalance(Connection owl) {
        try (PreparedStatement ps1 = owl.prepareStatement(" select *from customer WHERE cardBalance<0  ORDER BY cardBalance DESC")) {
            ResultSet rs = ps1.executeQuery();
            int count = 0;
            {
                while (rs.next()) {
                    count++;
                    int id = rs.getInt("id");
                    String surname = rs.getString("surname");
                    String name = rs.getString("name");
                    String patronymic = rs.getString("patronymic");
                    String address = rs.getString("address");
                    long cardNumber = rs.getLong("cardNumber");
                    double cardBalance = rs.getDouble("cardBalance");
                    Date birthDate = rs.getDate("birthDate");
                    System.out.println(+id + ". " + surname + ", " + name + ", " + patronymic + ", " + address + ", " + cardNumber + ", " + cardBalance + ", " + birthDate);
                }
                System.out.println("number of debtors=" + count);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void sortByCardBalanceAndCardNumber(Connection owl){
        try (PreparedStatement ps1 = owl.prepareStatement(" select *from customer ORDER BY cardBalance,cardNumber")) {
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
                    System.out.println(+id + ". " + surname + ", " + name + ", " + patronymic + ", " + address + ", " + cardNumber + ", " + cardBalance + ", " + birthDate);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void getOnlyYear(Connection owl){
        try (PreparedStatement ps1 = owl.prepareStatement(" SELECT DISTINCT year(birthDate) as birthYear FROM customer")) {
            ResultSet rs = ps1.executeQuery();
            {
                while (rs.next()) {
                    int birthYear = rs.getInt("birthYear");
                    System.out.println(birthYear);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void sortByCardBalanceForEveryYear (Connection owl)
    {
        try (PreparedStatement ps2 = owl.prepareStatement("SELECT year(birthDate) AS birthYear,id,surname,name,patronymic,address,cardNumber,birthDate, MAX(cardBalance) AS maxCardBalance FROM customer GROUP BY birthYear")) {
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
                    System.out.println(birthYear + "- " + id + ". " + surname + ", " + name + ", " + patronymic + ", " + address + ", " + cardNumber + ", " + maxCardBalance + ", " + birthDate);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}