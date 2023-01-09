package com.github.diegonighty.winter.configuration;

import com.github.diegonighty.winter.processor.template.TemplateVar;
import com.github.diegonighty.winter.processor.template.listed.AbstractListedAnnotationProcessor;

import javax.lang.model.element.Element;
import java.util.List;
import java.util.Set;

public class ConfigurationProcessor extends AbstractListedAnnotationProcessor<Configuration> {

	@Override
	public List<TemplateVar> values(Element element) {
		return List.of(
				TemplateVar.create("el_name", element.getAnnotation(Configuration.class).name()),
				TemplateVar.create("el_type", element.asType().toString())
		);
	}

	@Override
	public String getTemplate() {
		return "ConfigurationManifestTemplate.java";
	}

	@Override
	public String getPackage() {
		return "com.github.diegonighty.winter.configuration";
	}

	@Override
	public String getNewName() {
		return "ConfigurationManifest";
	}

	@Override
	public Set<Class<? extends Configuration>> getAnnotations() {
		return Set.of(Configuration.class);
	}

}
