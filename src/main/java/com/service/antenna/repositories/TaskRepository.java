package com.service.antenna.repositories;

import com.service.antenna.domain.BreakdownType;
import com.service.antenna.domain.Status;
import com.service.antenna.domain.Task;
import com.service.antenna.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    Set<Task> findAllByUsersAndIsClosed(User user, Boolean closed);
    Set<Task> findAll();
    Optional<Task> findByUsersAndId(User user , Long id);
//    Set<Task> findAllByUsersAndBreakdownTypeAndStatus(User user, List<BreakdownType> breakdown, Status status);
    Set<Task> findAllByUsersAndBreakdownTypeAndStatusAndCreateAtAfter(User user, List<BreakdownType> breakdown, Status status, Date date);
    Set<Task> findAllByUsersAndBreakdownTypeAndCreateAtAfter(User user, List<BreakdownType> breakdown,Date date);
    Set<Task> findAllByUsersAndStatusAndCreateAtAfter(User user, Status status, Date date);
    Set<Task> findAllByUsersAndCreateAtAfter(User user, Date date);
}
