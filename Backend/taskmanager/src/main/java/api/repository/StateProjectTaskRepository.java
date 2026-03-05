package api.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.model.StateProjectTask;

@Repository
public interface StateProjectTaskRepository extends JpaRepository<StateProjectTask, Long> {
    
}
