import multiprocessing
import cv2


def leerImagen(pipe1, imagen, tamanoSubconjunto):
    image = cv2.imread(imagen, cv2.IMREAD_GRAYSCALE)
    subconjunto = image[:tamanoSubconjunto[0], :tamanoSubconjunto[1]]
    coordenadas = (0, 0, tamanoSubconjunto[0], tamanoSubconjunto[1])
    pipe1.send((subconjunto, coordenadas))


def umbralOtsu(pipe1, pipe2):
    subconjunto, coordenadas = pipe1.recv()
    _, umbral = cv2.threshold(subconjunto, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)

    pipe2.send((umbral, coordenadas))


def umbralImagen(pipe2, imagen):
    umbralSubconjunto, coordenadas = pipe2.recv()
    x, y, w, h = coordenadas
    umbral_imagen = cv2.imread(imagen, cv2.IMREAD_GRAYSCALE)
    umbral_imagen[y:y + h, x:x + w] = umbralSubconjunto

    cv2.imwrite("imagenSalida.jpg", umbral_imagen)



if __name__ == "__main__":
    imagen = "sphx_glr_plot_thresholding_002.png"
    tamanoSubconjunto = (10, 10)  # Cambia el tamaño de los subconjuntos según tus necesidades

    # Crear tuberías
    pipe1 = multiprocessing.Pipe()
    pipe2 = multiprocessing.Pipe()

    proceso1 = multiprocessing.Process(target=leerImagen, args=(pipe1[0], imagen, tamanoSubconjunto))
    proceso2 = multiprocessing.Process(target=umbralOtsu, args=(pipe1[1], pipe2[0]))
    proceso3 = multiprocessing.Process(target=umbralImagen, args=(pipe2[1], imagen))

    proceso1.start()
    proceso2.start()
    proceso3.start()

    proceso1.join()
    proceso2.join()
    proceso3.join()
