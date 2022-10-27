package com.demo.excel.demoexcel;

import com.demo.excel.demoexcel.Utils.TimeDate;
import com.demo.excel.demoexcel.entity.Usuario;
import com.demo.excel.demoexcel.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@SpringBootApplication
public class DemoexcelApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoexcelApplication.class, args);
	}

	@Bean
	CommandLineRunner run (UsuarioService ususarioService){
		return args ->{
			ususarioService.save(new Usuario(null, "JUAN", "PAREDES", "JUAN@GMAIL.COM", TimeDate.timeDate()));
			ususarioService.save(new Usuario(null, "MARCOS", "HERNANDEZ", "MARCOS@GMAIL.COM", TimeDate.timeDate()));
			ususarioService.save(new Usuario(null, "JHON", "DOE", "JHON@GMAIL.COM", TimeDate.timeDate()));
			ususarioService.save(new Usuario(null, "STEVE", "JOBS", "STEBE@GMAIL.COM", TimeDate.timeDate()));
			ususarioService.save(new Usuario(null, "SAMUEL", "DIAS", "SAMUEL@GMAIL.COM", TimeDate.timeDate()));
			ususarioService.save(new Usuario(null, "RAQUEL", "RUIZ", "RAQUEL@GMAIL.COM", TimeDate.timeDate()));
		};
	}

}
