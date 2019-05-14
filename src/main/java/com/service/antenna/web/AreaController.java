package com.service.antenna.web;

import com.service.antenna.domain.Area;
import com.service.antenna.services.AreaService;
import com.service.antenna.services.MapValidationErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/areas")
public class AreaController {
    public final AreaService service;
    private final MapValidationErrorService mapValidationErrorService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Area area, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        return new ResponseEntity<>(service.create(area), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Area area) {
        return new ResponseEntity<>(service.save(area), HttpStatus.OK);
    }

    @DeleteMapping({"{areaId}"})
    public ResponseEntity<?> remove(@PathVariable Long areaId) {
        boolean isRemove = service.remove(areaId);
        HttpStatus status = isRemove ? HttpStatus.OK : HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(status);
    }
}
