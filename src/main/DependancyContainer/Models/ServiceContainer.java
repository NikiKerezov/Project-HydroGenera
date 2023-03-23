package DependancyContainer.Models;

import DataRiverCommunication.Contracts.IDataRiverCommunication;
import DependancyContainer.Services.DependencyContainerService;
import LocalData.Contracts.ISaveToFile;
import Logger.Contracts.ILogger;
import OnSiteCommunication.Contracts.IOnSiteCommunication;
import Processor.Contracts.IReadFromSerialPort;
import UpTime.Contracts.IUpTime;
import UpTime.Services.UpTime;

public class ServiceContainer {
    private static ServiceContainer container;
    private IDataRiverCommunication dataRiver;
    private IReadFromSerialPort serialPort;
    private IOnSiteCommunication onSite;
    private ILogger logger;
    private IUpTime upTime;

    public static ServiceContainer container() {
        if(container == null){
            try {
                DependencyContainerService service = new DependencyContainerService();
                container = service.populateContainer();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return container;
    }
    public ISaveToFile getFileStorage() {
        return fileStorage;
    }

    public void setFileStorage(ISaveToFile fileStorage) {
        this.fileStorage = fileStorage;
    }

    private ISaveToFile fileStorage;

    public ILogger getLogger() {
        return logger;
    }

    public void setLogger(ILogger logger) {
        this.logger = logger;
    }

    public IDataRiverCommunication getDataRiver() {
        return dataRiver;
    }

    public void setDataRiver(IDataRiverCommunication dataRiver) {
        this.dataRiver = dataRiver;
    }

    public IReadFromSerialPort getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(IReadFromSerialPort serialPort) {
        this.serialPort = serialPort;
    }

    public IOnSiteCommunication getOnSite() {
        return onSite;
    }

    public void setOnSite(IOnSiteCommunication onSite) {
        this.onSite = onSite;
    }

    public static ServiceContainer getContainer() {
        return container;
    }

    public static void setContainer(ServiceContainer container) {
        ServiceContainer.container = container;
    }

    public IUpTime getUpTime() {
        return upTime;
    }

    public void setUpTime(IUpTime upTime) {
        this.upTime = upTime;
    }
}
