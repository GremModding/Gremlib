package io.gremstudio.gremlib.config.v2;

import java.util.HashMap;
import java.util.Map;

public class GremModConfig {
    private final String configVersion;
    public Map<String, ConfigMember> memberMap = new HashMap<>();

    public GremModConfig(String configVersion) {
        this.configVersion = configVersion;
    }

    public String getConfigVersion() {
        return configVersion;
    }
}

