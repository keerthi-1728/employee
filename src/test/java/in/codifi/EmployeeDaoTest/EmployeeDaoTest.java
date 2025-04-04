package in.codifi.EmployeeDaoTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.*;

import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import in.codifi.EmployeRequst.EmployeReq;
import in.codifi.EmployeService.PrepareResponse;
import in.codifi.Responce.GenericResponse;
import in.codifi.doa.EmployeDao;

@ExtendWith(MockitoExtension.class)
class EmployeDaoTest {

    @Mock
    private DataSource mockDataSource;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private PrepareResponse mockPrepareResponse;
    
    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private EmployeDao employeDao;

    @BeforeEach
    void setUp() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
    }

    private EmployeReq createSampleRequest() {
        EmployeReq request = new EmployeReq();
        request.setId(1L);
        request.setName("John Doe");
        request.setAddress("123 Street");
        request.setCity("New York");
        request.setSalary(50000L);
        request.setActive(1);
        return request;
    }
    
    private GenericResponse createMockSuccessResponse() {
        GenericResponse response = new GenericResponse();
        response.setStatus("OK");
        response.setMessage("Success");
        response.setResult(null);
        return response;
    }
    
    @Test
    void testAddData_Success() throws SQLException {
        EmployeReq request = createSampleRequest();
        when(mockStatement.executeUpdate()).thenReturn(1);

        GenericResponse mockResponse = createMockSuccessResponse();
        when(mockPrepareResponse.prepareSuccessResponseObject(any()))
            .thenReturn(RestResponse.ok(mockResponse));

        RestResponse<GenericResponse> response = employeDao.AddData(request);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals("Success", response.getEntity().getMessage());

        verify(mockStatement, times(1)).executeUpdate();
        verify(mockPrepareResponse, times(1)).prepareSuccessResponseObject(any());
    }
    
    @Test
    void testGetByName_Success() throws SQLException {
        // Create request object
        EmployeReq request = new EmployeReq();
        request.setName("sanjay");

        // Mock database behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock result set behavior
        when(mockResultSet.next()).thenReturn(true, false); 
        when(mockResultSet.getLong("emp_id")).thenReturn(1L);
        when(mockResultSet.getString("emp_name")).thenReturn("sanjay");
        when(mockResultSet.getString("emp_address")).thenReturn("123 Street");
        when(mockResultSet.getString("emp_city")).thenReturn("New York");
        when(mockResultSet.getLong("emp_salary")).thenReturn(50000L);
        when(mockResultSet.getInt("activestatus")).thenReturn(1);

        // Mock response behavior
        GenericResponse mockResponse = new GenericResponse();
        mockResponse.setStatus("OK");
        mockResponse.setMessage("Success");
        mockResponse.setResult(request);

        when(mockPrepareResponse.prepareSuccessResponseObject(any()))
            .thenReturn(RestResponse.ok(mockResponse));

        // Execute the method
        RestResponse<GenericResponse> response = employeDao.GetByName(request);

        // Assertions
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals("Success", response.getEntity().getMessage());

        // Verify interactions
        verify(mockStatement, times(1)).executeQuery();
        verify(mockPrepareResponse, times(1)).prepareSuccessResponseObject(any());
    }
    
    @Test
    void testUpdateData_Success() throws SQLException {
        EmployeReq request = new EmployeReq();
        request.setId(1L);
        request.setName("John Doe");
        request.setAddress("123 Street");
        request.setCity("New York");
        request.setSalary(50000L);
        request.setActive(1);

        when(mockStatement.executeUpdate()).thenReturn(1);
        
        GenericResponse responseObj = new GenericResponse();
        responseObj.setStatus("OK");
        responseObj.setMessage("Success");
        responseObj.setResult(null);
        
        when(mockPrepareResponse.prepareSuccessResponseObject(any())).thenReturn(RestResponse.ok(responseObj));
        
        RestResponse<GenericResponse> response = employeDao.UpdateData(request);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals("Success", response.getEntity().getMessage());

        verify(mockStatement, times(1)).executeUpdate();
    }
    
    @Test
    void testDeleteData_Success() throws SQLException {
        EmployeReq request = new EmployeReq();
        request.setId(1L);

        when(mockStatement.executeUpdate()).thenReturn(1);
        
        GenericResponse responseObj = new GenericResponse();
        responseObj.setStatus("OK");
        responseObj.setMessage("Employee deleted successfully.");
        
        when(mockPrepareResponse.prepareSuccessResponseObject(anyString())).thenReturn(RestResponse.ok(responseObj));
        
        RestResponse<GenericResponse> response = employeDao.DeleteData(request);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals("Employee deleted successfully.", response.getEntity().getMessage());
    }

    
    @Test
    void testAddMultiData_Success() throws SQLException {
        List<EmployeReq> employeeList = new ArrayList<>();
        
        EmployeReq emp1 = new EmployeReq();
        emp1.setName("John Doe");
        emp1.setAddress("123 Street");
        emp1.setCity("New York");
        emp1.setSalary(50000L);
        emp1.setActive(1);
        
        EmployeReq emp2 = new EmployeReq();
        emp2.setName("Jane Doe");
        emp2.setAddress("456 Avenue");
        emp2.setCity("Los Angeles");
        emp2.setSalary(60000L);
        emp2.setActive(1);

        employeeList.add(emp1);
        employeeList.add(emp2);

        when(mockStatement.executeBatch()).thenReturn(new int[]{1, 1});

        GenericResponse mockResponse = new GenericResponse();
        mockResponse.setStatus("OK");
        mockResponse.setMessage("Success");

        when(mockPrepareResponse.prepareSuccessResponseObject(any()))
            .thenReturn(RestResponse.ok(mockResponse));

        RestResponse<GenericResponse> response = employeDao.addMultiData(employeeList);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals("Success", response.getEntity().getMessage());

        verify(mockStatement, times(1)).executeBatch();
        verify(mockPrepareResponse, times(1)).prepareSuccessResponseObject(any());
    }
    
    @Test
    void testDeleteMultiData_Success() throws SQLException {
        List<EmployeReq> employeeList = new ArrayList<>();

        EmployeReq emp1 = new EmployeReq();
        emp1.setId(1L);
        
        EmployeReq emp2 = new EmployeReq();
        emp2.setId(2L);
        
        EmployeReq emp3 = new EmployeReq();
        emp3.setId(3L);

        employeeList.add(emp1);
        employeeList.add(emp2);
        employeeList.add(emp3);

        when(mockStatement.executeBatch()).thenReturn(new int[]{1, 1, 1}); 

        GenericResponse mockResponse = new GenericResponse();
        mockResponse.setStatus("OK");
        mockResponse.setMessage("Multiple Records Deleted Successfully");

        when(mockPrepareResponse.prepareSuccessResponseObject(any()))
            .thenReturn(RestResponse.ok(mockResponse));

        RestResponse<GenericResponse> response = employeDao.deleteMultiData(employeeList);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals("Multiple Records Deleted Successfully", response.getEntity().getMessage());

        verify(mockStatement, times(employeeList.size())).setLong(eq(1), anyLong());
        verify(mockStatement, times(1)).executeBatch();
        verify(mockPrepareResponse, times(1)).prepareSuccessResponseObject(any());
    }
    
    @Test
    void testGetAllData_Success() throws SQLException {
        // Mock database behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock response behavior
        GenericResponse mockResponse = new GenericResponse();
        mockResponse.setStatus("OK");
        mockResponse.setMessage("Data fetched successfully");

        when(mockPrepareResponse.prepareSuccessResponseObject(any()))
            .thenReturn(RestResponse.ok(mockResponse));

        // Execute method
        RestResponse<GenericResponse> response = employeDao.getAllData();

        // Assertions: Only check status and message
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals("OK", response.getEntity().getStatus());
        assertEquals("Data fetched successfully", response.getEntity().getMessage());

        // Verify method interactions
        verify(mockStatement, times(1)).executeQuery();
        verify(mockPrepareResponse, times(1)).prepareSuccessResponseObject(any());
    }

    @Test
    void testGetByDate_Success() throws Exception {
        // Mock database connection and prepared statement
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        // Simulate one record found
        when(mockResultSet.next()).thenReturn(true, false);

        // Mock response preparation
        GenericResponse mockResponse = new GenericResponse();
        mockResponse.setStatus("OK");
        mockResponse.setMessage("Data fetched successfully");

        when(mockPrepareResponse.prepareSuccessResponseObject(any()))
            .thenReturn(RestResponse.ok(mockResponse));

        // Mock request object
        EmployeReq request = new EmployeReq();
        request.setCreatedOn(new java.sql.Date(System.currentTimeMillis()));


        // Execute the method
        RestResponse<GenericResponse> response = employeDao.getByDate(request);

        // Assertions: Check only status and message
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals("OK", response.getEntity().getStatus());
        assertEquals("Data fetched successfully", response.getEntity().getMessage());

        // Verify interactions
        verify(mockStatement, times(1)).executeQuery();
        verify(mockPrepareResponse, times(1)).prepareSuccessResponseObject(any());
    }

    @Test
    void testChangeStatus_Success() throws SQLException {
        // Create request object
        EmployeReq request = new EmployeReq();
        request.setId(1L);
        request.setActive(0); // Changing status to inactive

        // Mock database behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1); // Simulating 1 row updated

        // Mock response behavior
        GenericResponse mockResponse = new GenericResponse();
        mockResponse.setStatus("OK");
        mockResponse.setMessage("Status updated successfully");

        when(mockPrepareResponse.prepareSuccessResponseObject(any()))
            .thenReturn(RestResponse.ok(mockResponse));

        // Execute the method
        RestResponse<GenericResponse> response = employeDao.changestatus(request);

        // Assertions
        assertNotNull(response, "Response should not be null");
        assertEquals(200, response.getStatus(), "Status should be 200");
        assertNotNull(response.getEntity(), "Response entity should not be null");
        assertEquals("Status updated successfully", response.getEntity().getMessage(), "Message should be correct");

        // Verify interactions
        verify(mockStatement, times(1)).setInt(1, request.getActive());
        verify(mockStatement, times(1)).setLong(2, request.getId());
        verify(mockStatement, times(1)).executeUpdate();
        verify(mockPrepareResponse, times(1)).prepareSuccessResponseObject(any());
    }
    
    @Test
    void testActiveStatus_Success() throws SQLException {
        // Mock database behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock result set behavior (simulating two active employees)
        when(mockResultSet.next()).thenReturn(true, true, false);
        
        when(mockResultSet.getLong("emp_id")).thenReturn(1L, 2L);
        when(mockResultSet.getString("emp_name")).thenReturn("John Doe", "Jane Doe");
        when(mockResultSet.getString("emp_address")).thenReturn("123 Street", "456 Avenue");
        when(mockResultSet.getString("emp_city")).thenReturn("New York", "Los Angeles");
        when(mockResultSet.getLong("emp_salary")).thenReturn(50000L, 60000L);
        when(mockResultSet.getInt("activestatus")).thenReturn(1, 1); // Both employees are active

        // Creating expected response
        List<EmployeReq> activeEmployees = new ArrayList<>();

        EmployeReq emp1 = new EmployeReq();
        emp1.setId(1L);
        emp1.setName("John Doe");
        emp1.setAddress("123 Street");
        emp1.setCity("New York");
        emp1.setSalary(50000L);
        emp1.setActive(1);

        EmployeReq emp2 = new EmployeReq();
        emp2.setId(2L);
        emp2.setName("Jane Doe");
        emp2.setAddress("456 Avenue");
        emp2.setCity("Los Angeles");
        emp2.setSalary(60000L);
        emp2.setActive(1);

        activeEmployees.add(emp1);
        activeEmployees.add(emp2);

        GenericResponse mockResponse = new GenericResponse();
        mockResponse.setStatus("OK");
        mockResponse.setMessage("Active employees retrieved successfully");
        mockResponse.setResult(activeEmployees);

        when(mockPrepareResponse.prepareSuccessResponseObject(any()))
            .thenReturn(RestResponse.ok(mockResponse));

        // Execute method
        RestResponse<GenericResponse> response = employeDao.activeStatus();

        // Assertions
        assertNotNull(response, "Response should not be null");
        assertEquals(200, response.getStatus(), "Status should be 200");
        assertNotNull(response.getEntity(), "Response entity should not be null");
        assertEquals("Active employees retrieved successfully", response.getEntity().getMessage(), "Message should be correct");

        // Verify interactions
        verify(mockStatement, times(1)).executeQuery();
        verify(mockPrepareResponse, times(1)).prepareSuccessResponseObject(any());
    }

    @Test
    void testGetByID_Success() throws SQLException {
        EmployeReq request = new EmployeReq();
        request.setId(1L);

        // Mock database behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock result set behavior
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong("emp_id")).thenReturn(1L);
        when(mockResultSet.getString("emp_name")).thenReturn("sanjay");
        when(mockResultSet.getString("emp_address")).thenReturn("123 Street");
        when(mockResultSet.getString("emp_city")).thenReturn("New York");
        when(mockResultSet.getLong("emp_salary")).thenReturn(50000L);
        when(mockResultSet.getInt("activestatus")).thenReturn(1);

        // Mock response behavior
        GenericResponse mockResponse = new GenericResponse();
        mockResponse.setStatus("OK");
        mockResponse.setMessage("Success");

        when(mockPrepareResponse.prepareSuccessResponseObject(any()))
            .thenReturn(RestResponse.ok(mockResponse));

        // Execute the method
        RestResponse<GenericResponse> response = employeDao.GetByID(request);

        // Assertions
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals("Success", response.getEntity().getMessage());

        // Verify interactions
        verify(mockStatement, times(1)).executeQuery();
        verify(mockPrepareResponse, times(1)).prepareSuccessResponseObject(any());
    }
    
}