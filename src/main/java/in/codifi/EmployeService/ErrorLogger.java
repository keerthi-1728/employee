package in.codifi.EmployeService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.sql.SQLTimeoutException;
import java.sql.SQLTransientConnectionException;
import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;

import in.codifi.Utility.ErrorConstants;

@ApplicationScoped
public class ErrorLogger {

    @Inject
    DataSource dataSource;

    public void logError(String tableName, String operation, SQLException e) {
        String errorMessage = classifySqlException(e);
        int errorCodeId = classifySqlExceptionId(e);

        String sql = "INSERT INTO error_logs (error_code_id, table_name, operation, error_message, timestamp) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setInt(1, errorCodeId);
            pstmt.setString(2, tableName);
            pstmt.setString(3, operation);
            pstmt.setString(4, errorMessage);
            pstmt.setTimestamp(5, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            pstmt.executeUpdate();

        } catch (SQLException logException) {
            logException.printStackTrace(); // Consider using a logging framework instead.
        }
    }

    private String classifySqlException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            String message = e.getMessage().toLowerCase(); // Convert to lowercase for consistency
            
            if (message.contains("cannot insert null") || message.contains("column cannot be null")) {
                return ErrorConstants.ERROR_NULL_CONSTRAINT; // Now correctly identifies NOT NULL violations
            } else if (message.contains("duplicate entry")) {
                return ErrorConstants.ERROR_DUPLICATE_ENTRY;
            } else {
                return ErrorConstants.ERROR_INTEGRITY_CONSTRAINT;
            }
        } else if (e instanceof SQLSyntaxErrorException) {
            return ErrorConstants.ERROR_SYNTAX;
        } else if (e instanceof SQLTimeoutException) {
            return ErrorConstants.ERROR_TIMEOUT;
        } else if (e instanceof SQLTransientConnectionException) {
            return ErrorConstants.ERROR_CONNECTION;
        } else {
            return ErrorConstants.ERROR_SQL_EXCEPTION;
        }
    }


    private int classifySqlExceptionId(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            String message = e.getMessage().toLowerCase();
            if (message.contains("cannot insert null")|| message.contains("column cannot be null")) {
                return ErrorConstants.ID_NULL_CONSTRAINT;
            } else if (message.contains("duplicate entry")) {
                return ErrorConstants.ID_DUPLICATE_ENTRY;
            } else {
                return ErrorConstants.ID_INTEGRITY_CONSTRAINT;
            }
        } else if (e instanceof SQLSyntaxErrorException) {
            return ErrorConstants.ID_SYNTAX_ERROR;
        } else if (e instanceof SQLTimeoutException) {
            return ErrorConstants.ID_TIMEOUT;
        } else if (e instanceof SQLTransientConnectionException) {
            return ErrorConstants.ID_CONNECTION_ERROR;
        } else {
            return ErrorConstants.ID_SQL_EXCEPTION;
        }
    }
}
