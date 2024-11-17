package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.DoctorTimeworkRequest;
import PBL6.example.UNIME.dto.response.DoctorResponse;
import PBL6.example.UNIME.dto.response.DoctorTimeworkResponse;
import PBL6.example.UNIME.entity.*;
import PBL6.example.UNIME.enums.DayOfWeek;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class DoctorTimeworkService {

    private final PBL6.example.UNIME.service.DoctorService doctorService;
    private final TimeworkService timeworkService;
    private final DoctorTimeworkRepository doctorTimeworkRepository;
    private final EmployeeService employeeService;

    public void createDoctorTimework( Doctor doctor, DoctorTimeworkRequest request) {

        request.validateRequest();
        DayOfWeek dow = parseDayOfWeek(request.getDayOfWeek());
        LocalTime startTime = parseTime(request.getStartTime());
        LocalTime endTime = parseTime(request.getEndTime());
        log.info(" {} = {} = {}",dow,startTime,endTime);
        Timework timework = timeworkService.getTimeworkByInfo(dow,startTime,endTime);
        if(timework == null) throw new AppException(ErrorCode.TIMEWORK_NOT_FOUND);


        DoctorTimework doctorTimework = new DoctorTimework();
        doctorTimework.setYear(request.getDoctorTimeworkYear());
        doctorTimework.setWeekOfYear(request.getWeekOfYear());
        doctorTimework.setTimeWork(timework);
        doctorTimework.setDoctor(doctor);
        doctorTimework.setStatus(request.getDoctorTimeworkStatus());
        doctorTimeworkRepository.save(doctorTimework);

    }

//    public String updateDoctorTimework(String username, DoctorTimeworkRequest request) {
//        Integer employeeDepartmentId = employeeService.getEmployeeByUsername(username).getDepartment().getDepartmentId();
//
//        if(!employeeDepartmentId.equals(request.getDoctorId().)) {
//            throw new AppException(ErrorCode.FORBIDDEN);
//        }
//
//        request.validateRequest();
//        DayOfWeek dow = parseDayOfWeek(request.getDayOfWeek());
//        LocalTime startTime = parseTime(request.getStartTime());
//        LocalTime endTime = parseTime(request.getEndTime());
//
//        Timework timework = timeworkService.getTimeworkByInfo(dow,startTime,endTime);
//        log.info(" {} = {} = {}",dow,startTime,endTime);
//        if(timework == null) throw new AppException(ErrorCode.TIMEWORK_NOT_FOUND);
//
//
//        DoctorTimework doctorTimework = new DoctorTimework();
//        doctorTimework.setYear(request.getDoctorTimeworkYear());
//        doctorTimework.setWeekOfYear(request.getWeekOfYear());
//        doctorTimework.setTimeWork(timework);
//        doctorTimework.setStatus(request.getDoctorTimeworkStatus());
//        doctorTimeworkRepository.save(doctorTimework);
//
//        return "Thanh cong";
//    }

    public List<DoctorTimeworkResponse> getAllDoctorTimeworkByWeek(Employee employee, String week_year) {
        String[] strings = week_year.split("_");
        Integer year = Integer.valueOf(strings[1]);
        Integer week = Integer.valueOf(strings[0]);

        List<DoctorTimework> doctorTimeworkList = doctorTimeworkRepository.findByDepartmentAndWeek(
                employee.getDepartment().getDepartmentId(),
                week,
                year
                );
        return doctorTimeworkList
                .stream()
                .map(this::mapToServiceResponse)
                .collect(Collectors.toList());
    }



    private DoctorTimeworkResponse mapToServiceResponse(DoctorTimework doctorTimework) {
        return new DoctorTimeworkResponse(
                doctorTimework.getId(),
                doctorTimework.getYear(),
                doctorTimework.getWeekOfYear(),
                doctorTimework.getTimeWork().getDayOfWeek().name(),
                doctorTimework.getTimeWork().getStartTime().toString(),
                doctorTimework.getTimeWork().getEndTime().toString(),
                doctorTimework.getDoctor().getDoctorId(),
                doctorTimework.getStatus()
        );
    }

    public DayOfWeek parseDayOfWeek(String day) {
        log.info("Day: {}", day);
        try {
            return DayOfWeek.valueOf(day.toUpperCase());
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