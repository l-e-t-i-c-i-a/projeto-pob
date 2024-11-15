package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import regras_negocio.Fachada1;

public class Alterar {

	public Alterar(){
		Fachada1.inicializar();
		//altera�ao 1
		try {
			Fachada1.alterarData("joao","01/02/1990");
			System.out.println("alterado data de nascimento de joao para mes 02");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		//altera�ao 2
		try {
			Fachada1.alterarNumero("988880000", "999999999");
			System.out.println("alterado numero 988880000 para 999999999");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Fachada1.finalizar();
		System.out.println("fim do programa");
	}



	//=================================================
	public static void main(String[] args) {
		new Alterar();
	}
}

