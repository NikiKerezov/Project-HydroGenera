package DependancyContainer.Services;

import java.io.File;
import java.io.IOException;

import DependancyContainer.Contracts.IJsonReader;
import DependancyContainer.Models.Setting;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonReader implements IJsonReader {
    private ObjectMapper mapper;

    public JsonReader() {
        mapper = new ObjectMapper();
    }

    @Override
    public Setting readJson(String filePath) throws IOException {
        File jsonFile = new File(filePath);
        return mapper.readValue(jsonFile, Setting.class);
    }
}
