package daodb4o;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.db4o.query.Query;

import modelo.Bilhete;
import modelo.Veiculo;
public class DAOBilhete extends DAO<Bilhete>{
	
	public Bilhete read (String codigoDeBarra) {
		Query q = manager.query();
		q.constrain(Bilhete.class);
		q.descend("codigoDeBarra").constrain(codigoDeBarra);
		List<Bilhete> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
	
		/**********************************************************
		 * 
		 * TODAS AS CONSULTAS DE BILHETE
		 * 
		 **********************************************************/

		/*public  List<Bilhete> readAll(String caracteres) {
			Query q = manager.query();
			q.constrain(Bilhete.class);
			q.descend("codigoDeBarra").constrain(caracteres).like();		//insensitive
			List<Bilhete> result = q.execute(); 
			return result;
		}*/
		
		public Bilhete readByDataHoraInicial(String dataHoraInicial){
			Query q = manager.query();
			q.constrain(Bilhete.class);
			q.descend("dataHoraInicial").constrain(dataHoraInicial);
			List<Bilhete> resultados = q.execute();
			if(resultados.isEmpty())
				return null;
			else
				return resultados.getFirst();
		}
		
		public List<Bilhete> readByMes(String mes) {
			Query q = manager.query();
			q.constrain(Bilhete.class);  
			q.descend("dataHoraInicial").constrain("/"+mes+"/").contains();
			return q.execute();
		}
		
		public List<Bilhete> consultarPlaca(String placa) {
			Query q = manager.query();
			q.constrain(Bilhete.class);
			q.descend("veiculo").descend("placa").constrain(placa);
			return q.execute();
		}
		
		public List<Bilhete> readByValorPagoMaiorQue(double valor) {
		    Query q = manager.query(); // Criar consulta
		    q.constrain(Bilhete.class); // Definir a classe-alvo
		    q.descend("valorPago").constrain(valor).greater(); // Filtro: `valorPago > valor`
		    return q.execute(); // Retornar a lista de resultados
		}

		
		public long countAtivos() {
		    Query q = manager.query(); // Criar consulta
		    q.constrain(Bilhete.class); // Definir a classe-alvo
		    q.descend("dataHoraFinal").constrain(null); // Filtro: `dataHoraFinal` deve ser null
		    List<Bilhete> resultados = q.execute(); // Executar consulta
		    return resultados.size(); // Retornar o número de resultados
		}
		
		public List<Bilhete> readAtivos() {
		    Query q = manager.query(); // Criar consulta
		    q.constrain(Bilhete.class); // Definir a classe-alvo
		    q.descend("dataHoraFinal").constrain(null); // Filtro: `dataHoraFinal` deve ser null
		    List<Bilhete> resultados = q.execute(); // Executar consulta
		    return resultados; // Retornar resultados
		}
		
		public List<Bilhete> readByPeriodo(LocalDateTime inicio, LocalDateTime fim) {
		    Query q = manager.query(); // Criar consulta
		    q.constrain(Bilhete.class); // Definir a classe-alvo
		    q.descend("dataHoraInicial").constrain(inicio).greater().and( // Filtro: `dataHoraInicial >= inicio`
		        q.descend("dataHoraInicial").constrain(fim).smaller() // Filtro: `dataHoraInicial <= fim`
		    );
		    return q.execute(); // Retornar a lista de resultados
		}
		
		public double calcularTotalArrecadado(LocalDate inicio, LocalDate fim) {
		    Query q = manager.query();
		    q.constrain(Bilhete.class); // Classe-alvo

		    // Filtro: bilhetes com data de saída dentro do intervalo
		    q.descend("dataHoraFinal")
		        .constrain(inicio.atStartOfDay()).greater()
		        .and(q.descend("dataHoraFinal").constrain(fim.atTime(23, 59, 59)).smaller());

		    List<Bilhete> bilhetes = q.execute();
		    

		    // Verifica se foram encontrados bilhetes
		    if (bilhetes.isEmpty()) {
		        return 0.0; // Caso nenhum bilhete seja encontrado, retorna 0
		    }

		    // Calcula a soma do valor pago dos bilhetes encontrados
		    double total = 0.0;
		    for (Bilhete bilhete : bilhetes) {
		        total += bilhete.getValorPago(); // Acumula o valor pago
		    }

		    // Retorna o total arrecadado
		    return total;
		}
		
		


		


	/*-------------------------------------------------*/
	//@SuppressWarnings("serial")
	//class Filtro  implements Evaluation {
	//	private int n;
	//	public Filtro (int n) {
	//		this.n=n;
	//	}
	//	public void evaluate(Candidate candidate) {
	//		Pessoa p = (Pessoa) candidate.getObject();
	//		candidate.include( p.getTelefones().size() == n );
	//	}
	//}



}
