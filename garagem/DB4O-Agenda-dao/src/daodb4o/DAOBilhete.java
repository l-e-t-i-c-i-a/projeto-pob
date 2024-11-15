package daodb4o;

import java.util.List;

public class DAOBilhete extends DAO<Bilhete>{
	
	public Bilhete read (String codigoDeBarra) {
		Query q = manager.query();
		q.constrain(Bilhete.class);
		q.descend("id").constrain(codigoDeBarra);
		List<Bilhete> resultados = q.execute();
		if (resultados.size()>0)
			return resultados.get(0);
		else
			return null;
	}
	
	public void create(Bilhete obj){
		int novoid = super.gerarId(Bilhete.class);  	//gerar novo id da classe
		obj.setId(novoid);				//atualizar id do objeto antes de grava-lo no banco
		manager.store( obj );
	}
}
