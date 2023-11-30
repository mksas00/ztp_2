package ztp.project2.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "product_history")
public class ProductHistory {
    @Id
    private String id;
    private String productId;
    private List<String> name = new ArrayList<>();
    private List<String> description = new ArrayList<>();
    private List<Double> price = new ArrayList<>();
    private List<Double> weight = new ArrayList<>();
    private List<String> category = new ArrayList<>();
    private List<Integer> quantity = new ArrayList<>();
    private LocalDateTime lastUpdate;

}