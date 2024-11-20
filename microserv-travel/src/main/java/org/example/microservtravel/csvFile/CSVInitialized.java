package org.example.microservtravel.csvFile;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CSVInitialized {
    @Autowired
    private CSVReader csvReader;

    @PostConstruct
    public void init() throws IOException {
        csvReader.loadData();
    }
}
