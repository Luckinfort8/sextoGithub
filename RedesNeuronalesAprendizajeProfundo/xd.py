import numpy as np
import matplotlib.pyplot as plt

def hopfield_train(input_matrices):
    num_patterns, pattern_size = len(input_matrices), len(input_matrices[0].flatten())
    weight_matrix = np.zeros((pattern_size, pattern_size))

    for pattern in input_matrices:
        pattern_flat = pattern.flatten()
        weight_matrix += np.outer(pattern_flat, pattern_flat)

    np.fill_diagonal(weight_matrix, 0)
    weight_matrix /= pattern_size

    return weight_matrix

def hopfield_test(weight_matrix, input_pattern, max_iterations=100):
    pattern_size = len(input_pattern.flatten())
    input_pattern_flat = input_pattern.flatten()

    for _ in range(max_iterations):
        new_pattern = np.sign(weight_matrix @ input_pattern_flat)
        if np.array_equal(new_pattern, input_pattern_flat):
            break
        input_pattern_flat = new_pattern

    return new_pattern.reshape(input_pattern.shape)

def plot_pattern(pattern, title):
    plt.imshow(pattern, cmap='gray', interpolation='nearest')
    plt.title(title)
    plt.show()

#Definir las matrices de pesos
input_matrices = [
    np.array([[-1,-1,-1,-1,-1],
              [-1,1,1,1,-1],
              [-1,1,-1,1,-1],
              [-1,1,-1,1,-1],
              [-1,1,-1,1,-1],
              [-1,1,1,1,-1],
              [-1,-1,-1,-1,-1]]),

    np.array([[-1,-1,-1,-1,-1],
              [-1,-1,1,-1,-1],
              [-1,-1,1,-1,-1],
              [-1,-1,1,-1,-1],
              [-1,-1,1,-1,-1],
              [-1,-1,1,-1,-1],
              [-1,-1,-1,-1,-1]]),

    np.array([[-1,-1,-1,-1,-1],
             [-1,1,1,1,-1],
             [-1,-1,-1,1,-1],
             [-1,1,1,1,-1],
             [-1,1,-1,-1,-1],
             [-1,1,1,1,-1],
             [-1,-1,-1,-1,-1]]),

    np.array([[-1,-1,-1,-1,-1],
                [-1,1,1,1,-1],
                [-1,-1,-1,1,-1],
                [-1,1,1,1,-1],
                [-1,-1,-1,1,-1],
                [-1,1,1,1,-1],
                [-1,-1,-1,-1,-1]]),

    np.array([[-1,-1,-1,-1,-1],
                [-1,1,-1,1,-1],
                [-1,1,-1,1,-1],
                [-1,1,1,1,-1],
                [-1,-1,-1,1,-1],
                [-1,-1,-1,1,-1],
                [-1,-1,-1,-1,-1]]),

    np.array([[-1,-1,-1,-1,-1],
                [-1,1,1,1,-1],
                [-1,1,-1,-1,-1],
                [-1,1,1,1,-1],
                [-1,-1,-1,1,-1],
                [-1,1,1,1,-1],
                [-1,-1,-1,-1,-1]]),

    np.array([[-1,-1,-1,-1,-1],
                [-1,1,1,1,-1],
                [-1,1,-1,-1,-1],
                [-1,1,1,1,-1],
                [-1,1,-1,1,-1],
                [-1,1,1,1,-1],
                [-1,-1,-1,-1,-1]]),

    np.array([[-1,-1,-1,-1,-1],
                [-1,1,1,1,-1],
                [-1,-1,-1,1,-1],
                [-1,-1,-1,1,-1],
                [-1,-1,-1,1,-1],
                [-1,-1,-1,1,-1],
                [-1,-1,-1,-1,-1]]),

    np.array([[-1,-1,-1,-1,-1],
                [-1,1,1,1,-1],
                [-1,1,-1,1,-1],
                [-1,1,1,1,-1],
                [-1,1,-1,1,-1],
                [-1,1,1,1,-1],
                [-1,-1,-1,-1,-1]]),

    np.array([[-1,-1,-1,-1,-1],
                [-1,1,1,1,-1],
                [-1,1,-1,1,-1],
                [-1,1,1,1,-1],
                [-1,-1,-1,1,-1],
                [-1,-1,-1,1,-1],
                [-1,-1,-1,-1,-1]])
]

# Entrenar la red Hopfield
weight_matrix = hopfield_train(input_matrices)

# Definir el patrón de entrada con ruido
x6_noise = np.array([[1, 1, 1, -1, -1],
                     [-1, 1, 1, 1, -1],
                     [-1, 1, -1, -1, -1],
                     [-1, 1, 1, 1, -1],
                     [-1, 1, -1, 1, -1],
                     [-1, 1, 1, 1, -1],
                     [-1, 1, 1, -1, -1]])

# Realizar el test con el patrón de entrada ruidoso
result_pattern = hopfield_test(weight_matrix, x6_noise)

# Plot de los patrones originales
for i, pattern in enumerate(input_matrices, 0):
    plot_pattern(pattern, f'Número {i}')

# Plot del patrón con ruido
plot_pattern(x6_noise, 'Patrón con Ruido')

# Plot del resultado después del test
plot_pattern(result_pattern, 'Resultado después del Test')