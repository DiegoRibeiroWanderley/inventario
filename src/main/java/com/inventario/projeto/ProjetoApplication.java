package com.inventario.projeto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetoApplication {

	public static void main(String[] args) {
        System.out.println("TESTE ENV: " + System.getenv("DB_URL"));
		SpringApplication.run(ProjetoApplication.class, args);
	}

}
