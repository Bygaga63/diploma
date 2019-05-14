package com.service.antenna.web;

import com.service.antenna.domain.Task;
import com.service.antenna.payload.ReportRequest;
import com.service.antenna.services.TaskService;
import com.service.antenna.services.WordService;
import lombok.RequiredArgsConstructor;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final TaskService taskService;
    private final WordService wordService;
    @PostMapping
    public ResponseEntity<?> getReport(@RequestBody ReportRequest rep){
        Set<Task> result = taskService.findAll(rep.getUsers(), rep.getBreakdownType(), rep.getStatus(), rep.getPeriod());
        return ResponseEntity.ok(result);

    }


    @PostMapping("/document")
    public HttpEntity<byte[]> getWordFile(@RequestBody ReportRequest rep) throws Docx4JException, IOException {
        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.createPackage();
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
        mainDocumentPart.addStyledParagraphOfText("Title", "Hello World!");
        mainDocumentPart.addParagraphOfText("Welcome To Baeldung");


        File file = new File("welcome.docx");
        wordPackage.save(file);


        byte[] document = FileCopyUtils.copyToByteArray(file);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        header.set("Content-Disposition", "inline; filename=" + file.getName());
        header.setContentLength(document.length);

        return new HttpEntity<byte[]>(document, header);
    }
}
