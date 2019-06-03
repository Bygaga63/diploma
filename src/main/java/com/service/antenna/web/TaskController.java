package com.service.antenna.web;

import com.service.antenna.domain.Task;
import com.service.antenna.domain.User;
import com.service.antenna.services.MapValidationErrorService;
import com.service.antenna.services.TaskService;
import com.service.antenna.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService service;
    private final MapValidationErrorService mapValidationErrorService;

    @GetMapping
    public ResponseEntity<?> findAll(@AuthenticationPrincipal User user, @RequestParam boolean closed){
        Set<Task> result = service.findAll(user, closed);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id, @AuthenticationPrincipal User user){
        Task task = service.findOneRest(user, id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Task task, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        Task savedTask = service.create(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Task task, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        service.save(task);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("{taskId}")
    public ResponseEntity<?> delete(@PathVariable Long taskId){
        boolean isRemove = service.remove(taskId);
        HttpStatus status = isRemove ? HttpStatus.OK : HttpStatus.FORBIDDEN;

        return new ResponseEntity<>(status);
    }

}
