package com.example.identityservice.Exception;

import com.example.identityservice.dto.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)

    public ResponseEntity<APIResponse> loichuaxacdinh(Exception exception){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_Exception.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_Exception.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
    @ExceptionHandler(value = AppException.class)

    public ResponseEntity<APIResponse> handlineUserNotFoundException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        APIResponse apiResponse = new APIResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Map<String, List<String>>>> handleValidationErrors(
            MethodArgumentNotValidException ex
    ) {
        // field -> list lỗi
        Map<String, List<String>> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    String field = error.getField();
                    String message = error.getDefaultMessage();
                    // Nếu field chưa tồn tại → tạo list mới
                    errors
                            .computeIfAbsent(field, k -> new ArrayList<>())
                            .add(message);
                });

        APIResponse<Map<String, List<String>>> apiResponse = new APIResponse<>();
        apiResponse.setCode(ErrorCode.VALIDATE_Error.getCode());
        apiResponse.setMessage(ErrorCode.VALIDATE_Error.getMessage());
        apiResponse.setResult(errors);

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
