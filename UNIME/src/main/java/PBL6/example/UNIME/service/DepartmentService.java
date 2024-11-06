package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.DepartmentRequest;
import PBL6.example.UNIME.dto.response.DepartmentResponse;
import PBL6.example.UNIME.entity.Department;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.DepartmentRespository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentService {
    DepartmentRespository departmentRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public DepartmentResponse createDepartment(DepartmentRequest request) {
        if (departmentRepository.existsBydepartmentName(request.getDepartmentName())){
            throw new AppException(ErrorCode.DEPARTMENT_EXITED);
        }
        Department department = new Department();
        department.setDepartmentName(request.getDepartmentName());
        department.setDepartmentDescription(request.getDepartmentDescription());

        return mapToResponse(departmentRepository.save(department));
    }

    // Phương thức lấy tất cả phòng ban
    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Phương thức lấy phòng ban theo ID
    public DepartmentResponse getDepartmentById(Integer departmentId) {
        return mapToResponse(departmentRepository.findById(departmentId)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND)));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public DepartmentResponse updateDepartment(Integer departmentId, DepartmentRequest request) {
        // Tìm phòng ban cần cập nhật theo ID
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        if (departmentRepository.existsBydepartmentName(request.getDepartmentName())){
            if(!department.getDepartmentName().equals(request.getDepartmentName())){
                throw new AppException(ErrorCode.DEPARTMENT_EXITED);
            }
        }
        department.setDepartmentName(request.getDepartmentName());
        department.setDepartmentDescription(request.getDepartmentDescription());

        return mapToResponse(departmentRepository.save(department));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteDepartment(Integer departmentId) {
        // Tìm phòng ban cần xóa theo ID, nếu không có thì ném ra ngoại lệ
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        departmentRepository.deleteById(department.getDepartmentId());
    }

    private DepartmentResponse mapToResponse(Department department) {

        return new DepartmentResponse(
                department.getDepartmentId(),
                department.getDepartmentName(),
                department.getDepartmentDescription()
        );
    }
}
