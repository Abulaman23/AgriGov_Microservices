package com.agrigov.exception;

public class MilestoneNotFound extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MilestoneNotFound(String message) {
		super(message);
	}
}
