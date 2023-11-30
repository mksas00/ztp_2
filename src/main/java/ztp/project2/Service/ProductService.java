package ztp.project2.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ztp.project2.DTO.ProductDTO;
import ztp.project2.Mapper.ProductMapper;
import ztp.project2.Model.Product;
import ztp.project2.Model.ProductHistory;
import ztp.project2.Repository.ProductHistoryRepository;
import ztp.project2.Repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    final
    ProductRepository productRepository;
    final
    ProductMapper productMapper;
    final
    ProductHistoryRepository productHistoryRepository;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, ProductHistoryRepository productHistoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productHistoryRepository = productHistoryRepository;
    }

    public List<ProductDTO> listProducts(){
        List<Product> fetchedProducts = productRepository.findAll();
        return productMapper.modelToDTO(fetchedProducts);
    }

    public Product getProductById(String productId){
        Optional<Product> fetchedProduct = productRepository.findById(productId);
        if(fetchedProduct.isEmpty()){
            throw new NullPointerException("Product with id: " + productId + " doesn't exist");
        }
        return fetchedProduct.get();
    }

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public Product updateProduct(Product product){
        Optional<Product> fetchedOptional = productRepository.findById(product.getId());
        if(fetchedOptional.isEmpty()){
            throw new NullPointerException("Product with id: " + product.getId() + " doesn't exist");
        }
        Product fetchedProduct = fetchedOptional.get();

        /*
         * Adding new data to the update history of the product
         */
        ProductHistory productH;
        if(productHistoryRepository.findByProductId(fetchedProduct.getId()).isPresent()){
            productH = productHistoryRepository.findByProductId(fetchedProduct.getId()).get();
        }
        else {
            productH = new ProductHistory();
        }

        productH.setProductId(fetchedProduct.getId());
        productH.getName().add(fetchedProduct.getName());
        productH.getDescription().add(fetchedProduct.getDescription());
        productH.getPrice().add(fetchedProduct.getPrice());
        productH.getWeight().add(fetchedProduct.getWeight());
        productH.getCategory().add(fetchedProduct.getCategory());
        productH.getQuantity().add(fetchedProduct.getQuantity());
        productH.setLastUpdate(LocalDateTime.now());
        productHistoryRepository.save(productH);

        fetchedProduct.setName(product.getName());
        fetchedProduct.setDescription(product.getDescription());
        fetchedProduct.setCategory(product.getCategory());
        fetchedProduct.setPrice(product.getPrice());
        fetchedProduct.setWeight(product.getWeight());
        fetchedProduct.setQuantity(product.getQuantity());
        productRepository.save(fetchedProduct);

        return fetchedProduct;
    }

    public void deleteProduct(String productId){
        Optional<Product> productToDelete = productRepository.findById(productId);
        if(productToDelete.isEmpty()){
            throw new NullPointerException("Product with id: " + productId + " doesn't exist");
        }
        productRepository.delete(productToDelete.get());
    }


}
