package br.com.timesheetio.appointment.mapper;

public interface IMapper <E, D>{

	public D convertEntityToDto(E e);
	
	public E convertDtoToEntity(D d);
}
