package com.avocado.xbot.x.controller;


import com.avocado.xbot.integration.x.XClient;
import com.avocado.xbot.integration.x.dto.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/x")
public class XController {

    private final XClient xClient;

    @PostMapping()
    public ResponseEntity<Object> status(
        @RequestBody Status status
    ){
        return xClient.status(status);
    }


}
