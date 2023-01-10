package com.github.diegonighty.winter.configuration;

import com.github.diegonighty.winter.io.Writer;
import io.leangen.geantyref.GenericTypeReflector;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;

public interface ConfigurationHelper {

	static CommentedConfigurationNode load(Path folder, String name) throws IOException {
		var fileName = name.endsWith(".yml") ? name : name + ".yml";
		var file = folder.resolve(fileName).toFile();

		if (!file.exists()) Writer.copy(name + ".yml", file);

		return YamlConfigurationLoader.builder()
				.file(file)
				.defaultOptions(ConfigurationHelper.applyOptions())
				.build()
				.load();
	}

	private static ConfigurationOptions applyOptions() {
		return ConfigurationOptions.defaults()
				.serializers(
						TypeSerializerCollection.defaults()
								.childBuilder()
								.register(ConfigurationHelper::isAnnotationPresent, ObjectMapper.factory().asTypeSerializer())
								.build()
				)
				.shouldCopyDefaults(true);
	}

	private static boolean isAnnotationPresent(Type type) {
		return GenericTypeReflector.annotate(type).isAnnotationPresent(Configuration.class);
	}

}
