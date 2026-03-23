import java.io.Serializable;

/**
 * Serviço adicional associado a um aluguer.
 */
public class ServicoAdicional implements Serializable {
    /** Tipo do serviço (CAPACETE ou LUZ). */
    private String tipo;
    /** Custo diário aplicado ao serviço. */
    private double custoDiario;

    public static final String CAPACETE = "CAPACETE";
    public static final String LUZ = "LUZ";

    /**
     * Cria um serviço adicional.
     * @param tipo CAPACETE ou LUZ
     */
    public ServicoAdicional(String tipo) {
        this.tipo = tipo;
        this.custoDiario = tipo.equals(CAPACETE) ? 5.00 : 2.50;
    }

    /** @return tipo do serviço */
    public String getTipo() { 
        return tipo; }

    /**
     * Custo total para um número de dias (arredonda para cima).
     * @param duracaoDias duração em dias
     * @return custo total
     */
    public double calcularCusto(double duracaoDias) {
        return Math.ceil(duracaoDias) * custoDiario;
    }
}