package PBL6.example.UNIME.service;

import PBL6.example.UNIME.entity.DoctorService;
import PBL6.example.UNIME.repository.DoctorServiceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class DoctorSService {
    private final DoctorServiceRepository doctorServiceRepository;

//    public List<PBL6.example.UNIME.entity.Service> getServicesByDoctorId(Integer doctorId) {
//        List<PBL6.example.UNIME.entity.DoctorService> doctorServices = doctorServiceRepository.findBydoctor(doctorId);
//
//        // Lấy danh sách Service từ danh sách DoctorService
//        return doctorServices.stream()
//                .map(DoctorService::getService)
//                .collect(Collectors.toList());
//    }
//
//    public List<PBL6.example.UNIME.entity.Service> createDoctorServices(DoctorServiceRequest request) {
//
//
//
//    }
}
