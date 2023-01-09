package com.github.diegonighty.winter.processor.template.listed;

import com.github.diegonighty.winter.processor.template.AbstractTemplatedAnnotationProcessor;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class AbstractListedAnnotationProcessor<A extends Annotation>
		extends AbstractTemplatedAnnotationProcessor<A>
		implements ListedAnnotationProcessor<A> {

	public static final Pattern ELEMENT_PATTERN = Pattern.compile("(?:<el>)([\\s\\S]*)(?:<\\/el>)");

	@Override
	public void handle(Set<? extends Element> elements, RoundEnvironment environment) {
		copy((reader, writer) -> {
			String content = reader.lines()
					.collect(Collectors.joining(System.lineSeparator()));

			var matcher = ELEMENT_PATTERN.matcher(
					replaceVariables(content, commonVariables())
			);

			var buffer = new StringBuffer();

			while (matcher.find()) {
				var builder = new StringBuilder();

				for (Element element : elements) {
					builder.append(
							replaceVariables(
									matcher.group(1),
									values(element)
							)
					).append(System.lineSeparator());

					matcher.appendReplacement(buffer, builder.toString());
				}
			}

			matcher.appendTail(buffer);
			writer.println(buffer);
		});
	}
}
