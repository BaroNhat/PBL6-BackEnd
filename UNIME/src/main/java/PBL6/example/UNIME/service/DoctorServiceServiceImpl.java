package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.DoctorServiceRequest;
import PBL6.example.UNIME.dto.response.DoctorResponse;
import PBL6.example.UNIME.dto.response.ServiceResponse;
import PBL6.example.UNIME.entity.Appointment;
import PBL6.example.UNIME.entity.Doctor;
import PBL6.example.UNIME.entity.DoctorService;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.AppointmentRepository;
import PBL6.example.UNIME.repository.DoctorRepository;
import PBL6.example.UNIME.repository.DoctorServiceRepository;
import PBL6.example.UNIME.repository.ServiceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class DoctorServiceServiceImpl implements DoctorServiceService {
    DoctorServiceRepository doctorServiceRepository;
    DoctorRepository doctorRepository;
    ServiceRepository serviceRepository;
    EmployeeService employeeService;
    AppointmentRepository appointmentRepository;

    public void addDoctorForService(String username, DoctorServiceRequest request) {

        // Lấy service và doctor từ request
        PBL6.example.UNIME.entity.Service service = serviceRepository.findById(request.getServiceID())
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));
        Doctor doctor = doctorRepository.findById(request.getDoctorID())
                .orElseThrow(() -> new AppException(ErrorCode.DOCTORS_NOT_FOUND));

        checkEmployeeDepartmentAccess(username, service);
        // 2. Kiểm tra xem doctor và service đã tồn tại trong DoctorService chưa
        DoctorService existingDoctorService = doctorServiceRepository.findByDoctorAndService(request.getDoctorID(), request.getServiceID());
        if (existingDoctorService != null) {
            throw new AppException(ErrorCode.DOCTORSERVICE_EXITED);
        }

        DoctorService doctorService = new DoctorService();
        doctorService.setDoctor(doctor);
        doctorService.setService(service);
        doctorServiceRepository.save(doctorService);
    }

    public void delDoctorForSerVice(String username, Integer doctorServiceId) {
        List<Appointment> list = appointmentRepository.findByDoctorService(doctorServiceId);
        if (list.isEmpty()) {
            DoctorService doctorService = doctorServiceRepository.findById(doctorServiceId)
                    .orElseThrow(() -> new AppException(ErrorCode.DOCTORSERVICE_NOT_FOUND));
            doctorServiceRepository.delete(doctorService);
        }else throw  new AppException(ErrorCode.DOCTORSERVICE_CAN_NOT_DELETE);
    }


    // ===========
    public List<ServiceResponse> getServicesByDoctorId(Integer doctorId) {
        List<PBL6.example.UNIME.entity.DoctorService> doctorServices = doctorServiceRepository.findBydoctor(doctorId);

        // Lấy danh sách Service từ danh sách DoctorService
        return doctorServices.stream()
                .map(DoctorService::getService)
                .map(this::mapToServiceResponse)
                .collect(Collectors.toList());
    }

    public List<DoctorResponse> getDoctorByService(Integer serviceId) {

        List<PBL6.example.UNIME.entity.DoctorService> doctorServices = doctorServiceRepository.findByservice(serviceId);

        // Lấy danh sách Service từ danh sách DoctorService
        return doctorServices.stream()
                .map(DoctorService::getDoctor)
                .map(this::mapToDoctorListResponse)
                .collect(Collectors.toList());
    }


    private void checkEmployeeDepartmentAccess(String username, PBL6.example.UNIME.entity.Service service) {

        Integer employeeDepartmentId = employeeService.getEmployeeByUsername(username).getDepartment().getDepartmentId();
        if (!employeeDepartmentId.equals(service.getDepartment().getDepartmentId())) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }
    }

    private DoctorResponse mapToDoctorListResponse(Doctor doctor) {
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
    private ServiceResponse mapToServiceResponse(PBL6.example.UNIME.entity.Service service) {
        return new ServiceResponse(
                service.getServiceId(),
                service.getServiceName(),
                service.getServiceImage(),
                service.getServiceDescription(),
                service.getServicePrice(),
                service.getDepartment().getDepartmentId(),
                service.getDepartment().getDepartmentName()
        );
    }

}
