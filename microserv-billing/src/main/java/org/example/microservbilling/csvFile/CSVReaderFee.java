package org.example.microservbilling.csvFile;
import org.example.microservbilling.entity.Fee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.example.microservbilling.repository.FeeRepository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CSVReaderFee {
    @Autowired
    private FeeRepository feeRepository;

    private static final String PATH = "microserv-billing/src/main/resources/";
    private static final String CSVSPLIT = ",";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void loadFeeData() {
        String csvFile = PATH + "fee.csv";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("/") && !line.trim().isEmpty()) {
                    String[] datos = line.split(CSVSPLIT);

                    Long id = Long.parseLong(datos[0]);
                    Double monto = Double.parseDouble(datos[1]);
                    LocalDate fechaInicio = LocalDate.parse(datos[2], DATE_FORMATTER);
                    String tipo = datos[3];

                    Fee fee = new Fee(id, monto, fechaInicio, tipo);
                    feeRepository.save(fee);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
