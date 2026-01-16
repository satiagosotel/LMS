package org.santiago.lms.app.dto.response;

public class ApiResponse<T> {

    private String status;
    private T data;

    public ApiResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("SUCCESS", data);
    }

    public static <T> ApiResponse<T> error(T data) {
        return new ApiResponse<>("ERROR", data);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}