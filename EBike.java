/**
 * Bicicleta elétrica com bateria fixa ou removível.
 */
public class EBike extends VeiculoEletrico {
    /** Tipo de bateria (FIXA ou REMOVIVEL). */
    private String tipoBateria;

    /** Constante para bateria fixa. */
    public static final String FIXA = "FIXA";
    /** Constante para bateria removível. */
    public static final String REMOVIVEL = "REMOVIVEL";

    /**
     * Cria uma e-bike.
     * @param id identificador
     * @param localizacao localização atual
     * @param nivelBateria percentagem de bateria
     * @param tipoBateria FIXA ou REMOVIVEL
     */
    public EBike(int id, String localizacao, double nivelBateria, String tipoBateria) {
        super(id, localizacao, nivelBateria);
        this.tipoBateria = tipoBateria;
    }

    @Override
    public double calcularCusto(double tempoHoras, String tipoUtilizador) {
        double custoPorHora;
        if (tipoUtilizador.equalsIgnoreCase("ESTUDANTE")) {
            custoPorHora = tipoBateria.equals(FIXA) ? 1.25 : 1.50;
        } else {
            custoPorHora = tipoBateria.equals(FIXA) ? 2.75 : 3.00;
        }
        return calcularCustoComDiarias(tempoHoras, custoPorHora);
    }

    @Override
    public String getTipoVeiculo() { return "EBIKE"; }

    @Override
    public String toString() {
        return "EBike [ID: " + getId() + ", Bateria: " + tipoBateria + ", Nível: " + getNivelBateria() + "%]";
    }
}