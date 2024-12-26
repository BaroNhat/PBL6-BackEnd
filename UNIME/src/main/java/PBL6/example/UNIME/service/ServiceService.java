package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.ServiceRequest;
import PBL6.example.UNIME.dto.response.ServiceResponse;

import java.util.List;

public interface ServiceService {
    public ServiceResponse createService(ServiceRequest request);
    public ServiceResponse getServiceById(Integer serviceId);
    public ServiceResponse updateService(Integer serviceId, ServiceRequest request);
    public void deleteService(Integer serviceId);
    public List<ServiceResponse> getAllServices();
    public List<ServiceResponse> findServiceByName(String serviceName);

}
