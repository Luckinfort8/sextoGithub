import random
import time
import matplotlib.pyplot as plt


def generar_puntos():
    x = random.uniform(-1, 1)
    y = random.uniform(-1, 1)
    origenDistancia = x ** 2 + y ** 2
    if origenDistancia <= 1:
        return x, y, 1
    else:
        return x, y, 0

def secuencial(n):
    totalPuntos = n
    puntosDentroCirculo = 0

    # Listas para almacenar las coordenadas de los puntos dentro y fuera del círculo
    xCirculo = []
    yCirculo = []
    xCuadrado = []
    yCuadrado = []

    inicioEjecucion = time.time()

    for _ in range(n):
        x, y, dentro = generar_puntos()
        if dentro:
            puntosDentroCirculo += 1
            xCirculo.append(x)
            yCirculo.append(y)
        else:
            xCuadrado.append(x)
            yCuadrado.append(y)

    pi = 4 * puntosDentroCirculo / totalPuntos

    finalEjecucion = time.time()

    print("PI estimado:", pi)
    print("Total de puntos:", n)
    print("Puntos Dentro Circulo:", puntosDentroCirculo)
    print("Puntos Fuera Circulo:", n - puntosDentroCirculo)
    print("Tiempo de ejecución:", finalEjecucion - inicioEjecucion, "segundos")

    # Graficar los puntos dentro y fuera del círculo
    #plt.figure(figsize=(6, 6))
    #plt.scatter(xCirculo, yCirculo, color='blue', s=2, label='Dentro del círculo')
    #plt.scatter(xCuadrado, yCuadrado, color='red', s=2, label='Fuera del círculo')
    #plt.xlim(-1, 1)
    #plt.ylim(-1, 1)
    #plt.gca().set_aspect('equal', adjustable='box')
    #plt.title(f"PI Secuencial, n={n}, Tiempo: {finalEjecucion - inicioEjecucion:.2f} segundos")
    #plt.xlabel("Eje X")
    #plt.ylabel("Eje Y")
    #plt.show()


if __name__ == '__main__':
    secuencial(500000000)
