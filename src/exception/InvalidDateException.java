package exception;

/**
 * Created by 谢东方xdf on 2018/3/20.
 */
public class InvalidDateException extends Exception {
    public InvalidDateException(String date){
        super(date + " is not valid! ");
    }
}