package br.com.sdvs.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableAutoConfiguration
@SpringBootApplication
public class Application {

	//java -jar -Xms4096M -Xmx6144M -XX:NewRatio=1 -XX:-UseAdaptiveSizePolicy app-0.0.1-SNAPSHOT.jar

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	public static void main(String[] args) throws Exception {
		System.setProperty("spring.config.name", "Application");
		SpringApplication.run(Application.class, args);
	}
}
