import threading
import requests
import random
import string
import Levenshtein
import queue

# Creamos una base de datos
baseDatos = []  # Lista

# Creamos un candado
candadoBaseDatos = threading.Lock()

# Hacemos una enUsoFlag para saber si alguien accede a la base de datos
enUsoFlag = False  # False = nadie accede, True = alguien accede

# Creamos un candado para la variable enUsoFlag
candadoEnUsoFlag = threading.Lock()

# Semaforo para la oración
oracionSemaforo = threading.Semaphore(1)  # Solo un hilo puede acceder a la vez

# Cola para almacenar la oración
colaOracion = queue.Queue()

# Crear condiciones para esperar y notificar a los hilos
condicionGenerar = threading.Condition()
condicionConsumir = threading.Condition()


# Función para cargar la base de datos
def cargar():
    global enUsoFlag

    with candadoEnUsoFlag:  # Adquiere el candado
        if enUsoFlag:
            print("Espera, alguien está accediendo a la base de datos...")
            return
        enUsoFlag = True

    with candadoBaseDatos:
        print("Cargando datos a la base de datos...")

        try:
            response = requests.get("https://www.mit.edu/~ecprice/wordlist.10000")
            response.raise_for_status()
            palabras = response.text.splitlines()
            palabrasAleatorias = random.sample(palabras, 1000)

            baseDatos.clear()
            baseDatos.extend(palabrasAleatorias)

            print("Base de datos cargada con los datos")
            print("Contenido de la base de datos:", baseDatos)  # Agregar esta línea para mostrar el contenido
        except Exception as e:
            print("Error al cargar datos:", str(e))
        finally:
            with candadoEnUsoFlag:  # Libera el candado
                enUsoFlag = False

# Función para generar oraciones
def generarOracion():
    with candadoBaseDatos:
        if enUsoFlag:
            print("Espera, alguien está accediendo a la base de datos...")
            return

    num_palabras = random.randint(5, 10)
    sentencia = []
    for _ in range(num_palabras):
        palabra = random.choice(baseDatos)

        if random.random() < 0.3:
            indice = random.randint(0, len(palabra) - 1)
            palabraLista = list(palabra)
            letraModificada = random.choice(string.ascii_letters + '#$*=')
            palabraLista[indice] = letraModificada

            if random.random() < 0.5:
                palabraLista.append(random.choice(string.ascii_letters + '#$*='))

            palabra = "".join(palabraLista)

        sentencia.append(palabra)

    sentencia = " ".join(sentencia)

    with oracionSemaforo:
        colaOracion.put(sentencia)  # Agregar la oración a la cola
        with condicionGenerar:  # Adquiere el semáforo de generación
            condicionGenerar.notify()  # Notificar a otros hilos que la oración está lista

    print("Oración generada:", sentencia)  # Agregar esta línea para mostrar la oración generada

# Función para consumir palabras y verificar la ortografía
def consumirPalabras():
    with condicionGenerar: # Adquiere el semáforo de generación

        # Esperar a que se genere una oración
        if colaOracion.empty(): # Si la cola está vacía, imprimir mensaje y esperar
            print("Esperando a que se genere una oración...")
            condicionGenerar.wait() # El se

        oracion = colaOracion.get()  # Obtener la oración de la cola

        if oracion: # Si la oración no está vacía
            palabras = oracion.split() # Obtener las palabras de la oración

            for palabra in palabras:
                try:
                    # Verificar ortografía
                    correccion = corregirOrtografia(palabra)
                    print(f"Palabra original: {palabra}, Palabra corregida: {correccion}")
                except Exception as e:
                    print("Error al verificar ortografía:", str(e))

            with condicionConsumir:  # Adquiere el semáforo de consumo
                condicionConsumir.notify()  # Notificar a otros hilos


# Función para corregir ortografía utilizando la distancia de Levenshtein
def corregirOrtografia(palabra):
    global baseDatos

    distancia_minima = float('inf')
    palabra_corregida = palabra

    with candadoBaseDatos:  # Agregar un candado para acceder a baseDatos
        for palabra_db in baseDatos:
            distancia = Levenshtein.distance(palabra, palabra_db)
            if distancia < distancia_minima:
                distancia_minima = distancia
                palabra_corregida = palabra_db

    return palabra_corregida


# Función main
def main():
    # Crear hilos
    primerHilo = threading.Thread(target=cargar)
    segundoHilo = threading.Thread(target=generarOracion)
    tercerHilo = threading.Thread(target=consumirPalabras)

    # Iniciar hilos
    primerHilo.start()
    segundoHilo.start()
    tercerHilo.start()

    # Esperar a que los hilos terminen
    primerHilo.join()
    segundoHilo.join()
    tercerHilo.join()


if __name__ == "__main__":
    main()
