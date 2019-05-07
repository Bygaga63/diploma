package com.service.antenna.web;

import com.service.antenna.domain.Task;
import com.service.antenna.payload.ReportRequest;
import com.service.antenna.services.TaskService;
import com.service.antenna.services.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final TaskService taskService;
    private final WordService wordService;
    @PostMapping
    public ResponseEntity<?> getReport(@RequestBody ReportRequest rep){
        Set<Task> result = taskService.findAll(rep.getUser(), rep.getBreakdownType(), rep.getStatus(), rep.getPeriod());
        return ResponseEntity.ok(result);

    }


    @GetMapping
    public void getWordFile(HttpServletResponse response){
//        File file = new File();
//        ByteArrayResource resource = new ByteArrayResource(null);
        wordService.createFile(response);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-disposition", "attachment; fileName=\"" + "test.doc" + "\"" );
//        return ResponseEntity.ok()
//                .header(, "attachment; filename=1.docs")
////                .contentLength()
//                .contentType(MediaType.parseMediaType("application/octet-stream")).build();
//                .body(resource);

//        return ResponseEntity.ok().build();
    }
}
