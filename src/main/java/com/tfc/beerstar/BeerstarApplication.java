package com.tfc.beerstar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Beerstar.
 * 
 * Esta clase arranca el contexto de Spring Boot y lanza la aplicación.
 * 
 * <p>Se utiliza la anotación {@code @SpringBootApplication} que es una 
 * combinación de:
 * <ul>
 *   <li>{@code @Configuration}: indica que la clase contiene configuraciones de Spring</li>
 *   <li>{@code @EnableAutoConfiguration}: activa la configuración automática de Spring</li>
 *   <li>{@code @ComponentScan}: habilita el escaneo de componentes en el paquete y subpaquetes</li>
 * </ul>
 * 
 * @author rafalopezzz
 */
@SpringBootApplication
public class BeerstarApplication {

	/**
     * Método principal que lanza la aplicación Spring Boot.
     *
     * @param args Argumentos de línea de comandos pasados al iniciar la aplicación.
     */
	public static void main(String[] args) {
		// Lanza la aplicación utilizando el contexto de Spring
		SpringApplication.run(BeerstarApplication.class, args);
	}

}
