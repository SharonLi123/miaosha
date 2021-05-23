package top.safun.miaosha.exception;


import top.safun.miaosha.result.CodeMessage;

public class GlobalException extends RuntimeException{
    private static final long SerialVersionUID=1L;

    private CodeMessage codeMessage;

    public GlobalException(CodeMessage codeMessage){
        super(codeMessage.toString());
        this.codeMessage=codeMessage;
    }

    public CodeMessage getCodeMessage() {
        return codeMessage;
    }
}
