package org.example.microservbilling.csvFile;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CSVInitialized {

    @Autowired
    private CSVReaderBilling csvReaderBilling;

    @Autowired
    private CSVReaderFee csvReaderFee;

    @PostConstruct
    public void init() throws IOException {
        csvReaderBilling.loadData();
        csvReaderFee.loadFeeData();
    }
}
