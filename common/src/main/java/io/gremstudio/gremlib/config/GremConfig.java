package io.gremstudio.gremlib.config;

import io.gremstudio.gremlib.Gremlib;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

// GremConfig is based on https://github.com/khazoda-mods/khazodacore/blob/26.1/common/src/main/java/com/khazoda/core/config/KhazConfig.java
// Explicit permission to use this has been granted.
public class GremConfig {
    private final String modName;
    private final String modId;
    private final Path file;
    private final List<Entry<?>> entries;
    private final Map<Entry<?>, Object> values = new LinkedHashMap<>();
    private final Map<Entry<?>, Object> serverSyncedValues = new LinkedHashMap<>();
    private boolean loaded;

    private GremConfig(String modName, String modId, Path configDirectory, List<Entry<?>> entries) {
        this.modName = modName;
        this.modId = modId;
        this.file = configDirectory.resolve(this.modId + ".properties");
        this.entries = List.copyOf(entries);
        GremConfigHelper.validateEntries(this.modId, this.entries);
    }

    public static GremConfig of(String modName, String modId, Path configDirectory, Entry<?>... entries) {
        return new GremConfig(modName, modId, configDirectory, List.of(entries));
    }

    public static Entry<Boolean> bool(String key, boolean defaultValue, String comment) {
        return GremConfigHelper.createBooleanEntry(key, defaultValue, comment);
    }

    public static Entry<Integer> integer(String key, int defaultValue, int min, int max, String comment) {
        return GremConfigHelper.createIntegerEntry(key, defaultValue, min, max, comment);
    }

    public static Entry<Double> decimal(String key, double defaultValue, double min, double max, String comment) {
        return GremConfigHelper.createDecimalEntry(key, defaultValue, min, max, comment);
    }

    public static Entry<String> string(String key, String defaultValue, String comment) {
        return GremConfigHelper.createStringEntry(key, defaultValue, comment);
    }

    public synchronized void load() {
        if (loaded) return;

        Properties properties = new Properties();
        if (Files.exists(file)) {
            try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                properties.load(reader);
            } catch (IOException e) {
                Gremlib.INSTANCE.getLogger().warn("Failed to read config file {}: {}", file, e.getMessage());
            }
        }

        boolean changed = !Files.exists(file);
        values.clear();
        for (Entry<?> entry : entries) {
            Object value = GremConfigHelper.readValue(entry, properties.getProperty(entry.key()));
            values.put(entry, value);
            String serialized = GremConfigHelper.formatValue(entry, value);
            if (!Objects.equals(properties.getProperty(entry.key()), serialized)) {
                changed = true;
            }
        }

        loaded = true;
        if (changed) {
            write();
        }
    }

    // Re-loads the config file into memory.
    public synchronized void reload() {
        loaded = false;
        load();
    }

    public synchronized <T> T get(Entry<T> entry) {
        load();
        @SuppressWarnings("unchecked") T value = (T) serverSyncedValues.getOrDefault(entry, values.getOrDefault(entry, entry.defaultValue()));
        return value;
    }

    public synchronized Map<String, String> createServerSyncSnapshot() {
        load();

        Map<String, String> snapshot = new LinkedHashMap<>();
        for (Entry<?> entry : entries) {
            if (entry.serverSynced()) {
                snapshot.put(entry.key(), GremConfigHelper.formatValue(entry, values.getOrDefault(entry, entry.defaultValue())));
            }
        }
        return snapshot;
    }

    public synchronized void applyServerSyncedValues(Map<String, String> serializedValues) {
        load();
        serverSyncedValues.clear();
        for (Entry<?> entry : entries) {
            if (entry.serverSynced() && serializedValues.containsKey(entry.key())) {
                serverSyncedValues.put(entry, GremConfigHelper.readValue(entry, serializedValues.get(entry.key())));
            }
        }
    }

    public synchronized void clearServerSyncedValuesAndReload() {
        serverSyncedValues.clear();
        reload();
    }

    private synchronized void write() {
        try {
            Files.createDirectories(file.getParent());
            Files.writeString(file, render(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            Gremlib.INSTANCE.getLogger().warn("Failed to write config file {}: {}", file, e.getMessage());
        }
    }

    private String render() {
        StringBuilder builder = new StringBuilder();
        builder.append("# ").append(GremConfigHelper.formatConfigTitle(modName, modId)).append('\n').append('\n');

        for (Entry<?> entry : entries) {
            if (!entry.comment().isBlank()) {
                for (String line : GremConfigHelper.splitCommentLines(entry.comment())) {
                    builder.append("# ").append(line).append('\n');
                }
            }
            builder.append(entry.key())
                    .append('=')
                    .append(GremConfigHelper.formatValue(entry, values.getOrDefault(entry, entry.defaultValue())))
                    .append('\n')
                    .append('\n');
        }
        return builder.toString();
    }

    public interface ValueAdapter<T> {
        T parse(String raw, T fallback);

        String format(T value);
    }

    public record Entry<T>(String key, T defaultValue, String comment, ValueAdapter<T> adapter, boolean serverSynced) {
        public Entry(String key, T defaultValue, String comment, ValueAdapter<T> adapter) {
            this(key, defaultValue, comment, adapter, true);
        }

        public Entry {
            Objects.requireNonNull(key, "key");
            Objects.requireNonNull(defaultValue, "defaultValue");
            Objects.requireNonNull(comment, "comment");
            Objects.requireNonNull(adapter, "adapter");
        }

        /**
         * Use this for client-only settings that should ignore server sync.
         */
        public Entry<T> localOnly() {
            return new Entry<>(key, defaultValue, comment, adapter, false);
        }
    }
}
