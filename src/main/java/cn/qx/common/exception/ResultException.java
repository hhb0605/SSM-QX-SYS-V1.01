package cn.qx.common.exception;

import cn.qx.common.enums.ResultEnums;

/**
 * 
 * @author STK_Tofu
 * @date 2019年2月21日
 */
public class ResultException extends RuntimeException {
    private static final long serialVersionUID = 4844279654139668597L;

    public ResultException(String message) {
        super(message);
    }

    public ResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResultException(ResultEnums resultEnums) {
        
    }
}
