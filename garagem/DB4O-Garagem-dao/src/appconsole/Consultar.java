package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import modelo.Veiculo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import modelo.Bilhete;
import regras_negocio.Fachada;


public class Consultar {

	public Consultar(){

		try {
			Fachada.inicializar();
			
			// Consulta 1
			System.out.println("\nBilhetes com valor maior que 10 reais:");
			for (Bilhete bilhete : Fachada.consultarBilhetesPorValor(6.0)) {
                System.out.println(bilhete);
            }

			String dataStr = "24/11/2024"; // Exemplo de data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(dataStr, formatter);
            // Consulta 2
            System.out.println("\nVeículos com bilhete pago na data " + dataStr + ":");
            for (Veiculo veiculo : Fachada.consultarVeiculosPorDataDeSaida(data.atStartOfDay())) {
                System.out.println(veiculo);
            }

            // Consulta 3
            int n = 3; // Exemplo de número de bilhetes
            System.out.println("\nVeículos com mais de " + n + " bilhetes:");
            for (Veiculo veiculo : Fachada.consultarVeiculosPorQuantidadeBilhetes(n)) {
                System.out.println(veiculo);
            }
            
            
            
            
            //Consulta extra
            // Define o intervalo de datas
            LocalDate inicio = LocalDate.of(2024, 11, 20);
            LocalDate fim = LocalDate.of(2024, 11, 25);
            // Chama o método para calcular o total arrecadado
            double total = Fachada.calcularTotalArrecadado(inicio, fim);
            System.out.println("Total arrecadado entre " + inicio + " e " + fim + ": R$ " + total);



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

