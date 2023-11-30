package ztp.project2.Mapper;

import org.springframework.stereotype.Service;
import ztp.project2.DTO.ProductDTO;
import ztp.project2.Model.Product;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductMapper {

    public List<ProductDTO> modelToDTO(List<Product> products){

        List<ProductDTO> mappedProducts = new ArrayList<>();
        for(Product product : products){
            mappedProducts.add(new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getCategory(), product.getQuantity()));
        }
        return mappedProducts;
    }
}
