package api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import api.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByStateTrue();
}