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
        return "Veiculo [placa=" + placa + "]";
    }
}