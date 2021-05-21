package top.safun.miaosha.result;

public class CodeMessage {
    private int code;
    private String msg;

    public static CodeMessage SERVER_ERROR=new CodeMessage(1,"请先登录");
    private CodeMessage(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
