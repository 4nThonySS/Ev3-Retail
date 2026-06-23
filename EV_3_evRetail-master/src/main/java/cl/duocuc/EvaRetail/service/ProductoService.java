package cl.duocuc.EvaRetail.service;

import cl.duocuc.EvaRetail.dto.ProductoRequest;
import cl.duocuc.EvaRetail.dto.ProductoResponse;
import cl.duocuc.EvaRetail.model.Producto;
import cl.duocuc.EvaRetail.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;

    private ProductoResponse convertirAResponse(Producto producto){
        log.debug("Convirtiendo entidad Producto a Response - ID: {}", producto.getId());

        ProductoResponse response = new ProductoResponse();

        response.setId(producto.getId());
        response.setNombre(producto.getNombre());
        response.setPrecio(producto.getPrecio());
        response.setStock(producto.getStock());

        return response;
    }

    private Producto convertirAEntity(ProductoRequest request){
        log.debug("Convirtiendo request a entidad Producto: {}", request.getNombre());

        Producto producto = new Producto();

        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());

        return producto;
    }


    //Guardar
    public ProductoResponse guardarProducto(ProductoRequest request){
        log.info("Iniciando creacion de nuevo producto: {}", request.getNombre());

        Producto producto = convertirAEntity(request);
        Producto guardado = productoRepository.save(producto);

        log.info("Producto creado exitosamente - ID: {}, Nombre: {}", guardado.getId(), guardado.getNombre());
        return convertirAResponse(guardado);
    }

    //Listar
    @Transactional(readOnly = true)
    public List<ProductoResponse> listarProductos() {
        log.info("Listando todos los productos");

        List<Producto> productos = productoRepository.findAll();
        List<ProductoResponse> respuesta = new ArrayList<>();

        for (Producto producto : productos) {
            ProductoResponse dto = convertirAResponse(producto);
            respuesta.add(dto);
        }
        log.info("Se encontraron {} productos", respuesta.size());
        return respuesta;
    }

    //buscar x id

    @Transactional(readOnly = true)
    public ProductoResponse obtenerProductoPorID (Long id) {
        log.info("Buscando producto por ID: {}", id);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Producto no encontrado con ID: {}", id);
                    return new RuntimeException("ERROR: Producto no encontrado");
                });
        log.info("Producto encontrado - ID: {}, Nombre: {}", producto.getId(), producto.getNombre());
        return convertirAResponse(producto);
    }



    // actualizar x id
    public ProductoResponse actualizarProducto(Long idProducto, ProductoRequest request) {
        log.info("Iniciando actualización de producto - ID: {}", idProducto);
        log.debug("Datos nuevos - Nombre: {}, Precio: {}, Stock: {}",
                request.getNombre(), request.getPrecio(), request.getStock());

        Producto productoExistente = productoRepository.findById(idProducto)
                .orElseThrow(() -> {
                    log.error("Producto no encontrado para actualizar - ID: {}", idProducto);
                    return new RuntimeException("ERROR: Producto no encontrado");
                });

        productoExistente.setNombre(request.getNombre());
        productoExistente.setPrecio(request.getPrecio());
        productoExistente.setStock(request.getStock());

        Producto productoActualizado = productoRepository.save(productoExistente);

        log.info("Producto actualizado exitosamente - ID: {}, Nuevo nombre: {}",
                productoActualizado.getId(), productoActualizado.getNombre());

        return convertirAResponse(productoActualizado);
    }

    //eliminar xid
    public void eliminarProducto(Long id){
        log.info("Iniciando eliminación de producto - ID: {}", id);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Producto no encontrado para eliminar - ID: {}", id);
                    return new RuntimeException("ERROR: Producto no encontrado");
                });

        productoRepository.delete(producto);
        log.info("Producto eliminado correctamente - ID: {}", id);
    }

    public ProductoResponse reducirStock(Long id, Integer cantidad){
        log.info("Intentando reducir stock - Producto ID: {}, Cantidad: {}", id, cantidad);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Producto no encontrado para reducir stock - ID: {}", id);
                    return new RuntimeException("Producto no encontrado");
                });

        if(producto.getStock() < cantidad){
            log.warn("Stock insuficiente! Actual: {}, Solicitado: {}", producto.getStock(), cantidad);
            throw new RuntimeException("Stock insuficiente");
        }
        producto.setStock(producto.getStock() - cantidad);

        Producto actualizado = productoRepository.save(producto);
        log.info("Stock reducido correctamente. Producto ID: {}, Nuevo stock: {}",
                actualizado.getId(), actualizado.getStock());
        return convertirAResponse(actualizado);
    }

}