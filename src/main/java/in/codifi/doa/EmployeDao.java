package in.codifi.doa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestResponse;

import com.fasterxml.jackson.databind.JsonNode;

import javax.sql.DataSource;
import in.codifi.EmployeRequst.EmployeReq;
import in.codifi.EmployeService.ErrorLogger;
import in.codifi.EmployeService.PrepareResponse;
import in.codifi.ExternalApi.ExternalApi;
import in.codifi.Responce.GenericResponse;
import in.codifi.Utility.AppConstants;
import in.codifi.doa.spec.EmployeDaoSpec;
import io.quarkus.logging.Log;

// stateless service, initiated when application starts and exits when application shutsdown.
@ApplicationScoped
public class EmployeDao implements EmployeDaoSpec {

    @Inject
    DataSource dataSource;
    
    @Inject
	PrepareResponse prepareresponse;
    
    @Inject
    @RestClient
    ExternalApi externalApi;
    
    @inject
    ErrorLogger errorLogger;

    
    @Override
    public RestResponse<GenericResponse> getAllData() {
        String sql = "SELECT * FROM project.employees";
        List<EmployeReq> employees = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                employees.add(mapResultSetToEmployeReq(rs));
            }
            return prepareresponse.prepareSuccessResponseObject(employees);
        } catch (SQLException e) {
        	e.printStackTrace();
			Log.error(e.getMessage());
        }
        return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
    }

    @Override
    public RestResponse<GenericResponse> AddData(EmployeReq employeReq) {
        String sql = "INSERT INTO employees (emp_id, emp_name, emp_address, emp_city, emp_salary, activestatus) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            int index = 1;
            stmt.setLong(index++, employeReq.getId());
            stmt.setString(index++, employeReq.getName());
            stmt.setString(index++, employeReq.getAddress());
            stmt.setString(index++, employeReq.getCity());
            stmt.setLong(index++, employeReq.getSalary());
            stmt.setInt(index++, employeReq.getActive());

            stmt.executeUpdate();
            
            return prepareresponse.prepareSuccessResponseObject(employeReq);
        
        } catch (SQLException e) {
            Log.error("SQL Exception: " + e.getMessage());
            
            // Check if errorLogger is null
            if (errorLogger != null) {
                errorLogger.logError("employee", "INSERT addData", e);
            } else {
                Log.error("❌ errorLogger is NULL - Dependency Injection Failed!");
            }
            
            // Handle Duplicate Entry Error (MySQL Error Code 1062)
            if (e.getErrorCode() == 1062) {
                return prepareresponse.prepareFailedResponse("Failed: Duplicate Employee ID. Entry already exists.");
            }

            return prepareresponse.prepareFailedResponse("Failed to insert employee. Please check the data.");
        }
    }

    
    
    
    @Override
    public RestResponse<GenericResponse> GetByID(EmployeReq req) {
        String sql = "SELECT * FROM employees WHERE emp_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, req.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                	 return prepareresponse.prepareSuccessResponseObject(mapResultSetToEmployeReq(rs));
                }
            }
            return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
        } catch (SQLException e) {
        	e.printStackTrace();
			Log.error(e.getMessage());
        }
        return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
    }

    @Override
    public RestResponse<GenericResponse> DeleteData(EmployeReq req) {
        String sql = "DELETE FROM employees WHERE emp_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, req.getId());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
            	return prepareresponse.prepareSuccessResponseObject(AppConstants.SUCCESS_STATUS);
            } else {
            	   return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
			Log.error(e.getMessage());
        }
        return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
    }
    
    @Override
    public RestResponse<GenericResponse> UpdateData(EmployeReq employeReq) {
        String sql = "UPDATE employees SET emp_name = ?, emp_address = ?, emp_city = ?, emp_salary = ?, activestatus = ? WHERE emp_id = ?";
      

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            stmt.setString(index++, employeReq.getName());
            stmt.setString(index++, employeReq.getAddress());
            stmt.setString(index++, employeReq.getCity());
            stmt.setLong(index++, employeReq.getSalary());
            stmt.setInt(index++, employeReq.getActive());
            stmt.setLong(index++, employeReq.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
            	return prepareresponse.prepareSuccessResponseObject(AppConstants.SUCCESS_STATUS);
            } else {
            	   return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
			Log.error(e.getMessage());
        }
        return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
    }
    
    @Override
    public RestResponse<GenericResponse> getByUpDate(EmployeReq req) {
        String sql = "SELECT * FROM employees WHERE updated_on = ?";
        List<EmployeReq> employees = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(req.getUpdatedOn().getTime()));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(mapResultSetToEmployeReq(rs));
                }
            }
            return prepareresponse.prepareSuccessResponseObject(employees);
        } catch (SQLException e) {
        	e.printStackTrace();
			Log.error(e.getMessage());
        }
        return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
    }
    
    @Override
    public RestResponse<GenericResponse> deleteMultiData(List<EmployeReq> reqs) {
        String sql = "DELETE FROM employees WHERE emp_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (EmployeReq req : reqs) {
                int index = 1; 
                stmt.setLong(index++, req.getId());
                stmt.addBatch(); 
            }
            int[] results = stmt.executeBatch(); 
            if (results.length > 0) {
            	return prepareresponse.prepareSuccessResponseObject(AppConstants.SUCCESS_STATUS);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
			Log.error(e.getMessage());
        }
        return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
    }

    
    @Override
    public RestResponse<GenericResponse> getByDateCity(EmployeReq req) {
        String sql = "SELECT * FROM employees WHERE created_on = ? AND emp_city = ?";
        List<EmployeReq> employees = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int index = 1; // Auto-incrementing index
            stmt.setDate(index++, new java.sql.Date(req.getCreatedOn().getTime()));
            stmt.setString(index++, req.getCity());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(mapResultSetToEmployeReq(rs));
                }
            }
            return prepareresponse.prepareSuccessResponseObject(employees);
        } catch (SQLException e) {
        	e.printStackTrace();
			Log.error(e.getMessage());
        }
        return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
    }


    @Override
    public RestResponse<GenericResponse> addMultiData(List<EmployeReq> reqs) {
        String sql = "INSERT INTO employees (emp_name, emp_address, emp_city, emp_salary, activestatus) VALUES (?, ?, ?, ?, ?)";
        

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (EmployeReq req : reqs) {
                int index = 1; // Auto-incrementing index
                stmt.setString(index++, req.getName());
                stmt.setString(index++, req.getAddress());
                stmt.setString(index++, req.getCity());
                stmt.setLong(index++, req.getSalary());
                stmt.setInt(index++, req.getActive());
                stmt.addBatch(); // Add to batch for bulk insert
            }

            int[] results = stmt.executeBatch(); 
            if (results.length > 0) {
            	return prepareresponse.prepareSuccessResponseObject(AppConstants.SUCCESS_STATUS);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
			Log.error(e.getMessage());
        }
        return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
    }

    @Override
    public RestResponse<GenericResponse> changestatus(EmployeReq req) {
        String sql = "UPDATE employees SET activestatus = ? WHERE emp_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1; // Auto-incrementing index
            stmt.setInt(index++, req.getActive());
            stmt.setLong(index++, req.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
            	return prepareresponse.prepareSuccessResponseObject(AppConstants.SUCCESS_STATUS);
            } else {
            	return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
			Log.error(e.getMessage());
        }
        return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
    }

    @Override
    public RestResponse<GenericResponse> getByDateBtw(EmployeReq req) {
        String sql = "SELECT * FROM employees WHERE created_on BETWEEN ? AND ?";
        List<EmployeReq> employees = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1; // Auto-incrementing index
            stmt.setDate(index++, new java.sql.Date(req.getStart().getTime()));
            stmt.setDate(index++, new java.sql.Date(req.getEnd().getTime()));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(mapResultSetToEmployeReq(rs));
                }
            }

            return prepareresponse.prepareSuccessResponseObject(employees);
        } catch (SQLException e) {
        	e.printStackTrace();
			Log.error(e.getMessage());
        }
        return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
    }

    @Override
    public RestResponse<GenericResponse> GetByName(EmployeReq req) {
        String sql = "SELECT * FROM employees WHERE emp_name = ?";
        List<EmployeReq> employees = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1; // Auto-incrementing index
            stmt.setString(index++, req.getName());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(mapResultSetToEmployeReq(rs));
                }
            }

            return prepareresponse.prepareSuccessResponseObject(employees);
        } catch (SQLException e) {
        	e.printStackTrace();
			Log.error(e.getMessage());
        }
        return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
    }

    @Override
    public RestResponse<GenericResponse> getByDate(EmployeReq req) {
        String sql = "SELECT * FROM employees WHERE DATE(created_on) = ?";
        List<EmployeReq> employees = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(req.getCreatedOn().getTime()));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(mapResultSetToEmployeReq(rs));
                }
            }

            return prepareresponse.prepareSuccessResponseObject(employees);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.error(e.getMessage());
        }
        return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
    }


    @Override
    public RestResponse<GenericResponse> activeStatus() {
        String sql = "SELECT * FROM employees WHERE activestatus = 1"; 
        List<EmployeReq> employees = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                employees.add(mapResultSetToEmployeReq(rs));
            }

            return prepareresponse.prepareSuccessResponseObject(employees);
        } catch (SQLException e) {
        	e.printStackTrace();
			Log.error(e.getMessage());
        }
        return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
    }
    
    
    @Override
    public RestResponse<GenericResponse> getrmoney(String token){
	   	try {
	        JsonNode externalData = externalApi.fetchExternalData(token);
	        return prepareresponse.prepareSuccessResponseObject(externalData);
	    } catch (Exception e) {
	    	e.printStackTrace();
			Log.error(e.getMessage());
	    }
	   	return prepareresponse.prepareFailedResponse(AppConstants.FAILED_STATUS);
    }


    private EmployeReq mapResultSetToEmployeReq(ResultSet rs) throws SQLException {
        EmployeReq employeReq = new EmployeReq();
        employeReq.setId(rs.getLong("emp_id"));
        employeReq.setName(rs.getString("emp_name"));
        employeReq.setAddress(rs.getString("emp_address"));
        employeReq.setCity(rs.getString("emp_city"));
        employeReq.setSalary(rs.getLong("emp_salary"));
        employeReq.setCreatedOn(rs.getTimestamp("created_on"));
        employeReq.setUpdatedOn(rs.getTimestamp("updated_on"));
        employeReq.setActive(rs.getInt("activestatus"));
        return employeReq;
    }

}