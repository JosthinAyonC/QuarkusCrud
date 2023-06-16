package repositories;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import models.Producto;

@ApplicationScoped
public class ProductoRepository implements PanacheRepository<Producto>{

    public Producto buscarPorNombre(String nombre) {
        return find("nombre", nombre).firstResult();
    }
    
}
