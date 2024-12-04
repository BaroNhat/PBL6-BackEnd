package PBL6.example.UNIME.repository;

import PBL6.example.UNIME.entity.InvalidatedToken;
import PBL6.example.UNIME.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {

}
