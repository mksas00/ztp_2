package ztp.project2.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ztp.project2.Model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
