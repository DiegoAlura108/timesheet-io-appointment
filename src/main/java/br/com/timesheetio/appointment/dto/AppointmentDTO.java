package br.com.timesheetio.appointment.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.timesheetio.appointment.enums.PointStatusEnum;
import br.com.timesheetio.appointment.enums.PointTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO  implements Serializable {

	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private LocalDateTime point;

	private PointTypeEnum pointType;

	private PointStatusEnum pointStatus;

	private String sheetKey;
}
