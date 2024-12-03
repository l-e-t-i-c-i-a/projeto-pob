package regras_negocio;
/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import daodb4o.DAO;
import daodb4o.DAOVeiculo;
import daodb4o.DAOBilhete;

import modelo.Veiculo;
import modelo.Bilhete;


public class Fachada {
	private Fachada() {}

	private static DAOVeiculo daoveiculo = new DAOVeiculo();
	private static DAOBilhete daobilhete = new DAOBilhete();

	public static void inicializar() {
		DAO.open();
	}

	public static void finalizar() {
		DAO.close();
	}
	
	public static Bilhete localizarBilhete(String codigo) throws Exception {
		Bilhete b = daobilhete.read(codigo);
		if (b == null) {
			throw new Exception("bilhete inexistente:" + codigo);
		}
		return b;
	}
	
	public static List<Veiculo> listarVeiculos() {
		List<Veiculo> result = daoveiculo.readAll();
		return result;
	}
	
	public static List<Bilhete> listarBilhetes() {
		List<Bilhete> result = daobilhete.readAll();
		return result;
	}
	
	public static List<Bilhete> listarBilhetes1() {
		List<Bilhete> result = daobilhete.listarBilhetes();
		return result;
	}
	
	public static Veiculo criarVeiculo(String placa) throws Exception {
		DAO.begin();
		Veiculo v = daoveiculo.read(placa);
		if (v != null)
			throw new Exception("Veículo já cadastrado!");
		Veiculo veiculo = new Veiculo(placa);
		daoveiculo.create(veiculo);
		DAO.commit();
		return veiculo;
	}
	
	public static Bilhete criarBilhete(String placa, LocalDateTime dataHoraInicial) throws Exception {
	    try {
	        DAO.begin(); // Início da transação

	        // Verificar se o veículo existe
	        Veiculo veiculo = daoveiculo.read(placa);
	        if (veiculo == null) {
	            throw new Exception("Veículo com a placa '" + placa + "' não encontrado!");
	        }

	        // Verificar se já existe um bilhete ativo (sem data de saída) para este veículo
	        for (Bilhete b : veiculo.getBilhetes()) {
	            if (b.getDataHoraFinal() == null) {
	                throw new Exception("Veículo já possui um bilhete ativo!");
	            }
	        }

	        // Criar o bilhete
	        Bilhete bilhete = new Bilhete(veiculo, dataHoraInicial);

	        // Associar o bilhete ao veículo
	        veiculo.adicionarBilhete(bilhete);

	        // Persistir o bilhete no banco de dados
	        daobilhete.create(bilhete);

	        DAO.commit(); // Finalizar a transação com sucesso
	        return bilhete;
	    } catch (Exception e) {
	        DAO.rollback(); // Reverter em caso de erro
	        throw e; // Repassar a exceção
	    }
	}

	
	public static void registrarEntrada(String placa) throws Exception {
		// Verificar se a garagem já atingiu o limite de 10 veículos
	    long bilhetesAtivos = daobilhete.countAtivos(); // Método que retorna o número de bilhetes ativos
	    if (bilhetesAtivos >= 10) {
	        throw new Exception("Garagem está lotada");
	    }
		
	    DAO.begin();
	    
	    try {
			Veiculo veiculo = daoveiculo.read(placa);
	
		    if (veiculo == null) {
		        // Se o veículo não existe, cria um novo veículo
		        veiculo = criarVeiculo(placa);
		    } else {
		        // Verifica se o veículo já está na garagem (bilhete ativo)
		        for (Bilhete bilhete : veiculo.getBilhetes()) {
		            if (bilhete.getDataHoraFinal() == null) { // Se o bilhete não tem data de saída, o veículo está na garagem
		                throw new Exception("Veículo já está registrado na garagem: " + placa);
		            }
		        }
		    }
	
		    // Criar um novo bilhete de entrada
		    Bilhete bilhete = criarBilhete(veiculo.getPlaca(), LocalDateTime.now());
		    //Bilhete bilhete = new Bilhete(veiculo, LocalDateTime.now());
		    veiculo.adicionarBilhete(bilhete);
		    //daobilhete.create(bilhete);
		    DAO.commit();
	    } catch (Exception e) {
	        DAO.rollback(); // Reverter em caso de erro
	        throw e; // Repassar a exceção
	    }
	}

	
	/*public static void registrarSaida(String codigoDeBarra) throws Exception {
        Bilhete bilhete = daobilhete.read(codigoDeBarra);
        if (bilhete != null) {
            bilhete.setDataHoraFinal(LocalDateTime.now());
            daobilhete.update(bilhete);
        }
        else {
        	throw new Exception("Veículo não se encontra na garagem");
        }
    }*/
	
