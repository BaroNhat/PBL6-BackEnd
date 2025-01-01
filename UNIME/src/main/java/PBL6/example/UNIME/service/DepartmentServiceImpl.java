package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.DepartmentRequest;
import PBL6.example.UNIME.dto.response.DepartmentResponse;
import PBL6.example.UNIME.entity.Department;
import PBL6.example.UNIME.entity.Doctor;
import PBL6.example.UNIME.entity.Employee;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.DepartmentRepository;
import PBL6.example.UNIME.repository.DoctorRepository;
import PBL6.example.UNIME.repository.EmployeeRepository;
import PBL6.example.UNIME.repository.ServiceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;

    public DepartmentResponse createDepartment(DepartmentRequest request) {
        if (departmentRepository.findBydepartmentName(request.getDepartmentName()) != null ){
            throw new AppException(ErrorCode.DEPARTMENT_EXISTED);
        }
        Department department = new Department();
        department.setDepartmentName(request.getDepartmentName());
        department.setDepartmentDescription(request.getDepartmentDescription());

        return mapToResponse(departmentRepository.save(department));
    }

    public DepartmentResponse updateDepartment(Integer departmentId, DepartmentRequest request) {
        // Tìm phòng ban cần cập nhật theo ID
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        if (departmentRepository.findBydepartmentName(request.getDepartmentName()) != null){
            if(!department.getDepartmentName().equals(request.getDepartmentName())){
                throw new AppException(ErrorCode.DEPARTMENT_EXISTED);
            }
        }
        department.setDepartmentName(request.getDepartmentName());
        department.setDepartmentDescription(request.getDepartmentDescription());

        return mapToResponse(departmentRepository.save(department));
    }


    public void deleteDeparment(Integer departmentId) {
        Department department = getDepartmentById(departmentId);

        List<Doctor> doctorList = doctorRepository.findBydepartment(departmentId);
        List<Employee> employeesList = employeeRepository.findBydepartmentId(departmentId);
        List<PBL6.example.UNIME.entity.Service> serviceList = serviceRepository.findBydepartmentId(departmentId);

        if(!doctorList.isEmpty() || !employeesList.isEmpty() || !serviceList.isEmpty()){
            throw new AppException(ErrorCode.DEPARTMENT_CAN_NOT_DELETE);
        }
        departmentRepository.delete(department);
    }

    public Department getDepartmentById(Integer departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
    }

    // Phương thức lấy tất cả phòng ban
    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<DepartmentResponse> findDepartmentByName(String departmentName) {
        List<Department> departments = departmentRepository.findByDepartmentNameContaining(departmentName);
        if(departments == null) { throw new AppException(ErrorCode.DEPARTMENT_NOT_FOUND); }
        return departments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    private DepartmentResponse mapToResponse(Department department) {
        return new DepartmentResponse(
                department.getDepartmentId(),
                department.getDepartmentName(),
                department.getDepartmentDescription()
        );
    }
}
