import java.io.Serializable;

/**
 * Base para qualquer veículo da aplicação.
 */
public abstract class Veiculo implements Serializable {
    /** Identificador único do veículo. */
    private int id;
    /** Localização atual do veículo. */
    private String localizacao;

    /**
     * Cria um veículo.
     * @param id identificador
     * @param localizacao localização atual
     */
    public Veiculo(int id, String localizacao) {
        this.id = id;
        this.localizacao = localizacao;
    }

    /** @return identificador do veículo */
    public int getId() { return this.id; }

    /** @return localização atual do veículo */
    public String getLocalizacao() {
        return localizacao;
    }

    /**
     * Calcula custo para a duração e tipo de utilizador.
     * @param tempoHoras horas de aluguer
     * @param tipoUtilizador tipo de utilizador
     * @return custo calculado para este veículo
     */
    public abstract double calcularCusto(double tempoHoras, String tipoUtilizador);

    /**
     * Nome simples do tipo de veículo (ex.: Bicicleta, Trotinete, EBike).
     * @return tipo textual do veículo
     */
    public abstract String getTipoVeiculo();

    /**
     * Regra de diárias: cada 24h valem 8h.
     * @param tempoHoras duração em horas
     * @param custoPorHora tarifa por hora
     * @return custo ajustado
     */
    protected double calcularCustoComDiarias(double tempoHoras, double custoPorHora) {
        if (tempoHoras <= 24) {
            return tempoHoras * custoPorHora;
        }
        double diasCompletos = Math.floor(tempoHoras / 24);
        double horasRestantes = tempoHoras % 24;
        double custoDias = diasCompletos * (8 * custoPorHora);
        double custoRestante = horasRestantes * custoPorHora;
        return custoDias + custoRestante;
    }
}
