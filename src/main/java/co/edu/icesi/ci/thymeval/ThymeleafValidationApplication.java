package co.edu.icesi.ci.thymeval;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import co.edu.icesi.ci.thymeval.model.Appointment;
import co.edu.icesi.ci.thymeval.model.UserApp;
import co.edu.icesi.ci.thymeval.model.UserGender;
import co.edu.icesi.ci.thymeval.model.UserType;
import co.edu.icesi.ci.thymeval.service.AppointmentServiceImpl;
import co.edu.icesi.ci.thymeval.service.UserServiceImpl;

/*
 * Integrantes:
 * Esteban Ariza Acosta
 * Johan Sebastian Giraldo Rubio
 * Samuel Satizabal Tascon
 * Mateo Valdes Otero
 * Juan Ossa
*/
@SpringBootApplication
public class ThymeleafValidationApplication {

	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext c = SpringApplication.run(ThymeleafValidationApplication.class, args);
		UserServiceImpl u = c.getBean(UserServiceImpl.class);
		AppointmentServiceImpl a = c.getBean(AppointmentServiceImpl.class);
		//User1
		UserApp user1 = new UserApp();
		user1.setUsername("Esarac567");
		user1.setPassword("{noop}minecraft");
		user1.setName("Esteban Ariza Acosta");
		user1.setEmail("acosta57esteban@gmail.com");
		user1.setType(UserType.doctor);
		user1.setGender(UserGender.masculine);
		user1.setBirthDate(LocalDate.of(2000, 9, 9));
		u.save(user1);
		//User2
		UserApp user2 = new UserApp();
		user2.setUsername("IsabellaPro");
		user2.setPassword("{noop}minecraft");
		user2.setName("Isabella Villamil Cardenas");
		user2.setEmail("yishavica@gmail.com");
		user2.setType(UserType.patient);
		user2.setGender(UserGender.femenine);
		user2.setBirthDate(LocalDate.of(2000, 3, 25));
		u.save(user2);
		//User3
		UserApp user3 = new UserApp();
		user3.setUsername("Samuelar");
		user3.setPassword("{noop}minecraft");
		user3.setName("Samuel Ariza Acosta");
		user3.setEmail("samuel.ariza@yahoo.es");
		user3.setType(UserType.doctor);
		user3.setGender(UserGender.masculine);
		user3.setBirthDate(LocalDate.of(2008, 8, 29));
		u.save(user3);
		//User4
		UserApp user4 = new UserApp();
		user4.setUsername("Dianamacostap");
		user4.setPassword("{noop}minecraft");
		user4.setName("Diana Milena Acosta Puentes");
		user4.setEmail("dianamacostap@gmail.com");
		user4.setType(UserType.patient);
		user4.setGender(UserGender.femenine);
		user4.setBirthDate(LocalDate.of(1975, 6, 18));
		u.save(user4);
		//User5
		UserApp user5 = new UserApp();
		user5.setUsername("Arizafredy");
		user5.setPassword("{noop}minecraft");
		user5.setName("Fredy Ariza Cadena");
		user5.setEmail("fredyariza@gmail.com");
		user5.setType(UserType.admin);
		user5.setGender(UserGender.masculine);
		user5.setBirthDate(LocalDate.of(1975, 6, 18));
		u.save(user5);
		//Appointment
		Appointment appo = new Appointment();
		appo.setDate(LocalDate.of(2021, 12, 1));
		appo.setTime(LocalTime.of(12, 0));
		appo.setPatient(user2);
		appo.setDoctor(user1);
		a.save(appo);
	}

}
