package com.quartoTentativo.Prova;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Developer {
	@Id @GeneratedValue
	private Long id;
	private Double workedHrs;
	private String firstName;
	private String lastName;
	private ArrayList<Long> tasks;
	
	
	public Developer() {}
	
	public Developer(String firstName, String lastName, Double workedHrs) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.workedHrs = workedHrs;
	}
	
	
	
	public ArrayList<Long> getTasks() {
		return tasks;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setWorkedHrs(Double workedHrs) {
		this.workedHrs = workedHrs;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setTasks(ArrayList<Long> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return String.format(
				"Developer[id=%d, firstName='%s', lastName='%s', hours worked=%.2f]",
				this.id, this.firstName, this.lastName, this.workedHrs);
	}

	public Long getId() {
		return id;
	}

	public Double getWorkedHrs() {
		return workedHrs;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public void addHrs(Double hours) {
		if (hours > 0) {
		this.workedHrs= hours+this.workedHrs;
		}
	}
	
	public void assignTask(Long taskId) {
		this.tasks.add(taskId);
	}

}
