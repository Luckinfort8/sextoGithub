public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        int edad = 20;
        System.out.println("Mi edad es: " + edad);
        edad = 21;
        System.out.println("Mi edad es: " + edad);

        double decimalxd = 10.5;
        int toInt = (int) decimalxd;
        System.out.println("Mi entero es: " + toInt);

        // Tipos de datos
        // Primitivos
        long numeroGrande = 1000000000000000000L;
        System.out.println("Mi numero grande es: " + numeroGrande);
        short numeroPequeno = 10000;
        System.out.println("Mi numero pequeño es: " + numeroPequeno);
        byte numeroMuyPequeno = 100;
        System.out.println("Mi numero muy pequeño es: " + numeroMuyPequeno);
        boolean booleano = true;
        System.out.println("Mi booleano es: " + booleano);
        char caracter = 'a';
        System.out.println("Mi caracter es: " + caracter);
        float flotante = 10.5f;
        System.out.println("Mi flotante es: " + flotante);
        double decimal = 10.5;
        System.out.println("Mi decimal es: " + decimal);
        int numero = 10;
        System.out.println("Mi numero es: " + numero);
        byte numeroByte = 10;
        System.out.println("Mi numero es: " + numeroByte);


        // Referenciados
        String cadena = "Hola mundo";
        Integer numero2 = 10;
        Double decimal2 = 10.5;
        Float flotante2 = 10.5f;
        Character caracter2 = 'a';
        Boolean booleano2 = true;

        // Operadores aritmeticos
        int numero3 = 10;
        int numero4 = 5;
        int resultado = numero3 + numero4;
        System.out.println("El resultado es: " + resultado);
        resultado = numero3 - numero4;
        System.out.println("El resultado es: " + resultado);
        resultado = numero3 * numero4;
        System.out.println("El resultado es: " + resultado);
        resultado = numero3 / numero4;
        System.out.println("El resultado es: " + resultado);
        resultado = numero3 % numero4;
        System.out.println("El resultado es: " + resultado);

        // Operadores de asignacion
        int numero5 = 10;
        numero5 += 5;
        System.out.println("El resultado es: " + numero5);
        numero5 -= 5;
        System.out.println("El resultado es: " + numero5);
        numero5 *= 5;
        System.out.println("El resultado es: " + numero5);
        numero5 /= 5;
        System.out.println("El resultado es: " + numero5);
        numero5 %= 5;
        System.out.println("El resultado es: " + numero5);

        // Operadores de comparacion
        int numero6 = 10;
        int numero7 = 5;
        boolean resultado2 = numero6 == numero7;
        System.out.println("El resultado es: " + resultado2);
        resultado2 = numero6 != numero7;
        System.out.println("El resultado es: " + resultado2);
        resultado2 = numero6 > numero7;
        System.out.println("El resultado es: " + resultado2);
        resultado2 = numero6 < numero7;
        System.out.println("El resultado es: " + resultado2);
        resultado2 = numero6 >= numero7;
        System.out.println("El resultado es: " + resultado2);
        resultado2 = numero6 <= numero7;
        System.out.println("El resultado es: " + resultado2);

        // Operadores logicos
        boolean resultado3 = numero6 == numero7 && numero6 > numero7;
        System.out.println("El resultado es: " + resultado3);
        resultado3 = numero6 == numero7 || numero6 > numero7;
        System.out.println("El resultado es: " + resultado3);
        resultado3 = !(numero6 == numero7);
        System.out.println("El resultado es: " + resultado3);

        // Operadores de incremento y decremento
        int numero8 = 10;
        numero8++;
        System.out.println("El resultado es: " + numero8);
        numero8--;
        System.out.println("El resultado es: " + numero8);
        numero8 = 10;
        System.out.println("El resultado es: " + numero8++);
        System.out.println("El resultado es: " + numero8);
        numero8 = 10;
        System.out.println("El resultado es: " + ++numero8);
        System.out.println("El resultado es: " + numero8);

        // Condicionales
        int numero9 = 10;
        if (numero9 == 10) {
            System.out.println("El numero es 10" + "\n");
        } else if (numero9 == 20) {
            System.out.println("El numero es 20" + "\n");
        } else {
            System.out.println("El numero no es 10 ni 20" + "\n");
        }

        int edad2 = 19;
        int cantidadPersonas = 3;
        boolean acompanado = cantidadPersonas >= 2;

        if (edad2 >= 18 && acompanado) {
            System.out.println("Bienvenido");
        } else {
            System.out.println("Lamentablemente no puedes ingresar");
        }

        double valorCompra = 250.0;
        double descuento = 0.0;
        if (valorCompra >= 100 && valorCompra <= 199.99) {
            System.out.println("El descuento es de 10%");
            descuento = valorCompra * 0.1;
        } else if (valorCompra >= 200 && valorCompra <= 299.99) {
            System.out.println("El descuento es de 15%");
            descuento = valorCompra * 0.15;
        } else if (valorCompra >= 300) {
            System.out.println("El descuento es de 25%");
            descuento = valorCompra * 0.25;
        } else {
            System.out.println("No hay descuento");
        }
        System.out.println("El valor de la prenda con descuento es:" + (valorCompra - descuento));

        // Switch
        int numero10 = 10;
        switch (numero10) {
            case 10:
                System.out.println("El numero es 10");
                break;
            case 20:
                System.out.println("El numero es 20");
                break;
            default:
                System.out.println("El numero no es 10 ni 20");
                break;
        }

        // Ciclo for
        for (int i = 0; i <= 10; i++) {
            System.out.println("El valor de i es: " + i);
        }

        // Ciclo while
        int contador = 0;
        int suma = 0;
        while (contador <= 10) {
            System.out.println("El valor de i es: " + contador);
            suma = suma + contador;
            contador++;
        }
        System.out.println("La suma es: " + suma);

        // Ciclo do while
        int contador2 = 0;
        do {
            System.out.println("El valor de j es: " + contador2);
            contador2++;
        } while (contador2 <= 10);

        // Ciclos anidados
        int contador3 = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print("#" + contador3 + "\t");
                contador3++;
            }
            System.out.print("\n");
        }

        // ciclo factorial
        int factorial = 1;
        for (int i = 1; i <= 10; i++) {
            factorial = factorial * i;
        }
        System.out.println("El factorial de 10 es: " + factorial);

        // Arreglos

    }
}
