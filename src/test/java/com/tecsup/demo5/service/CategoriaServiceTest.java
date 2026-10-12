package com.tecsup.demo5.service;

import com.tecsup.demo5.model.Categoria;
import com.tecsup.demo5.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository repo;

    @InjectMocks
    private CategoriaService service;

    @Test
    void testGuardarCategoria() {
        Categoria categoria = new Categoria(null, "Tecnología", "Productos tecnológicos");

        when(repo.existsByNombre("Tecnología")).thenReturn(false);
        when(repo.save(categoria)).thenReturn(
                new Categoria(1L, "Tecnología", "Productos tecnológicos"));

        Categoria resultado = service.guardar(categoria);

        assertEquals(1L, resultado.getId());
        assertEquals("Tecnología", resultado.getNombre());
        verify(repo).save(categoria);
    }

    @Test
    void testObtenerCategoriaNoExiste() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> service.obtener(99L));
    }

    @Test
    void testActualizarCategoria() {
        Categoria existente = new Categoria(1L, "Tecnología", "Anterior");
        Categoria datos = new Categoria(null, "Informática", "Nueva descripción");

        when(repo.findById(1L)).thenReturn(Optional.of(existente));
        when(repo.existsByNombreAndIdNot("Informática", 1L)).thenReturn(false);
        when(repo.save(existente)).thenReturn(existente);

        Categoria resultado = service.actualizar(1L, datos);

        assertEquals("Informática", resultado.getNombre());
        assertEquals("Nueva descripción", resultado.getDescripcion());
        verify(repo).save(existente);
    }

    @Test
    void testEliminarCategoria() {
        Categoria categoria = new Categoria(1L, "Tecnología", "Productos tecnológicos");

        when(repo.findById(1L)).thenReturn(Optional.of(categoria));

        service.eliminar(1L);

        verify(repo).delete(categoria);
    }
}