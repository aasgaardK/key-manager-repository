package cz.spsbrno.keymanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import cz.spsbrno.keymanager.dao.RelationalDataAccess;

import java.io.IOException;

@SpringBootApplication
public class KeyManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeyManagerApplication.class, args);
	}
}
