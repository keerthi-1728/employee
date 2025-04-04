package in.codifi.EmployeRepository;


import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import in.codifi.Entity.Entitys;

public interface EmployeRepository extends JpaRepository<Entitys, Long> {

	
	/**
	 * Method to get by Name
	 * @author Thirupugal
	 * @return
	 */	
	
	Entitys findByName(String EMP_Name);

	

	/**
	 * Method to get ActiveData
	 * @author Thirupugal
	 * @return
	 */	
	

	List<Entitys> findByActivestatus(int activeStatus);

 
   
	/**
	 * Method to get by createdon Date
	 * @author Thirupugal
	 * @return
	 */	
	  @Transactional
	  
	  @Query("SELECT a FROM person a WHERE Date(a.createdOn) = :createdOn")
	  List<Entitys> getByDate(@Param("createdOn") Date createdOn);
	
	  /**
		 * Method to get by UpdatedOn Date
		 * @author Thirupugal
		 * @return
		 */	
		
	  @Query("SELECT a FROM person a WHERE Date(a.updatedOn)=:updatedOn")
	  List<Entitys> getByUpdateDate(@Param("updatedOn") Date updatedOn);

	  /**
		 * Method to get between Dates
		 * @author Thirupugal
		 * @return
		 */	

	  @Query("SELECT a FROM person a WHERE a.createdOn between :start AND :end")
	  List<Entitys> findByDateRange(@Param("start") Date start,@Param("end") Date end);
	
	  /**
		 * Method to get by Date and City 
		 * @author Thirupugal
		 * @return
		 */	
	
	  @Query("SELECT a FROM person a WHERE Date(a.createdOn) =:start AND a.city =:city")
       List<Entitys> getByDateCity(@Param("start") Date start, @Param("city") String city);
}