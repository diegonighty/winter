package com.github.diegonighty.winter;

import com.github.diegonighty.winter.plugin.DataFolder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.bukkit.plugin.Plugin;

import java.nio.file.Path;

public class BasicModule extends AbstractModule {

	@Provides @DataFolder
	public Path provideDataFolder(Plugin plugin) {
		return Path.of(plugin.getDataFolder().getAbsolutePath());
	}

}
