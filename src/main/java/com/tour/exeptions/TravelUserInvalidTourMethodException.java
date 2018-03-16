package com.tour.exeptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TravelUserInvalidTourMethodException extends RuntimeException {

    public TravelUserInvalidTourMethodException(String message) {
        super(message);
    }
}
