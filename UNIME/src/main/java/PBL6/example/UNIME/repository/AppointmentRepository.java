package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.entity.Appointment;
import PBL6.example.UNIME.entity.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {




    default List<Appointment> getAppointmentsToday() {
        LocalDate today = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        int year = today.getYear();
        int week = today.get(weekFields.weekOfYear());
        String dayOfWeek =  today.getDayOfWeek().toString();
        System.out.println(year + " " + week + " " + dayOfWeek);
        return findAppointmentByDay(week, year, dayOfWeek);
    }

    @Query("""
        SELECT apt
        FROM Appointment apt
        WHERE apt.doctortimework.timeWork.dayOfWeek = :dayOfWeek AND
              apt.doctortimework.weekOfYear = :week AND
              apt.doctortimework.year = :year
    """)
    List<Appointment> findAppointmentByDay(@Param("week") int week,
                                           @Param("year") int year,
                                           @Param("dayOfWeek") String dayOfWeek);

    @Query("""
        SELECT 
            apt.appointmentId AS appointmentId,
            apt.patient.patientName AS patientName,
            apt.doctortimework.doctor.doctorName AS doctorName,
            apt.doctortimework.timeWork.dayOfWeek AS dayOfWeek,
            apt.doctortimework.timeWork.startTime AS startTime,
            apt.doctortimework.timeWork.endTime   AS endTime  ,
            apt.doctorservice.service.serviceName AS serviceName,
            apt.appointmentCreatedAt  AS appointmentCreatedAt,
            apt.appointmentStatus AS appointmentStatus,
            apt.employee.employeeId AS employeeId,
            apt.employee.employeeName AS employeeName,
            apt.appointmentNote AS appointmentNote
        FROM Appointment apt
        WHERE apt.doctortimework.doctor.department.departmentId = :departmentId
    """)
    List<Map<String,Object>> findByDepartment(@Param("departmentId") Integer departmentId);


    @Query("""
        SELECT 
            apt.appointmentId AS appointmentId,
            apt.patient.patientName AS patientName,
            apt.doctortimework.doctor.doctorName AS doctorName,
            apt.doctortimework.timeWork.dayOfWeek AS dayOfWeek,
            apt.doctortimework.timeWork.startTime AS startTime,
            apt.doctortimework.timeWork.endTime   AS endTime  ,
            apt.doctorservice.service.serviceName AS serviceName,
            apt.appointmentCreatedAt  AS appointmentCreatedAt,
            apt.appointmentStatus AS appointmentStatus,
            apt.employee.employeeId AS employeeId,
            apt.employee.employeeName AS employeeName,
            apt.appointmentNote AS appointmentNote
        FROM Appointment apt
        WHERE apt.doctortimework.doctor.doctorId = :doctorId
    """)
    List<Map<String,Object>> findByDoctor(@Param("doctorId") Integer doctorId);
}

