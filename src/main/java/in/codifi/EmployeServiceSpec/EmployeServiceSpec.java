package in.codifi.EmployeServiceSpec;

import java.util.List;

import org.jboss.resteasy.reactive.RestResponse;

import in.codifi.EmployeRequst.EmployeReq;
import in.codifi.Responce.GenericResponse;

public interface EmployeServiceSpec {
        
	/**
	 * method to get all data 
	 * @author Thirupugal
	 * @return
	 */
	RestResponse<GenericResponse> getAllData();
	     
	/**
	 * Method to get Details by Name
	 * @author Thirupugal
	 * @return
	 */
	RestResponse<GenericResponse> GetByName(EmployeReq req);
     
	/**
	 * Method to get Details by Id
	 * @author Thirupugal
	 * @return
	 */	
	
	RestResponse<GenericResponse> GetByID(EmployeReq req);

	/**
	 * Method to Add Data 
	 * @author Thirupugal
	 * @return
	 */	
	
	RestResponse<GenericResponse> AddData(EmployeReq req);
	
	/**
	 * Method to Delete Data
	 * @author Thirupugal
	 * @return
	 */	
	RestResponse<GenericResponse> DeleteData(EmployeReq req);
	
	/**
	 * Method to  UpdateData
	 * @author Thirupugal
	 * @return
	 */	
	
	RestResponse<GenericResponse> UpdateData(EmployeReq req);
	/*
	 * RestResponse<GenericResponse> getDetailsbtwdate( LocalDate start,LocalDate
	 * end);
	 */
	/**
	 * Method to  get Activedata
	 * @author Thirupugal
	 * @return
	 */	
	 RestResponse<GenericResponse>activeStatus();
	 /**
		 * Method to  change Activedata
		 * @author Thirupugal
		 * @return
		 */	
	 RestResponse<GenericResponse>changestatus(EmployeReq req);
	 /**
		 * Method to Details  get by createdon date 
		 * @author Thirupugal
		 * @return
		 */	
	 RestResponse<GenericResponse> getByDate(EmployeReq req);
	 /**
		 * Method to Details  get by  UpdatedOn Date
		 * @author Thirupugal
		 * @return
		 */	
	 RestResponse<GenericResponse>getByUpDate(EmployeReq req); 
	 /**
		 * Method to Details  get between dates
		 * @author Thirupugal
		 * @return
		 */	
	 RestResponse<GenericResponse>getByDateBtw(EmployeReq req);	 
	 /**
		 * Method to Details  get by  Date and City
		 * @author Thirupugal
		 * @return
		 */	
	 RestResponse<GenericResponse>getByDateCity(EmployeReq req);
	 /**
		 * Method to Add MultiData
		 * @author Thirupugal
		 * @return
		 */	
	 RestResponse<GenericResponse>addMultiData(List <EmployeReq> reqs);
	 /**
		 * Method to Delete  MultiData
		 * @author Thirupugal
		 * @return
		 */	
	 RestResponse<GenericResponse>deleteMultiData(List <EmployeReq> reqs);
}
  
