package ztp.project2.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ztp.project2.Model.ProductHistory;

import java.util.Optional;

@Repository
public interface ProductHistoryRepository extends MongoRepository<ProductHistory, String> {
    Optional<ProductHistory> findByProductId(String productId);
}
