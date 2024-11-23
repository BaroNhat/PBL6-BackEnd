package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.DoctorTimeworkCreateRequest;
import PBL6.example.UNIME.dto.request.DoctorTimeworkUpdateRequest;
import PBL6.example.UNIME.dto.response.DoctorTimeworkResponse;
import PBL6.example.UNIME.entity.*;
import PBL6.example.UNIME.enums.DayOfWeek;
import PBL6.example.UNIME.enums.DoctorTimeworkStatus;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.*;
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
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class DoctorTimeworkService {

    TimeworkService timeworkService;
    DoctorTimeworkRepository doctorTimeworkRepository;
    EmployeeService employeeService;

    public void createDoctorTimework( Doctor doctor, DoctorTimeworkCreateRequest request) {

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

    public List<DoctorTimeworkResponse> getListTimeworkOfDoctor(Doctor doctor) {
        LocalDate today = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int year = today.getYear();
        int week = today.get(weekFields.weekOfYear());
        int nextWeek = getNextWeek(week,year);
        int nextYear = (nextWeek == 1 )? year+1: (year);
        log.info("week: {} ___year:  {}", week, year);
        log.info("nextWeek: {}  ___nextYear: {}", nextWeek, nextYear);

        List<DoctorTimework> doctorTimeworkList = doctorTimeworkRepository.findDoctorTimeworkBydoctor(doctor,week,year,nextWeek,nextYear);

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
    //===============================================//

    public  Integer getNextWeek(Integer weekOfYear, Integer year) {
        LocalDate lastDayOfYear = LocalDate.of(year, 12, 31);
        int maxWeeks = lastDayOfYear.get(WeekFields.of(Locale.getDefault()).weekOfYear());
        return weekOfYear < maxWeeks ? weekOfYear + 1 : 1; // Nếu là tuần cuối cùng, quay về tuần 1
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