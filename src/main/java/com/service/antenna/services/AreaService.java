package com.service.antenna.services;

import com.service.antenna.domain.Area;
import com.service.antenna.exceptions.CustomException;
import com.service.antenna.repositories.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AreaService {
    private final AreaRepository repository;

    public List<Area> findAll(){
        return repository.findAll();
    }

    public Area create(Area area){
        Optional<Area> areaFromDb = repository.findByCaption(area.getCaption());
        if (areaFromDb.isPresent()) {
            throw new CustomException("Данный тип поломки уже существует");
        }

        return save(area);
    }

    public Area save(Area caption){
        return repository.save(caption);
    }

    public Area findOne(Long id){
        return repository.findById(id).orElseThrow(() -> new CustomException("Район не найден"));
    }

    public boolean remove(Long areaId) {
        repository.deleteById(areaId);
        boolean isExists = repository.existsById(areaId);
        return !isExists;
    }
}
