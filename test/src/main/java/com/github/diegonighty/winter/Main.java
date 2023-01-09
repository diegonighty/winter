package com.github.diegonighty.winter;

import com.github.diegonighty.winter.configuration.ConfigurationManifest;
import com.github.diegonighty.winter.plugin.DataFolder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;

public class Main extends JavaPlugin {

	private @Inject ConfigurationTest test;

	@Override
	public void onEnable() {
		Guice.createInjector((binder) -> {
			binder.bind(JavaPlugin.class).toInstance(this);

			binder.bind(Path.class)
					.annotatedWith(DataFolder.class)
					.toInstance(Path.of(this.getDataFolder().getAbsolutePath()));

		}, new ConfigurationManifest()).injectMembers(this);

		System.out.println(test.test().isEnabled());
	}
}
