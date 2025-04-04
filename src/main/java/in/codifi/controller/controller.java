package in.codifi.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.jboss.resteasy.reactive.RestResponse;

import in.codifi.CreateUserKCSpec.CreateUserKCSpec;
import in.codifi.DTO.UserDTO;
import in.codifi.DTO.UserLoginDTO;
import in.codifi.EmployeRequst.DeptReq;
import in.codifi.EmployeRequst.EmployeReq;
import in.codifi.EmployeRequst.UserReq;
import in.codifi.EmployeService.PrepareResponse;
import in.codifi.Responce.GenericResponse;
import in.codifi.UserServiceSpec.DeptServiceSpec;
//import in.codifi.UserServiceSpec.UserServiceSpec;
import in.codifi.controller.spec.controllerSpec;
import in.codifi.doa.spec.EmployeDaoSpec;

@Path("/user")
public class controller implements controllerSpec {
	@Inject
	EmployeDaoSpec employedaospec;
//	EmployeServiceSpec employeservicespec;
	@Inject
	DeptServiceSpec deptservicespec;
	
//	testing purpose
	@Override
	public RestResponse<GenericResponse> getById(DeptReq deptReq){
		return deptservicespec.getById(deptReq);
	}
	@Override
	public RestResponse<GenericResponse>addData(DeptReq deptReq) {
		return deptservicespec.addData(deptReq);
	}
	
//	
	 
	/**
	 * method to get all data 
	 * @author Thirupugal
	 * @return
	 */
	@Override
	public RestResponse<GenericResponse> getAllData() {
		return employedaospec.getAllData();
	}

	/**
	 * Method to get Details by Name
	 * @author Thirupugal
	 * @return
	 */
	@Override
	public RestResponse<GenericResponse> GetByName(EmployeReq req) {
		return employedaospec.GetByName(req);
	}
    
	/**
	 * Method to get Details by Id
	 * @author Thirupugal
	 * @return
	 */	
	@Override
	public RestResponse<GenericResponse> GetByID(EmployeReq req) {
		return employedaospec.GetByID(req);
	}
	/**
	 * Method to Add Data 
	 * @author Thirupugal
	 * @return
	 */	
	@Override
	public RestResponse<GenericResponse> AddData(EmployeReq req) {
		return employedaospec.AddData(req);
    }
	
	/**
	 * Method to Delete Data
	 * @author Thirupugal
	 * @return
	 */	
	@Override 
	public RestResponse<GenericResponse> DeleteData(EmployeReq req){
		return employedaospec.DeleteData(req);
	}
	
	/**
	 * Method to Update Data
	 * @author Thirupugal
	 * @return
	 */	
	@Override
	public RestResponse<GenericResponse>UpdateData(EmployeReq req){
		return employedaospec.UpdateData(req);
	}
	/**
	 * Method to get Activedata
	 * @author Thirupugal
	 * @return
	 */	
	@Override
	public RestResponse<GenericResponse>activeStatus(){
		return employedaospec.activeStatus();
	}
	/**
	 * Method to  change Activedata
	 * @author Thirupugal
	 * @return
	 */	
	@Override
	public RestResponse<GenericResponse>changestatus(EmployeReq req){
		return employedaospec.changestatus(req);
	}
	
	/**
	 * Method to Details  get by date 
	 * @author Thirupugal
	 * @return
	 */	
	
	@Override
	public RestResponse<GenericResponse>getByDate(EmployeReq req){
		return employedaospec.getByDate(req);
	}
	/**
	 * Method to Details  get by  UpdatedOn Date
	 * @author Thirupugal
	 * @return
	 */	
	
	@Override 
	public RestResponse<GenericResponse>getByUpDate(EmployeReq req){
	   return employedaospec.getByUpDate(req); 
	   }
	 /**
	 * Method to Details  get between dates
	 * @author Thirupugal
	 * @return
	 */	
	@Override
	public RestResponse<GenericResponse>getByDateBtw(EmployeReq req){
		return employedaospec.getByDateBtw(req);
	}
	/**
	 * Method to Details  get by  Date and City
	 * @author Thirupugal
	 * @return
	 */	
	@Override 
	public RestResponse<GenericResponse>getByDateCity(EmployeReq req){
		return employedaospec.getByDateCity(req);
	}
	/**
	 * Method to Add MultiData
	 * @author Thirupugal
	 * @return
	 */	
	@Override
	public RestResponse<GenericResponse>addMultiData(List <EmployeReq> reqs){
		return employedaospec.addMultiData(reqs);
	}
	/**
	 * Method to Delete  MultiData
	 * @author Thirupugal
	 * @return
	 */	
	@Override
	public RestResponse<GenericResponse>deleteMultiData(List <EmployeReq> reqs){
		return employedaospec.deleteMultiData(reqs);
	}
	
	@Override
	public RestResponse<GenericResponse> getrmoney(@HeaderParam("Authorization") String token) {
		return employedaospec.getrmoney(token);
	}
	@Inject
	CreateUserKCSpec createuserspec;
	
	   @Inject
	   	PrepareResponse prepareresponse;
	
//	keycloak
	 // Create user in Keycloak
	   @Override
    public RestResponse<GenericResponse> createUserKC(UserDTO userDTO) {
        return createuserspec.createUser(userDTO);  // Using CreateUserKC service to handle user creation
    }

    // Login user and generate token
    @Override
    public RestResponse<GenericResponse> loginUser(UserLoginDTO loginDTO) {
       return createuserspec.loginUser(loginDTO);
    }

    // Validate token and check user authentication
    @Override
    public RestResponse<GenericResponse> validateToken(@Context SecurityContext securityContext) {
        if (securityContext.getUserPrincipal() != null) {
            String username = securityContext.getUserPrincipal().getName();
            return prepareresponse.prepareSuccessResponseObject("User is authenticated: " + username);
        } else {
            return prepareresponse.prepareFailedResponse("Invalid Token");
        }
    }
    @GET
    @Path("/profile")
    @RolesAllowed("admin")
    public Response userProfile() {
        return Response.ok("User Profile - Accessible by admin").build();
    }
}