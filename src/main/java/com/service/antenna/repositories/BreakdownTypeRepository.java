package com.service.antenna.repositories;

import com.service.antenna.domain.BreakdownType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BreakdownTypeRepository extends JpaRepository<BreakdownType, Long> {
    Optional<BreakdownType> findByType(String type);

    List<BreakdownType> findAllByIdIn(Set<Long> id);
}
