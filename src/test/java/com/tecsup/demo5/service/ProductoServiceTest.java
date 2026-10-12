package com.tecsup.demo5.service;

import com.tecsup.demo5.model.Producto;
import com.tecsup.demo5.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    @Mock
    private ProductoRepository repo;

    @InjectMocks
    private ProductoService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // TEST LISTAR
    @Test
    void testListarProductos() {

        List<Producto> lista = List.of(
                new Producto(1L, "Monitor", 500, 10)
        );

        when(repo.findAll()).thenReturn(lista);

        List<Producto> resultado = service.listar();

        assertEquals(1, resultado.size());
        assertEquals("Monitor", resultado.get(0).getNombre());

        verify(repo, times(1)).findAll();
    }

    // TEST GUARDAR
    @Test
    void testGuardarProducto() {

        Producto producto = new Producto(1L, "Laptop", 3000, 5);

        when(repo.save(producto)).thenReturn(producto);

        Producto resultado = service.guardar(producto);

        assertNotNull(resultado);
        assertEquals("Laptop", resultado.getNombre());

        verify(repo).save(producto);
    }

    // TEST OBTENER
    @Test
    void testObtenerProducto() {

        Producto producto = new Producto(1L, "Teclado", 100, 20);

        when(repo.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = service.obtener(1L);

        assertNotNull(resultado);
        assertEquals("Teclado", resultado.getNombre());

        verify(repo).findById(1L);
    }

    // TEST ELIMINAR
    @Test
    void testEliminarProducto() {

        doNothing().when(repo).deleteById(1L);

        service.eliminar(1L);

        verify(repo, times(1)).deleteById(1L);
    }

    // TEST ACTUALIZAR
    @Test
    void testActualizarProducto() {
        Producto producto = new Producto(1L, "Monitor LG", 700, 5);

        when(repo.existsById(1L)).thenReturn(true);
        when(repo.save(producto)).thenReturn(producto);

        Producto resultado = service.actualizar(1L, producto);

        assertNotNull(resultado);
        assertEquals("Monitor LG", resultado.getNombre());
        assertEquals(700, resultado.getPrecio());
        assertEquals(5, resultado.getStock());

        verify(repo).existsById(1L);
        verify(repo).save(producto);
    }

    //OBTENER PRODUCTO NO EXISTENTE
    @Test
    void testObtenerProductoNoExiste() {
        when(repo.findById(99L)).thenReturn(java.util.Optional.empty());
        Producto resultado = service.obtener(99L);
        assertNull(resultado);
        verify(repo).findById(99L);
    }
}
