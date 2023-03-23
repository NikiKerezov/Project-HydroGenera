package startup;
import DependancyContainer.Models.ServiceContainer;
import EventEmitter.EventEmitter;
import UpTime.Services.*;

public class Startup {

    public static void main(String[] args) throws Exception {

        ServiceContainer container = ServiceContainer.container();
        EventEmitter.getInstance().attach(container.getFileStorage());
       // EventEmitter.getInstance().attach(container.getDataRiver());
        EventEmitter.getInstance().attach(container.getOnSite());

        Thread server = new Thread(() -> {
            try {
               container.getOnSite().startServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Thread client = new Thread(() -> {
            try {
                container.getDataRiver().connect();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Thread writeTime = new Thread(){
            public void run(){
                try {
                    while(true){
                        Thread.sleep(1000);
                        UpTime.getInstance().WriteTimeToFile();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                   }
              }
        };

        writeTime.start();

        server.start();
        //client.start();
        container.getSerialPort().ReadAndProcess();

    }
}
