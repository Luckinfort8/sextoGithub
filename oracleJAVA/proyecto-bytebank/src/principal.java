public class principal {

    public static void main(String[] args) {
        for (Dia dia : Dia.values()){
            System.out.println("Dia: " + dia);
        }
        Dia domingo = Dia.Domingo;
        System.out.println("Dia: " + domingo.name());
        System.out.println("Dia: " + domingo.ordinal());
        System.out.println("Dia: " + domingo.toString());
    }
}
