package com.quartoTentativo.Prova;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

enum STATE {
	inBacklog,
	inProgress,
	completed
}

@Entity
public class Task {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private float hoursWorked;
	private STATE condition;
	private ArrayList<String> notes;
	
	protected Task() {}
	
	public Task(String name) {
		this.name = name;
		this.hoursWorked = 0;
		this.condition = STATE.inBacklog;
	}
	
	@Override
	public String toString() {
		return String.format(
				"Task[id=%d, Name='%s', hours spent=%.2f]",
				this.id, this.name, this.hoursWorked);
		//TODO: print out enum state, I'm unsure how.
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHoursWorked(float hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

	public void setCondition(STATE condition) {
		this.condition = condition;
	}

	public void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}

	public STATE getCondition() {
		return condition;
	}

	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public float getHoursWorked() {
		return hoursWorked;
	}
	public ArrayList<String> getNotes() {
		return notes;
	}
	
	public void addNote(String note) {
		this.notes.add(0, note);
	}
	
	public void addHrs(float hours) {
		if (hours > 0) {
		this.hoursWorked += hours;
		}
	}
	
}
