package org.formation.rest.views;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {

	private final String mesage;
	private final String level;
	private final Date timestamp;

}
