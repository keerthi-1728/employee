package in.codifi.UserServiceSpec;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.resteasy.reactive.RestResponse;


import in.codifi.EmployeRequst.DeptReq;
import in.codifi.Responce.GenericResponse;


public interface DeptServiceSpec {

	RestResponse<GenericResponse> addData(DeptReq deptReq);
	
	RestResponse<GenericResponse> getById(DeptReq deptReq);
	
}