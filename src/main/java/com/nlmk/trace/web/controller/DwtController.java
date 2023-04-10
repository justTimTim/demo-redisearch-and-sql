package com.nlmk.trace.web.controller;

import com.nlmk.trace.service.DowntimeFethService;
import com.nlmk.trace.service.DwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DwtController {

    private final DwtService dwtService;
    private final DowntimeFethService downtimeFethService;

    @GetMapping("/create-level")
    public ResponseEntity<String> createLevel(){
        dwtService.createLevel();
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/create-place")
    public ResponseEntity<String> createPlace(){
        dwtService.createPlace();
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/create-cause")
    public ResponseEntity<String> createCause(){
        dwtService.createCause();
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/create-fixer")
    public ResponseEntity<String> createFixer(){
        dwtService.createFixer();
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/create-dwt")
    public ResponseEntity<String> createDwt(){
        dwtService.createDwt();
        return ResponseEntity.ok("OK");
    }

}
