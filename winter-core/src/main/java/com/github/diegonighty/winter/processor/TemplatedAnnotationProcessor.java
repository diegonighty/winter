package com.github.diegonighty.winter.processor;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.function.BiConsumer;

public interface TemplatedAnnotationProcessor<A extends Annotation> extends AnnotationProcessor<A> {

	String getTemplate();

	String getNewName();

	void copy(
			BiConsumer<BufferedReader, PrintWriter> actions
	);


}
