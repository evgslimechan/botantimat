package ru.mgkit.antimat;


import ru.mgkit.antimat.server.Server;
import ru.mgkit.antimat.util.ConfigUtil;
import ru.mgkit.antimat.word.BadwordManager;

import java.io.IOException;

public class Main {

    private static final String CONFIG_NAME = "config.properties";

    public static void main(String[] args) throws IOException {
        ConfigUtil.loadDefaultConfigFile(CONFIG_NAME);
        ConfigUtil.loadDictionary(
                ConfigUtil.getConfigValue(CONFIG_NAME, ConfigUtil.ConfigParam.DICTIONARY_FILE),
                ConfigUtil.getConfigValue(CONFIG_NAME, ConfigUtil.ConfigParam.DICTIONARY_DELIM));
        Server s = new Server(Integer.parseInt(ConfigUtil.getConfigValue(CONFIG_NAME, ConfigUtil.ConfigParam.PORT)));
        s.startServer();
    }

}
