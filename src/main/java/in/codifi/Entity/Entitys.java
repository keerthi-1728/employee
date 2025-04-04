package in.codifi.Entity;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

@Table(name = "employees") // Ensure table name is correct
public class Entitys {

    @Id
    @Column(name = "emp_id") 
    private long id;  

	@Column(name = "emp_name") 
    private String name;

    @Column(name = "emp_address") 
    private String address;

    @Column(name = "emp_city") 
    private String city;

    @Column(name = "emp_salary") 
    private long salary;

    @Column(name = "activestatus")
    private int activestatus;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "created_on", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdOn;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "updated_on", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedOn;

}