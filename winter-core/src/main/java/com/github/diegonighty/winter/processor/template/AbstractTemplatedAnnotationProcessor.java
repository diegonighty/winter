package com.github.diegonighty.winter.processor.template;

import com.github.diegonighty.winter.processor.AbstractAnnotationProcessor;
import com.github.diegonighty.winter.processor.TemplatedAnnotationProcessor;
import com.github.diegonighty.winter.processor.error.TemplateNotFoundError;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.function.BiConsumer;

public abstract class AbstractTemplatedAnnotationProcessor<A extends Annotation>
		extends AbstractAnnotationProcessor<A>
		implements TemplatedAnnotationProcessor<A> {

	@Override
	public void copy(
			BiConsumer<BufferedReader, PrintWriter> actions
	) {
		String template = getTemplate();

		try (var templateStream = getClass().getClassLoader().getResourceAsStream(template)) {
			if (templateStream == null) {
				throw new TemplateNotFoundError(template);
			}

			try (var reader = new BufferedReader(new InputStreamReader(templateStream))) {
				try (var writer = new PrintWriter(filer().createSourceFile(getNewName()).openWriter())) {
					actions.accept(reader, writer);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String replaceVariables(String line, Iterable<TemplateVar> variables) {
		for (var variable : variables) {
			line = line.replace(variable.variable(), variable.value().toString());
		}

		return line;
	}

	protected String replaceVariables(String line, TemplateVar[] variables) {
		for (var variable : variables) {
			line = line.replace(variable.variable(), variable.value().toString());
		}

		return line;
	}

	public TemplateVar[] commonVariables() {
		return new TemplateVar[] {
				TemplateVar.create("package_name", getPackage()),
				TemplateVar.create("class_name", getNewName()),
		};
	}

}
