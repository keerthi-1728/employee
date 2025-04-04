package in.codifi.EmployeService;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.xmlbeans.impl.tool.XMLBean.ErrorLogger;
import org.jboss.resteasy.reactive.RestResponse;

import in.codifi.EmployeRepository.EmployeRepository;
import in.codifi.EmployeRequst.EmployeReq;
import in.codifi.EmployeServiceSpec.EmployeServiceSpec;
import in.codifi.Entity.Entitys;
import in.codifi.Responce.GenericResponse;
import in.codifi.Utility.AppConstants;
import io.quarkus.logging.Log;

@ApplicationScoped
public class EmployeService implements EmployeServiceSpec {
	@Inject
	EmployeRepository employerepository;
	@Inject
	PrepareResponse prepareresponse;
          
	/**
	 * method to get all data 
	 * @author Thirupugal
	 * @return
	 */
	public RestResponse<GenericResponse> getAllData() {
		try {
			List<Entitys> entity = employerepository.findAll();

			return prepareresponse.prepareSuccessResponseObject(entity);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());

		}
		return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
	}

	
	
	/**
	 * Method to get Details by Name
	 * @author Thirupugal
	 * @return
	 */
	
	public RestResponse<GenericResponse> GetByName(EmployeReq req) {
		try {
			Entitys entity = employerepository.findByName(req.getName());

			return prepareresponse.prepareSuccessResponseObject(entity);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());

		}
		return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
	}

	/**
	 * Method to get Details by Id
	 * @author Thirupugal
	 * @return
	 */	
	
	public RestResponse<GenericResponse> GetByID(EmployeReq req) {
		try {
			Entitys entity = employerepository.getOne(req.getId());
			return prepareresponse.prepareSuccessResponseObject(entity);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
		}
		return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
	}

	/**
	 * Method to Add Data 
	 * @author Thirupugal
	 * @return
	 */	
	
	public RestResponse<GenericResponse> AddData(EmployeReq req) {

		try {
			Entitys entity = new Entitys();
			entity.setId(req.getId());
			entity.setName(req.getName());
			entity.setAddress(req.getAddress());
			entity.setCity(req.getCity());
			entity.setSalary(req.getSalary());
			employerepository.save(entity);

			return prepareresponse.prepareSuccessResponseObject(AppConstants.SUCCESS_STATUS);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());

		}
		return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);

	}

	/**
	 * Method to Delete Data
	 * @author Thirupugal
	 * @return
	 */	
	
	public RestResponse<GenericResponse> DeleteData(EmployeReq req) {
		try {
			employerepository.deleteById(req.getId());

			return prepareresponse.prepareSuccessResponseObject(AppConstants.SUCCESS_STATUS);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());

		}
		return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
	}
	
	
	/**
	 * Method to Update Data
	 * @author Thirupugal
	 * @return
	 */	

	public RestResponse<GenericResponse> UpdateData(EmployeReq req) {
		try {
			Optional<Entitys> empentity = employerepository.findById(req.getId());
			Entitys entity = empentity.get();
			entity.setName(req.getName());
			entity.setAddress(req.getAddress());
			entity.setCity(req.getCity());
			entity.setSalary(req.getSalary());
			employerepository.save(entity);

			return prepareresponse.prepareSuccessResponseObject(AppConstants.SUCCESS_STATUS);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());

		}
		return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
	}
	/**
	 * Method to get Activedata
	 * @author Thirupugal
	 * @return
	 */	
	public RestResponse<GenericResponse> activeStatus() {
		try {
			List<Entitys> entity = employerepository.findByActivestatus(1);
			return prepareresponse.prepareSuccessResponseObject(entity);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());

		}
		return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);

	}
	/**
	 * Method to change Activedata
	 * @author Thirupugal
	 * @return
	 */	
	public RestResponse<GenericResponse> changestatus(EmployeReq req) {
		try {
			Optional<Entitys> empentity = employerepository.findById(req.getId());
			Entitys entity = empentity.get();
			if(entity.getActivestatus()==0) {
				entity.setActivestatus(1);
				
				employerepository.save(entity);
				return prepareresponse.prepareSuccessResponseObject(AppConstants.SUCCESS_STATUS);
			}
			else {
			entity.setActivestatus(0);
			
			employerepository.save(entity);
			return prepareresponse.prepareSuccessResponseObject(AppConstants.SUCCESS_STATUS);
			}
      
		
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());

		}
		return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
}
	/**
	 * Method to Details  get by createdon date 
	 * @author Thirupugal
	 * @return
	 */	

	public RestResponse<GenericResponse> getByDate(EmployeReq req) {
		try {
			
			List<Entitys> entity = employerepository.getByDate(req.getCreatedOn());
			return prepareresponse.prepareSuccessResponseObject(entity);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
          
		}
		return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);

	}
	
	/**
	 * Method to Details  get by  UpdatedOn Date
	 * @author Thirupugal
	 * @return
	 */	
	  public RestResponse<GenericResponse>getByUpDate(EmployeReq req){
		  
		  
		  try {
				
				List<Entitys> entity = employerepository.getByUpdateDate(req.getUpdatedOn());
				return prepareresponse.prepareSuccessResponseObject(entity);
			} catch (Exception e) {
				e.printStackTrace();
				Log.error(e.getMessage());
	          
			}
			return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
	  
	  }
	  /**
		 * Method to Details  get between dates
		 * @author Thirupugal
		 * @return
		 */	
	  public RestResponse<GenericResponse>getByDateBtw(EmployeReq req){
		  try {
			  List<Entitys> entity =employerepository.findByDateRange(req.getStart(),req.getEnd());
			  return prepareresponse.prepareSuccessResponseObject(entity);
		  }
		  catch(Exception e) {
			  e.printStackTrace();
			  Log.error(e.getMessage());
		  }
		  return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
	  }
	  
	  /**
		 * Method to Details  get by  Date and City
		 * @author Thirupugal
		 * @return
		 */	
	  public RestResponse<GenericResponse>getByDateCity(EmployeReq req){
		  try {
			  List<Entitys> entity=employerepository.getByDateCity(req.getStart(),req.getCity());
			  return prepareresponse.prepareSuccessResponseObject(entity);
		  }
		  catch(Exception e) {
			  e.printStackTrace();
			  Log.error(e.getMessage());
		  }
		  return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
	  }
	  
	  /**
		 * Method to Add MultiData
		 * @author Thirupugal
		 * @return
		 */	
	  public RestResponse<GenericResponse>addMultiData(List <EmployeReq> reqs){
		  try {
			  List<Entitys> entityList = new ArrayList<>();
				for (EmployeReq req : reqs) {
					Entitys entity = new Entitys();
					entity.setName(req.getName());
					entity.setAddress(req.getAddress());
					entity.setCity(req.getCity());
					entity.setSalary(req.getSalary());
					entity.setId(req.getId());
					entityList.add(entity);
		      }
				employerepository.saveAll(entityList);
				return prepareresponse.prepareSuccessResponseObject(AppConstants.SUCCESS_STATUS);		
		  }
				catch(Exception e) {
					  e.printStackTrace();
					  Log.error(e.getMessage());
				  }
				  return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
				
	  }
	  /**
		 * Method to Delete  MultiData
		 * @author Thirupugal
		 * @return
		 */	
	  public RestResponse<GenericResponse>deleteMultiData(List <EmployeReq> reqs){
		  try {
			   for(EmployeReq req : reqs) {
				   employerepository.deleteById(req.getId());
			   }
			   return prepareresponse.prepareSuccessResponseObject(AppConstants.SUCCESS_STATUS);
		  }
		  catch(Exception e) {
			  e.printStackTrace();
			  Log.error(e.getMessage());
		  }
		  return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
	  }
}