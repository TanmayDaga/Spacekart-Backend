package net.in.spacekart.backend.exceptions;

import jakarta.annotation.Nullable;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;


public class SpacekartBaseException extends Exception {

    @Getter
    final HttpStatusCode StatusCode;
    @Getter
    final String ResponseMessage;
    Logger logger = LoggerFactory.getLogger(SpacekartBaseException.class);


    public SpacekartBaseException(HttpStatusCode StatusCode, String ResponseMessage, @Nullable Exception ex,String errorMessage) {
        super(ex != null ? ex.getMessage() : errorMessage);
        logger.error(super.getMessage());
        this.StatusCode = StatusCode;
        this.ResponseMessage = ResponseMessage;

    }
}
