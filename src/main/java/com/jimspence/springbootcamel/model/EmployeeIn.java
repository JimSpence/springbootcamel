package com.jimspence.springbootcamel.model;

import lombok.Data;

@Data
public class EmployeeIn {
	String firstName;
	String surname;

	public static EmployeeOut transform(EmployeeIn employeeIn) {
		EmployeeOut employeeOut = new EmployeeOut();
		employeeOut.setFirstName(employeeIn.getFirstName());
		employeeOut.setSurname(employeeIn.getSurname());
		employeeOut.setExtraInformation("extraStuff");
		return employeeOut;
	}
}
