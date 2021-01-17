package exceptions;

public class RenderException extends RuntimeException{
    public RenderException(String msg){
        super(msg);
        super.initCause(this);
    }
}
