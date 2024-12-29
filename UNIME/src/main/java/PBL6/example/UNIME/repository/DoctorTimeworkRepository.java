package PBL6.example.UNIME.repository;

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
    FROM DoctorTimework dtw JOIN FETCH dtw.doctor d JOIN FETCH dtw.timeWork JOIN FETCH d.doctorUserId
    WHERE dtw.weekOfYear = :weekOfYear
      AND dtw.year = :year
""")
    List<DoctorTimework> findByWeek(
            @Param("weekOfYear") Integer weekOfYear,
            @Param("year") Integer year
    );

    @Query("""
    SELECT dtw
    FROM DoctorTimework dtw JOIN FETCH dtw.timeWork
""")
    List<DoctorTimework> findAll();

}
