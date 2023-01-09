package com.github.diegonighty.winter.processor.template.listed;

import com.github.diegonighty.winter.processor.AnnotationProcessor;
import com.github.diegonighty.winter.processor.template.TemplateVar;

import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;
import java.util.List;

public interface ListedAnnotationProcessor<A extends Annotation> extends AnnotationProcessor<A> {

	List<TemplateVar> values(Element element);

}
