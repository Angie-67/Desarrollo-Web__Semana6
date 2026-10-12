package com.tecsup.demo5.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.demo5.dto.CategoriaDTO;
import com.tecsup.demo5.model.Categoria;
import com.tecsup.demo5.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoriaRepository repo;

    @Test
    void testGuardarCategoria() throws Exception {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre("Libros");
        dto.setDescripcion("Libros de diferentes temas");

        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Libros"));
    }

    @Test
    void testListarCategorias() throws Exception {
        mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk());
    }

    @Test
    void testNombreVacio() throws Exception {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre("");
        dto.setDescripcion("Descripción");

        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testActualizarCategoria() throws Exception {
        Categoria categoria = repo.save(
                new Categoria(null, "Categoría anterior", "Descripción anterior"));

        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre("Categoría actualizada");
        dto.setDescripcion("Nueva descripción");

        mockMvc.perform(put("/api/categorias/" + categoria.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Categoría actualizada"));
    }

    @Test
    void testEliminarCategoria() throws Exception {
        Categoria categoria = repo.save(
                new Categoria(null, "Categoría para eliminar", "Prueba"));

        mockMvc.perform(delete("/api/categorias/" + categoria.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCategoriaNoExiste() throws Exception {
        mockMvc.perform(get("/api/categorias/999999"))
                .andExpect(status().isNotFound());
    }
}