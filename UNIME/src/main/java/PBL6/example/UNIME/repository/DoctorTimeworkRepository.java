package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.dto.response.DoctorTimeworkResponse;
import PBL6.example.UNIME.entity.Department;
import PBL6.example.UNIME.entity.Doctor;
import PBL6.example.UNIME.entity.DoctorTimework;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface  DoctorTimeworkRepository extends JpaRepository<DoctorTimework, Integer> {



    @Query("""
        SELECT
           dtw.id AS id,
           dtw.year AS year,
           dtw.weekOfYear AS weekOfYear,
           dtw.timeWork.dayOfWeek AS dayOfWeek,
           dtw.timeWork.startTime AS startTime,
           dtw.timeWork.endTime AS endTime,
           dtw.doctor.doctorId AS doctorId,
           dtw.doctor.doctorName AS doctorName,
           dtw.status AS status
        FROM DoctorTimework dtw
        WHERE ((dtw.weekOfYear = :weekOfYear AND dtw.year = :year)
            Or (dtw.weekOfYear = :nextWeek AND dtw.year = :nextYear))
            And dtw.status = :status
            AND dtw.doctor = :doctor
    """)
    List<Map<String, Object>> findDoctorTimeworkBydoctor(
            @Param("doctor") Doctor doctor,
            @Param("weekOfYear") Integer weekOfYear,
            @Param("year") Integer year,
            @Param("status") String status,
            @Param("nextWeek") Integer nextWeek,
            @Param("nextYear") Integer nextYear
    );



    @Query("""
    SELECT dtw.id AS id,
           dtw.year AS year,
           dtw.weekOfYear AS weekOfYear,
           dtw.timeWork.dayOfWeek AS dayOfWeek,
           dtw.timeWork.startTime AS startTime,
           dtw.timeWork.endTime AS endTime,
           dtw.doctor.doctorId AS doctorId,
           dtw.doctor.doctorName AS doctorName,
           dtw.status AS status
    FROM DoctorTimework dtw
    JOIN dtw.doctor d
    WHERE d.department = :department
      AND dtw.weekOfYear = :weekOfYear
      AND dtw.year = :year
""")
    List<Map<String, Object>> findByDepartmentAndWeek(
            @Param("department") Department department,
            @Param("weekOfYear") Integer weekOfYear,
            @Param("year") Integer year
    );



}
