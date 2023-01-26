package DependancyContainer.Contracts;

import DependancyContainer.Services.Data;

import java.io.IOException;

public interface IJsonReader {
    Data readJson(String filePath) throws IOException;
}