	public static void registrarSaida(String placa, LocalDateTime horaSaida) throws Exception {
	    // Verificar se a placa do veículo existe
	    Veiculo veiculo = daoveiculo.read(placa);
	    if (veiculo == null) {
	        throw new Exception("Veículo com a placa '" + placa + "' não encontrado!");
	    }

	    // Buscar o bilhete ativo do veículo
	    Bilhete bilhete = null;
	    for (Bilhete b : veiculo.getBilhetes()) {
	        // Procurando bilhete que não tenha dataHoraFinal (bilhete ainda ativo)
	        if (b.getDataHoraFinal() == null) {
	            bilhete = b;
	            break;
	        }
	    }

	    if (bilhete == null) {
	        throw new Exception("Veículo não possui um bilhete ativo ou já tem saída registrada.");
	    }

	    // Verificar se a hora de saída é posterior à hora de entrada
	    if (horaSaida.isBefore(bilhete.getDataHoraInicial())) {
	        throw new Exception("A hora de saída não pode ser anterior à hora de entrada.");
	    }

	    // Registrar a hora de saída no bilhete
	    bilhete.setDataHoraFinal(horaSaida);

	    // Atualizar o bilhete no banco de dados
	    daobilhete.update(bilhete);
	    daoveiculo.update(veiculo);
	}
	
	public static void alterarPlacaVeiculo(String placaAtual, String novaPlaca) throws Exception {
	    DAO.begin();
	    Veiculo veiculo = daoveiculo.read(placaAtual);
	    if (veiculo == null) {
	        throw new Exception("Veículo não encontrado: " + placaAtual);
	    }

	    Veiculo veiculoExistente = daoveiculo.read(novaPlaca);
	    if (veiculoExistente != null) {
	        throw new Exception("Já existe um veículo com a nova placa: " + novaPlaca);
	    }

	    veiculo.setPlaca(novaPlaca);
	    daoveiculo.update(veiculo);
	    DAO.commit();
	}
	
	public static void alterarBilhete(String codigoDeBarra, LocalDateTime novaDataHoraFinal) throws Exception {
	    DAO.begin();
	    Bilhete bilhete = daobilhete.read(codigoDeBarra);
	    if (bilhete == null) {
	        throw new Exception("Bilhete não encontrado: " + codigoDeBarra);
	    }
	    /*if (bilhete.getDataHoraFinal() != null) {
	        throw new Exception("Bilhete já encerrado, não é possível alterar a data de saída!");
	    }*/

	    bilhete.setDataHoraFinal(novaDataHoraFinal);
	    daobilhete.update(bilhete);
	    DAO.commit();
	}
	
