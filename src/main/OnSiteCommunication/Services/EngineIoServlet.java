package OnSiteCommunication.Services;

import io.socket.engineio.server.Emitter;
import io.socket.engineio.server.EngineIoServer;
import io.socket.engineio.server.EngineIoSocket;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/engine.io/*")
public class EngineIoServlet extends HttpServlet {

    private final EngineIoServer mEngineIoServer = new EngineIoServer();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        mEngineIoServer.handleRequest(request, response);
    }

    public EngineIoServlet() {
        // Register a connection listener
        mEngineIoServer.on("connection", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Connected");
                EngineIoSocket socket = (EngineIoSocket) args[0];
                // Do something with socket
            }
        });
    }
}
