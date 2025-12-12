package org.iesvdm.shoppingcart.service;

import org.iesvdm.shoppingcart.model.Product;
import org.iesvdm.shoppingcart.repository.ProductsRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {
   private ProductsRepository productsRepository;

    public ProductService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<Product> getALL(){
        return productsRepository.getAllProducts();
    }

    public Product findById(long id){
        return productsRepository.findById(id);
    }
}
