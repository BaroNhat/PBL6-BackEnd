package PBL6.example.UNIME.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED(3333, "Invalid or expired token", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(4444, "you do not have permission", HttpStatus.FORBIDDEN),

    // lỗi đăng nhập
    USER_NOT_EXITED(1011, "User Not Exited", HttpStatus.BAD_REQUEST),
    TOKEN(1012, "Unauthenticated", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1012, "Unauthenticated", HttpStatus.BAD_REQUEST),

    // Các mã lỗi liên quan đến thông tin đăng kí
    USERNAME_ALREADY_TAKEN(1021, "Username is already taken", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_REGISTERED(1022, "Email is already registered", HttpStatus.BAD_REQUEST),

    // Các mã lỗi liên quan đến Đối tượng
    PATIENT_NOT_FOUND(2001, "Patient not found", HttpStatus.NOT_FOUND),
    DEPARTMENT_NOT_FOUND(2002, "Department not found", HttpStatus.NOT_FOUND),
    DEPARTMENT_EXISTED(2003, "Department is already exist", HttpStatus.BAD_REQUEST),
    DEPARTMENT_CAN_NOT_DELETE(2004, "Cannot delete department as it has associated employees/doctors/service.", HttpStatus.BAD_REQUEST),
    SERVICE_NOT_FOUND(2004, "Service not found", HttpStatus.NOT_FOUND),
    SERVICE_EXITED(2005, "Service exited", HttpStatus.BAD_REQUEST),
    SERVICE_CAN_NOT_DELETE(2002, "Cannot delete service as it has associated doctors.", HttpStatus.BAD_REQUEST),
    TIMEWORK_EXITED(2006, "Timework exited", HttpStatus.BAD_REQUEST),
    TIMEWORK_NOT_FOUND(2007, "Timework not found", HttpStatus.NOT_FOUND),
    DOCTORSERVICE_EXITED(2008, "Doctor service exited", HttpStatus.BAD_REQUEST),
    DOCTORSERVICE_NOT_FOUND(2009, "Doctor service not found", HttpStatus.NOT_FOUND),
    DOCTORSERVICE_CAN_NOT_DELETE(2009, "Cannot delete as it has associated appointment.", HttpStatus.NOT_FOUND),
    DOCTORTIMEWORK_NOT_FOUND(2010, "Doctor timework not found", HttpStatus.NOT_FOUND),
    APPOINTMENT_NOT_FOUND(2011, "Appointment not found", HttpStatus.NOT_FOUND),
    INVALID_DAY(2008, "Invalid day of week", HttpStatus.BAD_REQUEST),
    INVALID_TIME(2009, "Invalid time", HttpStatus.BAD_REQUEST),
    DOCTOR_DETAILS_NOT_FOUND(2003, "Doctor detail not found", HttpStatus.NOT_FOUND),
    DOCTORS_NOT_FOUND(2004, "Doctors not found", HttpStatus.NOT_FOUND),
    EMPLOYEE_NOT_FOUND(2005, "Employee not found", HttpStatus.NOT_FOUND),
    // Các mã lỗi liên quan đến dữ liệu đầu vào
    INVALID_KEY(3000, "Invalid key", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME_FORMAT(3001, "Invalid username format", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_FORMAT(3002, "Invalid password format", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL_FORMAT(3003, "Invalid email format", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_NUMBER_FORMAT(3004, "Invalid phone number format", HttpStatus.BAD_REQUEST),
    MISSING_REQUIRED_FIELDS(3005, "Missing required fields", HttpStatus.BAD_REQUEST),
    INVALID_DATETIME_FORMAT(3006, "Invalid datetime format", HttpStatus.BAD_REQUEST),

    CAN_NOT_CONVERT(3300,"Cannot converter object", HttpStatus.BAD_REQUEST),
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
