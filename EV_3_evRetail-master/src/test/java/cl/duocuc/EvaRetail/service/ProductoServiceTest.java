package cl.duocuc.EvaRetail.service;

import cl.duocuc.EvaRetail.dto.ProductoRequest;
import cl.duocuc.EvaRetail.dto.ProductoResponse;
import cl.duocuc.EvaRetail.model.Producto;
import cl.duocuc.EvaRetail.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void guardarProducto_debeRetornarResponse() {
        ProductoRequest request = new ProductoRequest();
        request.setNombre("Laptop");
        request.setPrecio(999.99);
        request.setStock(10);

        Producto guardado = new Producto();
        guardado.setId(1L);
        guardado.setNombre("Laptop");
        guardado.setPrecio(999.99);
        guardado.setStock(10);

        when(productoRepository.save(any())).thenReturn(guardado);

        ProductoResponse res = productoService.guardarProducto(request);

        assertEquals("Laptop", res.getNombre());
        assertEquals(999.99, res.getPrecio());
        assertEquals(10, res.getStock());
    }

    @Test
    void listarProductos_debeRetornarLista() {
        Producto p = new Producto();
        p.setId(1L);
        p.setNombre("Mouse");
        p.setPrecio(29.99);
        p.setStock(50);

        when(productoRepository.findAll()).thenReturn(List.of(p));

        List<ProductoResponse> lista = productoService.listarProductos();

        assertEquals(1, lista.size());
        assertEquals("Mouse", lista.get(0).getNombre());
    }

    @Test
    void obtenerProductoPorID_debeRetornarProducto() {
        Producto p = new Producto();
        p.setId(1L);
        p.setNombre("Teclado");
        p.setPrecio(49.99);
        p.setStock(20);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(p));

        ProductoResponse res = productoService.obtenerProductoPorID(1L);

        assertEquals("Teclado", res.getNombre());
    }

    @Test
    void obtenerProductoPorID_noExiste_debeLanzarExcepcion() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> productoService.obtenerProductoPorID(99L));
    }

    @Test
    void reducirStock_conStockInsuficiente_debeLanzarExcepcion() {
        Producto p = new Producto();
        p.setId(1L);
        p.setNombre("Laptop");
        p.setPrecio(999.99);
        p.setStock(2);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(p));

        assertThrows(RuntimeException.class,
                () -> productoService.reducirStock(1L, 5));
    }

    @Test
    void reducirStock_correcto_debeActualizarStock() {
        Producto p = new Producto();
        p.setId(1L);
        p.setNombre("Laptop");
        p.setPrecio(999.99);
        p.setStock(10);

        Producto actualizado = new Producto();
        actualizado.setId(1L);
        actualizado.setNombre("Laptop");
        actualizado.setPrecio(999.99);
        actualizado.setStock(7);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(p));
        when(productoRepository.save(any())).thenReturn(actualizado);

        ProductoResponse res = productoService.reducirStock(1L, 3);

        assertEquals(7, res.getStock());
    }
}