package cl.duocuc.EvaRetail.controller;


import cl.duocuc.EvaRetail.dto.ApiResponse;
import cl.duocuc.EvaRetail.dto.ProductoRequest;
import cl.duocuc.EvaRetail.dto.ProductoResponse;
import cl.duocuc.EvaRetail.service.ProductoService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.extern.slf4j.Slf4j;


import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "API para gestionar productos")
public class ProductoController {

    private final ProductoService productoService;

    //listar
    @GetMapping
    @Operation(
            summary = "Listar todos los productos",
            description = "Devuelve una lista de todos los productos disponibles en el sistema"
    )

    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    })
    public ResponseEntity<List<ProductoResponse>> listarProductos() {
        log.info("Recibida petición GET para listar productos");
        List<ProductoResponse> productos = productoService.listarProductos();
        log.info("Retornando {} productos", productos.size());
        return ResponseEntity.ok(productos);
    }


    //guardar
    @PostMapping
    @Operation(
            summary = "Crear un nuevo producto",
            description = "Registra un nuevo producto en el sistema con la información proporcionada"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos del producto inválidos o incompletos")
    })
    public ResponseEntity<ProductoResponse> guardarProducto(
            @Valid @RequestBody ProductoRequest request) {

        log.info("Recibida petición POST para crear producto: {}", request.getNombre());
        ProductoResponse response = productoService.guardarProducto(request);
        log.info("Producto creado correctamente, retornando respuesta");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    //buscar x id
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener producto por ID",
            description = "Busca y devuelve un producto específico según su identificador único"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Producto encontrado exitosamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Producto no encontrado con el ID proporcionado")
    })
    public ResponseEntity<ProductoResponse> obtenerProductoPorID(@PathVariable Long id) {
        log.info("Recibida petición GET para obtener producto ID: {}", id);
        return ResponseEntity.ok(productoService.obtenerProductoPorID(id));
    }


    //actualizar x id
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar producto por ID",
            description = "Actualiza la información de un producto existente según su identificador único"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de actualización inválidos o incompletos"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Producto no encontrado con el ID proporcionado")
    })
    public ResponseEntity<ProductoResponse> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequest request) {

        log.info("Recibida petición PUT para actualizar producto ID: {}", id);
        return ResponseEntity.ok(productoService.actualizarProducto(id, request));
    }


    //eliminar x id
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar producto por ID",
            description = "Elimina permanentemente un producto del sistema según su identificador único"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Producto no encontrado con el ID proporcionado")
    })
    public ResponseEntity<ApiResponse<Object>> eliminarProducto(@PathVariable Long id) {
        log.info("Recibida petición DELETE para eliminar producto ID: {}", id);
        productoService.eliminarProducto(id);
        log.info("Producto eliminado correctamente");
        return ResponseEntity.ok(new ApiResponse<>(
                200,
                "Producto eliminado correctamente",
                false,
                null));
    }


    //reducir stock
    @PutMapping("/{id}/reducir-stock")
    @Operation(
            summary = "Reducir stock de un producto",
            description = "Reduce la cantidad disponible en stock de un producto según la cantidad indicada"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Stock reducido exitosamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Cantidad inválida o stock insuficiente para realizar la operación"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Producto no encontrado con el ID proporcionado")
    })
    public ResponseEntity<ProductoResponse> reducirStock(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable Long id,

            @Parameter(description = "Cantidad a reducir del stock", example = "5")
            @RequestParam Integer cantidad) {

        log.info("Recibida petición para reducir stock - ID: {}, Cantidad: {}", id, cantidad);
        return ResponseEntity.ok(productoService.reducirStock(id, cantidad));
    }
}
