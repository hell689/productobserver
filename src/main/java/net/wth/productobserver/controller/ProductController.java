
package net.wth.productobserver.controller;

import java.util.ArrayList;
import java.util.List;
import net.wth.productobserver.domain.Product;
import net.wth.productobserver.exception.NotFoundException;
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
    
    private int counter = 4;
    
    private List<Product> products = new ArrayList();
    
    {
        products.add(new Product(1L, "Товар 1"));
        products.add(new Product(2L, "Товар 2"));
        products.add(new Product(3L, "Товар 3"));
    }
    
    @GetMapping
    public List<Product> productList() {
        return products;
    }
    
    @GetMapping("{id}")
    public Product getOneProduct(@PathVariable Long id) {
        return getProduct(id);
    }
    
    private Product getProduct (@PathVariable Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
    
    @PostMapping
    public Product create(@RequestBody Product product) {
        Product newProduct = new Product((long)counter++, product.getName());
        products.add(newProduct);
        return newProduct;
    }
    
    @PutMapping("{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        Product dbProduct = getProduct(id);
        dbProduct.setName(product.getName());
        dbProduct.setId(id);
        
        return dbProduct;
    }
    
    @DeleteMapping("{id}")
    public void delete (@PathVariable Long id) {
        Product deletedProduct = getProduct(id);
        products.remove(deletedProduct);
    }
}
