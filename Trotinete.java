/**
 * Trotinete elétrica com ou sem LCD.
 */
public class Trotinete extends VeiculoEletrico {
    /** Indica se possui LCD. */
    private boolean temLCD;

    /**
     * Cria uma trotinete elétrica.
     * @param id identificador
     * @param localizacao localização atual
     * @param nivelBateria percentagem de bateria
     * @param temLCD se tem LCD
     */
    public Trotinete(int id, String localizacao, double nivelBateria, boolean temLCD) {
        super(id, localizacao, nivelBateria);
        this.temLCD = temLCD;
    }

    @Override
    public double calcularCusto(double tempoHoras, String tipoUtilizador) {
        double custoPorHora;
        if (tipoUtilizador.equalsIgnoreCase("ESTUDANTE")) {
            custoPorHora = temLCD ? 1.10 : 1.00;
        } else {
            custoPorHora = temLCD ? 2.60 : 2.50;
        }
        return calcularCustoComDiarias(tempoHoras, custoPorHora);
    }

    @Override
    public String getTipoVeiculo() { return "TROTINETE"; }

    @Override
    public String toString() {
        return "Trotinete [ID: " + getId() + ", LCD: " + temLCD + ", Bateria: " + getNivelBateria() + "%]";
    }
}