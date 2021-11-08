package co.edu.icesi.ci.thymeval.security;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.edu.icesi.ci.thymeval.model.UserApp;
import co.edu.icesi.ci.thymeval.model.UserType;
import co.edu.icesi.ci.thymeval.repository.UserRepository;

@Service
public class MyCustomUserDetailsService implements UserDetailsService {
	
	private UserRepository userRepository;
	
	@Autowired
	public MyCustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserApp> users = userRepository.findByUsername(username);
		UserApp userApp = null;
		
		if(!users.isEmpty())
			userApp = users.get(0);
		
		if (userApp != null) {
			User.UserBuilder builder = User.withUsername(username);
			builder.password(userApp.getPassword());
			System.out.println(userApp.getType().toString());
			builder.roles(userApp.getType().toString().toUpperCase());
			return builder.build();
		}
		else {
			throw new UsernameNotFoundException("User not found.");
		}
	}
}