package DependancyContainer.Models;

public class CommunicationProtocol {
    private String circuit;
    private String visualisation;
    private String server;

    public String getCircuit() {
        return circuit;
    }

    public void setCircuit(String circuit) {
        this.circuit = circuit;
    }

    public String getVisualisation() {
        return visualisation;
    }

    public void setVisualisation(String visualisation) {
        this.visualisation = visualisation;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
