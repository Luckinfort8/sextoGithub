import numpy as np
import matplotlib.pyplot as plt
from sklearn.cluster import KMeans
from sklearn.metrics import pairwise_distances_argmin_min
from sklearn.linear_model import LogisticRegression

# Generación de datos para dos clases
np.random.seed(0)
n_samples = 200 # Numero de muestras por clase
class_1 = 0.6 * np.random.randn(n_samples, 2) # Generación de 200 muestras aleatorias bidimensionales de la clase 1
class_2 = 1.2 * np.random.randn(n_samples, 2) + np.array([5, 1]) # Generación de 200 muestras aleatorias bidimensionales
                                                                # de la clase 2 pero desplazadas 5 unidades en el eje x y 1 en el eje y
x = np.vstack((class_1, class_2)) # Apilamiento de las muestras de ambas clases
y = np.hstack((np.zeros(n_samples), np.ones(n_samples))) # Etiquetas de las muestras, crea un vector de ceros con una longitud igual al numero de muestras

# Visualización de los datos
plt.scatter(x[:, 0], x[:, 1], c=y, cmap=plt.cm.RdBu)
plt.xlabel('Característica 1')
plt.ylabel('Característica 2')
plt.title('Conjunto de Datos de Dos Clases')
plt.show()

# Paso 2: Encontrar los centros de las neuronas RBF mediante K-Means
n_clusters = 10 # Numero de neuronas RBF
kmeans = KMeans(n_clusters=n_clusters, random_state=0, n_init=10).fit(x) # Creación del objeto KMeans
ceters = kmeans.cluster_centers_ # Centros de las neuronas RBF

#Paso 3: Calcular el ancho de los kernels RBF
widhts = pairwise_distances_argmin_min(x, ceters)[1].mean() # Calculo de la distancia euclidiana entre los centros de las neuronas RBF

# Paso 4: Calcular las activaciones de las neuronas RBF y preparar los datos
def rbf_kernel(x, centers, width):
    return np.exp(-0.5 * (np.linalg.norm(x[:, np.newaxis] - centers, axis=2) / width) ** 2)

phi = rbf_kernel(x, ceters, widhts) # Calculo de las activaciones de las neuronas RBF

# Convertir phi a una matriz 2D
phi = phi.reshape(-1, n_clusters)

# Paso 5: Entrenar un clasificaón en las activaciones de las neuronas RBF
rbf_model = LogisticRegression()
rbf_model.fit(phi, y)

# Paso 6: Visualizar las fronteras de decisión

x_min, x_max = x[:, 0].min() - 1, x[:, 0].max() + 1

y_min, y_max = x[:, 1].min() - 1, x[:, 1].max() + 1

xx, yy = np.meshgrid(np.arange(x_min, x_max, 0.1), np.arange(y_min, y_max, 0.1))

z = rbf_model.predict(rbf_kernel(np.c_[xx.ravel(), yy.ravel()], ceters, widhts))

z = z.reshape(xx.shape)
plt.contourf(xx, yy, z, alpha=0.4)
plt.scatter(x[:, 0], x[:, 1], c=y, cmap=plt.cm.RdBu)
plt.xlabel('Característica 1')
plt.ylabel('Característica 2')
plt.title('Clasificación con una RBF')
plt.show()

# Generar 5 conjuntos de datos que se encuentren completamente separados, ubicados en las cuatro puntas del plano y uno
# en el centro

np.random.seed(0)
n_samples = 100  # Numero de muestras por clase

# Generación de 200 muestras aleatorias bidimensionales para la clase centro
class_centro = 2.4 * np.random.randn(n_samples, 2)

# Generación de 200 muestras aleatorias bidimensionales para la clase superior derecha
class_superior_derecha = 0.6 * np.random.randn(n_samples, 2) + np.array([20, 20])

# Generación de 200 muestras aleatorias bidimensionales para la clase superior izquierda
class_superior_izquierda = 0.3 * np.random.randn(n_samples, 2) + np.array([-20, 20])

# Generación de 200 muestras aleatorias bidimensionales para la clase inferior derecha
class_inferior_derecha = 1.2 * np.random.randn(n_samples, 2) + np.array([20, -20])

# Generación de 200 muestras aleatorias bidimensionales para la clase inferior izquierda
class_inferior_izquierda = 4.8 * np.random.randn(n_samples, 2) + np.array([-20, -20])

# Apilamiento de las muestras de todas las clases
x = np.vstack((class_centro, class_superior_derecha, class_superior_izquierda, class_inferior_derecha, class_inferior_izquierda))

# Etiquetas de las muestras: Asignamos etiquetas numéricas distintas a cada clase
y = np.hstack((np.zeros(n_samples), np.ones(n_samples), np.ones(n_samples) * 2, np.ones(n_samples) * 3, np.ones(n_samples) * 4))

# Visualización de los datos
plt.scatter(x[:, 0], x[:, 1], c=y, cmap=plt.cm.RdBu)
plt.xlabel('Característica 1')
plt.ylabel('Característica 2')
plt.title('Conjunto de Datos de Cinco Clases Completamente Separadas')
plt.show()

# Paso 2: Encontrar los centros de las neuronas RBF mediante K-Means
n_clusters = 5  # Numero de neuronas RBF
kmeans = KMeans(n_clusters=n_clusters, random_state=0, n_init=10).fit(x)  # Creación del objeto KMeans
ceters = kmeans.cluster_centers_  # Centros de las neuronas RBF

# Paso 3: Calcular el ancho de los kernels RBF
widhts = pairwise_distances_argmin_min(x, ceters)[1].mean()  # Calculo de la distancia euclidiana entre los centros de las neuronas RBF

# Paso 4: Calcular las activaciones de las neuronas RBF y preparar los datos
phi = rbf_kernel(x, ceters, widhts)  # Calculo de las activaciones de las neuronas RBF

# Convertir phi a una matriz 2D
phi = phi.reshape(-1, n_clusters)

# Paso 5: Entrenar un clasificaón en las activaciones de las neuronas RBF
rbf_model = LogisticRegression()
rbf_model.fit(phi, y)

# Paso 6: Visualizar las fronteras de decisión

x_min, x_max = x[:, 0].min() - 1, x[:, 0].max() + 1

y_min, y_max = x[:, 1].min() - 1, x[:, 1].max() + 1

xx, yy = np.meshgrid(np.arange(x_min, x_max, 0.1), np.arange(y_min, y_max, 0.1))

z = rbf_model.predict(rbf_kernel(np.c_[xx.ravel(), yy.ravel()], ceters, widhts))

z = z.reshape(xx.shape)
plt.contourf(xx, yy, z, alpha=0.4)
plt.scatter(x[:, 0], x[:, 1], c=y, cmap=plt.cm.RdBu)
plt.xlabel('Característica 1')
plt.ylabel('Característica 2')
plt.title('Clasificación con una RBF')
plt.show()



