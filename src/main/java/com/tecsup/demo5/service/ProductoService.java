package com.tecsup.demo5.service;

import com.tecsup.demo5.model.Producto;
import com.tecsup.demo5.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repo;

    public List<Producto> listar() {
        return repo.findAll();
    }

    public Producto guardar(Producto p) {
        return repo.save(p);
    }

    public Producto actualizar(Long id, Producto p) {
        if (repo.existsById(id)) {
            p.setId(id);
            return repo.save(p);
        }
        return null;
    }

    public Producto obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public List<Producto> buscarPorNombre(String nombre) {return repo.findByNombre(nombre);}
}
