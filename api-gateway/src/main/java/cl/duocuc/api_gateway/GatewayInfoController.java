package cl.duocuc.api_gateway;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Gateway Info", description = "Endpoint de diagnóstico del Gateway")
public class GatewayInfoController {
    @GetMapping("/gateway/retail-info")
    @Operation(summary = "Información de diagnóstico del Gateway",
            description = "Retorna el nombre del sistema, el estado del Gateway, la versión y las rutas disponibles.")
    public GatewayInfoResponse retailInfo() {
        return new GatewayInfoResponse(
                "Sistema Retail",
                "api-gateway",
                "OK",
                "1.0",
                List.of("/api/productos/**", "/api/ventas/**")
        );
    }
}
