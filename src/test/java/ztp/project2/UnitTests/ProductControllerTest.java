package ztp.project2.UnitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ztp.project2.Controller.ProductController;
import ztp.project2.DTO.ProductDTO;
import ztp.project2.Filter.ProductFilter;
import ztp.project2.Model.Product;
import ztp.project2.Service.ProductService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductFilter productFilter;

    @InjectMocks
    private ProductController productController;


    @Test
    void saveProduct_ValidProduct_ReturnsCreatedResponse() {
        // Given
        Product product = new Product();
        when(productFilter.filterProduct(product)).thenReturn(product);
        when(productService.addProduct(product)).thenReturn(product);

        // When
        ResponseEntity<?> responseEntity = productController.saveProduct(product);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(product, responseEntity.getBody());
        verify(productFilter, times(1)).filterProduct(product);
        verify(productService, times(1)).addProduct(product);
    }

    @Test
    void saveProduct_InvalidProduct_ReturnsBadRequestResponse() {
        // Given
        Product product = new Product();
        String errorMessage = "Validation error message";
        when(productFilter.filterProduct(product)).thenThrow(new IllegalArgumentException(errorMessage));

        // When
        ResponseEntity<?> responseEntity = productController.saveProduct(product);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
        verify(productFilter, times(1)).filterProduct(product);
        verify(productService, never()).addProduct(product);
    }

    @Test
    void listProducts_ReturnsListOfProductDTOs() {
        // Given
        List<ProductDTO> productDTOList = Collections.singletonList(new ProductDTO());
        when(productService.listProducts()).thenReturn(productDTOList);

        // When
        ResponseEntity<List<ProductDTO>> responseEntity = productController.listProducts();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(productDTOList, responseEntity.getBody());
        verify(productService, times(1)).listProducts();
    }

    @Test
    void getProduct_ValidProductId_ReturnsProduct() {
        // Given
        String productId = "123";
        Product product = new Product();
        when(productService.getProductById(productId)).thenReturn(product);

        // When
        ResponseEntity<?> responseEntity = productController.getProduct(productId);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(product, responseEntity.getBody());
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    void updateProduct_ValidProduct_ReturnsOkResponse() {
        // Given
        Product product = new Product();
        when(productFilter.filterProduct(product)).thenReturn(product);
        when(productService.updateProduct(product)).thenReturn(product);

        // When
        ResponseEntity<?> responseEntity = productController.updateProduct(product);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(product, responseEntity.getBody());
        verify(productFilter, times(1)).filterProduct(product);
        verify(productService, times(1)).updateProduct(product);
    }

    @Test
    void updateProduct_InvalidProduct_ReturnsBadRequestResponse() {
        // Given
        Product product = new Product();
        String errorMessage = "Validation error message";
        when(productFilter.filterProduct(product)).thenThrow(new IllegalArgumentException(errorMessage));

        // When
        ResponseEntity<?> responseEntity = productController.updateProduct(product);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
        verify(productFilter, times(1)).filterProduct(product);
        verify(productService, never()).updateProduct(product);
    }

    @Test
    void deleteProduct_ValidProductId_ReturnsNoContentResponse() {
        // Given
        String productId = "123";

        // When
        ResponseEntity<Void> responseEntity = productController.deleteProduct(productId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(productService, times(1)).deleteProduct(productId);
    }
}