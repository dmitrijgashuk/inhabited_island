package ua.com.javarush.dhashuk.wonderisland.handler.configurator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import ua.com.javarush.dhashuk.wonderisland.factory.CreateAnimalException;
import ua.com.javarush.dhashuk.wonderisland.log.WonderLog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class PropertiesReader {
    private static WonderLog LOG = WonderLog.getInstance();

    private static String READ_JSON_FILE_ERROR = "Unable to read properties file";

    public Map<String, Object> readJsomFileProperties(Path filePath) {
        Map<String, Object> stringObjectMap;
        String jsomString = null;
        try {
            jsomString = Files.readString(filePath);
            ObjectMapper objectMapper = new ObjectMapper();
            stringObjectMap = objectMapper.readValue(jsomString, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            LOG.error("Unable to read properties file " + filePath.toString());
            throw new CreateAnimalException("Unable to read properties file " + filePath.toString(), e.getCause());
        }
        return stringObjectMap;
    }
}

