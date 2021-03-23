package br.com.timesheetio.appointment.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.timesheetio.appointment.configuration.SwaggerConfig;
import br.com.timesheetio.appointment.dto.AppointmentDTO;
import br.com.timesheetio.appointment.dto.ResponseDTO;
import br.com.timesheetio.appointment.service.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = SwaggerConfig.TAG_APPOINTMENT_OPERATION)
@RestController
@RequestMapping("/appointment-sheet")
public class AppointmentResource {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentResource.class);
	
	@Autowired
	private AppointmentService appointmentService;

	@ApiOperation(value = "This operation is used to find all appointment.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Appointment founded."),
			@ApiResponse(code = 404, message = "Appointment not found."),
			@ApiResponse(code = 500, message = "Server error.") })
	@GetMapping
	public ResponseEntity<ResponseDTO<Page<AppointmentDTO>>> findAll(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "limit", defaultValue = "12") int limit,
			@RequestParam(name = "direction", defaultValue = "asc") String direction,
			@RequestParam(name = "field", defaultValue = "id") String field) {

		Direction directionSelected = direction.equalsIgnoreCase("asc") ? Direction.ASC : Direction.DESC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(directionSelected, field));

		Page<AppointmentDTO> pagePersons = appointmentService.findAll(pageable);

		ResponseDTO<Page<AppointmentDTO>> response = new ResponseDTO<>();
		response.setData(pagePersons);
		response.setStatus(HttpStatus.OK.value());

		response.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(AppointmentResource.class).findAll(page, limit, direction, field))
				.withSelfRel());

		return ResponseEntity.ok(response);
	}
	
	@ApiOperation(value = "This operation is used to find appointment by id.")	
	@ApiResponses(value =  {
			@ApiResponse(code = 200, message = "Person founded."),
			@ApiResponse(code = 404, message = "Person not found."),
			@ApiResponse(code = 500, message = "Server error.")
	})
	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO<AppointmentDTO>> findById(@PathVariable("id") Long id){
		
		logger.info("FINDING APPOINTMENT OBJECT BY ID ...: {}", id);
		
		AppointmentDTO appointmentFound = appointmentService.findById(id);
		
		ResponseDTO<AppointmentDTO> response = new ResponseDTO<AppointmentDTO>();
		response.setData(appointmentFound);
		response.setStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AppointmentResource.class).findById(id)).withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AppointmentResource.class).delete(id)).withRel("DELETE"));
		
		return ResponseEntity.ok(response);
	}
	
	@ApiOperation(value = "This operation is used to save appointments.")	
	@ApiResponses(value =  {
			@ApiResponse(code = 200, message = "Appointments saved."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 500, message = "Server error.")
	})
	@PostMapping
	public ResponseEntity<ResponseDTO<AppointmentDTO>> save(@RequestBody AppointmentDTO appointment) {
		
		logger.info("SAVING APPOINTMENT OBJECT ...: {}", appointment);
		
		AppointmentDTO appointmentSaved = appointmentService.save(appointment);
		
		ResponseDTO<AppointmentDTO> response = new ResponseDTO<AppointmentDTO>();
		response.setData(appointmentSaved);
		response.setStatus(HttpStatus.CREATED.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AppointmentResource.class).findById(appointmentSaved.getId())).withRel("GET"));
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AppointmentResource.class).update(appointmentSaved)).withRel("UPDATE"));
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AppointmentResource.class).delete(appointmentSaved.getId())).withRel("DELETE"));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@ApiOperation(value = "This operation is used to update persons.")	
	@ApiResponses(value =  {
			@ApiResponse(code = 200, message = "Appointments updated."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 500, message = "Server error.")
	})
	@PutMapping
	public ResponseEntity<ResponseDTO<AppointmentDTO>> update(@RequestBody AppointmentDTO person) {
		
		logger.info("UPDATING APPOINTMENT OBJECT ...: {}", person);
		
		AppointmentDTO appointmentUpdated = appointmentService.update(person);
		
		ResponseDTO<AppointmentDTO> response = new ResponseDTO<AppointmentDTO>();
		response.setData(appointmentUpdated);
		response.setStatus(HttpStatus.OK.value());
		
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AppointmentResource.class).findById(appointmentUpdated.getId())).withRel("GET"));
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AppointmentResource.class).update(appointmentUpdated)).withSelfRel());
		response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AppointmentResource.class).delete(appointmentUpdated.getId())).withRel("DELETE"));		
		
		return ResponseEntity.ok(response);
	}
	
	@ApiOperation(value = "This operation is used to delete appointment.")	
	@ApiResponses(value =  {
			@ApiResponse(code = 204, message = "Appointment deleted."),
			@ApiResponse(code = 400, message = "Bad request."),
			@ApiResponse(code = 404, message = "Appointment Not Found."),
			@ApiResponse(code = 500, message = "Server error.")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id){

		logger.info("DELETING APPOINTMENT OBJECT ...: {}", id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
