/**
 * Veículo elétrico com nível de bateria.
 */
public abstract class VeiculoEletrico extends Veiculo {
    /** Percentagem de bateria atual. */
    private double nivelBateria;

    /**
     * Cria um veículo elétrico.
     * @param id identificador
     * @param localizacao localização atual
     * @param nivelBateria percentagem de bateria
     */
    public VeiculoEletrico(int id, String localizacao, double nivelBateria) {
        super(id, localizacao);
        this.nivelBateria = nivelBateria;
    }

    /** @return nível de bateria em percentagem */
    public double getNivelBateria() { 
        return nivelBateria; }
}