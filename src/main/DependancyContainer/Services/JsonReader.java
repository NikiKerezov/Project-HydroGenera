package DependancyContainer.Services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import DependancyContainer.Contracts.IJsonReader;
import DependancyContainer.Models.Setting;
import Logger.Contracts.ILogger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;


public class JsonReader implements IJsonReader {
    private ObjectMapper mapper;
    private ILogger logger;

    public JsonReader() {
        mapper = new ObjectMapper();
    }

    @Override
    public Setting readJson(String filePath) throws IOException {
        File jsonFile = new File(filePath);
        return mapper.readValue(jsonFile, Setting.class);
    }
}
