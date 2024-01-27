import numpy as np
import matplotlib.pyplot as plt


import numpy as np

class HopfieldNetwork:
    def __init__(self, size):
        """
        Initialize a Hopfield Network.

        Parameters:
        - size (int): Number of neurons in the network.
        """
        if not isinstance(size, int) or size <= 0:
            raise ValueError("Size must be a positive integer.")
        self.size = size
        self.weights = np.zeros((size, size))

    def train(self, patterns):
        """
        Train the Hopfield Network with given patterns.

        Parameters:
        - patterns (list of numpy arrays): List of patterns to train the network.
        """
        for pattern in patterns:
            pattern = pattern.reshape(-1, 1)
            self.weights += np.outer(pattern, pattern)
            np.fill_diagonal(self.weights, 0)

    def predict(self, pattern, max_iters=2):
        """
        Predict the output pattern for a given input pattern.

        Parameters:
        - pattern (numpy array): Input pattern.
        - max_iters (int): Maximum number of iterations.
        - tol (float): Convergence tolerance.

        Returns:
        - numpy array: Predicted pattern.
        """
        pattern = pattern.reshape(-1, 1)
        for _ in range(max_iters):
            net_input = np.dot(self.weights, pattern)
            new_pattern = np.sign(net_input)
            if np.allclose(new_pattern, pattern):
                break
            pattern = new_pattern
        return pattern.reshape(-1)


