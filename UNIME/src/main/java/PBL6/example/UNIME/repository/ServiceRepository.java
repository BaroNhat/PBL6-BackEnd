package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.entity.Service;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {
    boolean existsByserviceName(String serviceName);

    @Query("SELECT s " +
            "FROM Service s JOIN FETCH s.department " +
            "WHERE s.serviceName like %:serviceName%")
    List<Service> findByServiceNameContaining(@Param("serviceName")String serviceName);

    @Query("SELECT s " +
            "FROM Service s JOIN FETCH s.department ")
    List<Service> findAll();

    @Query("SELECT s " +
            "FROM Service s JOIN FETCH s.department " +
            "WHERE s.serviceId =:serviceId")
    Optional<Service> findById(@NotNull @Param("serviceId") Integer serviceId);
}
