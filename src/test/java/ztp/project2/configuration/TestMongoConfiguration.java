package ztp.project2.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@TestConfiguration
@TestPropertySource
@ActiveProfiles("test")
public class TestMongoConfiguration {

    public class MongoConfiguration{

        @Value("${mongo.database}")
        private String mongoDatabase;

        @Value("${mongo.port}")
        private String mongoPort;

        @Bean
        public MongoClient mongoClient(){return MongoClients.create("mongodb://localhost:" + this.mongoPort);}

        @Bean
        public MongoTemplate mongoTemplate(final MongoClient mongoClient){
            return new MongoTemplate(mongoClient,mongoDatabase);
        }
    }
}
