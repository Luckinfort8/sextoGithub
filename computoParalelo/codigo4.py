import multiprocessing
import cv2

def aplicarSubconjunto(pipe1, imagen, tamanoSubconjunto):
    image = cv2.imread(imagen, cv2.IMREAD_GRAYSCALE)
    height, width = image.shape[:2]

    for x in range(0, width, tamanoSubconjunto[1]):
        for y in range(0, height, tamanoSubconjunto[0]):
            subconjunto = image[y:y + tamanoSubconjunto[0], x:x + tamanoSubconjunto[1]]
            coordenadas = (x, y, tamanoSubconjunto[1], tamanoSubconjunto[0])
            pipe1.send((subconjunto, coordenadas))

def umbralOtsu(pipe1, pipe2):
    while True:
        subconjunto, coordenadas = pipe1.recv()
        if subconjunto is None:
            break
        _, umbral = cv2.threshold(subconjunto, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
        pipe2.send((umbral, coordenadas))

def umbralImagen(pipe2, imagen, output):
    umbral_imagen = cv2.imread(imagen, cv2.IMREAD_GRAYSCALE)
    while True:
        umbralSubconjunto, coordenadas = pipe2.recv()
        if umbralSubconjunto is None:
            break
        x, y, w, h = coordenadas
        umbral_imagen[y:y + h, x:x + w] = umbralSubconjunto

    cv2.imwrite(output, umbral_imagen)

if __name__ == "__main__":
    imagen = "sphx_glr_plot_thresholding_002.png"
    tamanoSubconjunto = (30, 30)  # Cambia el tamaño de los subconjuntos según tus necesidades
    output_filename = "imagenSalida.jpg"

    # Crear tuberías
    pipe1 = multiprocessing.Pipe()
    pipe2 = multiprocessing.Pipe()

    proceso1 = multiprocessing.Process(target=aplicarSubconjunto, args=(pipe1[0], imagen, tamanoSubconjunto))
    proceso2 = multiprocessing.Process(target=umbralOtsu, args=(pipe1[1], pipe2[0]))
    proceso3 = multiprocessing.Process(target=umbralImagen, args=(pipe2[1], imagen, output_filename))

    proceso1.start()
    proceso2.start()
    proceso3.start()

    proceso1.join()
    # Indicar a los otros procesos que han terminado
    pipe1[0].send((None, None))
    pipe1[1].send((None, None))
    proceso2.join()
    pipe2[0].send((None, None))
    pipe2[1].send((None, None))
    proceso3.join()
