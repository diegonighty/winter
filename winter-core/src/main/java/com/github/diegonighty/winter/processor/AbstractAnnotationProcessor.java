package com.github.diegonighty.winter.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public abstract class AbstractAnnotationProcessor<A extends Annotation>
		extends AbstractProcessor
		implements AnnotationProcessor<A> {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if (annotations.isEmpty() || roundEnv.errorRaised() || roundEnv.processingOver()) {
			return false;
		}

		for (var annotation : getAnnotations()) {
			handle(
					roundEnv.getElementsAnnotatedWith(annotation),
					roundEnv
			);
		}

		return true;
	}

	@Override
	public void handle(Set<? extends Element> elements, RoundEnvironment environment) {
		for (Element element : elements) {
			handle(element, environment);
		}
	}

	@Override
	public void handle(Element element, RoundEnvironment environment) {
		// Override this method to handle the single element
	}

	protected boolean isMethod(Element element) {
		return is(element, ElementKind.METHOD);
	}

	protected boolean isField(Element element) {
		return is(element, ElementKind.FIELD);
	}

	protected boolean is(Element element, ElementKind kind) {
		return element.getKind() == kind;
	}

	public boolean is(TypeMirror mirror, Class<?> clazz) {
		return is(mirror, fromClass(clazz));
	}

	public boolean is(TypeMirror mirror, TypeMirror toEqual) {
		return mirror.toString().equals(toEqual.toString());
	}

	public boolean isWithSameWildcards(TypeMirror mirror, TypeMirror clazz) {
		var returnType = (DeclaredType) mirror;
		var expectedType = (DeclaredType) clazz;

		var expectedWildcards = returnType.getTypeArguments()
				.stream()
				.map(TypeMirror::toString)
				.collect(toSet())
				.containsAll(
						expectedType.getTypeArguments()
								.stream()
								.map(TypeMirror::toString)
								.collect(toSet())
				);

		return is(mirror, clazz) && expectedWildcards;
	}

	protected boolean isChild(TypeMirror interfaceChild, TypeMirror toEqual) {
		var declaredType = (DeclaredType) interfaceChild;
		var element = (TypeElement) declaredType.asElement();

		var toEqualType = (DeclaredType) toEqual;
		var toEqualElement = (TypeElement) toEqualType.asElement();
		var toEqualQualifiedName = toEqualElement.getQualifiedName().toString();

		return element.getInterfaces().stream()
				.map(parentInterface -> (DeclaredType) parentInterface)
				.map(DeclaredType::asElement)
				.map(TypeElement.class::cast)
				.map(TypeElement::getQualifiedName)
				.map(Name::toString)
				.anyMatch(name -> name.equals(toEqualQualifiedName));
	}

	public TypeMirror fromClass(Class<?> clazz) {
		if (clazz.isPrimitive()) {
			String primitiveName = clazz.getName().toUpperCase();
			TypeKind primitiveKind = TypeKind.valueOf(primitiveName);

			return typeUtils().getPrimitiveType(primitiveKind);
		}

		return elementUtils().getTypeElement(clazz.getCanonicalName()).asType();
	}

	public TypeMirror fromWildcardClass(Class<?> clazz, Class<?>... wildcards) {
		TypeElement element = elementUtils().getTypeElement(clazz.getCanonicalName());

		return typeUtils().getDeclaredType(
				element,
				Arrays.stream(wildcards)
						.map(this::fromClass)
						.toArray(TypeMirror[]::new)
		);
	}

	public TypeMirror fromWildcardClass(Class<?> clazz, TypeMirror... wildcards) {
		TypeElement element = elementUtils().getTypeElement(clazz.getCanonicalName());

		return typeUtils().getDeclaredType(
				element,
				wildcards
		);
	}

	protected Messager messager() {
		return processingEnv.getMessager();
	}

	protected Filer filer() {
		return processingEnv.getFiler();
	}

	protected Elements elementUtils() {
		return processingEnv.getElementUtils();
	}

	protected Types typeUtils() {
		return processingEnv.getTypeUtils();
	}

	protected boolean isAssignable(TypeMirror type, Class<?> clazz) {
		return typeUtils().isAssignable(type, fromClass(clazz));
	}

	protected boolean isAssignableWithoutWildcard(TypeMirror type, Class<?> clazz) {
		var declaredType = (DeclaredType) type;
		var typeName = declaredType.asElement().getSimpleName();

		return typeName.toString().equals(clazz.getSimpleName());
	}

	protected boolean hasMethod(TypeMirror type, String methodName) {
		var declaredType = (DeclaredType) type;
		var element = (TypeElement) declaredType.asElement();

		return element.getEnclosedElements().stream()
				.filter(this::isMethod)
				.map(Element::getSimpleName)
				.map(Name::toString)
				.anyMatch(name -> name.equals(methodName));
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return getAnnotations().stream()
				.map(Class::getName)
				.collect(toSet());
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.RELEASE_17;
	}

}
