package api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import api.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    

}
