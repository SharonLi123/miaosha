package top.safun.miaosha.result;


public class CodeMessage {
    private int code;
    private String msg;

    //通用错误码
    public static CodeMessage SUCCESS=new CodeMessage(0,"success");
    public static CodeMessage SERVER_ERROR=new CodeMessage(500100,"服务端异常");
    public static CodeMessage BIND_ERROR=new CodeMessage(500101,"参数校验异常: %s");

    //登陆模块
    public static CodeMessage SESSION_ERROR=new CodeMessage(500210,"Session不存在或已失效");
    public static CodeMessage PASSWORD_EMPTY=new CodeMessage(500211,"登陆密码不能为空");
    public static CodeMessage MOBILE_EMPTY=new CodeMessage(500212,"手机号不能为空");
    public static CodeMessage MOBILE_ERROR=new CodeMessage(500212,"手机号格式错误");
    public static CodeMessage MOBILE_NOT_EXIST=new CodeMessage(500213,"手机号码不存在");
    public static CodeMessage PASSWORD_ERROR=new CodeMessage(500212,"密码错误");

    //商品模块 5003XX

    //订单模块 5004XX

    //秒杀模块 5005XX
    public static CodeMessage MIAOSHA_OVER=new CodeMessage(500500,"商品已经秒杀完毕");
    public static CodeMessage REPEATE_MIAOSHA=new CodeMessage(500501,"不能重复秒杀");

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

    public CodeMessage fillArgs(Object... args){
        int code=this.code;
        String msg=String.format(this.msg,args);
        return new CodeMessage(code,msg);
    }

    @Override
    public String toString() {
        return "CodeMessage{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
