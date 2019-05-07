package com.service.antenna.services;

import com.service.antenna.domain.Area;
import com.service.antenna.domain.BreakdownType;
import com.service.antenna.exceptions.CustomException;
import com.service.antenna.repositories.BreakdownTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BreakdownTypeService {
    private final BreakdownTypeRepository repository;

    public List<BreakdownType> findAll() {
        return repository.findAll();
    }

    public List<BreakdownType> findAllById(List<Long> ids) {
        return repository.findAllByIdIn(ids);
    }

    public BreakdownType create(BreakdownType type) {
        Optional<BreakdownType> breakdownType = repository.findByType(type.getType());
        if (breakdownType.isPresent()) {
            throw new CustomException("Данный тип поломки уже существует");
        }

        return save(type);
    }

    public BreakdownType save(BreakdownType type) {
        return repository.save(type);
    }

    public boolean remove(Long breakId) {
        repository.deleteById(breakId);
        boolean isExists = repository.existsById(breakId);
        return !isExists;
    }
}
