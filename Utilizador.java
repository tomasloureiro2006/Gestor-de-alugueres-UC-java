import java.io.Serializable;

/**
 * Base para qualquer utilizador da aplicação.
 */
public abstract class Utilizador implements Serializable {
    /** Número mecanográfico do utilizador. */
    private String numeroMecanografico;
    /** Método de pagamento preferido. */
    private String metodoPagamento;

    /**
     * Cria um utilizador genérico.
     * @param numeroMecanografico número mecanográfico
     * @param metodoPagamento método de pagamento
     */
    public Utilizador(String numeroMecanografico, String metodoPagamento) {
        this.numeroMecanografico = numeroMecanografico;
        this.metodoPagamento = metodoPagamento;
    }

    /** @return número mecanográfico */
    public String getNumeroMecanografico() {
        return numeroMecanografico;
    }

    /**
     * Aplica o desconto deste tipo de utilizador.
     * @param custoBase valor antes do desconto
     * @return valor depois do desconto
     */
    public abstract double calcularDesconto(double custoBase);

    /** @return tipo textual do utilizador */
    public abstract String getTipo();

    @Override
    public String toString() {
        return "Utilizador [Nº: " + numeroMecanografico + ", Pagamento: " + metodoPagamento + "]";
    }
}