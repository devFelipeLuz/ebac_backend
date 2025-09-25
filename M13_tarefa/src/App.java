public class App {

    public static void main(String[] args) {
        Pessoa p = new Pessoa();
        p.setFullName("Teste da Silva Sauro");
        p.setAddress("Rua dos bobos, n0");

        PessoaFisica pf = new PessoaFisica();
        pf.setCpf("123.456.789-10");

        PessoaJuridica pj = new PessoaJuridica();
        pj.setCnpj("12.345.678/1000-11");

        print(p, pf, pj);
    }

    public static void print(Pessoa p, PessoaFisica pF, PessoaJuridica pJ){

        System.out.println("Nome: " + p.getFullName());
        System.out.println("Endereco: " + p.getAddress());
        System.out.println("CPF: " + pF.getCpf());
        System.out.println("CNPJ: " + pJ.getCnpj());
    }
}
