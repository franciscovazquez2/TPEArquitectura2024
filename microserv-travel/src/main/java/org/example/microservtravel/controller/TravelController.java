package org.example.microservtravel.controller;

import org.example.microservtravel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travel/")
public class TravelController {

    @Autowired
    private TravelService service;

}
