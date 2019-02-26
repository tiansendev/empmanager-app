package empmanager.alochol.empmanager.model;

import java.io.Serializable;

/**
 * http返回实体包装类
 * @param <T>
 */
public class ServiceResult<T>  implements Serializable{
    private String code;
    private String message;
    private T result;

    public static <T> ServiceResult<T> success(T result) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.result = result;
        item.code = ServiceResultCode.SUCCESS.getCode();
        item.message = ServiceResultCode.SUCCESS.getMessage();
        return item;
    }
    public static ServiceResult success() {
        return ServiceResult.success(null);
    }

    public static <T> ServiceResult<T> failure(String errorCode) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.code = errorCode;
        item.message = ServiceResultCode.getNameByCode(errorCode);
        return item;
    }
    public static <T> ServiceResult<T> failure(ServiceResultCode serviceResultCode) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.code = serviceResultCode.getCode();
        item.message = serviceResultCode.getMessage();
        return item;
    }
    public static <T> ServiceResult<T> failure(String errorCode, String message) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.code = errorCode;
        item.message = ServiceResultCode.getNameByCode(errorCode) + ": " + message;
        return item;
    }

    public boolean hasResult() {
        return result != null;
    }
    public boolean isSuccess(){
        return code.equals(ServiceResultCode.SUCCESS.getCode());
    }

    public T getResult() {
        return result;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private ServiceResult() {

    }
    public void setResult(T result) {
        this.result = result;
    }
}
