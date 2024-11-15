package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import regras_negocio.Fachada1;


public class Deletar {

	public Deletar(){
		Fachada1.inicializar();
		try {
			Fachada1.excluirPessoa("jose");
			System.out.println("apagou jose e seus telefones orfaos");
			
			Fachada1.excluirTelefone("988881111");
			System.out.println("apagou telefone...988881111");
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Fachada1.finalizar();
		System.out.println("fim do programa");
	}



	//=================================================
	public static void main(String[] args) {
		new Deletar();
	}
}

