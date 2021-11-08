package co.edu.icesi.ci.thymeval.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.ci.thymeval.model.UserApp;
import co.edu.icesi.ci.thymeval.model.UserGender;
import co.edu.icesi.ci.thymeval.model.UserType;
import co.edu.icesi.ci.thymeval.repository.UserRepository;
import co.edu.icesi.ci.thymeval.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {

	
	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void delete(UserApp user) {
		userRepository.delete(user);

	}

	@Override
	public Iterable<UserApp> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Iterable<UserApp> findAllDoctors() {
		return userRepository.findByType(UserType.doctor);
	}

	@Override
	public Iterable<UserApp> findAllPatients() {
		return userRepository.findByType(UserType.patient);
	}

	@Override
	public Optional<UserApp> findById(long id) {

		return userRepository.findById(id);
	}

	@Override
	public UserGender[] getGenders() {

		return UserGender.values();
	}

	@Override
	public UserType[] getTypes() {
		// TODO Auto-generated method stub
		return UserType.values();
	}

	@Override
	public UserApp save(UserApp user) {
		return userRepository.save(user);

	}

	@Override
	@Transactional
	public void update(UserApp user) {
		UserApp entityUser = userRepository.findById(user.getId()).get();
		entityUser.setBirthDate(user.getBirthDate());
		entityUser.setEmail(user.getEmail());
		entityUser.setGender(user.getGender());
		entityUser.setName(user.getName());
		entityUser.setType(user.getType());
		userRepository.save(entityUser);

	}
}
