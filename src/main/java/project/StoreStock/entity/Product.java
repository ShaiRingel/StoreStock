package project.StoreStock.entity;

import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Product implements Comparable<Product>, Serializable {
    @Setter
    private static int counter = 0;

    @Positive(message = "Id must be positive!")
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

    public Product(String name, String description, int priority, Supplier supplier) {
        id = ++counter;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.supplier = supplier;
    }

    ;

    @Override
    public int compareTo(Product p) {
        return this.name.compareTo(p.name);
    }
}
