public class Media {
    private float nota1;
    private float nota2;
    private float nota3;
    private float nota4;
    private float media;

    public void setNota1(float nota1) {
        this.nota1 = nota1;
    }

    public void setNota2(float nota2) {
        this.nota2 = nota2;
    }

    public void setNota3(float nota3) {
        this.nota3 = nota3;
    }

    public void setNota4(float nota4) {
        this.nota4 = nota4;
    }

    public float calculaMedia() {
        media = (nota1 + nota2 + nota3 + nota4) / 4;
        return media;
    }

    public float getMedia() {
        return media;
    }

    public void printMedia(){
        System.out.println("Média: " + getMedia());
    }

    public void printStatus() {
        if (media >= 7) {
            System.out.println("Status: Aprovado");
        } else if (media > 4) {
            System.out.println("Status: Recuperacao");
        } else {
            System.out.println("Status: Reprovado");
        }
    }
}
