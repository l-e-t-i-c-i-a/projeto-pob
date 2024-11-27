package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import java.time.LocalDateTime;

import regras_negocio.Fachada;

public class Alterar {

	public Alterar(){
		Fachada.inicializar();
		//altera�ao 1
		try {
			Fachada.alterarPlacaVeiculo("XYZ5678","XYZ55577");
			System.out.println("Placa alterada com sucesso");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//altera�ao 2
		try {
			Fachada.alterarBilhete("27112024756", LocalDateTime.of(2024, 11, 27, 5, 0, 0, 0));
			System.out.println("Bilhete alterado com sucesso");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Fachada.finalizar();
		System.out.println("fim do programa");
	}



	//=================================================
	public static void main(String[] args) {
		new Alterar();
	}
}

