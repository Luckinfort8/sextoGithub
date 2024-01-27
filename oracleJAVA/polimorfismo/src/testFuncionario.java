public class testFuncionario {
    public static void main(String[] args) {
        funcionario diego = new funcionario();
        diego.setName("Diego");
        diego.setDocumento("123456789-00");
        diego.setSalario(2000.00);
        diego.setTipo(0);

        gerente juan = new gerente();
        juan.setName("Juan");
        juan.setDocumento("123456789-01");
        juan.setSalario(5000.00);
        juan.setTipo(1);
        juan.setClave("alura-cursos");

        System.out.println("Name: " + diego.getName());
        System.out.println("Documento: " + diego.getDocumento());
        System.out.println("Salario: " + diego.getSalario());
        System.out.println("Bonificacion: " + diego.getBonificacion());

        System.out.println("Name: " + juan.getName());
        System.out.println("Documento: " + juan.getDocumento());
        System.out.println("Salario: " + juan.getSalario());
        System.out.println("Bonificacion: " + juan.getBonificacion());
        System.out.println("Clave: " + juan.iniciarSesion("alura-cursos"));

    }
}
