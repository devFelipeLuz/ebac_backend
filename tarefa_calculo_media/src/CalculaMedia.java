public class CalculaMedia {
    private double nota1;
    private double nota2;
    private double nota3;
    private double nota4;
    private double media;

    public double calculaMedia() {
       media = (nota1 + nota2 + nota3 + nota4) / 4;
       return media;
    }

    public void setNota1(double nota1) {
        this.nota1 = nota1;
    }

    public void setNota2(double nota2) {
        this.nota2 = nota2;
    }

    public void setNota3(double nota3) {
        this.nota3 = nota3;
    }

    public void setNota4(double nota4) {
        this.nota4 = nota4;
    }

    public double getMedia() {
        return media;
    }

    public void printMedia() {
        System.out.println("A média é: " + getMedia());
    }
}
