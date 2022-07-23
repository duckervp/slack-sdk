package vn.savvycom.slackprovider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import vn.savvycom.slackprovider.exception.SendMessageFailedException;
import vn.savvycom.slackprovider.exception.SlackException;

import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends BaseController {
    @ExceptionHandler({ HttpMessageNotReadableException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleException(HttpMessageNotReadableException e) {
        final String message = "Request body is required!";
        log.info("{} {}: {}", HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), message);
        return failedResponse(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException e) {
        String message;
        if(CollectionUtils.isEmpty(e.getBindingResult().getFieldErrors())) {
            message = e.getMessage();
        } else {
            message = e.getBindingResult().getFieldErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
        }
        log.info("{} {}: {}", HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), message);
        return failedResponse(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ SendMessageFailedException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleException(SendMessageFailedException e) {
        log.info("{} {}: {}", HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), e.getMessage());
        return failedResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ SlackException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleException(SlackException e) {
        log.info("{} {}: {}", HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), e.getMessage());
        return failedResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleException(IllegalArgumentException e) {
        log.info("{} {}: {}", HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), e.getMessage());
        return failedResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleException(Exception e) {
        log.info("{} {}: {}", HttpStatus.INTERNAL_SERVER_ERROR, e.getClass().getSimpleName(), e.getMessage());
        return failedResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
