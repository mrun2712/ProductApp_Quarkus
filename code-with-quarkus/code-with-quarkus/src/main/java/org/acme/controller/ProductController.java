package org.acme.controller;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entity.Product;
import org.acme.service.ProductService;

import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {

    @Inject
    ProductService productService;

    @GET
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GET
    @Path("/{id}")
    public Product getProduct(@PathParam("id") int id) {
        return productService.getProductById(id);
    }

    @POST
    public Response createProduct(Product product) {
        productService.createProduct(product);
        return Response.status(Response.Status.CREATED).entity(product).build();
    }

    @PUT
    @Path("/{id}")
    public Product updateProduct(@PathParam("id") int id, Product product) {
        return productService.updateProduct(id, product);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") int id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return Response.ok("Successfully deleted").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Product not found").build();
        }
    }

    @GET
    @Path("/availability/{id}")
    public Response checkStockAvailability(@PathParam("id") int id, @QueryParam("count") int count) {

        boolean available = productService.checkStockAvailability(id, count);

        return Response.ok(available).build();
    }

    @GET
    @Path("/sorted-by-price")
    public List<Product> getProductsSortedByPrice() {
        return productService.getProductsSortedByPrice();
    }

    @GET
    @Path("/descending-sort")
    public List<Product> getProductsSortedByPriceDesc() {
        return productService.getProductsSortedByPriceDesc()    ;
    }
}
