package com.widetech.menuapp.dto.responses;

import com.widetech.menuapp.constants.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Http Rest response
 *
 * @author xiongxiaoyang
 * @date 2022/5/11
 */
@Getter
@Setter
public class RestResponse<T> {

    private int status; // HTTP status code

    /**
     * error code
     */
    @Schema(description = "error code")
    private String code;

    /**
     * response message
     */
    @Schema(description = "response msg")
    private String message;

    /**
     * response data
     */
    @Schema(description = "response data")
    private T data;

    private RestResponse() {
        this.code = ErrorCode.SUCCESS.getCode();
        this.message = ErrorCode.SUCCESS.getDescription();
    }

    private RestResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getDescription();
    }

    public RestResponse(T data) {
        this();
        this.data = data;
    }

    /**
     * business successfully done, with no data returned
     */
    public static RestResponse<Void> success() {
        return new RestResponse<>();
    }

    /**
     * Creates a successful REST response with the provided data.
     *
     * @param data The data to be included in the response.
     * @param <T>  The type of the data.
     * @return A RestResponse object representing a successful response, with the provided data included.
     */
    public static <T> RestResponse<T> success(T data) {
        RestResponse<T> response = new RestResponse<>();
        response.status = 200;
        response.data = data;
        response.message = "OK";
        return response;
    }

    /**
     * Creates a REST response with HTTP status code 204 (No Content) and no body content.
     *
     * @return RestResponse
     */
    public static RestResponse<Void> noContent() {
        RestResponse<Void> response = new RestResponse<>();
        response.status = 204;
        response.message = "No Content";
        return response;
    }

    /**
     * Creates a RestResponse object with status code 404 (Not Found) and message "Not Found".
     *
     * @return a RestResponse object with status code 404 and message "Not Found"
     * @param <T> the type parameter
     */
    public static <T> RestResponse<T> notFound() {
        RestResponse<T> response = new RestResponse<>();
        response.status = 404;
        response.message = "Not Found";
        return response;
    }

    /**
     * Creates a RestResponse object with the provided error code.
     *
     * @param errorCode The error code to be included in the response.
     * @return A RestResponse object representing a failed response, with the provided error code included.
     */
    public static <T>  RestResponse<T> fail(ErrorCode errorCode) {
        return new RestResponse<>(errorCode);
    }

    /**
     * Creates a RestResponse object representing a system error.
     *
     * @return A RestResponse object representing a system error.
     */
    public static RestResponse<Void> sysError() {
        return new RestResponse<>(ErrorCode.SYSTEM_ERROR);
    }

}
