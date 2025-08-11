package com.owr.order_service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

    static {
        // Load environment variables from .env file
        // Ignores file if missing (useful for production environments like Heroku)
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        // List of expected keys to load from the .env file
        String[] envVars = {
                "PORT",
                "SPRING_DATA_MONGODB_URI",
                "SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI",
                "INVENTORY_SERVICE_URL",
                "PRODUCT_SERVICE_URL"
        };

        // Iterate through keys and set them as JVM system properties if found
        for (String key: envVars){

            String value = dotenv.get(key);

            if (value != null){
                System.setProperty(key, value);  // Makes it accessible via System.getProperty
                System.out.println("✅ " + key + " loaded and set.");
            } else {
                System.out.println("⚠\uFE0F " + key + " not found in .env file. Skipping System." );
            }
        }
    }


}
