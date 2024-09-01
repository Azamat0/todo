package kg.azamat.todo.repositories;

import kg.azamat.todo.dto.TaskFilter;
import kg.azamat.todo.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("""
            SELECT t FROM Task t
            WHERE t.description LIKE concat('%', :#{#filter.description}, '%')
            AND (t.taskStatus = :#{#filter.status} OR :#{#filter.status} IS NULL)
            """)
    Page<Task> getTasksByFilter(TaskFilter filter, Pageable pageable);

}
