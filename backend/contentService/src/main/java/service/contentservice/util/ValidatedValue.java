package service.contentservice.util;

/**
 * Util class to enable services to return valid and invalid values
 * Wrapper of Pair with appropriate naming and additional flags
 * @see Pair
 * @param <T> The type of exception
 * @param <W> The type of the value
 */
public class ValidatedValue<T,W>{

    private final Pair<T,W> pair = new Pair<>();

    public boolean isInvalid(){
        return pair.getFirst() != null;
    }

    public T getException(){
        return pair.getFirst();
    }

    public void setException(T exception){
        this.pair.setFirst(exception);
    }

    public W getValue(){
        return pair.getSecond();
    }

    public void setValue(W value){
        this.pair.setSecond(value);
    }
}
