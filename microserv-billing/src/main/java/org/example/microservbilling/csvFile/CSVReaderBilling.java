package org.example.microservbilling.csvFile;

import org.example.microservbilling.entity.Billing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.example.microservbilling.repository.BillingRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class CSVReaderBilling {

    @Autowired
    private BillingRepository billingRepository;

    private static final String PATH = "microserv-billing/src/main/resources/billings.csv";
    private static final String CSVSPLIT = ",";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void loadData(){
        readFileBilling();
    }

    // Lee archivos y los agrega a la base de datos
    private void readFileBilling() {
        String csvFile = PATH + "billing.csv";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // Ignora las líneas vacías o los comentarios
                if (!line.startsWith("/") && !line.trim().isEmpty()) {
                    String[] datos = line.split(CSVSPLIT);

                    // Parseo de los datos
                    Long id = Long.parseLong(datos[0]);
                    LocalDate fechaEmision = LocalDate.parse(datos[1], DATE_FORMATTER);
                    Long idReserva = Long.parseLong(datos[2]);
                    Long idUsuario = Long.parseLong(datos[3]);
                    Double montoTotal = Double.parseDouble(datos[4]);

                    // Crear instancia de Billing con todos los atributos
                    Billing billing = new Billing(id, fechaEmision, idReserva, idUsuario, montoTotal);

                    // Guardar en la base de datos
                    billingRepository.save(billing);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error de formato en el archivo CSV: " + e.getMessage());
        }
    }
}
