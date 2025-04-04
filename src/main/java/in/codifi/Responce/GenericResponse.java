package in.codifi.Responce;

import java.util.List;

import org.json.simple.JSONObject;

import in.codifi.EmployeRequst.EmployeReq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericResponse {
	
	private Object result; 
	private String status;
    private String message;

}  