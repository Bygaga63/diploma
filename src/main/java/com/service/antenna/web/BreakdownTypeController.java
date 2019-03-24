package com.service.antenna.web;

import com.service.antenna.domain.BreakdownType;
import com.service.antenna.repositories.BreakdownTypeRepository;
import com.service.antenna.services.BreakdownTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/breakdowns")
public class BreakdownTypeController {
    private final BreakdownTypeService service;
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BreakdownType type) {
        return new ResponseEntity<>(service.create(type), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody BreakdownType BreakdownType) {
        return new ResponseEntity<>(service.save(BreakdownType), HttpStatus.OK);
    }

    @DeleteMapping("{breakId}")
    public ResponseEntity<?> remove(@PathVariable Long breakId) {
        boolean isRemove = service.remove(breakId);
        HttpStatus status = isRemove ? HttpStatus.OK : HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(status);
    }
}
