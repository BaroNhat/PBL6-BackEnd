package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.DoctorRequest;
import PBL6.example.UNIME.dto.response.DoctorResponse;
import PBL6.example.UNIME.entity.*;
import PBL6.example.UNIME.enums.Role;
import PBL6.example.UNIME.enums.Status;
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
public class DoctorServiceImpl  implements DoctorService {
    private static final Logger log = LoggerFactory.getLogger(DoctorServiceImpl.class);

    UserService userService;
    DepartmentService departmentService;
    DoctorRepository doctorRespository;
    DoctorDetailRepository doctorDetailRespository;

    public void createDoctor(@Valid DoctorRequest request) {
        // Tìm department có tồn tại
        Department department = departmentService.getDepartmentById(request.getDepartmentId());

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
        doctor.setDepartment(department);
        doctor.setDoctorDetail(detail);

        doctorRespository.save(doctor);
    }
    public Department changeDoctorDepartment(Integer doctor_id, Integer departmentId) {
        Doctor doctor = doctorRespository.findById(doctor_id)
                .orElseThrow(() -> new AppException(ErrorCode.DOCTORS_NOT_FOUND));
        Department department = departmentService.getDepartmentById(departmentId);
        doctor.setDepartment(department);
        doctorRespository.save(doctor);
        return department;
    }
    public User changeDoctorStatus(Integer doctor_id) {
        Doctor doctor = doctorRespository.findById(doctor_id)
                .orElseThrow(() -> new AppException(ErrorCode.DOCTORS_NOT_FOUND));
        return  userService.changeUserStatus(doctor.getDoctorUserId());
    }

    public DoctorResponse getDoctorById(Integer doctorId) {
        return mapToDoctorResponse(doctorRespository.findDoctorById(doctorId)
                .orElseThrow(() -> new AppException(ErrorCode.DOCTORS_NOT_FOUND)));
    }

    // token
    public DoctorResponse getMyInfo(String Username) {
        return mapToDoctorResponse(doctorRespository.findDetailByUsername(Username)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXITED)));
    }
    //token
    public void updateMyInfo(String doctor_username, DoctorRequest request) {
        Doctor doctor = doctorRespository.findDetailByUsername(doctor_username)
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


    // ADMIN
    public List<DoctorResponse> getAllDoctors() {
        List<Doctor> doctors = doctorRespository.findAll();
        return doctors.stream()
                .map(this::mapToDoctorResponse)
                .collect(Collectors.toList());
    }

    // public
    public List<DoctorResponse> getAllDoctorActive() {
        List<Doctor> doctors = doctorRespository.findAll();
        return doctors.stream()
                .filter(d->d.getDoctorUserId().getStatus().equals(Status.ACTIVE.name()))
                .map(this::mapToDoctorResponse)
                .collect(Collectors.toList());
    }

    // public
    public List<DoctorResponse> getDoctorByName(String doctorName) {
        List<Doctor> resultList = doctorRespository.findByDoctorNameContaining(doctorName);
        if (resultList.isEmpty()) {throw new AppException(ErrorCode.DOCTORS_NOT_FOUND);}
        return resultList.stream()
                .filter(d->d.getDoctorUserId().getStatus().equals(Status.ACTIVE.name()))
                .map(this::mapToDoctorResponse)
                .collect(Collectors.toList());
    }
    // public
    public List<DoctorResponse> getDoctorByDepartment(Integer departmentId) {
        List<Doctor> resultList = doctorRespository.findBydepartment(departmentId);
        if (resultList.isEmpty()) {throw new AppException(ErrorCode.DOCTORS_NOT_FOUND);}
        return resultList.stream()
                .filter(d->d.getDoctorUserId().getStatus().equals(Status.ACTIVE.name()))
                .map(this::mapToDoctorResponse)
                .collect(Collectors.toList());
    }


    public Doctor getDoctorByUsername(String Username) {
        return doctorRespository.findByUsername(Username)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXITED));

    }
    private DoctorResponse mapToDoctorResponse(Doctor doctor) {
        return new DoctorResponse(
                doctor.getDoctorId(),
                doctor.getDoctorImage(),
                doctor.getDoctorName(),
                doctor.getDoctorAddress(),
                doctor.getDoctorPhoneNumber(),
                doctor.isDoctorGender(),
                doctor.getDoctorDateOfBirth(),
                doctor.getDoctorDescription(),

                doctor.getDoctorUserId().getEmail(),
                doctor.getDoctorUserId().getStatus(),

                doctor.getDepartment().getDepartmentName(),

                doctor.getDoctorDetail().getDoctordetailInformation(),
                doctor.getDoctorDetail().getDoctordetailExperience(),
                doctor.getDoctorDetail().getDoctordetailAwardRecognization());
    }

}
