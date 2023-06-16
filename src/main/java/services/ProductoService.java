package services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import customErrors.ErrorResponse;
import models.Producto;
import repositories.ProductoRepository;

@ApplicationScoped
public class ProductoService {
    @Inject
    ProductoRepository productoRepository;

    public Response getAll() {
        if (productoRepository.findAll().count() == 0){
            ErrorResponse error = new ErrorResponse("Sin productos ");
            return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
        return Response
                .ok(productoRepository.findAll().list())
                .build();
    }

    public Response getProducto(Long id) {
        Producto producto = productoRepository.findById(id);
        if (producto == null){
            ErrorResponse error = new ErrorResponse("No se encontro un producto con id: " + id);
            return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
        return Response.ok(producto).build();
    }

    public long count() {
		if (productoRepository.count() == 0)
			throw new WebApplicationException("Sin registros!", Response.Status.NOT_FOUND);

		return productoRepository.count();
	}

    public Response getProductoByName(String name) {
        Producto producto = productoRepository.buscarPorNombre(name);
        if (producto == null){
            ErrorResponse error = new ErrorResponse("Producto con nombre: " + "'"+name+"'"+" no encontrado");
            return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
        return Response.ok(producto).build();
    }

    public Response save(Producto producto) {
        productoRepository.persist(producto);
        return Response.ok(producto).build();
    }

    public Response actualizar(Long id, Producto producto) {
        Producto productoPut = productoRepository.findById(id);
        if (productoPut == null){
            ErrorResponse error = new ErrorResponse("No se encontro un producto con id: " + id);
            return Response.status(Response.Status.NOT_FOUND)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
        copiarNoNulos(productoPut, producto);

        return Response.ok(productoPut).build();
    }

    public void copiarNoNulos(Producto datosexistentes, Producto datosNuevos){
        if (datosNuevos.getNombre() != null) {
            datosexistentes.setNombre(datosNuevos.getNombre());
        }
        if (datosNuevos.getDescripcion() != null) {
            datosexistentes.setDescripcion(datosNuevos.getDescripcion());
        }
    }

}
