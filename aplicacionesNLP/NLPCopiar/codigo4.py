import numpy as np
import pandas as pd
import nltk
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import accuracy_score, classification_report
from sklearn.decomposition import PCA
from sklearn.preprocessing import LabelEncoder
import matplotlib.pyplot as plt

# Cargar el conjunto de datos
data = pd.read_csv("movie_review.csv")
X = data['text']
y = data['tag']

# Convertir etiquetas no enteras a enteros utilizando LabelEncoder
label_encoder = LabelEncoder()
y = label_encoder.fit_transform(y)

# Dividir los datos en conjuntos de entrenamiento y prueba
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Preprocesamiento de texto
def preprocess_text(text):
    stopwords = set(nltk.corpus.stopwords.words('spanish'))
    word_tokens = nltk.word_tokenize(text.lower())
    filtered_words = [word for word in word_tokens if word not in stopwords and word.isalpha()]
    return " ".join(filtered_words)

X_train = [preprocess_text(sentence) for sentence in X_train]
X_test = [preprocess_text(sentence) for sentence in X_test]

# Vectorización de texto
vectorizer = TfidfVectorizer(max_features=50000)
X_train = vectorizer.fit_transform(X_train)
X_test = vectorizer.transform(X_test)

# Entrenar un modelo de clasificación (utiliza K-Nearest Neighbors en lugar de Naive Bayes)
k = 5  # Número de vecinos más cercanos a considerar (ajusta según sea necesario)
clf = KNeighborsClassifier(n_neighbors=k)
clf.fit(X_train, y_train)

# Reducción de dimensionalidad con PCA (para visualización)
pca = PCA(n_components=2)
X_train_pca = pca.fit_transform(X_train.toarray())

# Realizar predicciones en el conjunto de prueba
y_pred = clf.predict(X_test)

# Evaluación del modelo
accuracy = accuracy_score(y_test, y_pred)
report = classification_report(y_test, y_pred)

print(f"Precisión del modelo: {accuracy}")
print("Informe de clasificación:")
print(report)

# Visualización de agrupación en un gráfico de dispersión
plt.figure(figsize=(10, 6))
colors = ['b', 'r']

for label in np.unique(y_train):
    indices = np.where(y_train == label)
    plt.scatter(X_train_pca[indices, 0], X_train_pca[indices, 1], c=colors[label], label=label)

plt.title('Agrupación de Reseñas de Películas (PCA)')
plt.xlabel('Dimensión 1')
plt.ylabel('Dimensión 2')
plt.legend(['Negativo', 'Positivo'])
plt.show()

# guardar imagen
plt.savefig('imagen.png')