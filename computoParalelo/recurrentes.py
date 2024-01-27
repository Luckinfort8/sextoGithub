import numpy as np

def softmax(x):
    e_x = np.exp(x - np.max(x))  # Restar np.max(x) para evitar problemas numéricos
    return e_x / e_x.sum(axis=0)

# Ejemplo de un vector de salida de una red neuronal recurrente
vector_de_salida = np.array([1.87556260466954, 1.0840300581148, 0.928476463977578, 1.25472155968632])

# Aplicar softmax al vector de salida
probabilidades = softmax(vector_de_salida)

# Imprimir las probabilidades resultantes
print("Vector de salida:", vector_de_salida)
print("Probabilidades después de softmax:", probabilidades)
print("Suma de probabilidades:", np.sum(probabilidades))
