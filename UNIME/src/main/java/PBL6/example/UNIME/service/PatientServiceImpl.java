package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.PatientRequest;
import PBL6.example.UNIME.dto.response.PatientResponse;
import PBL6.example.UNIME.entity.Appointment;
import PBL6.example.UNIME.entity.Patient;
import PBL6.example.UNIME.entity.User;
import PBL6.example.UNIME.enums.Role;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.AppointmentRepository;
import PBL6.example.UNIME.repository.PatientRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class PatientServiceImpl implements PatientService {

    @Autowired
    UserService userService;

    PatientRepository patientRepository;

    @Override
    public List<PatientResponse> getAllPatients(){
        List<Patient> patients = patientRepository.findAll();
        return patients.stream()
                .map(this::mapToResponseDetail)
                .collect(Collectors.toList());
    }

    @Override
    public PatientResponse getPatientById(Integer patient_id){
        return mapToResponseDetail(getPatientByPatientId(patient_id));
    }

    @Override
    public PatientResponse updatePatient(String patient_username, PatientRequest request) {
        //1. kiểm tra tồn tại Patient theo Username
        Patient patient = patientRepository.findBypatientUserUsername(patient_username)
                .orElseThrow(() -> new AppException(ErrorCode.PATIENT_NOT_FOUND));

        //2. Kiểm tra  mail cập nhật có trùng với tài khoản khác
        if(userService.ExitByEmail(request.getPatientEmail())){ // kiểm tra tồn tại email trong table User không
            User userByEmail = userService.getUserByEmail(request.getPatientEmail());
            if(!userByEmail.getUserId().equals(patient.getPatientUser().getUserId())) {
                throw new AppException(ErrorCode.EMAIL_ALREADY_REGISTERED);
            }
        }
        // 3. cập nhật vào bảng User
        User user = new User();
        user.setEmail(request.getPatientEmail());
        userService.updateUser(patient.getPatientUser().getUserId(), user );


        // 4. cập nhật vào bẳng Patient
        if(request.getPatientImage() == null){
            patient.setPatientImage("https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731150727/e3e8df1e56e1c8839457b42bdcd750e5_smkmhm.jpg");
        } else   patient.setPatientImage(request.getPatientImage());
        patient.setPatientName(request.getPatientName());
        patient.setPatientGender(request.getPatientGender());
        patient.setPatientAddress(request.getPatientAddress());
        patient.setPatientPhoneNumber(request.getPatientPhoneNumber());
        patient.setPatientDateOfBirth(request.getPatientDateOfBirth());

        return mapToResponseDetail(patientRepository.save(patient));
    }

    @Override
    public User changePatientStatus(Integer patient_id) {
        Patient patient = getPatientByPatientId(patient_id);
        return userService.changeUserStatus(patient.getPatientUser());
    }

    @Override
    public PatientResponse getMyInfo(String username) {

        Patient patient = patientRepository.findBypatientUserUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.PATIENT_NOT_FOUND));

        return mapToResponseDetail(patient);
    }

    @Override
    public PatientResponse createPatient(PatientRequest request) {

        //1. kiểm tra, khởi tạo user
        User user = new User();
        user.setUsername(request.getPatientUsername());
        user.setPassword(request.getPatientPassword());
        user.setEmail(request.getPatientEmail());
        user.setRole(Role.PATIENT.name());

        //2. Tạo patient
        Patient patient = new Patient();
        if(request.getPatientImage() == null){
            patient.setPatientImage("https://res.cloudinary.com/dy8p5yjsd/image/upload/v1731150727/e3e8df1e56e1c8839457b42bdcd750e5_smkmhm.jpg");
        } else   patient.setPatientImage(request.getPatientImage());
        patient.setPatientUser(userService.createUser(user));
        patient.setPatientName(request.getPatientName());
        patient.setPatientAddress(request.getPatientAddress());
        patient.setPatientGender(request.getPatientGender());
        patient.setPatientPhoneNumber(request.getPatientPhoneNumber());
        patient.setPatientDateOfBirth(request.getPatientDateOfBirth());

        return mapToResponseDetail(patientRepository.save(patient));
    }


    public Patient getPatientByPatientId(Integer patient_id) {
        return patientRepository.findById(patient_id)
                .orElseThrow(() -> new AppException(ErrorCode.PATIENT_NOT_FOUND));
    }

    private PatientResponse mapToResponseDetail(Patient patient) {
        User user  = patient.getPatientUser();
        return new PatientResponse(
                patient.getPatientId(),
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getStatus(),
                patient.getPatientImage(),
                patient.getPatientName(),
                patient.getPatientAddress(),
                patient.getPatientPhoneNumber(),
                patient.isPatientGender(),
                patient.getPatientDateOfBirth()
        );
    }

}
