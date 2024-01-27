import numpy as np
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import MultinomialNB
from sklearn.metrics import accuracy_score, classification_report

# Cargar el conjunto de datos
data = pd.read_csv("movie_review.csv")

# Separar los datos en características (X) y etiquetas (y)
X = data['text']
y = data['tag']

# Dividir los datos en conjuntos de entrenamiento y prueba
X_train, X_test, y_train, y_test = train_test_split(X, y,test_size=0.2, random_state=42)

# Paso 4: Vectorización de texto
vectorizer = TfidfVectorizer(max_features=5000) # Convertir el texto
X_train = vectorizer.fit_transform(X_train)
X_test = vectorizer.transform(X_test)

# Paso 5: Entrenar un modelo de clasificación
clf = MultinomialNB()
clf.fit(X_train, y_train)

# Paso 6: Realizar predicciones en el conjunto de prueba
y_pred = clf.predict(X_test)

# Paso 7: Evaluar el rendimiento del modelo
accuracy = accuracy_score(y_test, y_pred)
report = classification_report(y_test, y_pred)

print(f"Precisión del modelo: {accuracy}")
print("Informe de clasificación:")
print(report)
