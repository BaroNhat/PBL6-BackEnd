package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.DoctorServiceRequest;
import PBL6.example.UNIME.dto.response.DoctorResponse;
import PBL6.example.UNIME.dto.response.ServiceResponse;

import java.util.List;

public interface DoctorServiceService {

    public void addDoctorForService(String username, DoctorServiceRequest request);
    public void delDoctorForSerVice(String username, DoctorServiceRequest request);
    public List<ServiceResponse> getServicesByDoctorId(Integer doctorId);
    public List<DoctorResponse> getDoctorByService(Integer serviceId);


}
