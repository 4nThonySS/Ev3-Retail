package cl.duocuc.venta.controller;

import cl.duocuc.venta.dto.VentaRequest;
import cl.duocuc.venta.dto.VentaResponse;
import cl.duocuc.venta.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;
    @GetMapping
    @Operation(
            summary = "Listar todas las ventas",
            description = "Devuelve una lista de todas las ventas registradas en el sistema"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de ventas obtenida exitosamente")
    })
    public ResponseEntity<List<VentaResponse>> listarVentas() {
        log.info("Recibida petición GET para listar ventas");
        List<VentaResponse> ventas = ventaService.listarVentas();
        log.info("Retornando {} ventas", ventas.size());
        return ResponseEntity.ok(ventas);
    }

    @PostMapping
    @Operation(
            summary = "Crear una nueva venta",
            description = "Registra una nueva venta en el sistema asociada a un cliente con sus productos"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Venta creada exitosamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de la venta inválidos o incompletos")
    })
    public ResponseEntity<VentaResponse> crearVenta(@Valid @RequestBody VentaRequest request) {
        log.info("Recibida petición POST para crear venta - Cliente: {}", request.getCliente());
        VentaResponse response = ventaService.crearVenta(request);
        log.info("Venta creada correctamente, retornando respuesta");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener venta por ID",
            description = "Busca y devuelve una venta específica según su identificador único"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Venta encontrada exitosamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Venta no encontrada con el ID proporcionado")
    })
    public ResponseEntity<VentaResponse> obtenerVentaPorId(@PathVariable Long id) {
        log.info("Recibida petición GET para obtener venta ID: {}", id);
        return ResponseEntity.ok(ventaService.obtenerVentaPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar venta por ID",
            description = "Actualiza la información de una venta existente según su identificador único"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Venta actualizada exitosamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de actualización inválidos o incompletos"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Venta no encontrada con el ID proporcionado")
    })
    public ResponseEntity<VentaResponse> actualizarVenta(
            @PathVariable Long id,
            @Valid @RequestBody VentaRequest request) {

        log.info("Recibida petición PUT para actualizar venta ID: {}", id);
        return ResponseEntity.ok(ventaService.actualizarVenta(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar venta por ID",
            description = "Elimina permanentemente una venta del sistema según su identificador único"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Venta eliminada correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Venta no encontrada con el ID proporcionado")
    })
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        log.info("Recibida petición DELETE para eliminar venta ID: {}", id);
        ventaService.eliminarVenta(id);
        log.info("Venta eliminada correctamente");
        return ResponseEntity.noContent().build();
    }
}
