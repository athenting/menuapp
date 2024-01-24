package com.widetech.menuapp.dto.responses;

import com.widetech.menuapp.constants.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Objects;

/**
 * Http Rest 响应工具及数据格式封装
 *
 * @author xiongxiaoyang
 * @date 2022/5/11
 */
@Getter
public class RestResponse<T> {

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

    /**
     * 业务处理成功，有数据返回
     */
    public static <T> RestResponse<T> success(T data) {
        return new RestResponse<>(data);
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

    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return Objects.equals(this.code, ErrorCode.SUCCESS.getCode());
    }

}
