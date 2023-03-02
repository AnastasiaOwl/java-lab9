package main.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Customer {
    private int id;
    private String surname;
    private  String name;
    private  String patronymic;
    private String address;
    private  long cardNumber;
    private  double cardBalance;
    private Date birthDate;

}