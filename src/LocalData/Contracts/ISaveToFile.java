package LocalData.Contracts;

import LocalData.Models.DataPackage;

public interface ISaveToFile {
    public void saveDataPackageToFile(DataPackage dataPackage);
}
