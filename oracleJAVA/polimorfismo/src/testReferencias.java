public class testReferencias {
    public static void main(String[] args) {

        funcionario funcionario;
        funcionario = new funcionario();
        funcionario.setName("Diego");

        gerente gerente;
        gerente  = new gerente();
        gerente.setName("Juan");

        funcionario.setSalario(2000.00);
        gerente.setSalario(5000.00);
    }

}