	public static void apagarVeiculo(String placa) throws Exception {
	    DAO.begin();
	    
	    // Verifica se o veículo existe
	    Veiculo veiculo = daoveiculo.read(placa);
	    if (veiculo == null) {
	        throw new Exception("Veículo não encontrado, verifique a placa: " + placa);
	    }

	    // Verifica se o veículo tem bilhetes ativos (com data de saída nula)
	    for (Bilhete bilhete : veiculo.getBilhetes()) {
	        if (bilhete.getDataHoraFinal() == null) {
	            throw new Exception("Veículo não pode ser excluído enquanto houver bilhete ativo.");
	        }
	    }

	    // Apagar os bilhetes associados ao veículo
	    for (Bilhete bilhete : veiculo.getBilhetes()) {
	        daobilhete.delete(bilhete);
	    }

	    // Se o veículo não tiver bilhetes ativos, ele pode ser excluído
	    daoveiculo.delete(veiculo);
	    DAO.commit();
	}

	
	public static void apagarBilhete(String codigoDeBarra) throws Exception {
	    DAO.begin();
	    // Verifica se o bilhete existe
	    Bilhete bilhete = daobilhete.read(codigoDeBarra);
	    if (bilhete == null) {
	    	DAO.rollback();
	        throw new Exception("Bilhete não encontrado, verifique o código de barra: " + codigoDeBarra);
	    }

	    // Verifica se o bilhete já foi encerrado (se não tem data de saída)
	    if (bilhete.getDataHoraFinal() == null) {
	        throw new Exception("Bilhete não pode ser excluído enquanto não foi encerrado.");
	    }

	    // Se o bilhete estiver encerrado, podemos deletá-lo
	    Veiculo v = bilhete.getVeiculo();
	    v.removerBilhete(bilhete);
	    bilhete.setVeiculo(null);
	    daoveiculo.update(v);
	    daobilhete.delete(bilhete);
	    DAO.commit();
	}


	
	
	
	public static List<Bilhete> listarBilhetesAtivos() {
	    return daobilhete.readAtivos();
	}
	
	public static int contarVeiculosNaGaragem() {
	    return (int) daobilhete.countAtivos();
	}
	
	public static List<Bilhete> consultarBilhetesPorPlaca(String placa) throws Exception {
	    return daobilhete.consultarPlaca(placa);
	}
	
	public static List<Veiculo> listarVeiculosPorTempoPermanencia(long horas) {
	    return daoveiculo.readByTempoPermanencia(horas);
	}
	
	public static double calcularTotalArrecadado(LocalDateTime inicio, LocalDateTime fim) {
	    return daobilhete.calcularTotalArrecadadoPorPeriodo(inicio, fim);
	}
	
	public static double calcularTotalArrecadado1(LocalDate inicio, LocalDate fim) {
	    return daobilhete.calcularTotalArrecadado1(inicio, fim);
	}
	
	public static List<Bilhete> consultarHistoricoVeiculo(String placa) throws Exception {
		Veiculo veiculo = daoveiculo.read(placa);
	    if (veiculo == null) {
	        throw new Exception("Veículo não encontrado: " + placa);
	    }
	    return daoveiculo.readHistoricoByPlaca(placa);
	}
	
	public static List<Veiculo> listarVeiculosFrequentes(int vezes) {
	    return daoveiculo.readByFrequencia(vezes);
	}
	
	public static List<Bilhete> listarBilhetesPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
	    return daobilhete.readByPeriodo(inicio, fim);
	}
	
	public static double valorArrecadadoPorMes(String mes) {
		return daobilhete.calcularTotalArrecadadoPorMes(mes);
	}
	
	
	
	/*
	public static LocalDateTime mostrarDataHoraFinal(String codigodebarra) {
		return daobilhete.mostrarHoraFinal(codigodebarra);
	}*/
	
	
	public static List<Bilhete> consultarBilhetes(String codigo) {
		List<Bilhete> result;
		if (codigo.isEmpty())
			result = daobilhete.readAll();
		else
			result = daobilhete.readAllCodigos(codigo);
		return result;
	}
	
	public static List<Veiculo> consultarVeiculos(String placa) {
		List<Veiculo> result;
		if (placa.isEmpty())
			result = daoveiculo.readAll();
		else
			result = daoveiculo.readAllPlacas(placa);
		return result;
	}


	
	/**********************************************************
	 * 
	 * CONSULTAS IMPLEMENTADAS NOS DAO
	 * 
	 **********************************************************/
	
	public static List<Bilhete> consultarBilhetesPorValor(double valor) {
        return daobilhete.readByValorPagoMaiorQue(valor);
    }
	
	public static List<Veiculo> consultarVeiculosPorDataDeEntrada(LocalDateTime data) {
        return daoveiculo.readByDataDeEntrada(data);
    }
	
	public static List<Veiculo> consultarVeiculosPorDataDeSaida(LocalDateTime data) {
        return daoveiculo.readByDataDeSaida(data);
    }
	
	public static List<Veiculo> consultarVeiculosPorQuantidadeBilhetes(int n) {
        return daoveiculo.readByFrequencia(n);
    }

}
