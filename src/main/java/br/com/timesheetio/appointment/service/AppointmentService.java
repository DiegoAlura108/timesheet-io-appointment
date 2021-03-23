package br.com.timesheetio.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.timesheetio.appointment.domain.AppointmentEntity;
import br.com.timesheetio.appointment.dto.AppointmentDTO;
import br.com.timesheetio.appointment.exception.ObjectAlreadyExistsException;
import br.com.timesheetio.appointment.exception.ObjectNotFoundException;
import br.com.timesheetio.appointment.mapper.impl.AppointmentMapper;
import br.com.timesheetio.appointment.repository.AppointmentRepository;

@Service
public class AppointmentService {
	
	private static final String MESSAGE_OBJECT_ALREADY_EXIST = "This Appointment Object Already Exists.";

	private static final String MESSAGE_OBJECT_NOT_FOUND = "This Appointment Object Not Found.";
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private AppointmentMapper mapper;
	
	public Page<AppointmentDTO> findAll(Pageable pageable) {
		
		return appointmentRepository.findAll(pageable).map(mapper::convertEntityToDto);
	}
	
	public AppointmentDTO findById(Long id) {
		
		AppointmentEntity appointmentEntity = appointmentRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(AppointmentService.MESSAGE_OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND));
		
		return mapper.convertEntityToDto(appointmentEntity);
	}
	
	public AppointmentDTO update(AppointmentDTO appointmentDTO) {
		
		AppointmentDTO appointmentFound = this.findById(appointmentDTO.getId());
		
		AppointmentEntity appointmentEntity = mapper.convertDtoToEntity(appointmentFound);
		
		return mapper.convertEntityToDto(appointmentRepository.save(appointmentEntity));
	}
	
	public void delete(Long id) {
		
		AppointmentDTO appointmentFound = this.findById(id);
		
		appointmentRepository.delete(mapper.convertDtoToEntity(appointmentFound));
	}
	
	public AppointmentDTO save(AppointmentDTO appointmentDTO) {
		
		if(appointmentDTO.getId() != null) {
			
			throw new ObjectAlreadyExistsException(AppointmentService.MESSAGE_OBJECT_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
		}
		
		return mapper.convertEntityToDto(appointmentRepository.save(mapper.convertDtoToEntity(appointmentDTO)));
	}
}
