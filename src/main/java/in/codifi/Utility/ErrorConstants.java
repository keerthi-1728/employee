package in.codifi.Utility;

public class ErrorConstants {
	
	 public static final int ID_OK = 0;
	    public static final int ID_SQL_EXCEPTION = 101;
	    public static final int ID_INTEGRITY_CONSTRAINT = 102;
	    public static final int ID_DUPLICATE_ENTRY = 103;
	    public static final int ID_SYNTAX_ERROR = 104;
	    public static final int ID_TIMEOUT = 105;
	    public static final int ID_CONNECTION_ERROR = 106;
	    public static final int ID_PERMISSION_DENIED = 107;
	    public static final int ID_NULL_CONSTRAINT = 107;

	    public static final String STATUS_OK = "OK";
	    public static final String ERROR_SQL_EXCEPTION = "SQL Exception occurred";
	    public static final String ERROR_INTEGRITY_CONSTRAINT = "Integrity constraint violation";
	    public static final String ERROR_DUPLICATE_ENTRY = "Duplicate entry";
	    public static final String ERROR_SYNTAX = "Syntax error in SQL statement";
	    public static final String ERROR_TIMEOUT = "Query execution timeout";
	    public static final String ERROR_CONNECTION = "Database connection issue";
	    public static final String ERROR_PERMISSION_DENIED = "Permission denied for the query";
	    public static final String ERROR_NULL_CONSTRAINT = "Inserted null value for a not null field";

}