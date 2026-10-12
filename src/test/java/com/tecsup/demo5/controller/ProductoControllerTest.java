package com.tecsup.demo5.controller;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.tecsup.demo5.dto.ProductoDTO;
import com.tecsup.demo5.model.Producto;
import com.tecsup.demo5.repository.ProductoRepository;
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
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductoRepository repo;

    // TEST POST
    @Test
    void testGuardarProducto() throws Exception {

        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("Mouse");
        dto.setPrecio(80);
        dto.setStock(20);

        mockMvc.perform(post("/api/productos")
                        .header("Usuario", "Ricardo")
                        .header("Rol", "ADMIN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Mouse"));
    }

    // TEST GET
    @Test
    void testListarProductos() throws Exception {

        mockMvc.perform(get("/api/productos")
                        .header("Usuario", "Ricardo")
                        .header("Rol", "ADMIN"))
                .andExpect(status().isOk());
    }

    // TEST VALIDACIÓN
    @Test
    void testValidacionNombreVacio() throws Exception {

        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("");
        dto.setPrecio(10);
        dto.setStock(2);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    // TEST PUT
    @Test
    void testActualizarProducto() throws Exception {

        Producto producto = new Producto();
        producto.setNombre("Laptop");
        producto.setPrecio(3000);
        producto.setStock(5);

        Producto guardado = repo.save(producto);

        ProductoDTO dto = new ProductoDTO();
        dto.setNombre("Laptop actualizada");
        dto.setPrecio(3500);
        dto.setStock(8);

        mockMvc.perform(put("/api/productos/" + guardado.getId())
                        .header("Usuario", "Ricardo")
                        .header("Rol", "ADMIN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Laptop actualizada"))
                .andExpect(jsonPath("$.precio").value(3500))
                .andExpect(jsonPath("$.stock").value(8));
    }


    // TEST DELETE
    @Test
    void testEliminarProducto() throws Exception {

        Producto producto = new Producto();
        producto.setNombre("Producto para eliminar");
        producto.setPrecio(100);
        producto.setStock(10);

        Producto guardado = repo.save(producto);

        mockMvc.perform(delete("/api/productos/" + guardado.getId())
                        .header("Usuario", "Ricardo")
                        .header("Rol", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(content().string("Eliminado correctamente"));
    }

}

