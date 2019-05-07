package com.service.antenna.web;

import com.service.antenna.domain.Address;
import com.service.antenna.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService service;
    @GetMapping
    public ResponseEntity<?> findByword(@RequestParam String word, @PageableDefault Pageable pageable) {
        List<Address> addresses = service.findByWord(word, pageable);
        return ResponseEntity.ok(addresses);
    }
}
