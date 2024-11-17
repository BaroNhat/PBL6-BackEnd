package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.entity.DoctorTimework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  DoctorTimeworkRepository extends JpaRepository<DoctorTimework, Integer> {
    List<DoctorTimework> findDoctorTimeworkByweekOfYear(Integer weekOfYear);

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
