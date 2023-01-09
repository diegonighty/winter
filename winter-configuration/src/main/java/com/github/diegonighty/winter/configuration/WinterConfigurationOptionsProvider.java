package com.github.diegonighty.winter.configuration;

import io.leangen.geantyref.GenericTypeReflector;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

import java.lang.reflect.Type;

public class WinterConfigurationOptionsProvider {

	public static ConfigurationOptions apply() {
		return ConfigurationOptions.defaults()
				.serializers(
						TypeSerializerCollection.defaults()
								.childBuilder()
								.register(WinterConfigurationOptionsProvider::isAnnotationPresent, ObjectMapper.factory().asTypeSerializer())
								.build()
				)
				.shouldCopyDefaults(true);
	}

	static boolean isAnnotationPresent(Type type) {
		return GenericTypeReflector.annotate(type).isAnnotationPresent(Configuration.class);
	}

}
