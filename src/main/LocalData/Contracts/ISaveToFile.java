package LocalData.Contracts;

import LocalData.Models.DataPackage;

public interface ISaveToFile {
    public void SaveToCsv(DataPackage dataPackage);
}
