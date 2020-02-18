package br.com.sdvs.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	/*
	java -jar -Xms4096M -Xmx6144M -XX:NewRatio=1 -XX:-UseAdaptiveSizePolicy app-0.0.1-SNAPSHOT.jar
	*****************************
	/opt/sonarqube/bin/linux-x86-64/sonar.sh console
	mvn clean verify sonar:sonar
	*/

	public static void main(String[] args) {
		String[] args2 = new String[0];
		System.setProperty("spring.config.name", "Application");
		SpringApplication.run(Application.class, args2);
	}
}
