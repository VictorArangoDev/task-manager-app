package api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import api.model.UserProject;

public interface UserProjectRepository extends JpaRepository<UserProject, Long> {
}
