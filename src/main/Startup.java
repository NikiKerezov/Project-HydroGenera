import DependancyContainer.Models.ServiceContainer;
import DependancyContainer.Services.DependencyContainerService;
import EventEmitter.EventEmitter;
import StopWatch.Services.*;

public class Startup {

    public static void main(String[] args) throws Exception {

        ServiceContainer container = ServiceContainer.container();

        EventEmitter.getInstance().attach(container.getFileStorage());
        EventEmitter.getInstance().attach(container.getDataRiver());
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

//        StopWatch overallTime = new StopWatch();
//        overallTime.start();
//
//        StopWatch elapsedTime = new StopWatch();
//        elapsedTime.start();

//        Thread writeTime = new Thread(){
//            public void run(){
//                try {
//                    while(true){
//                        Thread.sleep(1000);
//                        WriteElapsedTimeToFile.getInstance().WriteTimeToFile(elapsedTime, overallTime);
//                    }
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        };

//        writeTime.start();

        server.start();
        //client.start();
        container.getSerialPort().ReadAndProcess();

    }
}
