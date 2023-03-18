package DependancyContainer.Contracts;

import DependancyContainer.Models.Setting;

import java.io.IOException;

public interface IJsonReader {
    Setting readJson(String filePath) throws IOException;
}
