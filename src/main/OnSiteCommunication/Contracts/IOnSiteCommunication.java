package OnSiteCommunication.Contracts;

import EventEmitter.Contracts.IObserver;
import LocalData.Models.DataPackage;
import org.json.JSONObject;

import java.io.IOException;

public interface IOnSiteCommunication extends IObserver {
    void sendPackage(DataPackage dataPackage) throws IOException;
    void startServer();
    void receiveJson(JSONObject data);
}
