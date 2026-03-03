package api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import api.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}