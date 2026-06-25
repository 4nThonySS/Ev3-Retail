package cl.duocuc.venta.service;

import cl.duocuc.venta.client.ProductoClient;
import cl.duocuc.venta.dto.VentaRequest;
import cl.duocuc.venta.dto.VentaResponse;
import cl.duocuc.venta.model.Venta;
import cl.duocuc.venta.repository.VentaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private VentaService ventaService;

    @Test
    void listarVentas_debeRetornarLista() {
        Venta v = new Venta();
        v.setId(1L);
        v.setCliente("Juan");
        v.setProductoId(1L);
        v.setCantidad(2);
        v.setTotal(200.0);
        v.setFecha(LocalDateTime.now());

        when(ventaRepository.findAll()).thenReturn(List.of(v));

        List<VentaResponse> lista = ventaService.listarVentas();

        assertEquals(1, lista.size());
        assertEquals("Juan", lista.get(0).getCliente());
    }

    @Test
    void obtenerVentaPorId_debeRetornarVenta() {
        Venta v = new Venta();
        v.setId(1L);
        v.setCliente("Maria");
        v.setProductoId(2L);
        v.setCantidad(1);
        v.setTotal(500.0);
        v.setFecha(LocalDateTime.now());

        when(ventaRepository.findById(1L)).thenReturn(Optional.of(v));

        VentaResponse res = ventaService.obtenerVentaPorId(1L);

        assertEquals("Maria", res.getCliente());
        assertEquals(500.0, res.getTotal());
    }

    @Test
    void obtenerVentaPorId_noExiste_debeLanzarExcepcion() {
        when(ventaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> ventaService.obtenerVentaPorId(99L));
    }

    @Test
    void crearVenta_conStockSuficiente_debeRetornarResponse() {
        VentaRequest request = new VentaRequest();
        request.setCliente("Pedro");
        request.setProductoId(1L);
        request.setCantidad(2);

        var productoMock = new cl.duocuc.venta.dto.ProductoResponse();
        productoMock.setStock(10);
        productoMock.setPrecio(100.0);

        Venta guardada = new Venta();
        guardada.setId(1L);
        guardada.setCliente("Pedro");
        guardada.setProductoId(1L);
        guardada.setCantidad(2);
        guardada.setTotal(200.0);
        guardada.setFecha(LocalDateTime.now());

        when(productoClient.obtenerProductoPorId(1L)).thenReturn(productoMock);
        when(ventaRepository.save(any())).thenReturn(guardada);

        VentaResponse res = ventaService.crearVenta(request);

        assertEquals("Pedro", res.getCliente());
        assertEquals(200.0, res.getTotal());
    }

    @Test
    void crearVenta_conStockInsuficiente_debeLanzarExcepcion() {
        VentaRequest request = new VentaRequest();
        request.setCliente("Pedro");
        request.setProductoId(1L);
        request.setCantidad(10);

        var productoMock = new cl.duocuc.venta.dto.ProductoResponse();
        productoMock.setStock(2);
        productoMock.setPrecio(100.0);

        when(productoClient.obtenerProductoPorId(1L)).thenReturn(productoMock);

        assertThrows(RuntimeException.class,
                () -> ventaService.crearVenta(request));
    }

    @Test
    void eliminarVenta_noExiste_debeLanzarExcepcion() {
        when(ventaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> ventaService.eliminarVenta(99L));
    }

    @Test
    void actualizarVenta_debeRetornarVentaActualizada() {
        // Se prepara el request con los nuevos datos
        VentaRequest request = new VentaRequest();
        request.setCliente("Carlos");
        request.setProductoId(3L);
        request.setCantidad(5);

        // Se crea la venta que ya existe en la BD
        Venta ventaExistente = new Venta();
        ventaExistente.setId(1L);
        ventaExistente.setCliente("Pedro");
        ventaExistente.setProductoId(1L);
        ventaExistente.setCantidad(2);
        ventaExistente.setTotal(200.0);
        ventaExistente.setFecha(LocalDateTime.now());
        // Se crea como quedara la venta despues de actualizar
        Venta ventaActualizada = new Venta();
        ventaActualizada.setId(1L);
        ventaActualizada.setCliente("Carlos");
        ventaActualizada.setProductoId(3L);
        ventaActualizada.setCantidad(5);
        ventaActualizada.setTotal(200.0);
        ventaActualizada.setFecha(LocalDateTime.now());
        // Se le dice a Mockito que simule el repositorio
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(ventaExistente));
        when(ventaRepository.save(any())).thenReturn(ventaActualizada);

        // Se ejecuta el metodo real
        VentaResponse res = ventaService.actualizarVenta(1L, request);

        // Se verifica que los datos sean correctos
        assertEquals("Carlos", res.getCliente());
        assertEquals(3L, res.getProductoId());
        assertEquals(5, res.getCantidad());
        verify(ventaRepository).save(any());
    }
}