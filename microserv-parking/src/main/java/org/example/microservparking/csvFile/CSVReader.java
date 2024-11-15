package org.example.microservparking.csvFile;

import org.example.microservparking.entity.Parking;
import org.example.microservparking.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class CSVReader {
    @Autowired
    private ParkingRepository parkingRepository;

    private static final String PATH = "microserv-parking/src/main/resources/";
    private static final String CSVSPLIT = ",";

    public void loadData(){
        readFileParking();
    }

    //lee archivos y los agrega a la base
    private void readFileParking() {
        String csvFile = PATH+"parking.csv";
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("/") && !line.trim().isEmpty()) {
                    String[] datos = line.split(CSVSPLIT);
                    parkingRepository.save(Parking.builder()
                            .latitude(Double.parseDouble(datos[0]))
                            .longitude(Double.parseDouble(datos[1]))
                            .capacity(Integer.parseInt(datos[2]))
                            .actualCapacity(Integer.parseInt(datos[3]))
                            .available(Boolean.parseBoolean(datos[4])).build());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


