package org.example.microservscooter.csvFile;

import org.example.microservscooter.entity.Scooter;
import org.example.microservscooter.repository.ScooterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class CSVReader {
    @Autowired
    private ScooterRepository scooterRepository;

    private static final String PATH = "microserv-scooter/src/main/resources/";
    private static final String CSVSPLIT = ",";

    public void loadData(){
        readFileMaintenance();
    }

    //lee archivos y los agrega a la base
    private void readFileMaintenance() {
        String csvFile = PATH+"scooter.csv";
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("/") && !line.trim().isEmpty()) {
                    String[] datos = line.split(CSVSPLIT);
                    scooterRepository.save(Scooter.builder().latitude(Double.parseDouble(datos[0]))
                                                            .longitude(Double.parseDouble(datos[1]))
                                                            .kilometers(Long.parseLong(datos[2]))
                                                            .usageTime(Integer.parseInt(datos[3]))
                                                            .start(Boolean.parseBoolean(datos[4]))
                                                            .available(Boolean.parseBoolean(datos[5]))
                                                            .maintenance(Boolean.parseBoolean(datos[6]))
                                                    .build());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


