package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import modelo.Veiculo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import modelo.Bilhete;
import regras_negocio.Fachada;


public class Consultar {

	public Consultar(){

		try {
			Fachada.inicializar();
			
			// Consulta 1
			System.out.println("\nBilhetes com valor maior que 2 reais:");
			for (Bilhete bilhete : Fachada.consultarBilhetesPorValor(2.0)) {
                System.out.println(bilhete);
            }

			String dataStr = "27/11/2024"; // Exemplo de data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(dataStr, formatter);
            // Consulta 2
            System.out.println("\nVeículos com bilhete pago na data " + dataStr + ":");
            for (Veiculo veiculo : Fachada.consultarVeiculosPorDataDeSaida(data.atStartOfDay())) {
                System.out.println(veiculo);
            }

            // Consulta 3
            int n = 2; // Exemplo de número de bilhetes
            System.out.println("\nVeículos com mais de " + n + " bilhetes:");
            for (Veiculo veiculo : Fachada.consultarVeiculosPorQuantidadeBilhetes(n)) {
                System.out.println(veiculo);
            }
            
            
            
            
            //Consulta extra
            // Define o intervalo de datas
            LocalDate inicio = LocalDate.of(2024, 11, 20);
            LocalDate fim = LocalDate.of(2024, 11, 28);
            // Chama o método para calcular o total arrecadado
            double total = Fachada.calcularTotalArrecadado(LocalDateTime.of(2024, 9, 21, 0, 0, 0, 0), LocalDateTime.of(2024, 9, 28, 0, 0, 0, 0));
            System.out.println("\nTotal arrecadado entre " + inicio + " e " + fim + ": R$ " + total);
            
            
            String mes9 = "9";  // Outubro, por exemplo
            double totalMes9 = Fachada.valorArrecadadoPorMes(mes9);
            System.out.println("\nTotal arrecadado no mês " + mes9 + ": R$ " + totalMes9);
            
            String mes11 = "11";  // Outubro, por exemplo
            double totalMes11 = Fachada.valorArrecadadoPorMes(mes11);
            System.out.println("Total arrecadado no mês " + mes11 + ": R$ " + totalMes11);
            
            
            
            
            
            
            /*
            List<Bilhete >bilhetesPorPeriodo = Fachada.listarBilhetesPorPeriodo(LocalDateTime.of(2024, 11, 20, 0, 0, 0),LocalDateTime.of(2024, 11, 28, 21, 0, 0));
            System.out.println("Bilhetes entre: " +LocalDateTime.of(2024, 11, 20, 0, 0, 0, 0) + "e" + LocalDateTime.of(2024, 11, 20, 0, 0, 0, 0)+ "=" + bilhetesPorPeriodo);
            
            
            List<Bilhete> bilhetesTodos = Fachada.listarBilhetes1();
            System.out.println("\nTodos os bilhetes teste:" + bilhetesTodos);
            
            LocalDateTime datafinal = Fachada.mostrarDataHoraFinal("27112024661");
            System.out.println("Hora final:" + datafinal);*/

            
            
            
            



		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada.finalizar();
		System.out.println("\nfim do programa");
	}


	//=================================================
	public static void main(String[] args) {
		new Consultar();
	}
}

