/**
 * Utilizador estudante, sem desconto extra.
 */
public class Estudante extends Utilizador {
    /** Curso em que está matriculado. */
    private String curso;
    /** Pólo universitário mais frequente. */
    private String polo;

    /**
     * Cria um estudante com curso e polo.
     * @param numeroMecanografico número mecanográfico.
     * @param metodoPagamento método de pagamento.
     * @param curso curso frequentado.
     * @param polo polo académico.
     */
    public Estudante(String numeroMecanografico, String metodoPagamento, String curso, String polo) {
        super(numeroMecanografico, metodoPagamento);
        this.curso = curso;
        this.polo = polo;
    }

    @Override
    public double calcularDesconto(double custoBase) {
        return custoBase;
    }

    @Override
    public String getTipo() {
        return "ESTUDANTE";
    }

    @Override
    public String toString() {
        return "Estudante [Nº: " + getNumeroMecanografico() + ", Curso: " + curso + ", Polo: " + polo + "]";
    }
}