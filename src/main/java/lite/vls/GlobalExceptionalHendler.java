package lite.vls;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import lite.vls.transportation.TransportationController;

public class GlobalExceptionalHendler {
    
    private static final Logger log = LoggerFactory.getLogger(TransportationController.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handlerGeneralException(
        EntityNotFoundException e
    ){
        log.error("Handle exception {}", e);

        ErrorResponseDto response = new ErrorResponseDto(
            "Intrnal server error",
            e.getMessage(),
            LocalDateTime.now()
        );

        return ResponseEntity
            .status(500)
            .body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlerEntityNotFound(
        EntityNotFoundException e
    ){
        log.error("Handle entityNotFound");

        ErrorResponseDto response = new ErrorResponseDto("Entity not found",
        e.getMessage(),
        LocalDateTime.now()
        );


        return ResponseEntity
            .status(500)
            .body(response);
    }

    @ExceptionHandler( exception = {
        IllegalArgumentException.class,
        IllegalStateException.class,
        MethodArgumentNotValidException.class
    })
        public ResponseEntity<ErrorResponseDto> handlerBadRequest(
        EntityNotFoundException e
    ){
        log.error("Handle bad request");

        ErrorResponseDto response = new ErrorResponseDto("Bad request",
        e.getMessage(),
        LocalDateTime.now()
        );


        return ResponseEntity
            .status(400)
            .body(response);
    }
}
