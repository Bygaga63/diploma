package com.service.antenna.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.antenna.domain.Address;
import com.service.antenna.domain.Task;
import com.service.antenna.payload.AddressResponse;
import com.service.antenna.payload.ReportRequest;
import com.service.antenna.services.AddressService;
import com.service.antenna.services.TaskService;
import com.service.antenna.services.WordService;
import lombok.RequiredArgsConstructor;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService service;
    private final WordService wordService;
    @GetMapping("/word")
    public ResponseEntity<?> findByword(@RequestParam String word, @PageableDefault Pageable pageable) {
        List<Address> addresses = service.findByWord(word, pageable);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<AddressResponse> taskAddress = service.getTaskAddress();
        return ResponseEntity.ok(taskAddress);
    }

    @GetMapping("/download")
    public void getWordFile(HttpServletResponse response) throws Docx4JException {
        List<AddressResponse> taskAddress = service.getTaskAddress();
        wordService.createAddressFile(taskAddress, response);
    }

}
