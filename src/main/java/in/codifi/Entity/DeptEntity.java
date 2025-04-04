package in.codifi.Entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "dept")
public class DeptEntity {
	
	@Id
	@Column(name = "dept_id") 
	private long id;  

	@Column(name = "dept_name") 
	private String name;

	@Column(name = "dept_age") 
	private int age;

	@Column(name = "dept_dept") 
	private String dept;

	@Column(name="dept_headofdept")
	private String headofdept;

	@Column(name = "dept_totalstudents") 
	private int totalstudents;

	@Column(name = "dept_location") 
	private String location;

	@Column(name = "dept_establishedyear") 
	private int establishedyear;

}

