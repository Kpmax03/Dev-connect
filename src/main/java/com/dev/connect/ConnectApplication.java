package com.dev.connect;

import com.dev.connect.entity.Role;
import com.dev.connect.repository.RoleRepository;
import com.dev.connect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Optional;
@SpringBootApplication
@EnableWebMvc
public  class ConnectApplication implements CommandLineRunner {
    @Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	public static void main(String[] args) {
		SpringApplication.run(ConnectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Optional<Role> byId = roleRepository.findById(1);
		if(byId.isEmpty()){
			Role role=new Role();
			role.setRoleId(1);
			role.setName("ROLE_ADMIN");
			roleRepository.save(role);
		}
		Optional<Role> byId2 = roleRepository.findById(2);
		if(byId2.isEmpty()){
			Role role=new Role();
			role.setRoleId(2);
			role.setName("ROLE_USER");
			roleRepository.save(role);
		}
	}
}
