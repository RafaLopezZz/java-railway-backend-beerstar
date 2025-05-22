package com.tfc.beerstar.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

    @Value("${beerstar.openapi.dev-url:http://localhost:8080}")
    private String devUrl;

    @Value("${beerstar.openapi.prod-url:https://java-railway-backend-beerstar-production.up.railway.app}")
    private String prodUrl;

    @Bean
    public OpenAPI beerstarOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Servidor de desarrollo");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Servidor de producción");

        Contact contact = new Contact()
            .name("Soporte Beerstar")
            .email("contacto@beerstar.com")
            .url("https://www.beerstar.com/contacto");

        License mitLicense = new License()
            .name("MIT License")
            .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
            .title("API de Beerstar eCommerce")
            .version("1.0")
            .description("API para gestionar el eCommerce de Beerstar")
            .termsOfService("https://www.beerstar.com/terminos")
            .contact(contact)
            .license(mitLicense);

        return new OpenAPI()
            .info(info)
            .servers(List.of(devServer, prodServer));
    }
}
