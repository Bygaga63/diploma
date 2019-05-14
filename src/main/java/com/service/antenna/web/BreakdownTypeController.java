package com.service.antenna.web;

import com.service.antenna.domain.BreakdownType;
import com.service.antenna.services.BreakdownTypeService;
import com.service.antenna.services.MapValidationErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/breakdowns")
public class BreakdownTypeController {
    private final BreakdownTypeService service;
    private final MapValidationErrorService mapValidationErrorService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BreakdownType type, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        return new ResponseEntity<>(service.create(type), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody BreakdownType breakdownType) {
        return new ResponseEntity<>(service.save(breakdownType), HttpStatus.OK);
    }

    @DeleteMapping("{breakId}")
    public ResponseEntity<?> remove(@PathVariable Long breakId) {
        boolean isRemove = service.remove(breakId);
        HttpStatus status = isRemove ? HttpStatus.OK : HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(status);
    }
}
