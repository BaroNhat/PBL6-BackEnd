package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.DoctorTimeworkCreateRequest;
import PBL6.example.UNIME.dto.response.DoctorTimeworkResponse;
import PBL6.example.UNIME.entity.*;
import PBL6.example.UNIME.enums.DayOfWeek;
import PBL6.example.UNIME.enums.DoctorTimeworkStatus;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class DoctorTimeworkService {

    TimeworkService timeworkService;
    DoctorTimeworkRepository doctorTimeworkRepository;
    DoctorServiceImpl doctorService;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createDoctorTimework(String  username, List<DoctorTimeworkCreateRequest> requests) {
        Doctor doctor = doctorService.getDoctorByUsername(username);
        List<DoctorTimework> doctorTimeworkList = new ArrayList<>();
        for(DoctorTimeworkCreateRequest request : requests) {
            doctorTimeworkList.add(maptoDoctorTimework(doctor,request));
        }

        batchInsertDoctorTimework(doctorTimeworkList);
    }
    private void batchInsertDoctorTimework(List<DoctorTimework> doctorTimeworks) {
        String sql = "INSERT INTO doctor_timework (doctortimework_year, week_of_year, timework_id, doctor_id, doctortimework_status) VALUES ";
        StringBuilder sb = new StringBuilder(sql);
        for (int i = 0; i < doctorTimeworks.size(); i++) {
            sb.append("(")
                    .append(doctorTimeworks.get(i).getYear()).append(",")
                    .append(doctorTimeworks.get(i).getWeekOfYear()).append(", ")
                    .append(doctorTimeworks.get(i).getTimeWork().getId()).append(", ")
                    .append(doctorTimeworks.get(i).getDoctor().getDoctorId()).append(", '")
                    .append(doctorTimeworks.get(i).getStatus()).append("')");

            if (i < doctorTimeworks.size() - 1) {
                sb.append(", ");
            }
        }

        jakarta.persistence.Query query = entityManager.createNativeQuery(sb.toString());
        query.executeUpdate();
    }



    public String updateDoctorTimework(Integer doctorTimeworkId , String doctorTimeworkStatus) {

        DoctorTimework doctorTimework = doctorTimeworkRepository.findById(doctorTimeworkId).
                orElseThrow(()->new AppException(ErrorCode.DOCTORTIMEWORK_NOT_FOUND));

        if(!DoctorTimeworkStatus.contains(doctorTimeworkStatus)) throw new AppException(ErrorCode.INVALID_KEY);
        doctorTimework.setStatus(doctorTimeworkStatus);
        doctorTimeworkRepository.save(doctorTimework);

        return "Thanh cong";
    }

    public List<DoctorTimeworkResponse> getAllDoctorTimeworkByWeek(Employee employee, String week_year) {
        String[] strings = week_year.split("_");
        Integer year = Integer.valueOf(strings[1]);
        Integer week = Integer.valueOf(strings[0]);
        log.info("getAllDoctorTimeworkByWeek");
        return doctorTimeworkRepository.findByDepartmentAndWeek(employee.getDepartment(), week, year)
                .stream()
                .map(this::mapToDoctorTimeworkResponse)
                .collect(Collectors.toList());
    }

    public List<DoctorTimeworkResponse> getListTimeworkOfDoctor(Doctor doctor) {
        LocalDate today = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int year = today.getYear();
        int week = today.get(weekFields.weekOfYear());
        int nextWeek = getNextWeek(week,year);
        int nextYear = (nextWeek == 1 )? year+1: (year);
        log.info("week: {} ___year:  {}", week, year);
        log.info("nextWeek: {}  ___nextYear: {}", nextWeek, nextYear);
        log.info("doctorID: {}", doctor.getDoctorId());

        return doctorTimeworkRepository.findDoctorTimeworkBydoctor(doctor, week,year, DoctorTimeworkStatus.Available.name(), nextWeek,nextYear)
                .stream()
                .map(this::mapToDoctorTimeworkResponse)
                .collect(Collectors.toList());
    }


    private DoctorTimeworkResponse mapToDoctorTimeworkResponse(Map<String, Object> tuple) {
    return DoctorTimeworkResponse.builder()
            .doctorTimeworkId((Integer) tuple.get("id"))
            .doctorTimeworkYear((Integer) tuple.get("year"))
            .weekOfYear((Integer) tuple.get("weekOfYear"))
            .dayOfWeek((String) tuple.get("dayOfWeek"))
            .startTime((LocalTime) tuple.get("startTime"))
            .endTime((LocalTime) tuple.get("endTime"))
            .doctorId((Integer) tuple.get("doctorId"))
            .doctorName((String) tuple.get("doctorName"))
            .doctorTimeworkStatus((String) tuple.get("status"))
            .build();
}

    //===============================================//
    public  Integer getNextWeek(Integer weekOfYear, Integer year) {
        LocalDate lastDayOfYear = LocalDate.of(year, 12, 31);
        int maxWeeks = lastDayOfYear.get(WeekFields.of(Locale.getDefault()).weekOfYear());
        return weekOfYear < maxWeeks ? weekOfYear + 1 : 1; // Nếu là tuần cuối cùng, quay về tuần 1
    }

    private  DoctorTimework  maptoDoctorTimework(Doctor doctor,  DoctorTimeworkCreateRequest dtRequest ){
        Timework timework = entityManager.getReference(Timework.class, dtRequest.getTimework_id());
        return DoctorTimework.builder()
                .year(dtRequest.getDoctorTimeworkYear())
                .weekOfYear(dtRequest.getWeekOfYear())
                .timeWork(timework)
                .doctor(doctor)
                .status(dtRequest.getDoctorTimeworkStatus())
                .build();
    }

    public void isDayOfWeek(String day) {
        log.info("Day: {}", day);
        try {
            DayOfWeek.valueOf(day.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.INVALID_DAY);
        }
    }

    public static LocalTime parseTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        log.info("Time: {}", time);
        try {
            return LocalTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            throw new AppException(ErrorCode.INVALID_TIME);
        }
    }
}