package com.github.diegonighty.winter;

import com.github.diegonighty.winter.configuration.Configuration;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@Configuration(name = "config")
public class ConfigurationTest {

	private ConfigurationTestPath test;

	public ConfigurationTestPath test() {
		return test;
	}

	@ConfigSerializable
	public static class ConfigurationTestPath {

		@Comment("This is a test")
		private boolean enabled = true;

		public boolean isEnabled() {
			return enabled;
		}
	}

}