def main():
    # matriz con representación de numeros de 7x5
    #Numero cero
    x0= np.array([[-1,-1,-1,-1,-1],
                  [-1,1,1,1,-1],
                  [-1,1,-1,1,-1],
                  [-1,1,-1,1,-1],
                  [-1,1,-1,1,-1],
                  [-1,1,1,1,-1],
                  [-1,-1,-1,-1,-1]])

    #Numero uno
    x1= np.array([[-1,-1,-1,-1,-1],
                  [-1,-1,1,-1,-1],
                  [-1,-1,1,-1,-1],
                  [-1,-1,1,-1,-1],
                  [-1,-1,1,-1,-1],
                  [-1,-1,1,-1,-1],
                  [-1,-1,-1,-1,-1]])

    #Numero dos
    x2= np.array([[-1,-1,-1,-1,-1],
                  [1,1,1,1,-1],
                  [1,-1,-1,1,-1],
                  [-1,1,1,1,-1],
                  [-1,1,-1,-1,-1],
                  [-1,1,1,1,1],
                  [-1,-1,-1,-1,-1]])

    #Numero tres
    x3= np.array([[-1,-1,-1,-1,-1],
                  [-1,1,1,1,-1],
                  [-1,-1,-1,1,-1],
                  [-1,1,1,1,-1],
                  [-1,-1,-1,1,-1],
                  [-1,1,1,1,-1],
                  [-1,-1,-1,-1,-1]])

    #Numero cuatro
    x4= np.array([[-1,-1,-1,-1,-1],
                  [-1,1,-1,1,-1],
                  [-1,1,-1,1,-1],
                  [-1,1,1,1,-1],
                  [-1,-1,-1,1,-1],
                  [-1,-1,-1,1,-1],
                  [-1,-1,-1,-1,-1]])

    #Numero cinco
    x5= np.array([[-1,-1,-1,-1,-1],
                  [-1,1,1,1,-1],
                  [-1,1,-1,-1,-1],
                  [-1,1,1,1,-1],
                  [-1,-1,-1,1,-1],
                  [-1,1,1,1,-1],
                  [-1,-1,-1,-1,-1]])

    #Numero seis
    x6= np.array([[-1,-1,-1,-1,-1],
                  [-1,1,1,1,-1],
                  [-1,1,-1,-1,-1],
                  [-1,1,1,1,-1],
                  [-1,1,-1,1,-1],
                  [-1,1,1,1,-1],
                  [-1,-1,-1,-1,-1]])

    #Numero siete
    x7= np.array([[-1,-1,-1,-1,-1],
                  [-1,1,1,1,-1],
                  [-1,-1,-1,1,-1],
                  [-1,-1,-1,1,-1],
                  [-1,-1,-1,1,-1],
                  [-1,-1,-1,1,-1],
                  [-1,-1,-1,-1,-1]])

    #Numero ocho
    x8= np.array([[-1,-1,-1,-1,-1],
                  [-1,1,1,1,-1],
                  [-1,1,-1,1,-1],
                  [-1,1,1,1,-1],
                  [-1,1,-1,1,-1],
                  [-1,1,1,1,-1],
                  [-1,-1,-1,-1,-1]])

    #Numero nueve
    x9= np.array([[-1,-1,-1,-1,-1],
                  [-1,1,1,1,-1],
                  [-1,1,-1,1,-1],
                  [-1,1,1,1,-1],
                  [-1,-1,-1,1,-1],
                  [-1,-1,-1,1,-1],
                  [-1,-1,-1,-1,-1]])

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

        np.array([[-1, -1, -1, -1, -1],
                  [1, 1, 1, 1, -1],
                  [1, -1, -1, 1, -1],
                  [-1, 1, 1, 1, -1],
                  [-1, 1, -1, -1, -1],
                  [-1, 1, 1, 1, 1],
                  [-1, -1, -1, -1, -1]])
    ]

    #Crear y entrenar la red Hopfield
    hopfield = HopfieldNetwork(size=7*5)
    hopfield.train(input_matrices)



    #Plotear los numeros
    plt.subplot(4,4,1)
    plt.imshow(x0, cmap=plt.cm.gray_r, interpolation='nearest')
    plt.title('Numero -1')
    plt.axis('off')

    plt.subplot(4,4,2)
    plt.imshow(x1, cmap=plt.cm.gray_r, interpolation='nearest')
    plt.title('Numero 1')
    plt.axis('off')

    plt.subplot(4,4,3)
    plt.imshow(x2, cmap=plt.cm.gray_r, interpolation='nearest')
    plt.title('Numero 2')
    plt.axis('off')

    plt.subplot(4,4,4)
    plt.imshow(x3, cmap=plt.cm.gray_r, interpolation='nearest')
    plt.title('Numero 3')
    plt.axis('off')

    plt.subplot(4,4,5)
    plt.imshow(x4, cmap=plt.cm.gray_r, interpolation='nearest')
    plt.title('Numero 4')
    plt.axis('off')

    plt.subplot(4,4,6)
    plt.imshow(x5, cmap=plt.cm.gray_r, interpolation='nearest')
    plt.title('Numero 5')
    plt.axis('off')

    plt.subplot(4,4,7)
    plt.imshow(x6, cmap=plt.cm.gray_r, interpolation='nearest')
    plt.title('Numero 6')
    plt.axis('off')

    plt.subplot(4,4,8)
    plt.imshow(x7, cmap=plt.cm.gray_r, interpolation='nearest')
    plt.title('Numero 7')
    plt.axis('off')

    plt.subplot(4,4,9)
    plt.imshow(x8, cmap=plt.cm.gray_r, interpolation='nearest')
    plt.title('Numero 8')
    plt.axis('off')

    plt.subplot(4,4,10)
    plt.imshow(x9, cmap=plt.cm.gray_r, interpolation='nearest')
    plt.title('Numero 9')
    plt.axis('off')

    # Probar la red Hopfield con patrones ruidosos
    # Numero cero con ruido
    x0_noise = np.array([[1,1,1,1,1],
                          [1,1,1,1,1],
                          [1,1,-1,1,1],
                          [1,1,-1,1,-1],
                          [1,1,-1,1,-1],
                          [1,1,1,1,-1],
                          [1,-1,-1,-1,-1]])

    # Ploteo del numero cero con ruido
    plt.subplot(4, 4, 11)
    plt.imshow(x0_noise, cmap=plt.cm.gray_r, interpolation='nearest')
    plt.title('0 con ruido')

    # Numero seis predicho
    x0_pred = hopfield.predict(x0_noise)

    # Ploteo del numero seis predicho
    plt.subplot(4, 4, 12)
    plt.imshow(x0_pred.reshape(7, 5), cmap=plt.cm.gray_r, interpolation='nearest')
    plt.title('0 predicho')

    x1_noise = np.array([[-1,-1,-1,-1,-1],
                          [-1,-1,1,-1,-1],
                          [-1,-1,1,-1,-1],
                          [-1,-1,1,-1,-1],
                          [-1,-1,1,-1,-1],
                          [-1,-1,1,-1,-1],
                          [-1,-1,-1,-1,-1]])

    # ploteo del numero cinco con ruido
    plt.subplot(4, 4, 13)
    plt.imshow(x1_noise, cmap=plt.cm.gray_r, interpolation='nearest')
    plt.title('1 con ruido')

    # Numero cinco predicho
    x1_pred = hopfield.predict(x1_noise)

    # Ploteo del numero cinco predicho
    plt.subplot(4, 4, 14)
    plt.imshow(x1_pred.reshape(7, 5), cmap=plt.cm.gray_r, interpolation='nearest')
    plt.title('1 predicho')

    x2_noise = np.array([[-1, -1, -1, -1, -1],
                          [1, 1, 1, 1, -1],
                          [1, -1, -1, 1, -1],
                          [-1, 1, 1, 1, -1],
                          [-1, 1, -1, -1, -1],
                          [-1, 1, 1, 1, 1],
                          [-1, -1, -1, -1, -1]])

    # ploteo del numero cinco con ruido
    plt.subplot(4, 4, 15)
    plt.imshow(x2_noise, cmap=plt.cm.gray_r, interpolation='nearest')
    plt.title('2 con ruido')

    # Numero cinco predicho
    x2_pred = hopfield.predict(x2_noise)

    # Ploteo del numero cinco predicho
    plt.subplot(4, 4, 16)
    plt.imshow(x2_pred.reshape(7, 5), cmap=plt.cm.gray_r, interpolation='nearest')
    plt.title('2 predicho')



    plt.show()

if __name__ == '__main__':
    main()