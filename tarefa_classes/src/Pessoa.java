import java.util.Date;

/**
 * @author Felipe Luz
 */
public class Pessoa {

    /**
     * Declarando atributos do objeto
     */
    private int id;
    private String name;
    private int age;
    private String cpf;
    private String occupation;

    /**
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * @return Nome
     */
    public String getName() {
        return name;
    }

    /**
     * Altera o nome
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Idade
     */
    public int getAge() {
        return age;
    }

    /**
     * Altera a idade
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return CPF
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Altera o CPF
     * @param cpf
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return Ocupação
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * Altera a ocupação
     * @param occupation
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * Imprime o ID
     */
    public void printId() {
        System.out.println("ID: " + getId());
    }

    /**
     * Imprime o nome
     */
    public void printName() {
        System.out.println("Nome: " + getName());
    }

    /**
     * Imprime a idade
     */
    public void printAge() {
        System.out.println("Idade: " + getAge());
    }

    /**
     * Imprime o CPF
     */
    public void printCpf() {
        System.out.println("CPF: " + getCpf());
    }

    /**
     * Imprime a ocupação
     */
    public void printOccupation() {
        System.out.println("Ocupação: " + getOccupation());
    }
}
