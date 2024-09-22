package org.acme.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Product;

@ApplicationScoped
public class ProductRepository implements PanacheRepositoryBase<Product, Integer> {

}
