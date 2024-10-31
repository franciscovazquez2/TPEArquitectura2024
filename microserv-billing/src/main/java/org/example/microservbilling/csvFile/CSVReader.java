package org.example.microservmaintenance.csvFile;

import org.example.microservmaintenance.entity.Maintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.example.microservmaintenance.repository.MaintenanceRepository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class CSVReader {
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    //TODO: cambiar a billin repository

    private static final String PATH = "microserv-billing/src/main/resources/";
    private static final String CSVSPLIT = ",";

    public void loadData(){
        readFileBilling();
    }

    //lee archivos y los agrega a la base
    private void readFileBilling() {
        String csvFile = PATH+"billing.csv";
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("/") && !line.trim().isEmpty()) {
                    String[] datos = line.split(CSVSPLIT);
                    //TODO: cambiar a billin entity
                    Maintenance billing = new Maintenance(Long.parseLong(datos[0]), Long.parseLong(datos[1]));
                    maintenanceRepository.save(billing);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


