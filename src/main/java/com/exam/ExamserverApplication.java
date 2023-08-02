package com.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExamserverApplication {
//	@Autowired
//	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ExamserverApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println("Code Starting");
//
//		User user = new User();
//
//		user.setUserName("Shirish03");
//		user.setFirstName("Shirish");
//		user.setLastName("Jaiswal");
//		user.setEmail("shirish@gmail.com");
//		user.setPassword("123");
//		user.setMobileNumber("123456");
//		user.setProfile("Shirish.png");
//
//		Role role = new Role();
//		role.setRoleName("Admin");
//
//		Set<UserRole> userRoles = new HashSet<>();
//		UserRole userRole = new UserRole();
//		userRole.setRole(role);
//		userRole.setUser(user);
//
//		userRoles.add(userRole);
//
//		User user1 = this.userService.createUser(user, userRoles);
//
//		System.out.println(user1.getUserName());
//	}
}
