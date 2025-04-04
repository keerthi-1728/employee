package in.codifi.controller.spec;


import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestResponse;
import javax.ws.rs.core.SecurityContext;

import in.codifi.DTO.UserDTO;
import in.codifi.DTO.UserLoginDTO;
import in.codifi.EmployeRequst.DeptReq;
import in.codifi.EmployeRequst.EmployeReq;
import in.codifi.EmployeRequst.UserReq;
import in.codifi.Responce.GenericResponse;

public interface controllerSpec {
	
	
//	testing purpose
	
	
	@Path("/addData")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse> addData(DeptReq deptReq);
	
	@Path("/getById")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse> getById(DeptReq deptReq);
	
//	
	
	/**
	 * method to get all data 
	 * @author Thirupugal
	 * @return
	 */
	@Path("/getAllData")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse> getAllData();
	/**
	 * Method to get Details by Name
	 * @author Thirupugal
	 * @return
	 */
	
	@Path("/getDetails")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse> GetByName(EmployeReq req);
	  
	/**
	 * Method to get Details by Id
	 * @author Thirupugal
	 * @return
	 */	
		
	@Path("/getDetailsByID")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse> GetByID(EmployeReq req);
	
	
	/**
	 * Method to Add Data 
	 * @author Thirupugal
	 * @return
	 */	
	@Path("/AddData")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse> AddData(EmployeReq req);
	
	/**
	 * Method to Delete Data
	 * @author Thirupugal
	 * @return
	 */	
	
	@Path("/DeleteData")
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse> DeleteData(EmployeReq req);

	/**
	 * Method to Update Data
	 * @author Thirupugal
	 * @return
	 */	
	@Path("/UpdateData")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse>UpdateData(EmployeReq req);
    
	/**
	 * Method to get Activedata
	 * @author Thirupugal
	 * @return
	 */	
	@Path("/activestatus")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse>activeStatus();	
	
	/**
	 * Method to  change  Activedata
	 * @author Thirupugal
	 * @return
	 */	
	
	@Path("/changeactivestatus")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse>changestatus(EmployeReq req);
	   
	/**
	 * Method to Details  get by createdon date 
	 * @author Thirupugal
	 * @return
	 */	
	
	@Path("/getByDate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse>getByDate(EmployeReq req);

	/**
	 * Method to Details  get by  UpdatedOn Date
	 * @author Thirupugal
	 * @return
	 */	
	
	 @Path("/getByupdatedon")
	 @POST
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces(MediaType.APPLICATION_JSON)
	 RestResponse<GenericResponse>getByUpDate(EmployeReq req);
	
	 /**
		 * Method to Details  get between dates
		 * @author Thirupugal
		 * @return
		 */	
	@Path("/getBtwDate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse>getByDateBtw(EmployeReq req);
	
	/**
	 * Method to Details  get by  Date and City
	 * @author Thirupugal
	 * @return
	 */	
	
	@Path("/getByDateCity")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse>getByDateCity(EmployeReq req);
	/**
	 * Method to Add MultiData
	 * @author Thirupugal
	 * @return
	 */	
	
	@Path("/addMultiData")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse>addMultiData(List <EmployeReq> reqs);
	/**
	 * Method to Delete  MultiData
	 * @author Thirupugal
	 * @return
	 */	
	
	@Path("/deleteMultiData")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse>deleteMultiData(List <EmployeReq> reqs);
	
	@Path("/rmoney")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse> getrmoney(@HeaderParam("Authorization") String token);
	
//	keycloak
	
	@Path("/create")
	@POST 
	@RolesAllowed("user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse> createUserKC(UserDTO userDTO);
	
	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse> loginUser(UserLoginDTO loginDTO);
	
	@GET
    @Path("/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	RestResponse<GenericResponse> validateToken(@Context SecurityContext securityContext);
	
	
	
	

}
