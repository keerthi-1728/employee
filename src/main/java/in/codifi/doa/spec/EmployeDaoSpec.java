package in.codifi.doa.spec;

import java.util.List;

import org.jboss.resteasy.reactive.RestResponse;

import in.codifi.EmployeRequst.EmployeReq;
import in.codifi.Responce.GenericResponse;

public interface EmployeDaoSpec {
    
	RestResponse<GenericResponse> getAllData();
    
	RestResponse<GenericResponse> GetByName(EmployeReq req);
    
	RestResponse<GenericResponse> GetByID(EmployeReq req);
    
	RestResponse<GenericResponse> AddData(EmployeReq req);
    
	RestResponse<GenericResponse> DeleteData(EmployeReq req);
    
	RestResponse<GenericResponse> UpdateData(EmployeReq req);
    
	RestResponse<GenericResponse> activeStatus();
    
	RestResponse<GenericResponse> changestatus(EmployeReq req);
    
	RestResponse<GenericResponse> getByDate(EmployeReq req);
    
	RestResponse<GenericResponse> getByUpDate(EmployeReq req);
    
	RestResponse<GenericResponse> getByDateBtw(EmployeReq req);
    
	RestResponse<GenericResponse> getByDateCity(EmployeReq req);
    
	RestResponse<GenericResponse> addMultiData(List<EmployeReq> reqs);
    
	RestResponse<GenericResponse> deleteMultiData(List<EmployeReq> reqs);

	RestResponse<GenericResponse> getrmoney(String token);

//	RestResponse<GenericResponse> getResponse();

}
