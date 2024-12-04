package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.DoctorRequest;
import PBL6.example.UNIME.dto.response.DoctorDetailResponse;
import PBL6.example.UNIME.dto.response.DoctorListResponse;
import PBL6.example.UNIME.entity.*;
import PBL6.example.UNIME.enums.Role;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.DepartmentRepository;
import PBL6.example.UNIME.repository.DoctorDetailRepository;
import PBL6.example.UNIME.repository.DoctorRepository;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorService {
    private static final Logger log = LoggerFactory.getLogger(DoctorService.class);

    DoctorRepository doctorRespository;
    UserService userService;
    private final DepartmentService departmentService;
    private final DoctorDetailRepository doctorDetailRespository;
    private final DepartmentRepository departmentRepository;

    public void createDoctor(@Valid DoctorRequest request) {
        // Tìm department có tồn tại
        Department department = departmentService.getDepartmentByName(request.getDepartmentName());

        //1. kiểm tra, khởi tạo user
        User user = new User();
        user.setUsername(request.getDoctorUsername());
        user.setPassword(request.getDoctorPassword());
        user.setEmail(request.getDoctorEmail());
        user.setRole(Role.DOCTOR.name());


        DoctorDetail detail = new DoctorDetail();
        detail.setDoctordetailExperience(request.getDoctordetailExperience());
        detail.setDoctordetailInformation(request.getDoctordetailInformation());
        detail.setDoctordetailAwardRecognization(request.getDoctordetailAwardRecognization());
        doctorDetailRespository.save(detail);

        // 2. Tạo Employee
        Doctor doctor = new Doctor();
        doctor.setDoctorImage(request.getDoctorImage());
        doctor.setDoctorUserId(userService.createUser(user));
        doctor.setDoctorName(request.getDoctorName());
        doctor.setDoctorPhoneNumber(request.getDoctorPhoneNumber());
        doctor.setDoctorGender(request.isDoctorGender());
        doctor.setDoctorAddress(request.getDoctorAddress());
        doctor.setDoctorDescription(request.getDoctorDescription());
        doctor.setDoctorStatus("ON");
        doctor.setDepartment(department);
        doctor.setDoctorDetail(detail);

        doctorRespository.save(doctor);
    }
    public void updateByAdmin(Integer doctor_id, DoctorRequest request) {
        //1.
        Doctor doctor = doctorRespository.findById(doctor_id)
                .orElseThrow(() -> new AppException(ErrorCode.DOCTORS_NOT_FOUND));

        //2.
        Department department = departmentService.getDepartmentByName(request.getDepartmentName());

        //3.
        doctor.setDepartment(department);
        doctor.setDoctorStatus(request.getDoctorStatus());

        doctorRespository.save(doctor);
    }
    public void deleteDoctor(Integer doctor_id) {
        Doctor doctor = doctorRespository.findById(doctor_id)
                .orElseThrow(() -> new AppException(ErrorCode.DOCTORS_NOT_FOUND));

        doctorRespository.delete(doctor);
        userService.deleteUser(doctor.getDoctorUserId().getUserId());
    }

    public DoctorDetailResponse getDoctorById(Integer doctorId) {
        return doctorRespository.findDoctorById(doctorId)
                .orElseThrow(() -> new AppException(ErrorCode.DOCTORS_NOT_FOUND));
    }

    // token
    public DoctorDetailResponse getMyInfo(String Username) {
        log.info("Username : " + Username);
        return doctorRespository.findByUsername(Username)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXITED));
    }
    //token
    public void updateMyInfo(String doctor_username, DoctorRequest request) {
        Doctor doctor = doctorRespository.findByDoctorUserId(userService.getUserByUsername(doctor_username))
                .orElseThrow(()-> new AppException(ErrorCode.DOCTORS_NOT_FOUND));

        //1. cập nhật User
        User user = new User();
        user.setEmail(request.getDoctorEmail());
        userService.updateUser(doctor.getDoctorUserId().getUserId(), user);

        //2. cập nhật doctorDetail
        DoctorDetail detail = doctor.getDoctorDetail();
        detail.setDoctordetailExperience(request.getDoctordetailExperience());
        detail.setDoctordetailInformation(request.getDoctordetailInformation());
        detail.setDoctordetailAwardRecognization(request.getDoctordetailAwardRecognization());
        doctorDetailRespository.save(detail);

        //3. cập nhật doctor
        doctor.setDoctorName(request.getDoctorName());
        doctor.setDoctorPhoneNumber(request.getDoctorPhoneNumber());
        doctor.setDoctorGender(request.isDoctorGender());
        doctor.setDoctorAddress(request.getDoctorAddress());
        doctor.setDoctorDetail(detail);
        doctor.setDoctorDescription(request.getDoctorDescription());
        doctor.setDoctorImage(doctor.getDoctorImage());

        doctorRespository.save(doctor);
    }


    // public
    public List<DoctorListResponse> getAllDoctors() {
        List<Doctor> doctors = doctorRespository.findAll();
        return doctors.stream()
                .map(this::mapToDoctorListResponse)
                .collect(Collectors.toList());
    }

    // public
    public List<DoctorListResponse> getDoctorByName(String doctorName) {
        List<Doctor> resultList = doctorRespository.findByDoctorNameContaining(doctorName);
        if (resultList.isEmpty()) {throw new AppException(ErrorCode.DOCTORS_NOT_FOUND);}
        return resultList.stream()
                .map(this::mapToDoctorListResponse)
                .collect(Collectors.toList());
    }
    // public
    public List<DoctorListResponse> getDoctorByDepartment(Integer departmentId) {
        Department department = departmentService.getDepartmentById(departmentId);
        List<Doctor> resultList = doctorRespository.findBydepartment(department);
        if (resultList.isEmpty()) {throw new AppException(ErrorCode.DOCTORS_NOT_FOUND);}
        return resultList.stream()
                .map(this::mapToDoctorListResponse)
                .collect(Collectors.toList());
    }


    public Doctor getDoctorByUsername(String Username) {

        User user = userService.getUserByUsername(Username);
        return doctorRespository.findByDoctorUserId(user)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXITED));

    }
    private DoctorDetailResponse mapToDoctorDetailResponse(Doctor doctor) {
        return new DoctorDetailResponse(
                doctor.getDoctorId(),
                doctor.getDoctorImage(),
                doctor.getDoctorName(),
                doctor.getDoctorAddress(),
                doctor.getDoctorPhoneNumber(),
                doctor.isDoctorGender(),
                doctor.getDoctorDateOfBirth(),
                doctor.getDoctorDescription(),

                doctor.getDoctorUserId().getEmail(),

                doctor.getDepartment().getDepartmentName(),

                doctor.getDoctorDetail().getDoctordetailInformation(),
                doctor.getDoctorDetail().getDoctordetailExperience(),
                doctor.getDoctorDetail().getDoctordetailAwardRecognization());
    }

    private DoctorListResponse mapToDoctorListResponse(Doctor doctor) {
        return new DoctorListResponse(

                doctor.getDoctorId(),
                doctor.getDoctorImage(),
                doctor.getDoctorName(),
                doctor.getDoctorAddress(),
                doctor.getDoctorPhoneNumber(),
                doctor.isDoctorGender(),
                doctor.getDoctorDateOfBirth(),
                doctor.getDoctorDescription()
        );
    }
}
