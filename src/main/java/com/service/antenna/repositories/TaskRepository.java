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
    Optional<Task> findByUsersInAndId(User user , Long id);
//    Set<Task> findAllByUsersAndBreakdownTypeAndStatus(User user, List<BreakdownType> breakdown, Status status);
    Set<Task> findAllByUsersInAndBreakdownTypeAndStatusAndCreateAtBetween(Set<User> user, List<BreakdownType> breakdown, Status status, Date start, Date end);
    Set<Task> findAllByUsersInAndBreakdownTypeAndCreateAtBetween(Set<User> user, List<BreakdownType> breakdown, Date start, Date end);
    Set<Task> findAllByUsersInAndStatusAndCreateAtBetween(Set<User> user, Status status, Date start, Date end);
    Set<Task> findAllByUsersInAndCreateAtBetween(Set<User> user, Date start, Date end);
}
