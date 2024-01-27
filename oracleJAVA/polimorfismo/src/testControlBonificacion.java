public class testControlBonificacion {
    public static void main(String[] args) {
        funcionario diego = new funcionario();
        diego.setSalario(2000.00);

        gerente juan = new gerente();
        juan.setSalario(5000.00);

        director pedro = new director();
        pedro.setSalario(10000.00);

        controlBonificacion control = new controlBonificacion();
        control.registrarSalario(diego);
        control.registrarSalario(juan);
        control.registrarSalario(pedro);


    }
}
