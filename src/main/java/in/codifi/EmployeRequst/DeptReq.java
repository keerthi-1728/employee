package in.codifi.EmployeRequst;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeptReq {
    private long id;
    private String name;
    private int age;
    private String dept;
    private String headofdept ;
    private int totalstudents;
    private String location;
    private int establishedyear;
}