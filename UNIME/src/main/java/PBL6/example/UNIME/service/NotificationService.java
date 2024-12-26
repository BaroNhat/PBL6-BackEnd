package PBL6.example.UNIME.service;

import PBL6.example.UNIME.entity.Doctor;
import PBL6.example.UNIME.entity.Notification;
import PBL6.example.UNIME.enums.Role;
import PBL6.example.UNIME.repository.DoctorRepository;
import PBL6.example.UNIME.repository.NotificationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
@EnableScheduling
public class NotificationService {

    private final DoctorRepository doctorRepository;
    private final NotificationRepository notificationRepository;
    private final PatientServiceImpl patientService;

    @Scheduled(cron = "0 0 8 ? * SAT")//second, minute, hour, day of month, month, day(s) of week
    private void addNotifyDoctorSchedule(){
        List<Doctor> doctors = doctorRepository.findAll();
        for (Doctor doctor : doctors) {
            Notification no  = new Notification();
            no.setRecipientType(Role.DOCTOR.name());
            no.setRecipientId(doctor.getDoctorId());
            no.setRelatedObjectType("DOCTORTIMEWORK");
            no.setNotificationMessage("Bác sĩ "+doctor.getDoctorName()+", " +
                    "lịch làm việc tuần mới chưa được cập nhật. " +
                    "Vui lòng chọn ca làm việc trước 24h hôm nay. " +
                    "Nếu không, hệ thống sẽ tự động tạo lịch đầy đủ.");
            notificationRepository.save(no);
        }
    }
    // thử chuyển sang thêm 1 lần nhiều dòng


    @Scheduled(cron = "0 0 8 * * *")
    private void addNotifyPatientSchedule(){
        List<Integer> patients = patientService.getPatientsHaveAppointment();
        int dem = 0;
        for (Integer patient_id : patients) {
            log.info("dem: {}", dem ++ );
            Notification no  = new Notification();
            no.setRecipientType(Role.PATIENT.name());
            no.setRecipientId(patient_id);
            no.setRelatedObjectType("APPOINTMENT");
            no.setNotificationMessage("Hôm nay bạn có lịch hẹn với bệnh viện");
            notificationRepository.save(no);
        }
    }


}
