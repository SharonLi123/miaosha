package top.safun.miaosha.result;

public class Result<T> {
    private int code;
    private String msg;
    private T data;

    private Result(T data){
        this.code=0;
        this.msg="success";
        this.data=data;
    }

    private Result(CodeMessage codeMessage){
        this.code=codeMessage.getCode();
        this.msg=codeMessage.getMsg();
    }

    public static <T> Result<T> success(T data){
        return new Result<>(data);
    }

    public static <T> Result<T> error(CodeMessage codeMessage){
        return new Result<>(codeMessage);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
