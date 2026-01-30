package project.StoreStock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
public class Product implements Comparable<Product>, Serializable {
    @Setter
    private static int counter = 0;

    private int id;

    @NotBlank(message = "Product name is required")
    @Size(max = 20, message = "Product name must be up to 20 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(max = 100, message = "Description must be up to 100 characters")
    private String description;

    @Min(value = 1, message = "Priority must be at least 1")
    @Max(value = 5, message = "Priority must be at most 5")
    private int priority;

    private Supplier supplier;

    public Product(){}


    public Product(String name, String description, int priority, Supplier supplier) {
        System.out.println("3. "+counter);
        id = ++counter;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.supplier = supplier;
    }
    public Product(int id,String name, String description, int priority, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.supplier = supplier;
    }

    @Override
    public int compareTo(Product p) {
        return this.name.compareTo(p.name);
    }
}
