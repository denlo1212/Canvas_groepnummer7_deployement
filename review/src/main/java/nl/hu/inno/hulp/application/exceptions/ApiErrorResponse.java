package nl.hu.inno.hulp.application.exceptions;

public class ApiErrorResponse {
    private int status;
    private String message;
    private long timestamp;

    // Constructor to initialize the fields
    public ApiErrorResponse(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters for the fields
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // Setters for the fields (optional, if you want to modify the fields later)
    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}