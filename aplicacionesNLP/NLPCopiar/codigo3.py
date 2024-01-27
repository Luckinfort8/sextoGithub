import numpy as np
import pandas as pd
import nltk
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import MultinomialNB
from sklearn.metrics import accuracy_score, classification_report


# Función para cargar y dividir el conjunto de datos
def load_and_split_data(file_path, test_size=0.2):
    data = pd.read_csv(file_path)
    X = data['text']
    y = data['tag']
    return train_test_split(X, y, test_size=test_size)


# Función para preprocesar texto
def preprocess_text(text, stopwords):
    word_tokens = [nltk.word_tokenize(sentence.lower()) for sentence in text]
    word_tokens = [[word for word in sentence if word not in stopwords and word.isalpha()] for sentence in word_tokens]
    return [" ".join(sentence) for sentence in word_tokens]


# Función para entrenar y evaluar el modelo
def train_and_evaluate_model(X_train, X_test, y_train, y_test, max_features=50000):
    vectorizer = TfidfVectorizer(max_features=max_features)
    X_train = vectorizer.fit_transform(X_train)
    X_test = vectorizer.transform(X_test)

    clf = MultinomialNB()
    clf.fit(X_train, y_train)

    y_pred = clf.predict(X_test)

    accuracy = accuracy_score(y_test, y_pred)
    report = classification_report(y_test, y_pred)

    return accuracy, report


if __name__ == "__main__":
    # Cargar y dividir los datos
    X_train, X_test, y_train, y_test = load_and_split_data("movie_review.csv")

    # Preprocesar texto
    stopwords = set(nltk.corpus.stopwords.words('spanish'))
    X_train = preprocess_text(X_train, stopwords)
    X_test = preprocess_text(X_test, stopwords)

    # Entrenar y evaluar el modelo
    accuracy, report = train_and_evaluate_model(X_train, X_test, y_train, y_test)

    # Imprimir resultados
    print(f"Precisión del modelo: {accuracy}")
    print("Informe de clasificación:")
    print(report)
