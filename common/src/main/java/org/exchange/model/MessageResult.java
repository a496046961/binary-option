package org.exchange.model;


import java.io.Serializable;

/**
 * @author 书记
 */
public class MessageResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private Object data;
    private int code;
    private String message;
    private Long total;

    public MessageResult(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public MessageResult(int code, String msg, Object object, Long total) {
        this.code = code;
        this.message = msg;
        this.total = total;
        this.data = object;
    }

    public MessageResult(int code, String msg, Object object) {
        this.code = code;
        this.message = msg;
        this.data = object;
    }

    public MessageResult() {
    }

    public static MessageResult success() {
        return new MessageResult(0, "SUCCESS");
    }

    public static MessageResult success(String msg) {
        return new MessageResult(0, msg);
    }

    public static MessageResult success(String msg, Object data) {
        return new MessageResult(0, msg, data);
    }

    public static MessageResult success(Object obj) {
        MessageResult mr = new MessageResult(0, "SUCCESS");
        mr.setData(obj);
        return mr;
    }

    public static MessageResult error(int code, String msg) {
        return new MessageResult(code, msg);
    }

    public static MessageResult error(String msg) {
        return new MessageResult(500, msg);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static MessageResult getSuccessInstance(String message, Object data) {
        MessageResult result = success(message);
        result.setData(data);
        return result;
    }

    @Override
    public String toString() {
        return "MessageResult{" +
                "data=" + data +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", total=" + total +
                '}';
    }
}