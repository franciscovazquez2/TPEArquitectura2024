@RestController
@RequestMapping("api/fee")

public class FeeController {
    @Autowired
    private FeeService feeService;

    // Obtener listado de tarifas
    @GetMapping
    public @ResponseBody ResponseEntity<?> getAllFees() {
        try {
            return
                    ResponseEntity.status(HttpStatus.OK).body(feeService.getAllFees());
        } catch (Exception e) {
            String errorJson = "{\"message\": \"Error al listar las tarifas\", \"details\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }

}