package PBL6.example.UNIME.enums;

import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;

public enum AppointmentStatus {
    Cancelled,
    Pending,
    Completed
;
    public static boolean contains(String status) {
        for (AppointmentStatus appointmentStatus : values()) {
            if (appointmentStatus.name().equals(status)) {
                return true;
            }
        }
        return false;
    }
}
