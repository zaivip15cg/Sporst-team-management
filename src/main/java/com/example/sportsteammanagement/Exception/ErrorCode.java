package com.example.sportsteammanagement.Exception;

public enum ErrorCode {
    UNCATEGORIZED_Exception(9999,"Lỗi không xác định"),
    User_Exist(1001, "User Existed"),
    PHONE_EXISTED(1011, "Số điện thoại đã được sử dụng"),
    VALIDATE_Error(1002,"Thieu chu rồi"),
    User_NOT_Exist(1003, "Khong co ton tai user nay"),
    UnAuthenticated(1004, "Unauthenticated"),
    TOKEN_NOT_EXIST(1005,"sai token"),
    PASSWORD_NOT_TRUE( 1006, "Password  sai"),
    PASSWORD_NOT_MATCH(1007,"Password does not match"),
    EMAIL_SEND_FAID(1008,"email không gửi được"),
    TOKEN_OVERDUE(1010, "token đã bị hết hạn, hãy nhập lại email"),
    TEAM_EXISTED(2000, "Tên team đã tồn tại"),
    TEAM_NOT_FOUND(2001, "Không tìm thấy team"),
    MEMBER_EXISTED(3000, "Member đã tồn tại"),
    POSITION_NOT_FOUND(3001, "Không tìm thấy Position"),
    MEMBER_NOT_FOUND(3002, "Không tìm thấy thành viên"),
    TOKEN_NOT_EXIT(4000,"không thấy token")



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

