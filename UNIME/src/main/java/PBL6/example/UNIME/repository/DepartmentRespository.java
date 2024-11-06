package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRespository extends JpaRepository<Department, Integer> {
    boolean existsBydepartmentName(String departmentName);
}
