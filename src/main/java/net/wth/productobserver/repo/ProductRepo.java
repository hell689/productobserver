package net.wth.productobserver.repo;

import net.wth.productobserver.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long>{
    
}
