package co.edu.icesi.ci.thymeval.service.interfaces;

import java.util.Optional;

import co.edu.icesi.ci.thymeval.model.UserApp;
import co.edu.icesi.ci.thymeval.model.UserGender;
import co.edu.icesi.ci.thymeval.model.UserType;

public interface UserService {
	public void delete(UserApp user);

	public Iterable<UserApp> findAll();

	public Iterable<UserApp> findAllDoctors();

	public Iterable<UserApp> findAllPatients();

	public Optional<UserApp> findById(long id);

	public UserGender[] getGenders();

	public UserType[] getTypes();

	public UserApp save(UserApp user);

	public void update(UserApp user);
}
