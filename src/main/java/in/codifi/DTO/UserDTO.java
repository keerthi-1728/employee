package in.codifi.DTO;

import javax.json.JsonValue;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class UserDTO {
	
	private String username;
	private String email;
	private String password;
    private String firstname;
    private String lastname;
	
}
