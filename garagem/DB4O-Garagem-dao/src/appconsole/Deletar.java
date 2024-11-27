package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import regras_negocio.Fachada;


public class Deletar {

	public Deletar(){
		Fachada.inicializar();
		try {
			Fachada.apagarVeiculo("ABC1234");
			System.out.println("apagou o veículo de placa ABC1234 e seus bilhetes");
			
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Fachada.finalizar();
		System.out.println("fim do programa");
	}



	//=================================================
	public static void main(String[] args) {
		new Deletar();
	}
}

