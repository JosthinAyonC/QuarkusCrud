package controllers;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.Producto;
import services.ProductoService;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
public class ProductoController {
    @Inject
    private ProductoService productoService;

    @GET
	@Transactional
	public Response getAll() {
		return productoService.getAll();
	}

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getProducto(@PathParam("id")  Long id) {
        return productoService.getProducto(id);
    }

    @GET
    @Path("/name/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getProducto(@PathParam("name")  String name) {
        return productoService.getProductoByName(name);
    }

    @POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Response persist(@Valid Producto producto) {
		return productoService.save(producto);
	}

    @PUT
    @Path("/editar/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Response actualizar(@Valid @PathParam("id")Long id, Producto producto) {
		return productoService.actualizar(id, producto);
	}

    @GET
	@Path("/count")
	@Transactional
	public long count() {
		return productoService.count();
	}
}
