package com.tecsup.demo5.service;

import com.tecsup.demo5.model.Categoria;
import com.tecsup.demo5.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public List<Categoria> listar() {
        return repo.findAll();
    }

    public Categoria obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Categoría no encontrada"));
    }

    public Categoria guardar(Categoria categoria) {
        if (repo.existsByNombre(categoria.getNombre())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "La categoría ya existe");
        }
        return repo.save(categoria);
    }

    public Categoria actualizar(Long id, Categoria categoria) {
        Categoria existente = obtener(id);

        if (repo.existsByNombreAndIdNot(categoria.getNombre(), id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Ya existe otra categoría con ese nombre");
        }

        existente.setNombre(categoria.getNombre());
        existente.setDescripcion(categoria.getDescripcion());

        return repo.save(existente);
    }

    public void eliminar(Long id) {
        Categoria categoria = obtener(id);
        repo.delete(categoria);
    }
}