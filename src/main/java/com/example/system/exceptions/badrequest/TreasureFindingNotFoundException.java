package com.example.system.exceptions.badrequest;

import com.example.system.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TreasureFindingNotFoundException extends ApplicationException {
    public TreasureFindingNotFoundException(int findingId) {
        super("Unable to find a treasure finding with id "+findingId);
    }
}
