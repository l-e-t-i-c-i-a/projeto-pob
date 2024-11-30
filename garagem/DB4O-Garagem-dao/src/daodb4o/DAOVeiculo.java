package daodb4o;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.db4o.query.Query;

import modelo.Bilhete;
import modelo.Veiculo;
public class DAOVeiculo extends DAO<Veiculo>{
	
	public Veiculo read (String placa) {
		Query q = manager.query();
		q.constrain(Veiculo.class);
		q.descend("placa").constrain(placa);
		List<Veiculo> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
	
	//public void create(Veiculo obj){
	//	int novoid = super.gerarId(Veiculo.class);  	//gerar novo id da classe
	//	obj.setId(novoid);				//atualizar id do objeto antes de grava-lo no banco
	//	manager.store( obj );
	//}
	
	public List<Veiculo> readByTempoPermanencia(long horas) {
	    Query q = manager.query();
	    q.constrain(Veiculo.class); // Classe-alvo
	    q.descend("bilhetes").descend("dataHoraFinal").constrain(null).not(); // Apenas bilhetes com saída registrada

	    // Filtra bilhetes cuja diferença entre entrada e saída é maior ou igual às horas especificadas
	    q.descend("bilhetes").descend("dataHoraInicial")
	        .constrain(java.time.Duration.ofHours(horas).toMillis())
	        .greater();

	    return q.execute();
	}
	
	public List<Veiculo> readAllPlacas(String placa) {
		Query q = manager.query();
		q.constrain(Veiculo.class);
		q.descend("placa").constrain(placa).like();
		return q.execute(); 
	}
	
	public List<Bilhete> readHistoricoByPlaca(String placa) {
	    Query q = manager.query();
	    q.constrain(Veiculo.class); // Classe-alvo
	    q.descend("placa").constrain(placa); // Filtrar pelo veículo com a placa fornecida

	    List<Veiculo> veiculos = q.execute();

	    // Verifica se a lista de veículos está vazia
	    if (veiculos.isEmpty()) {
	        // Retorna uma lista vazia caso nenhum veículo tenha sido encontrado
	        return new ArrayList<>();
	    } else {
	        // Retorna a lista de bilhetes do primeiro veículo encontrado
	        Veiculo veiculoEncontrado = veiculos.get(0);
	        return veiculoEncontrado.getBilhetes();
	    }
	}
	
	public List<Veiculo> readByFrequencia(int vezes) {
	    Query q = manager.query();
	    q.constrain(Veiculo.class); // Define a classe-alvo (Veiculo)

	    // Executa a consulta para recuperar todos os veículos
	    List<Veiculo> veiculosEncontrados = q.execute();

	    // Filtra os veículos que possuem mais bilhetes do que 'vezes'
	    List<Veiculo> veiculosFiltrados = new ArrayList<>();
	    for (Veiculo veiculo : veiculosEncontrados) {
	        if (veiculo.getBilhetes() != null && veiculo.getBilhetes().size() > vezes) {
	            veiculosFiltrados.add(veiculo);
	        }
	    }

	    // Retorna a lista de veículos filtrados
	    return veiculosFiltrados;
	}
	
	public List<Veiculo> readByDataDeEntrada(LocalDateTime data) {
	    Query q = manager.query();
	    q.constrain(Veiculo.class); // Consulta para a classe Veiculo

	    // Executa a consulta para recuperar todos os veículos
	    List<Veiculo> veiculosEncontrados = q.execute();

	    // Lista para armazenar os veículos que atenderam à condição de data de entrada
	    List<Veiculo> veiculosFiltrados = new ArrayList<>();

	    // Itera sobre todos os veículos encontrados
	    for (Veiculo veiculo : veiculosEncontrados) {
	        // Verifica se o veículo tem bilhetes
	        if (veiculo.getBilhetes() != null) {
	            // Itera sobre todos os bilhetes do veículo
	            for (Bilhete bilhete : veiculo.getBilhetes()) {
	                // Verifica se a data de entrada coincide com a data fornecida
	                if (bilhete.getDataHoraInicial() != null &&
	                    bilhete.getDataHoraInicial().toLocalDate().equals(data.toLocalDate())) {
	                    veiculosFiltrados.add(veiculo); // Adiciona o veículo à lista de filtrados
	                    break; // Interrompe o loop, pois o veículo já foi encontrado para essa data
	                }
	            }
	        }
	    }

	    // Retorna a lista de veículos que tiveram data de entrada na data fornecida
	    return veiculosFiltrados;
	}
	
	public List<Veiculo> readByDataDeSaida(LocalDateTime data) {
	    Query q = manager.query();
	    q.constrain(Veiculo.class); // Consulta para a classe Veiculo

	    // Executa a consulta para recuperar todos os veículos
	    List<Veiculo> veiculosEncontrados = q.execute();

	    // Lista para armazenar os veículos que atenderam à condição de data de saída
	    List<Veiculo> veiculosFiltrados = new ArrayList<>();

	    // Itera sobre todos os veículos encontrados
	    for (Veiculo veiculo : veiculosEncontrados) {
	        // Verifica se o veículo tem bilhetes
	        if (veiculo.getBilhetes() != null) {
	            // Itera sobre todos os bilhetes do veículo
	            for (Bilhete bilhete : veiculo.getBilhetes()) {
	                // Verifica se a data de saída (dataHoraFinal) coincide com a data fornecida
	                if (bilhete.getDataHoraFinal() != null &&
	                    bilhete.getDataHoraFinal().toLocalDate().equals(data.toLocalDate())) {
	                    veiculosFiltrados.add(veiculo); // Adiciona o veículo à lista de filtrados
	                    break; // Interrompe o loop, pois o veículo já foi encontrado para essa data
	                }
	            }
	        }
	    }

	    // Retorna a lista de veículos que tiveram data de saída na data fornecida
	    return veiculosFiltrados;
	}




}