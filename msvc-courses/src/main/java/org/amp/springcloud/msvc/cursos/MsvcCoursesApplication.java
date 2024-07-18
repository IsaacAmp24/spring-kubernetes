package org.amp.springcloud.msvc.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MsvcCoursesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcCoursesApplication.class, args);
	}

}
