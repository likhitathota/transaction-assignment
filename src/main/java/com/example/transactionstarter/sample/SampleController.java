package com.example.transactionstarter.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController   // handles REST API'S and receives  the GET request from the user and applications and send back the responses
public class SampleController {

    @GetMapping("/api/sample") // used to create an API end point, someone sends GET request ,when this happens it tells to run the below method
    public Map<String, String> sample() {
        return Map.of("message", "Starter project is running");
    }
}
