package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.ServiceRequest;
import PBL6.example.UNIME.dto.response.ServiceResponse;

import java.util.List;

public interface ServiceService {
    ServiceResponse createService(ServiceRequest request);
    ServiceResponse getServiceById(Integer serviceId);
    ServiceResponse updateService(Integer serviceId, ServiceRequest request);
    void deleteService(Integer serviceId);
    List<ServiceResponse> getAllServices();
    List<ServiceResponse> findServiceByName(String serviceName);
    List<ServiceResponse> getServicesByDepartment(String username);
}
