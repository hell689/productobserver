
package net.wth.productobserver.controller;

import java.util.List;
import net.wth.productobserver.domain.Product;
import net.wth.productobserver.repo.ProductRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/product")
class ProductController {
    
    private final ProductRepo productRepo;

    public ProductController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }
    
    @GetMapping
    public List<Product> productList() {
        return productRepo.findAll();
    }
    
    @GetMapping("{id}")
    public Product getOneProduct(@PathVariable("id") Product product) {
        return product;
    }
    
    @PostMapping
    public Product create(@RequestBody Product product) {
        return productRepo.save(product);
    }
    
    @PutMapping("{id}")
    public Product update(@PathVariable("id") Product productFromdb, 
            @RequestBody Product product) {
        BeanUtils.copyProperties(product, productFromdb, "id");
        return productRepo.save(productFromdb);
    }
    
    @DeleteMapping("{id}")
    public void delete (@PathVariable("id")Product product) {
        productRepo.delete(product);
    }
}
