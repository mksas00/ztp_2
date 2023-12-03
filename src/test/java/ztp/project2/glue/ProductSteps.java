package ztp.project2.glue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.junit.Cucumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import ztp.project2.DTO.ProductDTO;
import ztp.project2.Mapper.ProductMapper;
import ztp.project2.Model.Product;
import ztp.project2.Repository.ProductRepository;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ProductSteps {

    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    ObjectMapper objectMapper;


    private ProductMapper productMapper;


    private Product newProduct;
    private List<ProductDTO> actualProductsDTO;
    private List<ProductDTO> expectedProductsDTO;
    private List<Product> savedProducts;
    private Product expectedProduct;
    private Product actualProduct;
    private Product savedProduct;
    private  ResponseEntity<?> response;

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        productMapper = new ProductMapper();
        actualProductsDTO = new ArrayList<>();
        expectedProductsDTO = new ArrayList<>();
        productRepository.deleteAll();
    }

    /**
     * Add a new Product
     */

    @Given("the client wants to add a new product")
    public void theClientWantsToAddANewProduct() {
        newProduct = new Product();
    }

    @When("the client provides product details:")
    public void theClientProvidesProductDetails(List<Product> products) {
        newProduct = products.get(0);
        testRestTemplate.postForEntity("/product", newProduct,Product.class).getBody();
    }

    @Then("the product is successfully added")
    public void theProductIsSuccessfullyAdded() {
        assertNotNull(newProduct);
    }


    /**
     *  Fetch the product by ID
     */

    @Given("^the following products in the database to pick from:$")
    public void theClientWantsToRetrieveAProductById(List<Product> products) {
        expectedProduct = productRepository.saveAll(products).get(0);
    }

    @When("the client provides the product ID to get")
    public void theClientProvidesIdOfProductToGet() throws JsonProcessingException{
        actualProduct = objectMapper.readValue(testRestTemplate.getForEntity("/product/" + expectedProduct.getId(), String.class).getBody(), Product.class);
    }

    @Then("the product with passed ID is successfully retrieved")
    public void theProductWithIdProvidedByTheClientIsRetrieved(){
        assertNotNull(actualProduct);
        assertEquals(expectedProduct, actualProduct);
        assertEquals(expectedProduct.getId(), actualProduct.getId());
    }

    /**
     * Product update
     */

    @Given("the client product in a database:")
    public void theProductInTheDatabaseToBeUpdated(List<Product> products){
        savedProduct = productRepository.saveAll(products).get(0);
    }

    @When("the client provides updated product details:")
    public void theClientProvidesTheProductToUpdate(List<Product> products) throws JsonProcessingException{

        products.get(0).setId(savedProduct.getId());
        expectedProduct = products.get(0);
        HttpEntity<Product> requestEntity = new HttpEntity<>(products.get(0));
        ResponseEntity<String> responseEntity = testRestTemplate.exchange("/product",HttpMethod.PUT,requestEntity,String.class);
        actualProduct = objectMapper.readValue(responseEntity.getBody(), Product.class);
    }

    @Then("the product is successfully updated")
    public void theProductWithIdIsSuccessfullyRetrieved() {
        assertNotNull(actualProduct);
        assertEquals(expectedProduct.getId(), actualProduct.getId());
        assertEquals(expectedProduct.getName(), actualProduct.getName());
    }

    /**
     * Delete product with given ID
     */

    @Given("the following products in the database client can delete:")
    public void theClientWantsToDeleteAnExistingProduct(List<Product> products) {

        savedProducts = productRepository.saveAll(products);
    }

    @When("the client provides the product ID to delete")
    public void theClientProvidesTheProductIdForDeletion() {
        testRestTemplate.delete("/product/" + savedProducts.get(1).getId());
    }

    @Then("the product with provided ID is successfully deleted")
    public void theProductWithIdIsSuccessfullyDeleted() {
        assertEquals(productRepository.findAll().size(), 1);
        assertEquals(productRepository.findAll().get(0).getId(), savedProducts.get(0).getId());
    }


    /**
     * Fetch all products
     */

    @Given("^the following products in the database:$")
    public void theFollowingProductsInTheDatabase(List<Product> products) {

        expectedProductsDTO = productMapper.modelToDTO(products);
        productRepository.saveAll(products);
    }

    @When("^the client wants to fetch all products$")
    public void theClientWantsToFetchAllProducts() throws JsonProcessingException {

        actualProductsDTO.addAll(Arrays.asList(objectMapper.readValue(testRestTemplate.getForEntity("/product", String.class).getBody(), ProductDTO[].class)));
    }

    @Then("^a list of products is successfully retrieved$")
    public void theAListOfProductsIsSuccessfullyRetrieved() {
        Assertions.assertNotNull(actualProductsDTO);
        Assertions.assertEquals(actualProductsDTO.size(), expectedProductsDTO.size());
        Assertions.assertEquals(actualProductsDTO.get(0).getName(), expectedProductsDTO.get(0).getName());
    }

    @When("the client provides wrong product ID to fetch")
    public void theClientProvidesWrongProductIDToFetch() throws JsonProcessingException {

        response = testRestTemplate.getForEntity("/product/" + "non-existent-id", String.class);
    }

    @Then("an error is returned")
    public void anErrorIsReturned() {
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Given("the client wants to add a new product with wrong data")
    public void theClientWantsToAddANewProductWithWrongData() {
        newProduct = new Product();
    }

    @When("the client provides wrong product details:")
    public void theClientProvidesWrongProductDetails(List<Product> products) {
        newProduct = products.get(0);
        response = testRestTemplate.postForEntity("/product", newProduct, String.class);
    }

    @Then("a product creation error is returned")
    public void aProductCreationErrorIsReturned() {
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody(), "Product price is equal or lower than 0");
    }

}