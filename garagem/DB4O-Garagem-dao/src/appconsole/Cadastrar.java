package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import regras_negocio.Fachada;
import modelo.Veiculo;
import modelo.Bilhete;

public class Cadastrar {

	public Cadastrar(){
        try {
            // Inicia o processo de cadastro
            System.out.println("Cadastrando veículos...");
            
            // Inicializa a fachada de garagem
            Fachada.inicializar();

            // Cadastra veículos
            Fachada.criarVeiculo("ABC1234");
            Fachada.criarVeiculo("XYZ5678");
            Fachada.criarVeiculo("DEF2345");
            Fachada.criarVeiculo("GHI6789");
            
            //Registrar entrada de veículos
            Fachada.registrarEntrada("ABC1234");
            Fachada.registrarEntrada("XYZ5678");

            System.out.println("Cadastrando bilhetes...");

            //Cadastra bilhetes para os veículos (obs: registrarEntrada já cria o carro e o bilhete!)
            Fachada.criarBilhete("ABC1234", LocalDateTime.of(2024, 11, 20, 8, 0, 0, 0));
            Fachada.criarBilhete("XYZ5678", LocalDateTime.of(2024, 11, 21, 9, 0, 0, 0));
            Fachada.criarBilhete("DEF2345", LocalDateTime.of(2024, 11, 20, 10, 0, 0, 0));
            Fachada.criarBilhete("GHI6789", LocalDateTime.of(2024, 11, 22, 7, 30, 0, 0));
            
            
            
            
            //Registrar saída de veículos
            Fachada.registrarSaida("ABC1234", LocalDateTime.of(2024, 11, 24, 5, 0, 0, 0));

            // Finaliza a fachada de garagem
            Fachada.finalizar();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	
        System.out.println("Fim do programa");
	}
	        
	
	//=================================================
	public static void main(String[] args) {
		new Cadastrar();
	}
}


