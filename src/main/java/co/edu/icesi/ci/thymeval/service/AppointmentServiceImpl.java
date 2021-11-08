package co.edu.icesi.ci.thymeval.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.ci.thymeval.model.Appointment;
import co.edu.icesi.ci.thymeval.repository.AppointmentRepository;
import co.edu.icesi.ci.thymeval.repository.UserRepository;
import co.edu.icesi.ci.thymeval.service.interfaces.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	private AppointmentRepository appointmentRepository;
	private UserRepository userRepository;

	@Autowired
	public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserRepository userRepository) {
		this.appointmentRepository = appointmentRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void delete(Appointment app) {
		appointmentRepository.delete(app);

	}

	@Override
	public Iterable<Appointment> findAll() {
		return appointmentRepository.findAll();
	}

	@Override
	public Optional<Appointment> findById(long id) {

		return appointmentRepository.findById(id);
	}

	@Override
	@Transactional
	public void save(Appointment app) {
		app.setDoctor(userRepository.findById(app.getDoctor().getId()).get());
		app.setPatient(userRepository.findById(app.getPatient().getId()).get());
		appointmentRepository.save(app);

	}

	@Transactional
	public void update(Appointment app) {
		Appointment entityApp = appointmentRepository.findById(app.getId()).get();
		entityApp.setDate(app.getDate());
		entityApp.setTime(app.getTime());
		entityApp.setDoctor(userRepository.findById(app.getDoctor().getId()).get());
		entityApp.setPatient(userRepository.findById(app.getPatient().getId()).get());
		appointmentRepository.save(entityApp);

	}
}
