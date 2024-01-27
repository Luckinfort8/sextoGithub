public class controlBonificacion {
    private double totalBonificacion;
    public double registrarSalario(funcionario funcionario){
        double bonificacion = funcionario.getBonificacion();
        this.totalBonificacion += bonificacion;
        System.out.println("calculo actual: " + this.totalBonificacion);
        return this.totalBonificacion;
    }
}
