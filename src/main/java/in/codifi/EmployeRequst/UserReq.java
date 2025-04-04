package in.codifi.EmployeRequst;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  
@AllArgsConstructor 
public class UserReq {
    private long id;
    private String name;
    private String address;
    private String city;
    private int age;
    private long number;
    private String email;
    private boolean status;
    
    public UserReq(String name, String address, String city, int age, long number, String email, boolean status) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.age = age;
        this.number = number;
        this.email = email;
        this.status = status;
    }
    
}

