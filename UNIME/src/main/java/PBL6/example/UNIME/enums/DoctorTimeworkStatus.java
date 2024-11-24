package PBL6.example.UNIME.enums;

import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum DoctorTimeworkStatus {
    Available,
    Busy
    ;
    public static boolean contains(String status) {
        for (DoctorTimeworkStatus timeworkStatus : values()) {
            if (timeworkStatus.name().equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }
}
