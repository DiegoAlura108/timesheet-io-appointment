package br.com.timesheetio.appointment.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.timesheetio.appointment.domain.AppointmentEntity;
import br.com.timesheetio.appointment.dto.AppointmentDTO;
import br.com.timesheetio.appointment.mapper.IMapper;

@Component
public class AppointmentMapper implements IMapper<AppointmentEntity, AppointmentDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public AppointmentDTO convertEntityToDto(AppointmentEntity entity) {
		
		return modelMapper.map(entity, AppointmentDTO.class);
	}

	@Override
	public AppointmentEntity convertDtoToEntity(AppointmentDTO dto) {
		
		return modelMapper.map(dto, AppointmentEntity.class);
	}

}
