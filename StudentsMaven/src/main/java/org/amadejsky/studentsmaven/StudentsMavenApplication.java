package org.amadejsky.studentsmaven;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class StudentsMavenApplication {
//	@Value("${my.dogs}")
//	private List<String> dogs;
//	@Value("${show.dogs}")
//	private boolean showDogs;

	public static void main(String[] args) {
		SpringApplication.run(StudentsMavenApplication.class, args);
	}
//@PostConstruct
//	void showDogs(){
//		if(showDogs){
//			dogs.forEach(System.out::println);
//		}else{
//			System.out.println("There is no dogs!");
//		}
//	}


//	@Bean
//	@ConfigurationProperties(prefix="car")
//	MyProperties carProperties(){
//		return new MyProperties();
//	}
//	@Bean
//	@ConfigurationProperties(prefix="horse")
//	MyProperties horseProperties(){
//		return new MyProperties();
//	}


}
