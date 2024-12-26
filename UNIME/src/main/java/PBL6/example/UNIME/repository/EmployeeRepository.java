package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.entity.Employee;
import PBL6.example.UNIME.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e " +
            "FROM Employee e JOIN FETCH e.employeeUserId JOIN FETCH e.department " +
            "WHERE e.employeeUserId.username = :username")
    Optional<Employee> findByemployeeUsername(@Param("username") String username);

    @Query("SELECT e " +
            "FROM Employee e  "+
            "WHERE e.department.departmentId = :departmentId")
    List<Employee> findBydepartmentId(@Param("departmentId") Integer departmentId);

    @Query("SELECT e " +
            "FROM Employee e JOIN FETCH e.employeeUserId JOIN FETCH e.department de ")
    List<Employee> findAll();

}
