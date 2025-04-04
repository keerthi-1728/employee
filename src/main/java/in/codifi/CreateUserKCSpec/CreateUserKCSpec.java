package in.codifi.CreateUserKCSpec;

import org.jboss.resteasy.reactive.RestResponse;

import in.codifi.DTO.*;
import in.codifi.Responce.GenericResponse;

public interface CreateUserKCSpec {

	RestResponse<GenericResponse> createUser(UserDTO userDTO);
	RestResponse<GenericResponse> loginUser(UserLoginDTO loginDTO);
	
}
