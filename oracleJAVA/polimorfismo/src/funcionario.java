public class funcionario {
    private String name;
    private String documento;
    private double salario;
    private int tipo; // 0 - funcionario, 1 - gerente, 2 - diretor


    public funcionario() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public double getBonificacion(){
        return this.salario * 0.10;
    }
}
