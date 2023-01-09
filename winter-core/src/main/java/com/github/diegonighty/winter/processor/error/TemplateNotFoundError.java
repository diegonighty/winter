package com.github.diegonighty.winter.processor.error;

public class TemplateNotFoundError extends RuntimeException {
	public TemplateNotFoundError(String template) {
		super("Template not found: " + template);
	}
}
