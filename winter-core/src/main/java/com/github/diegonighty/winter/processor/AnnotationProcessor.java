package com.github.diegonighty.winter.processor;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;
import java.util.Set;

public interface AnnotationProcessor<A extends Annotation> extends Processor {

	String getPackage();

	Set<Class<? extends A>> getAnnotations();

	void handle(Set<? extends Element> elements, RoundEnvironment environment);

	void handle(Element element, RoundEnvironment environment);

}
