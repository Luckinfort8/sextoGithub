public class testGerente {
    public static void main(String[] args) {
        gerente juan = new gerente();
        juan.setName("Juan");
        juan.setDocumento("123456789-01");
        juan.setSalario(5000.00);
        juan.setTipo(1);
        juan.setClave("alura-cursos");

        System.out.println("Name: " + juan.getName());
        System.out.println("Documento: " + juan.getDocumento());
        System.out.println("Salario: " + juan.getSalario());
        System.out.println("Bonificacion: " + juan.getBonificacion());
        System.out.println("Clave: " + juan.iniciarSesion("alura-cursos"));
    }
}
