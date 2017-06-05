package com.steveq.error;

/**
 * Created by Adam on 2017-06-05.
 */
public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String message){
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
