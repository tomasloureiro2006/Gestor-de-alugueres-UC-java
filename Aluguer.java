import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Registo de um aluguer com utilizador, veículo, datas e serviços extra.
 */
public class Aluguer implements Serializable {
    /** Utilizador que realiza o aluguer. */
    private Utilizador utilizador;
    /** Veículo alugado. */
    private Veiculo veiculo;
    /** Data/hora de início do aluguer. */
    private LocalDateTime dataHoraInicio;
    /** Data/hora de fim do aluguer. */
    private LocalDateTime dataHoraFim;
    /** Lista de serviços adicionais associados. */
    private List<ServicoAdicional> servicosAdicionais;

    /**
     * Cria um aluguer com utilizador, veículo e intervalo de tempo.
     * @param utilizador quem aluga
     * @param veiculo veículo escolhido
     * @param dataHoraInicio início
     * @param dataHoraFim fim
     */
    public Aluguer(Utilizador utilizador, Veiculo veiculo, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {
        this.utilizador = utilizador;
        this.veiculo = veiculo;
        this.dataHoraInicio = dataHoraInicio;
        this.servicosAdicionais = new ArrayList<>();
        this.dataHoraFim = dataHoraFim;
    }

    /** @return utilizador associado a este aluguer */
    public Utilizador getUtilizador() {
        return utilizador;
    }

    /** @return veículo associado a este aluguer */
    public Veiculo getVeiculo() {
        return veiculo;
    }

    /** @return instante de início do aluguer */
    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    /** @return instante de fim do aluguer */
    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    /** @return lista de serviços adicionais associados */
    public List<ServicoAdicional> getServicosAdicionais() {
        return servicosAdicionais;
    }

    /**
     * Adiciona um serviço ao aluguer.
     * @param servico serviço a incluir
     */
    public void adicionarServico(ServicoAdicional servico) {
        servicosAdicionais.add(servico);
    }

    /**
     * Duração em horas completas.
     * @return horas decorridas
     */
    public double calcularDuracaoHoras() {
        return java.time.Duration.between(dataHoraInicio, dataHoraFim).toHours();
    }

    /**
     * Custo final com veículo, extras e desconto do utilizador.
     * @return valor a pagar
     */
    public double calcularCustoTotal() {
        double duracaoHoras = calcularDuracaoHoras();
        double custoVeiculo = veiculo.calcularCusto(duracaoHoras, utilizador.getTipo());
        double custoServicos = 0;
        double dias = Math.ceil(duracaoHoras / 24);
        for (ServicoAdicional servico : servicosAdicionais) {
            custoServicos += servico.calcularCusto(dias);
        }
        double custoTotal = custoVeiculo + custoServicos;
        return utilizador.calcularDesconto(custoTotal);
    }

    @Override
    public String toString() {
        return "Aluguer [User: " + utilizador.getNumeroMecanografico() + ", Veiculo: " + veiculo.getId() + ", Custo: " + calcularCustoTotal() + " euros]";
    }
}