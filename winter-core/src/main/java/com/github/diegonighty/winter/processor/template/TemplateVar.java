package com.github.diegonighty.winter.processor.template;

public record TemplateVar(
		String variable,
		Object value
) {

	public static TemplateVar create(String variable, Object value) {
		return new TemplateVar("<" + variable + ">", value);
	}

}
