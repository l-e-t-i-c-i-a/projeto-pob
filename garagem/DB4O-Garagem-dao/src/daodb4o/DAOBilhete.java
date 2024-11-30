package daodb4o;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.db4o.ObjectSet;
import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
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
	
	public List<Bilhete> readAllCodigos(String codigo) {
		Query q = manager.query();
		q.constrain(Bilhete.class);
		q.descend("codigoDeBarra").constrain(codigo).like();
		return q.execute(); 
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
		/*
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
		*/
		
		public double calcularTotalArrecadado1(LocalDate inicio, LocalDate fim) {
			// Convertendo LocalDate para LocalDateTime para compatibilidade
		    LocalDateTime inicioDateTime = inicio.atStartOfDay();  // Início do dia
		    LocalDateTime fimDateTime = fim.atTime(23, 59, 59);   // Fim do dia

		    Query q = manager.query();
		    q.constrain(Bilhete.class);

		    // Filtro por dataHoraFinal dentro do intervalo
		    q.descend("dataHoraFinal").constrain(inicioDateTime).greater().and(q.descend("dataHoraFinal").constrain(fimDateTime).smaller());
		    //q.descend("dataHoraFinal").constrain(inicioDateTime).greater();
		    //List<Bilhete> bilhetes1 = q.execute();
		    
		    //q.descend("dataHoraFinal").constrain(fimDateTime).smaller();
		    //List<Bilhete> bilhetes2 = q.execute();

		    // Executa a consulta com os filtros combinados
		    List<Bilhete> bilhetes = q.execute();
		    
		    // Exibe os bilhetes encontrados (debug)
		    //System.out.println("Bilhetes encontrados: " + bilhetes);
		    //System.out.println("todos bilhetes:" + listarBilhetes());
		    
		 // Calcula a soma dos valores pagos dos bilhetes encontrados
		    //double total1 = 0.0;
		    //for (Bilhete bilhete : bilhetes1) {
		    //    total1 += bilhete.getValorPago();
		    //}
		    
		    //double total2 = 0.0;
		    //for (Bilhete bilhete : bilhetes2) {
		    //    total2 += bilhete.getValorPago();
		    //}

		    // Calcula a soma dos valores pagos dos bilhetes encontrados
		    double total = 0.0;
		    for (Bilhete bilhete : bilhetes) {
		        total += bilhete.getValorPago();
		    }

		    return total;
		}
		
		/*
		public LocalDateTime mostrarHoraFinal(String codigodebarra) {
			Query q = manager.query();
			q.constrain(Bilhete.class);
			q.descend("codigodebarra").constrain(codigodebarra).equal();
			Bilhete resultado = (Bilhete) q.execute();
			
			return resultado.getDataHoraFinal();
		}*/
		
		public double calcularTotalArrecadado(LocalDate inicio, LocalDate fim) {
		    // Consulta todos os veículos no banco de dados
		    Query qVeiculos = manager.query();
		    qVeiculos.constrain(Veiculo.class); // Classe dos veículos
		    List<Veiculo> veiculos = qVeiculos.execute();
		    
		    double totalArrecadado = 0.0;
		    
		    // Para cada veículo, verifique os bilhetes e some os valores dos bilhetes dentro do intervalo
		    for (Veiculo veiculo : veiculos) {
		        // Consulta os bilhetes do veículo
		        Query qBilhetes = manager.query();
		        qBilhetes.constrain(Bilhete.class); // Classe dos bilhetes
		        qBilhetes.descend("veiculo").constrain(veiculo).equal(); // Filtro por veículo
		        
		        // Filtro pela dataHoraFinal dos bilhetes dentro do intervalo
		        qBilhetes.descend("dataHoraFinal")
		                .constrain(inicio.atStartOfDay()).greater()
		                .and(qBilhetes.descend("dataHoraFinal")
		                    .constrain(fim.atTime(23, 59, 59)).smaller());

		        // Executa a consulta e obtém os bilhetes encontrados
		        List<Bilhete> bilhetes = qBilhetes.execute();
		        
		        // Soma o valor arrecadado
		        for (Bilhete bilhete : bilhetes) {
		            totalArrecadado += bilhete.getValorPago();
		        }
		    }
		    
		    return totalArrecadado;
		}
		
		public List<Bilhete>listarBilhetes() {
		Query q = manager.query();
		q.constrain(Bilhete.class);
		List<Bilhete> bilhetes = q.execute();
		//System.out.println("Todos os bilhetes: " + bilhetes);
		return bilhetes;
		}
		
		public double calcularTotalArrecadadoPorMes(String mes) {
		    double totalArrecadado = 0.0;
		    
		    // Consulta todos os bilhetes salvos no banco de dados
		    Query q = manager.query();
		    q.constrain(Bilhete.class);  // Classe Bilhete
		    List<Bilhete> bilhetes = q.execute();
		    
		    // Itera sobre todos os bilhetes
		    for (Bilhete bilhete : bilhetes) {
		        // Verifica se a dataHoraFinal do bilhete não é nula
		        if (bilhete.getDataHoraFinal() != null) {
		            // Obtém o mês da dataHoraFinal
		            int mesFinal = bilhete.getDataHoraFinal().getMonthValue();
		            
		            // Se o mês da dataHoraFinal for o mesmo do mês passado como parâmetro
		            if (mesFinal == Integer.parseInt(mes)) {
		                // Soma o valor pago no bilhete
		                totalArrecadado += bilhete.getValorPago();
		            }
		        }
		    }
		    
		    // Retorna o total arrecadado para o mês especificado
		    return totalArrecadado;
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
		public double calcularTotalArrecadadoPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
	        double totalArrecadado = 0.0;

	        // Cria uma consulta para todos os bilhetes
	        Query q = manager.query();
	        q.constrain(Bilhete.class);

	        // Aplica um filtro baseado na dataHoraFinal
	        q.constrain(new Evaluation() {
	            @Override
	            public void evaluate(Candidate candidate) {
	                Bilhete bilhete = (Bilhete) candidate.getObject();
	                LocalDateTime dataHoraFinal = bilhete.getDataHoraFinal();

	                // Verifica se a dataHoraFinal do bilhete está dentro do intervalo
	                if (dataHoraFinal != null) {
	                    if ((dataHoraFinal.isEqual(inicio) || dataHoraFinal.isAfter(inicio)) &&
	                        (dataHoraFinal.isEqual(fim) || dataHoraFinal.isBefore(fim))) {
	                        candidate.include(true);  // Inclui no resultado
	                    } else {
	                        candidate.include(false);  // Exclui do resultado
	                    }
	                } else {
	                    candidate.include(false);  // Exclui bilhetes com dataHoraFinal nula
	                }
	            }
	        });

	        // Executa a consulta
	        List<Bilhete> bilhetes = q.execute();

	        // Soma os valores pagos dos bilhetes encontrados
	        for (Bilhete bilhete : bilhetes) {
	            totalArrecadado += bilhete.getValorPago();
	        }

	        return totalArrecadado;
	    }


}
