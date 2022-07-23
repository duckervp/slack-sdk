package vn.savvycom.slackprovider.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.savvycom.slackprovider.common.Response;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Create default return for controller and controller exception handler methods
 */
public class BaseController{
    public ResponseEntity<?> successResponse(Object data) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(Response.SUCCESS, true);
        body.put(Response.DATA, data);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public ResponseEntity<?> successResponse() {
        return successResponse(null);
    }

    public ResponseEntity<?> failedResponse(String message, HttpStatus httpStatus) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(Response.SUCCESS, false);
        body.put(Response.ERROR, message);
        return new ResponseEntity<>(body, httpStatus);
    }
}
