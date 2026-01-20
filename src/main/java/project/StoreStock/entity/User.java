package project.StoreStock.entity;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class User implements Comparable<User>, Serializable {
	@Setter
	private static int counter = 0;

	@Positive(message = "Id must be positive!")
	private int id;

	private String username;
	@ToString.Exclude
	private String password;
	private Boolean hasPermission;

	@Override
	public int compareTo(User u) {
		return this.username.compareTo(u.username);
	}
}
