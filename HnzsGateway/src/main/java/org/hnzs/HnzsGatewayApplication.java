package org.hnzs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class HnzsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(HnzsGatewayApplication.class, args);
    }

}
