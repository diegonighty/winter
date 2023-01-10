package <package_name>;

import com.github.diegonighty.winter.plugin.DataFolder;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.nio.file.Path;
import java.io.IOException;

public class <class_name> extends AbstractModule {

	// Auto generated code //
<el>
	@Provides @Singleton
	public <el_type> provide<el_name>(@DataFolder Path folder) {
		try {
			return ConfigurationHelper.load(folder, "<el_name>").get(<el_type>.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
</el>
	// End of auto generated code //

}
