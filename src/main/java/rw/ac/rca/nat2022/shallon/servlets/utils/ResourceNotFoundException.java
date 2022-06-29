package rw.ac.rca.nat2022.shallon.servlets.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
public ResourceNotFoundException(String model,String column,String value){
        super(model+" with "+column+" ["+value+"] not found");
    }

}
