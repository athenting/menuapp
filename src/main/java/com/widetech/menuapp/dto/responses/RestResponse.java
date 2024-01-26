package com.widetech.menuapp.dto.responses;

import com.widetech.menuapp.constants.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Http Rest 响应工具及数据格式封装
 *
 * @author xiongxiaoyang
 * @date 2022/5/11
 */
@Getter
@Setter
public class RestResponse<T> {

    private int status; // HTTP status code

    /**
     * 响应码
     */
    @Schema(description = "错误码，00000-没有错误")
    private String code;

    /**
     * 响应消息
     */
    @Schema(description = "响应消息")
    private String message;

    /**
     * 响应数据
     */
    @Schema(description = "响应数据")
    private T data;

    private RestResponse() {
        this.code = ErrorCode.SUCCESS.getCode();
        this.message = ErrorCode.SUCCESS.getDescription();
    }

    private RestResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getDescription();
    }

    private RestResponse(T data) {
        this();
        this.data = data;
    }

    /**
     * 业务处理成功,无数据返回
     */
    public static RestResponse<Void> success() {
        return new RestResponse<>();
    }

    public static <T> RestResponse<T> success(T data) {
        RestResponse<T> response = new RestResponse<>();
        response.status = 200;
        response.data = data;
        response.message = "OK";
        return response;
    }

    public static RestResponse<Void> noContent() {
        RestResponse<Void> response = new RestResponse<>();
        response.status = 204;
        response.message = "No Content";
        return response;
    }

    public static <T> RestResponse<T> notFound() {
        RestResponse<T> response = new RestResponse<>();
        response.status = 404;
        response.message = "Not Found";
        return response;
    }

    /**
     * 业务处理失败
     */
    public static RestResponse<Void> fail(ErrorCode errorCode) {
        return new RestResponse<>(errorCode);
    }

    /**
     * 系统错误
     */
    public static RestResponse<Void> sysError() {
        return new RestResponse<>(ErrorCode.SYSTEM_ERROR);
    }

}
