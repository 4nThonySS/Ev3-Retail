package cl.duocuc.api_gateway;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    @Operation(
            summary = "Verifica el estado del API Gateway",
            description = "Endpoint de prueba que confirma que el API Gateway esta operativo y respondiendo correctamente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "El API Gateway esta operativo"
    )
    public String test() {
        return "API Gateway operativo";
    }

}