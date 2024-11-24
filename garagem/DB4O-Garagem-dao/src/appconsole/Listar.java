package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/



import java.time.LocalDate;

import modelo.Bilhete;
import modelo.Veiculo;
import regras_negocio.Fachada;

public class Listar {

	public Listar(){
		try {
			Fachada.inicializar();

			System.out.println("*** Listagem de bilhetes:");
			for(Bilhete b : Fachada.listarBilhetes())		
				System.out.println(b);

			System.out.println("\n*** Listagem de veículos:");
			for(Veiculo v : Fachada.listarVeiculos())		
				System.out.println(v);
			
		
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Fachada.finalizar();
	}


	//=================================================
	public static void main(String[] args) {
		new Listar();
	}
}

