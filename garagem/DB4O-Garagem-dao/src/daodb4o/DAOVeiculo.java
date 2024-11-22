package daodb4o;
import java.util.List;
public class DAOVeiculo {
	
	public Veiculo read (String placa) {
		Query q = manager.query();
		q.constrain(Veiculo.class);
		q.descend("id").constrain(placa);
		List<Veiculo> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
	
	public void create(Veiculo obj){
		int novoid = super.gerarId(Veiculo.class);  	//gerar novo id da classe
		obj.setId(novoid);				//atualizar id do objeto antes de grava-lo no banco
		manager.store( obj );
	}
}