package PBL6.example.UNIME.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "service")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    Integer serviceId;

    @Column(name = "service_image", nullable = false)
    String serviceImage ;

    @Column(name = "service_name", nullable = false)
    String serviceName ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "department_id", nullable = false)
    Department department;

    @Column(name = "service_description", nullable = false)
    String serviceDescription ;

    @Column(name = "service_price", nullable = false)
    Integer servicePrice ;
}
