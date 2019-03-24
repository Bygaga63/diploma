package com.service.antenna.web;

import com.service.antenna.domain.Task;
import com.service.antenna.domain.User;
import com.service.antenna.services.MapValidationErrorService;
import com.service.antenna.services.TaskService;
import com.service.antenna.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService service;
    private final MapValidationErrorService mapValidationErrorService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> findAll(User user){
        Set<Task> result = service.findAll(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Task task, BindingResult result, Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        User user = userService.findOne(principal.getName());
        Task savedTask = service.create(user, task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody Task task, BindingResult result, Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        User user = userService.findOne(principal.getName());
        Task savedTask = service.save(task);
        return new ResponseEntity<>(savedTask, HttpStatus.OK);
    }

    @DeleteMapping("{taskId}")
    public ResponseEntity<?> delete(@PathVariable Long taskId){
        boolean isRemove = service.remove(taskId);
        HttpStatus status = isRemove ? HttpStatus.OK : HttpStatus.FORBIDDEN;

        return new ResponseEntity<>(status);
    }

}
