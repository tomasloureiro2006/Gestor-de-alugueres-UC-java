/**
 * Bicicleta com preços diferentes para 1 ou 2 pessoas.
 */
public class Bicicleta extends Veiculo {
    /** Constante para bicicleta individual. */
    private static String individual = "INDIVIDUAL";
    /** Constante para bicicleta de duas pessoas. */
    private static String duasPessoas = "DUAS_PESSOAS";

    /** Tipo de aluguer (individual ou duas pessoas). */
    private String tipo;

    /** @return constante para bicicleta individual */
    public static String getIndividual() { 
        return individual; }

    /** @return constante para bicicleta de duas pessoas */
    public static String getDuasPessoas() { 
        return duasPessoas; }

    /**
     * Cria uma bicicleta com tipo definido.
     * @param id identificador
     * @param localizacao localização atual
     * @param tipo INDIVIDUAL ou DUAS_PESSOAS
     */
    public Bicicleta(int id, String localizacao, String tipo) {
        super(id, localizacao);
        this.tipo = tipo;
    }

    @Override
    public double calcularCusto(double tempoHoras, String tipoUtilizador) {
        double custoPorHora;
        if (tipoUtilizador.equalsIgnoreCase("ESTUDANTE")) {
            custoPorHora = tipo.equals(getIndividual()) ? 1.00 : 2.00;
        } else {
            custoPorHora = tipo.equals(getIndividual()) ? 2.00 : 3.00;
        }
        return calcularCustoComDiarias(tempoHoras, custoPorHora);
    }

    @Override
    public String getTipoVeiculo() { return "BICICLETA"; }

    @Override
    public String toString() {
        return "Bicicleta [ID: " + getId() + ", Tipo: " + tipo + "]";
    }
}