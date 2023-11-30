package ztp.project2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ztp.project2.DTO.ProductDTO;
import ztp.project2.Filter.ProductFilter;
import ztp.project2.Model.Product;
import ztp.project2.Service.ProductService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductFilter productFilter;

    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody Product product){
        try {
            productFilter.filterProduct(product);
        }
        catch (IllegalArgumentException | NullPointerException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
        Product responseData = productService.addProduct(product);
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> listProducts(){
        List<ProductDTO> responseData = productService.listProducts();
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping(value = "/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable String productId){
        Product responseData = productService.getProductById(productId);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody Product product){
        try {
            productFilter.filterProduct(product);
        }
        catch (IllegalArgumentException | NullPointerException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
        Product responseData = productService.updateProduct(product);
        return new ResponseEntity<>(responseData, HttpStatus.OK);

    }

    @DeleteMapping(value = "/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId){
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
