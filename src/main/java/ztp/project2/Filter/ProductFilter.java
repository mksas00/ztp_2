package ztp.project2.Filter;

import org.springframework.stereotype.Service;
import ztp.project2.Model.Product;

@Service
public class ProductFilter{

    public void filterProduct(Product product){

        if (product.getName().isEmpty()){
            throw new NullPointerException("Product name is blank");
        }
        if (product.getDescription().isEmpty()){
            throw new NullPointerException("Product description is blank");
        }
        if (product.getPrice() <= 0 ){
            throw new IllegalArgumentException("Product price is equal or lower than 0");
        }
        if (product.getWeight() <= 0 ){
            throw new IllegalArgumentException("Product weight is equal or lower than 0");
        }
        if (product.getQuantity() <= 0 ){
            throw new IllegalArgumentException("Product quantity is equal or lower than 0");
        }
        if (product.getCategory().isEmpty()){
            throw new IllegalArgumentException("Product category is blank");
        }
    }
}
