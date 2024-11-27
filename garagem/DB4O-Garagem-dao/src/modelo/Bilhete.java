package modelo;

import java.time.LocalDateTime;
import java.util.Random;

/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/

public class Bilhete {
	private String codigoDeBarra;
	private LocalDateTime dataHoraInicial;
	private Veiculo veiculo; //lado inverso do relacionamento
	private LocalDateTime dataHoraFinal;
	private double valorPago;
	
				


	public Bilhete (Veiculo veiculo, LocalDateTime dataHoraInicial){
		this.veiculo = veiculo;
		this.dataHoraInicial = dataHoraInicial;
		this.codigoDeBarra = gerarCodigoDeBarra();
	}
	
	private String gerarCodigoDeBarra() {
		Random random = new Random();
		int numeroAleatorio = random.nextInt(1000);
		return String.format("%02d%02d%04d%03d", dataHoraInicial.getDayOfMonth(), 
                dataHoraInicial.getMonthValue(),
                dataHoraInicial.getYear(),
                numeroAleatorio);
	}
	
	//private void registrarSaida(LocalDateTime dataHoraFinal) {
	//	this.dataHoraFinal = dataHoraFinal;
	//	calcularValorPago();
	//}
	
	private void calcularValorPago() {
		//if (dataHoraFinal == null) {
	    //    throw new IllegalStateException("Data/hora final não foi definida.");
	    //}
	    long horas = java.time.Duration.between(dataHoraInicial, dataHoraFinal).toHours();
	    this.valorPago = Math.ceil(horas) * 2.0;
	}
	
	public double getValorPago() {
		return valorPago;
	}
	
	public LocalDateTime getDataHoraInicial() {
		return dataHoraInicial;
	}
	
	public LocalDateTime getDataHoraFinal() {
		return dataHoraFinal;
	}
	
	public void setDataHoraFinal(LocalDateTime dataHoraFinal) {
		if (dataHoraFinal.isBefore(dataHoraInicial)) {
	        throw new IllegalArgumentException("Data/hora final não pode ser anterior à data/hora inicial.");
	    }
	    this.dataHoraFinal = dataHoraFinal;
	    calcularValorPago();
    }
	
	public String getCodigoDeBarra() {
		return codigoDeBarra;
	}
	
//	--------------------RELACIONAMENTO--------------------------------
	public Veiculo getVeiculo() {
		return veiculo;
	}
	
	
	@Override
	public String toString() {
	    return "Bilhete [codigoDeBarra=" + codigoDeBarra + 
	           ", veiculo=" + (veiculo != null ? veiculo.getPlaca() : "null") + 
	           ", dataHoraInicial=" + dataHoraInicial +
	           ", dataHoraFinal=" + dataHoraFinal + 
	           ", valorPago=" + valorPago + "]";
	}

	

}
