import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Lê e grava utilizadores, veículos e alugueres.
 */
public class GestorDados {

    /**
     * Lê utilizadores de um ficheiro texto.
     * @param nomeFicheiro caminho para o ficheiro
     * @return lista lida ou vazia em erro
     */
    public static List<Utilizador> carregarUtilizadores(String nomeFicheiro) {
        List<Utilizador> utilizadores = new ArrayList<>();
        try {
            List<String> linhas = Files.readAllLines(Paths.get(nomeFicheiro));
            for (String linha : linhas) {
                if (linha.trim().isEmpty()) {
                    continue;
                }
                String[] caracteristicas = linha.split(";");
                String tipo = caracteristicas[0].trim().toUpperCase();
                String numMec = caracteristicas[1].trim();
                String metodoPag = caracteristicas[2].trim();
                switch (tipo) {
                    case "ESTUDANTE":
                        String curso = caracteristicas[3].trim();
                        String polo = caracteristicas[4].trim();
                        utilizadores.add(new Estudante(numMec, metodoPag, curso, polo));
                        break;
                    case "DOCENTE":
                        int anoContratoD = Integer.parseInt(caracteristicas[3].trim());
                        List<String> faculdades = Arrays.asList(caracteristicas[4].split(","));
                        utilizadores.add(new FuncionarioDocente(numMec, metodoPag, anoContratoD, faculdades));
                        break;
                    case "NAO_DOCENTE":
                        int anoContratoND = Integer.parseInt(caracteristicas[3].trim());
                        String servico = caracteristicas[4].trim();
                        utilizadores.add(new FuncionarioNaoDocente(numMec, metodoPag, anoContratoND, servico));
                        break;
                    default:
                        System.err.println("Aviso: Tipo de utilizador desconhecido na linha: " + linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Aviso: Ficheiro " + nomeFicheiro + " não encontrado. A carregar lista vazia.");
        } catch (NumberFormatException e) {
            System.err.println("Erro de formato numérico ao ler utilizadores (Ano Contrato).");
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Erro de formato na linha do utilizador (campos em falta).");
            e.printStackTrace();
        }
        return utilizadores;
    }

    /**
     * Lê veículos de um ficheiro texto.
     * @param nomeFicheiro caminho para o ficheiro
     * @return lista lida ou vazia em erro
     */
    public static List<Veiculo> carregarVeiculos(String nomeFicheiro) {
        List<Veiculo> veiculos = new ArrayList<>();
        try {
            List<String> linhas = Files.readAllLines(Paths.get(nomeFicheiro));
            for (String linha : linhas) {
                if (linha.trim().isEmpty()) {
                    continue;
                }
                String[] caracteristicas = linha.split(";");
                String tipo = caracteristicas[0].trim().toUpperCase();

                try {
                    switch (tipo) {
                        case "BICICLETA": {
                            int id = Integer.parseInt(caracteristicas[1].trim());
                            String localizacao = caracteristicas[2].trim();
                            String tipoParam = caracteristicas[3].trim().toUpperCase().replace(" ", "_");
                            veiculos.add(new Bicicleta(id, localizacao, tipoParam));
                            break;
                        }
                        case "TROTINETE": {
                            int id = Integer.parseInt(caracteristicas[1].trim());
                            String localizacao = caracteristicas[2].trim();
                            double nivelBateria = Double.parseDouble(caracteristicas[3].trim());
                            boolean temLCD = Boolean.parseBoolean(caracteristicas[4].trim());
                            veiculos.add(new Trotinete(id, localizacao, nivelBateria, temLCD));
                            break;
                        }
                        case "EBIKE": {
                            int id = Integer.parseInt(caracteristicas[1].trim());
                            String localizacao = caracteristicas[2].trim();
                            double nivelBateria = Double.parseDouble(caracteristicas[3].trim());
                            String tipoBateria = caracteristicas[4].trim().toUpperCase();
                            veiculos.add(new EBike(id, localizacao, nivelBateria, tipoBateria));
                            break;
                        }
                        default:
                            System.err.println("Aviso: Tipo de veículo desconhecido na linha: " + linha);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Erro de formato numérico na linha (veículos): " + linha);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Erro de formato na linha do veículo (campos em falta): " + linha);
                } catch (IllegalArgumentException e) {
                    System.err.println("Aviso: dados inválidos na linha do veículo: " + linha + " -> " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Aviso: Ficheiro " + nomeFicheiro + " não encontrado. A carregar lista vazia.");
        }
        return veiculos;
    }

    /**
     * Guarda alugueres num ficheiro binário.
     * @param nomeFicheiro caminho do ficheiro
     * @param alugueres lista a gravar
     */
    public static void guardarAlugueres(String nomeFicheiro, List<Aluguer> alugueres) {
        try {
            java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(new java.io.FileOutputStream(nomeFicheiro));
            out.writeObject(alugueres);
            out.close();
        } catch (java.io.IOException e) {
            System.err.println("Erro ao guardar alugueres: " + e.getMessage());
        }
    }

    /**
     * Lê alugueres de um ficheiro binário.
     * @param nomeFicheiro caminho do ficheiro
     * @return lista lida ou vazia
     */
    public static List<Aluguer> carregarAlugueres(String nomeFicheiro) {
        List<Aluguer> alugueres = new ArrayList<>();

        if (!Files.exists(Paths.get(nomeFicheiro))) {
            return alugueres;
        }

        try (java.io.ObjectInputStream in = new java.io.ObjectInputStream(new java.io.FileInputStream(nomeFicheiro))) {
            Object obj = in.readObject();
            alugueres = (List<Aluguer>) obj;
        } catch (java.io.IOException e) {
            System.err.println("Erro de IO ao carregar alugueres: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Classe não encontrada durante a desserialização.");
            e.printStackTrace();
        } catch (ClassCastException e) {
            System.err.println("Erro de formato: O objeto lido não é uma Lista de Alugueres.");
            e.printStackTrace();
        }

        return alugueres;
    }
}
