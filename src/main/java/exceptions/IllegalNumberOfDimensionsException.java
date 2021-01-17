package exceptions;

public class IllegalNumberOfDimensionsException extends IllegalArgumentException{
    public IllegalNumberOfDimensionsException(String msg){
        super(msg);
        super.initCause(this);
    }
}
