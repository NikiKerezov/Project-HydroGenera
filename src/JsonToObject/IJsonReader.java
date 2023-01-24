package JsonToObject;

import java.io.IOException;

public interface IJsonReader {
    Data readJson(String filePath) throws IOException;
}
