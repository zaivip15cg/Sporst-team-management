package com.example.identityservice.Exception;

public enum ErrorCode {
    UNCATEGORIZED_Exception(9999,"Lỗi không xác định"),
    User_Exist(1001, "User Existed"),
    VALIDATE_Error(1002,"Thieu chu rồi"),
    User_NOT_Exist(1003, "Khong co ton tai user nay"),
    UnAuthenticated(1004, "Unauthenticated"),
    TOKEN_NOT_EXIST(1005,"sai token")
    ;


    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
}
}

