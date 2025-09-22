package br.com.feluz;

public class Converte {
    private int numeroInicial;
    private Integer numeroFinal;


    public void setNumeroInicial(int numeroInicial) {
        this.numeroInicial = Integer.valueOf(numeroInicial);
    }

    public Integer converteNumero() {
        numeroFinal = numeroInicial;
        return numeroFinal;
    }

    public Integer getNumeroFinal() {
        return numeroFinal;
    }

    public void printFinalNumber() {
        System.out.println("Número convertido: " + getNumeroFinal());
    }
}
