package com.github.diegonighty.winter;

import com.github.diegonighty.winter.configuration.Configuration;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@Configuration(name = "config")
public record ConfigurationTest(ConfigurationTestPath test) {

	@ConfigSerializable
		public record ConfigurationTestPath(
				@Comment("This is a test") boolean enabled
	) {

	}

}
