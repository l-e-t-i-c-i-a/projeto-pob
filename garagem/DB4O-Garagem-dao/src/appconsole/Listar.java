package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/


import modelo.Aluno;
import modelo.Pessoa;
import modelo.Telefone;
import regras_negocio.Fachada1;

public class Listar {

	public Listar(){
		try {
			Fachada1.inicializar();

			System.out.println("*** Listagem de pessoas:");
			for(Pessoa p : Fachada1.listarPessoas())		
				System.out.println(p);

			System.out.println("\n*** Listagem de alunos:");
			for(Aluno a : Fachada1.listarAlunos())		
				System.out.println(a);

			System.out.println("\n*** Listagem de telefones:");
			for(Telefone t : Fachada1.listarTelefones())	
				System.out.println(t);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Fachada1.finalizar();
	}


	//=================================================
	public static void main(String[] args) {
		new Listar();
	}
}

