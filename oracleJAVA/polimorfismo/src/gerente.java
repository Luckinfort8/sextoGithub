public class gerente extends funcionario {
    private String clave;
    public gerente() {

    }


    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean iniciarSesion(String clave){
        return clave == "alura-cursos";
    }
    public double getBonificacion(){
        return super.getSalario();
    }
}
