public class director extends funcionario{
    private String clave;
    public director() {

    }
    public double getBonificacion(){
        return super.getSalario()*2;
    }
}
