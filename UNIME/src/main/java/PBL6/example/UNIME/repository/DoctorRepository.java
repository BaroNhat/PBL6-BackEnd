package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.dto.response.DoctorDetailResponse;
import PBL6.example.UNIME.entity.Department;
import PBL6.example.UNIME.entity.Doctor;
import PBL6.example.UNIME.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByDoctorNameContaining(String doctorName);
    List<Doctor> findBydepartment(Department department);
    Optional<Doctor> findByDoctorUserId(User user);
    @Query("""
        SELECT new PBL6.example.UNIME.dto.response.DoctorDetailResponse(
            doc.doctorId ,
            doc.doctorImage ,
            doc.doctorName ,
            doc.doctorAddress,
            doc.doctorPhoneNumber ,
            doc.doctorGender,
            doc.doctorDateOfBirth ,
            doc.doctorDescription,
            doc.doctorUserId.email,
            
            doc.department.departmentName ,
            
            doc.doctorDetail.doctordetailInformation ,
            doc.doctorDetail.doctordetailExperience ,
            doc.doctorDetail.doctordetailAwardRecognization
          )
        FROM Doctor  doc
        WHERE doc.doctorId = :id
    """)
    Optional<DoctorDetailResponse>  findDoctorById(@Param("id") Integer id);

    @Query("""
        SELECT new PBL6.example.UNIME.dto.response.DoctorDetailResponse(
            doc.doctorId ,
            doc.doctorImage ,
            doc.doctorName ,
            doc.doctorAddress,
            doc.doctorPhoneNumber ,
            doc.doctorGender,
            doc.doctorDateOfBirth ,
            doc.doctorDescription,
            doc.doctorUserId.email,
    
            doc.department.departmentName ,
   
            doc.doctorDetail.doctordetailInformation ,
            doc.doctorDetail.doctordetailExperience ,
            doc.doctorDetail.doctordetailAwardRecognization
          )
        FROM Doctor  doc
        WHERE doc.doctorUserId.username = :username
    """)
    Optional<DoctorDetailResponse> findByUsername(@Param("username") String username);
}
