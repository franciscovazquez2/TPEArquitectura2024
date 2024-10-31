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
                    LocalDate fechaFin = datos[3].isEmpty() ? null : LocalDate.parse(datos[3], DATE_FORMATTER);
                    String tipo = datos[4];

                    Fee fee = new Fee(id, monto, fechaInicio, fechaFin, tipo);
                    feeRepository.save(fee);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
