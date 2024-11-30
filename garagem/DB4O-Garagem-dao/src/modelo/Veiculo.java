package modelo;
import java.util.ArrayList;
import java.util.List;

public class Veiculo {
    private String placa;
    private List<Bilhete> bilhetes;

    public Veiculo(String placa) {
        this.placa = placa;
        this.bilhetes = new ArrayList<>();
    }

    public String getPlaca() {
        return placa;
    }

    public List<Bilhete> getBilhetes() {
        return bilhetes;
    }

    public void adicionarBilhete(Bilhete bilhete) {
        bilhetes.add(bilhete);
    }

    @Override
    public String toString() {
        if (bilhetes.isEmpty()) {
            return "Veiculo [placa=" + placa + ", bilhetes=Sem bilhetes]";
        }

        StringBuilder bilhetesString = new StringBuilder();
        for (Bilhete bilhete : bilhetes) {
            bilhetesString.append(bilhete.getCodigoDeBarra()).append(", ");
        }
        // Remover a última vírgula e espaço
        bilhetesString.setLength(bilhetesString.length() - 2);

        return "Veiculo [placa=" + placa + ", bilhetes=" + bilhetesString + "]";
    }

	public void setPlaca(String novaPlaca) {
		// TODO Auto-generated method stub
		this.placa = novaPlaca;
		
	}
}