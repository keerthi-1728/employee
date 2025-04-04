package in.codifi.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.jboss.resteasy.reactive.RestResponse;

import in.codifi.EmployeRequst.DeptReq;
import in.codifi.EmployeService.PrepareResponse;
import in.codifi.Responce.GenericResponse;
import in.codifi.Responce.deptResponse;
import in.codifi.UserServiceSpec.DeptServiceSpec;
import in.codifi.Utility.AppConstants;
import io.quarkus.logging.Log;
import lombok.Getter;
import lombok.Setter;

@ApplicationScoped
public class DeptService implements DeptServiceSpec{
	
	@Inject
    DataSource dataSource;
    @Inject
	PrepareResponse prepareresponse;
	    
    @Override
    public RestResponse<GenericResponse> addData(DeptReq deptReq) {
    	String sql="INSERT INTO dept (dept_id, dept_name, dept_age,dept_dept,dept_headofdept,dept_totalstudents,dept_location,dept_establishedyear) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    	
    	try(Connection con = dataSource.getConnection();
    			PreparedStatement stmt=con.prepareStatement(sql); ){
    		int index=1;
    		  stmt.setLong(index++, deptReq.getId());
    		    stmt.setString(index++, deptReq.getName());
    		    stmt.setInt(index++, deptReq.getAge());
    		    stmt.setString(index++, deptReq.getDept());
    		    stmt.setString(index++, deptReq.getHeadofdept());
    		    stmt.setInt(index++, deptReq.getTotalstudents());
    		    stmt.setString(index++, deptReq.getLocation());
    		    stmt.setInt(index++, deptReq.getEstablishedyear());
    		    int inserted=stmt.executeUpdate();
    		    if(inserted>0) {
    		    	return prepareresponse.prepareSuccessResponseObject(deptReq);
    		    }
    	}catch(Exception e) {
    		e.printStackTrace();
    		Log.error(e.getMessage());
    	}
    	return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
}
    
    @Override
    public RestResponse<GenericResponse> getById(DeptReq deptReq){
    	String sql = "SELECT dept_name,dept_age,dept_dept, dept_headofdept,dept_totalstudents,dept_location,dept_establishedyear"+ " FROM dept d,employees e " + " WHERE d.dept_id = ? AND e.emp_id=d.dept_id";
    	
    	try(Connection con=dataSource.getConnection();
    			PreparedStatement stmt=con.prepareStatement(sql);){
    		stmt.setLong(1,deptReq.getId());
    		try(ResultSet rs=stmt.executeQuery()){
    			if(rs.next()) {
    				deptResponse dept = new deptResponse();
                    dept.setName(rs.getString("dept_name"));
                    dept.setAge(rs.getInt("dept_age"));
                    dept.setDept(rs.getString("dept_dept"));
                    dept.setHeadofdept(rs.getString("dept_headofdept"));
                    dept.setTotalstudents(rs.getInt("dept_totalstudents"));
                    dept.setLocation(rs.getString("dept_location"));
                    dept.setEstablishedyear(rs.getInt("dept_establishedyear"));
                    return prepareresponse.prepareSuccessResponseObject(dept);
    			}else {
    				return prepareresponse.prepareFailedResponse("User ID not found");
    			}
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    		Log.error(e.getMessage());
    	}
    	 return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
    	}
    }
