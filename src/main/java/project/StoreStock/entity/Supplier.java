package project.StoreStock.entity;

import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Supplier implements Comparable<Supplier>, Serializable {
    private String id;

    @NotBlank(message = "Supplier name is required")
    @Size(max = 30, message = "Supplier name must be up to 30 characters")
    private String name;

    @NotBlank(message = "Phone number is required")
    @Size(max = 15, message = "Phone number must be up to 15 characters")
    private String phone;

    public Supplier(String name, String phone) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.phone = phone;
    }

    @Override
    public int compareTo(Supplier s) {
        return this.name.compareTo(s.name);
    }
}
