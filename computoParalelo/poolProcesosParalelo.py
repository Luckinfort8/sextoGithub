import random
import time
import matplotlib.pyplot as plt
from multiprocessing import Pool


def generar_puntos(_):
    x = random.uniform(-1, 1)
    y = random.uniform(-1, 1)
    origenDistancia = x ** 2 + y ** 2
    if origenDistancia <= 1:
        return x, y, 1
    else:
        return x, y, 0


def paralelo(n):
    totalPuntos = n
    puntosDentroCirculo = 0

    # Graficar los puntos dentro y fuera del círculo
    xCirculo = []
    yCirculo = []
    xCuadrado = []
    yCuadrado = []

    inicioEjecucion = time.time()

    with Pool(processes=16) as pool:
        resultados = pool.map(generar_puntos, range(n))

    for x, y, dentro in resultados:
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

    #plt.figure(figsize=(6, 6))
    #plt.scatter(xCirculo, yCirculo, color='blue', s=2, label='Dentro del círculo')
    #plt.scatter(xCuadrado, yCuadrado, color='red', s=2, label='Fuera del círculo')
    #plt.xlim(-1, 1)
    #plt.ylim(-1, 1)
    #plt.gca().set_aspect('equal', adjustable='box')
    #plt.title(f"PI Paralelo, n={n}, Tiempo: {finalEjecucion - inicioEjecucion:.2f} segundos")
    #plt.xlabel("Eje X")
    #plt.ylabel("Eje Y")
    #plt.show()


if __name__ == '__main__':
    paralelo(500000000)
