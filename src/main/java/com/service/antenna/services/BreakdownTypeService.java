package com.service.antenna.services;

import com.service.antenna.domain.Area;
import com.service.antenna.domain.BreakdownType;
import com.service.antenna.exceptions.CustomException;
import com.service.antenna.repositories.BreakdownTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BreakdownTypeService {
    private final BreakdownTypeRepository repository;
    public List<BreakdownType> findAll() {
        return repository.findAll();
    }

    public List<BreakdownType> findAllById(Set<Long> ids) {
        return repository.findAllByIdIn(ids);
    }

    public ResponseEntity<?> create( BreakdownType type) {
        Optional<BreakdownType> breakdownType = repository.findByType(type.getType());
        if (breakdownType.isPresent()) {
            throw new CustomException("Данный тип поломки уже существует");
        }

        return new ResponseEntity<>(save(type), HttpStatus.CREATED);
    }

    public BreakdownType save(BreakdownType type) {
        return repository.save(type);
    }

    public boolean remove(Long breakId) {
        repository.deleteById(breakId);
        boolean isExists = repository.existsById(breakId);
        return !isExists;
    }

    public BreakdownType findOne(Long id){
        return repository.findById(id).orElseThrow(() -> new CustomException("Тип поломки не найден"));
    }
}
