package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import modelo.Pessoa;
import modelo.Telefone;
import regras_negocio.Fachada1;


public class Consultar {

	public Consultar(){

		try {
			Fachada1.inicializar();
			System.out.println("\npessoas com nome jo ");
			for(Pessoa p : Fachada1.consultarPessoas("jo")) 
				System.out.println(p);

			System.out.println("\ntelefones com numero 987 ");
			for(Telefone t : Fachada1.consultarTelefones("987")) 
				System.out.println(t);

			System.out.println("\npessoas que nasceram no mes 02");
			for(Pessoa p : Fachada1.consultarMesNascimento("02")) 
				System.out.println(p);

			System.out.println("\npessoas com apelido mary");
			for(Pessoa p : Fachada1.consultarApelido("mary")) 
				System.out.println(p);


			System.out.println("\npessoas com dois telefones " );
			for(Pessoa p : Fachada1.consultarPessoasNTelefones(2) ) 
				System.out.println(p);

			System.out.println("\nmaria tem telefone fixo?\n"+
					Fachada1.temTelefoneFixo("maria") );



		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Fachada1.finalizar();
		System.out.println("\nfim do programa");
	}


	//=================================================
	public static void main(String[] args) {
		new Consultar();
	}
}

