package cn.qx.common.vo;

import cn.qx.common.enums.HttpExceptionEnum;
import cn.qx.common.enums.ResultEnums;

/**
 * 操作状态码
 * @author Satone
 * @date 2019年2月21日
 */
public class Result {

    private Integer code; //状态码
    private Object data; //返回数据

    public Result() {
    }

    public Result(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Result(Integer code, ResultEnums enums) {
        this.code = code;
        this.data = enums.getInfo();
    }

    public Result(Integer code, HttpExceptionEnum enums) {
        this.code = code;
        this.data = enums.getInfo();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
