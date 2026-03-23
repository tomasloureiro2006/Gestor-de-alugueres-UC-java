/**
 * Funcionário não docente com desconto de 50%.
 */
public class FuncionarioNaoDocente extends Funcionario {
    /** Serviço onde trabalha. */
    private String servico;

    /**
     * Cria um não docente.
     * @param numeroMecanografico número mecanográfico
     * @param metodoPagamento método de pagamento
     * @param anoContrato ano de contrato
     * @param servico serviço onde trabalha
     */
    public FuncionarioNaoDocente(String numeroMecanografico, String metodoPagamento, int anoContrato, String servico) {
        super(numeroMecanografico, metodoPagamento, anoContrato);
        this.servico = servico;
    }

    @Override
    public double calcularDesconto(double custoBase) {
        return custoBase * 0.5;
    }

    @Override
    public String getTipo() {
        return "NAO_DOCENTE";
    }

    @Override
    public String toString() {
        return "FuncionarioNaoDocente [Nº: " + getNumeroMecanografico() + ", Serviço: " + servico + "]";
    }
}