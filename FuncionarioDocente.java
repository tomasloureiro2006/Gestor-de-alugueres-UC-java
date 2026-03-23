import java.util.List;
import java.util.ArrayList;

/**
 * Funcionário docente sem desconto adicional.
 */
public class FuncionarioDocente extends Funcionario {
    /** Lista de faculdades onde leciona. */
    private List<String> faculdades;

    /**
     * Cria um docente com várias faculdades.
     * @param numeroMecanografico número mecanográfico
     * @param metodoPagamento método de pagamento
     * @param anoContrato ano de contrato
     * @param faculdades faculdades associadas
     */
    public FuncionarioDocente(String numeroMecanografico, String metodoPagamento, int anoContrato, List<String> faculdades) {
        super(numeroMecanografico, metodoPagamento, anoContrato);
        this.faculdades = faculdades;
    }

    /**
     * Cria um docente com uma faculdade.
     * @param numeroMecanografico número mecanográfico
     * @param metodoPagamento método de pagamento
     * @param anoContrato ano de contrato
     * @param faculdade faculdade associada
     */
    public FuncionarioDocente(String numeroMecanografico, String metodoPagamento, int anoContrato, String faculdade) {
        super(numeroMecanografico, metodoPagamento, anoContrato);
        this.faculdades = new ArrayList<>();
        this.faculdades.add(faculdade);
    }

    @Override
    public double calcularDesconto(double custoBase) {
        return custoBase;
    }

    @Override
    public String getTipo() {
        return "DOCENTE";
    }

    @Override
    public String toString() {
        return "FuncionarioDocente [Nº: " + getNumeroMecanografico() + ", Ano: " + getAnoContrato() + "]";
    }
}