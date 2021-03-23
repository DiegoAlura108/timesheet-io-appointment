package br.com.timesheetio.appointment.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.timesheetio.appointment.enums.PointStatusEnum;
import br.com.timesheetio.appointment.enums.PointTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "APPOINTMENT")
public class AppointmentEntity implements Serializable {

	/**
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "POINT")
	private LocalDateTime point;

	@Column(name = "POINTTYPE")
	@Enumerated(EnumType.STRING)
	private PointTypeEnum pointType;

	@Column(name = "POINTSTATUS")
	private PointStatusEnum pointStatus;

	@Column(name = "SHEETKEY")
	private String sheetKey;
}
