package project.StoreStock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.*;

import java.beans.Transient;
import java.io.Serializable;


@Data
@ToString
@EqualsAndHashCode
public class Supplier implements Comparable<Supplier>, Serializable {
    @Setter
    private static int counter = 0;

    private int id ;




    @NotBlank(message = "Supplier name is required")
    @Size(max = 30, message = "Supplier name must be up to 30 characters")
    private String name;

    @NotBlank(message = "Phone number is required")
    @Size(max = 10, message = "Phone number must be up to 10 characters")
    private String phone;



    public Supplier(String name, String phone) {
        id = ++counter;
        this.name = name;
        this.phone = phone;
    }
    public Supplier(){}


     public Supplier(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    @Override
    public int compareTo(Supplier s) {
        return this.name.compareTo(s.name);
    }

}
