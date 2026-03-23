/**
 * Base comum para funcionários docentes e não docentes.
 */
public abstract class Funcionario extends Utilizador {
    /** Ano de início do contrato. */
    private int anoContrato;

    /**
     * Cria um funcionário.
     * @param numeroMecanografico número mecanográfico
     * @param metodoPagamento método de pagamento
     * @param anoContrato ano de início do contrato
     */
    public Funcionario(String numeroMecanografico, String metodoPagamento, int anoContrato) {
        super(numeroMecanografico, metodoPagamento);
        this.anoContrato = anoContrato;
    }

    /** @return ano de contrato */
    public int getAnoContrato() { 
        return anoContrato; }

    @Override
    public abstract double calcularDesconto(double custoBase);

    @Override
    public abstract String getTipo();
}