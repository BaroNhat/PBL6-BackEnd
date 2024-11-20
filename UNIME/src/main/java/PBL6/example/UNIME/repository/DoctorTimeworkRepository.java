package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.entity.Doctor;
import PBL6.example.UNIME.entity.DoctorTimework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  DoctorTimeworkRepository extends JpaRepository<DoctorTimework, Integer> {


    @Query("""
        SELECT dtw
        FROM DoctorTimework dtw
        WHERE (dtw.weekOfYear = :weekOfYear AND dtw.year = :year)
           OR (dtw.weekOfYear = :nextWeekOfYear AND dtw.year = :nextYear)
           And dtw.status = "Available"
           AND dtw.doctor = :doctor
    """)
    List<DoctorTimework> findDoctorTimeworkBydoctor(
            @Param("doctor") Doctor doctor,
            @Param("weekOfYear") Integer weekOfYear,
            @Param("year") Integer year,
            @Param("nextWeekOfYear") Integer nextWeekOfYear,
            @Param("nextYear") Integer nextYear);

    @Query("""
        SELECT dtw
        FROM DoctorTimework dtw
        JOIN dtw.doctor d
        WHERE d.department.departmentId = :departmentId
          AND dtw.weekOfYear = :weekOfYear
          AND dtw.year = :year
    """)
    List<DoctorTimework> findByDepartmentAndWeek(
            @Param("departmentId") Integer departmentId,
            @Param("weekOfYear") Integer weekOfYear,
            @Param("year") Integer year
    );

}
