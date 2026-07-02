package cl.duocuc.api_gateway;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GatewayInfoControllerTest {
    private final GatewayInfoController controller = new GatewayInfoController();

    @Test
    void retailInfo_debeRetornarInformacionBasicaDelSistema() {
        GatewayInfoResponse response = controller.retailInfo();

        assertNotNull(response);
        assertEquals("Sistema Retail", response.getSistema());
        assertEquals("api-gateway", response.getGateway());
        assertEquals("OK", response.getEstado());
        assertEquals("1.0", response.getVersion());
    }

    @Test
    void retailInfo_debeRetornarRutasDisponibles() {
        GatewayInfoResponse response = controller.retailInfo();

        assertNotNull(response.getRutasDisponibles());
        assertTrue(response.getRutasDisponibles().contains("/api/productos/**"));
        assertTrue(response.getRutasDisponibles().contains("/api/ventas/**"));
    }
}
