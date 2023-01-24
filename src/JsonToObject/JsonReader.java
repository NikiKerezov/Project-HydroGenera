package JsonToObject;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonReader implements IJsonReader{
    private ObjectMapper mapper;

    public JsonReader() {
        mapper = new ObjectMapper();
    }

    @Override
    public Data readJson(String filePath) throws IOException {
        File jsonFile = new File(filePath);
        return mapper.readValue(jsonFile, Data.class);
    }
}
