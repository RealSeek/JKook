package snw.jkook.config.file;

import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.representer.Representer;
import snw.jkook.JKook;
import snw.jkook.config.Configuration;
import snw.jkook.config.ConfigurationSection;
import snw.jkook.config.InvalidConfigurationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

/**
 * An implementation of {@link Configuration} which saves all files in Yaml.
 * Note that this implementation is not synchronized.
 */
public class YamlConfiguration extends FileConfiguration {
    protected static final String BLANK_CONFIG = "{}\n";
    private final DumperOptions yamlOptions = new DumperOptions();
    private final LoaderOptions loaderOptions = new LoaderOptions();
    private final Yaml yaml = new Yaml(new Constructor(), new Representer(), yamlOptions, loaderOptions);

    @NotNull
    @Override
    public String saveToString() {
        yamlOptions.setIndent(4);
        yamlOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        String dump = yaml.dump(getValues(false));

        if (dump.equals(BLANK_CONFIG)) {
            dump = "";
        }

        return dump;
    }

    @Override
    public void loadFromString(@NotNull String contents) throws InvalidConfigurationException {
        Validate.notNull(contents, "Contents cannot be null");

        Map<?, ?> input;
        try {
            loaderOptions.setMaxAliasesForCollections(Integer.MAX_VALUE);
            input = yaml.load(contents);
        } catch (YAMLException e) {
            throw new InvalidConfigurationException(e);
        } catch (ClassCastException e) {
            throw new InvalidConfigurationException("Top level is not a Map.");
        }

        this.map.clear();

        if (input != null) {
            convertMapsToSections(input, this);
        }
    }

    protected void convertMapsToSections(@NotNull Map<?, ?> input, @NotNull ConfigurationSection section) {
        for (Map.Entry<?, ?> entry : input.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if (value instanceof Map) {
                convertMapsToSections((Map<?, ?>) value, section.createSection(key));
            } else {
                section.set(key, value);
            }
        }
    }

    /**
     * Creates a new {@link YamlConfiguration}, loading from the given file.
     * <p>
     * Any errors loading the Configuration will be logged and then ignored.
     * If the specified input is not a valid config, a blank config will be
     * returned.
     * <p>
     * The encoding used may follow the system dependent default.
     *
     * @param file Input file
     * @return Resulting configuration
     * @throws IllegalArgumentException Thrown if file is null
     */
    @NotNull
    public static YamlConfiguration loadConfiguration(@NotNull File file) {
        Validate.notNull(file, "File cannot be null");

        YamlConfiguration config = new YamlConfiguration();

        try {
            config.load(file);
        } catch (FileNotFoundException ignored) {
        } catch (IOException | InvalidConfigurationException e) {
            JKook.getLogger().error("Cannot load " + file, e);
        }

        return config;
    }

    /**
     * Creates a new {@link YamlConfiguration}, loading from the given reader.
     * <p>
     * Any errors loading the Configuration will be logged and then ignored.
     * If the specified input is not a valid config, a blank config will be
     * returned.
     *
     * @param reader input
     * @return resulting configuration
     * @throws IllegalArgumentException Thrown if stream is null
     */
    @NotNull
    public static YamlConfiguration loadConfiguration(@NotNull Reader reader) {
        Validate.notNull(reader, "Stream cannot be null");

        YamlConfiguration config = new YamlConfiguration();

        try {
            config.load(reader);
        } catch (IOException | InvalidConfigurationException e) {
            JKook.getLogger().error("Cannot load configuration from stream", e);
        }

        return config;
    }
}
