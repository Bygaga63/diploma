package com.service.antenna.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.antenna.domain.Task;
import com.service.antenna.payload.ReportRequest;
import com.service.antenna.services.TaskService;
import com.service.antenna.services.WordService;
import lombok.RequiredArgsConstructor;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.wml.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Set;

//import com.service.antenna.services.WordService;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final TaskService taskService;
    private final WordService wordService;

    @PostMapping
    public ResponseEntity<?> getReport(@RequestBody ReportRequest rep) {
        Set<Task> result = taskService.findAll(rep.getUsers(), rep.getBreakdownType(), rep.getStatus(), rep.getPeriod());
        return ResponseEntity.ok(result);

    }


    @GetMapping("/{base64ReportInfo}")
    public void getWordFile(@PathVariable  String base64ReportInfo, HttpServletResponse response) throws Docx4JException, IOException {
       String reportInfo = new String(Base64.getDecoder().decode(base64ReportInfo.getBytes()));
        ObjectMapper objectMapper = new ObjectMapper();
        ReportRequest rep = objectMapper.readValue(reportInfo, ReportRequest.class);
        Set<Task> result = taskService.findAll(rep.getUsers(), rep.getBreakdownType(), rep.getStatus(), rep.getPeriod());
        wordService.createFile(new ArrayList<>(result), response);
    }
}
