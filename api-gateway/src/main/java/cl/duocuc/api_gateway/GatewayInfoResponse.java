package cl.duocuc.api_gateway;

import java.util.List;

public class GatewayInfoResponse {
    private String sistema;
    private String gateway;
    private String estado;
    private String version;
    private List<String> rutasDisponibles;
    private String ambiente;

    public GatewayInfoResponse() {
    }

    public GatewayInfoResponse(String sistema, String gateway, String estado, String version,
                               List<String> rutasDisponibles, String ambiente) {
        this.sistema = sistema;
        this.gateway = gateway;
        this.estado = estado;
        this.version = version;
        this.rutasDisponibles = rutasDisponibles;
        this.ambiente = ambiente;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getRutasDisponibles() {
        return rutasDisponibles;
    }

    public void setRutasDisponibles(List<String> rutasDisponibles) {
        this.rutasDisponibles = rutasDisponibles;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }
}
