package DependancyContainer.Contracts;

import DependancyContainer.Models.*;

public interface IData {
    CommunicationProtocol getCommunicationProtocol();
    GeneratorSetting getGeneratorSetting();
    LogSettings getLogSettings();
    LocalDataSettings getLocalDataSettings();
    UartSetting getUartSetting();
    ServerSettings getServerSettings();
}
