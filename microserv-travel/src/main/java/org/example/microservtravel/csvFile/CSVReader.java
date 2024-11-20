package org.example.microservtravel.csvFile;

import org.example.microservtravel.entity.Travel;
import org.example.microservtravel.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

@Component
public class CSVReader {
    @Autowired
    private TravelRepository travelRepository;

    private static final String PATH = "microserv-travel/src/main/resources/";
    private static final String CSVSPLIT = ",";

    public void loadData(){
        readFileTravel();
    }

    //lee archivos y los agrega a la base
    private void readFileTravel() {
        String csvFile = PATH+"travel.csv";
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("/") && !line.trim().isEmpty()) {
                    String[] datos = line.split(CSVSPLIT);
                    travelRepository.save(Travel.builder().id_user(Long.parseLong(datos[0]))
                                                            .id_scooter(Long.parseLong(datos[1]))
                                                            .date(new Date(Integer.parseInt(datos[2]),Integer.parseInt(datos[3]),Integer.parseInt(datos[4])))
                                                            .price(Double.parseDouble(datos[5]))
                                                            .kilometers(Long.parseLong(datos[6]))
                                                            .usageTime(Long.parseLong(datos[7]))
                                                            .pauseTime(Long.parseLong(datos[8]))
                                                            .hasPauses(Boolean.parseBoolean(datos[9])).build());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


