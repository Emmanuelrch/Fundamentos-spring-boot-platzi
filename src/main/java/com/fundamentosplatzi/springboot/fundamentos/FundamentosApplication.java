package com.fundamentosplatzi.springboot.fundamentos;

import com.fundamentosplatzi.springboot.fundamentos.bean.MyBean;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosplatzi.springboot.fundamentos.component.ComponentDependency;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.pojo.UserPojo;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {
	Log LOGGER= LogFactory.getLog(FundamentosApplication.class);
	private ComponentDependency componentDependency;
	private MyBean myBean;
    private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;

	public FundamentosApplication(@Qualifier("comaponentTwoImplement") ComponentDependency componentDependency,
								  MyBean myBean,MyBeanWithDependency myBeanWithDependency,
								  MyBeanWithProperties myBeanWithProperties,
								  UserPojo userPojo,
								  UserRepository userRepository) {
		this.componentDependency = componentDependency;
		this.myBean = myBean;
        this.myBeanWithDependency=myBeanWithDependency;
		this.myBeanWithProperties=myBeanWithProperties;
		this.userPojo=userPojo;
		this.userRepository=userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//	ejemplosAnteriores();
	saveUserInDataBase();
	getInformationJpqlFromUser();
	}
	private void getInformationJpqlFromUser(){
//		LOGGER.info("Usuario con el metodo findByUserEmail" + userRepository.findByUserEmail("julie@gmail.com")
//				.orElseThrow(()-> new RuntimeException("No se encontro el usuario")));
//
//		userRepository.findAndSort("user", Sort.by("id").ascending())
//				.stream()
//				.forEach(user -> LOGGER.info("Usuario con metodo sort "+user));
//
//		userRepository.findByName("julie").stream()
//				.forEach(user -> LOGGER.info("Usuario con query method "+ user));
//
//		LOGGER.info("Usuario con query method findByemailAndName"+
//				userRepository.findByEmailAndName("user3@gmail.com","user3")
//						.orElseThrow(()-> new RuntimeException("Usuario no encontrado")));
//
//
//		userRepository.findByNameLike("%J%").stream()
//				.forEach(user -> LOGGER.info("Usuario findByNameLike"+ user));
//
//
//		userRepository.findByNameOrEmail("user10",null).stream()
//				.forEach(user -> LOGGER.info("Usuario findByNameOrEmail"+ user));
		userRepository.findByBirthDateBetween(LocalDate.of(2021,3,1),LocalDate.of(2021,4,20))
				.stream()
				.forEach(user -> LOGGER.info("Usuario con intervalo de fechas "+ user));

		// esto es lo mismo que solo con diferente metodo


		userRepository.findByNameLikeOrderByIdDesc("user")
				.stream()
				.forEach(user -> LOGGER.info("Usuario encontrado con like y ordenado "+user));

//		userRepository.findByNameContainingOrderByIdDesc("user")
//				.stream()
//				.forEach(user -> LOGGER.info("Usuario encontrado con like y ordenado "+user));


	}
	private void saveUserInDataBase (){
		User user1=new User("Jhon","jhon@gmail.com", LocalDate.of(2021,3,20));
		User user2=new User("julie","julie@gmail.com", LocalDate.of(2021,5,2));
		User user3=new User("user3","user3@gmail.com", LocalDate.of(2021,1,3));
		User user4=new User("user4","user4@gmail.com", LocalDate.of(2021,6,4));
		User user5=new User("user5","user5@gmail.com", LocalDate.of(2021,8,5));
		User user6=new User("user6","user6@gmail.com", LocalDate.of(2021,9,6));
		User user7=new User("user7","user7@gmail.com", LocalDate.of(2021,10,7));
		User user8=new User("user8","user8@gmail.com", LocalDate.of(2021,11,8));
		User user9=new User("user9","user9@gmail.com", LocalDate.of(2021,12,9));
		User user10=new User("user10","user10@gmail.com", LocalDate.of(2021,2,10));
		User user11=new User("user11","user11@gmail.com", LocalDate.of(2021,4,11));
		User user12=new User("user12","user12@gmail.com", LocalDate.of(2021,1,12));
		List<User> list= Arrays.asList(user1,user2,user3,user4,user5,user6,user7,user8,user9,user10,user11,user12);
		list.stream().forEach(userRepository::save);

	}
	private void ejemplosAnteriores(){
		componentDependency.saludar();
		myBean.print();
        myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.funtion());
		System.out.println(userPojo.getEmail()+"-"+userPojo.getPassword());
		try{
		int value= 10/0;
		LOGGER.debug("Mi valor"+ value);
		}catch (Exception e) {
			LOGGER.error("Esto es un error al dividir por cero "+ e.getMessage());
		}
	}
}
