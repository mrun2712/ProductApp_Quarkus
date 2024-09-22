package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.acme.entity.Product;
import org.acme.repository.ProductRepository;


import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.listAll();
    }

    public Product getProductById(int id) {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new WebApplicationException("Product not found", 404);
        }
        return product;
    }

    @Transactional
    public void createProduct(Product product) {
        productRepository.persist(product);
    }

    @Transactional
    public Product updateProduct(int id, Product updatedProduct) {
        Product existingProduct = getProductById(id);
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        return existingProduct;
    }

    @Transactional
    public boolean deleteProduct(int id) {
        boolean b = productRepository.deleteById(id);
        if (!b) {
            throw new WebApplicationException("Product not found", 404);
        }
        return b;
    }

    public boolean checkStockAvailability(int id, int count) {
        Product product = getProductById(id);
        System.out.println(product.getQuantity() >= count);
        System.out.println(product.getQuantity()+" "+count);
        return product.getQuantity() >= count;
    }

    public List<Product> getProductsSortedByPrice() {
        return productRepository.list("order by price asc");
    }

    public List<Product> getProductsSortedByPriceDesc() {
        return productRepository.list("order by price desc");
    }
}
