package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import java.util.ArrayList;
import java.util.List;

import regras_negocio.Fachada1;

public class Cadastrar {

	public Cadastrar(){
		try {
			System.out.println("cadastrando pessoas...");
			Fachada1.inicializar();
			
			Fachada1.criarPessoa("joao", "01/01/1990",new ArrayList<>(List.of("jo", "joaozinho", "jojo")));
			Fachada1.criarPessoa("maria","01/01/1980",new ArrayList<>(List.of("mary", "mar")));
			Fachada1.criarPessoa("jose", "01/01/1990",new ArrayList<>(List.of("zezinho", "zezao")));
			Fachada1.criarPessoa("paulo","01/01/1990",new ArrayList<>(List.of("paulao")));
			
			
			System.out.println("cadastrando aluno...");
			Fachada1.criarAluno("ana","01/01/1990",new ArrayList<>(List.of("aninha")),	10.0);
			Fachada1.criarAluno("marta","01/02/1990",new ArrayList<>(), 9.0);
			
		} catch (Exception e) 	{
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("cadastrando telefones...");
			Fachada1.criarTelefone("joao","988880000");
			Fachada1.criarTelefone("joao","988881111");	
			Fachada1.criarTelefone("maria","987882222");
			Fachada1.criarTelefone("maria","988883333");
			Fachada1.criarTelefone("maria","32471234");
			Fachada1.criarTelefone("jose","987884444");
			Fachada1.criarTelefone("paulo","988885555");
		} catch (Exception e) 	{
			System.out.println(e.getMessage());
		}

		Fachada1.finalizar();
		System.out.println("fim do programa");
	}

	
	//=================================================
	public static void main(String[] args) {
		new Cadastrar();
	}
}


