import numpy as np
import nltk

# Calcular similitud de palabras
def cosine_similarity(vec1, vec2):
    dot_product = np.dot(vec1, vec2)
    norm1 = np.linalg.norm(vec1)
    norm2 = np.linalg.norm(vec2)

    if norm1 == 0.0 or norm2 == 0.0:
        return 0.0  # Evita la división por cero

    similarity = dot_product / (norm1 * norm2)
    return similarity


# Leer el conjunto de datos desde un archivo de texto plano (reemplaza 'dataset.txt' con tu archivo)
with open('Reglamento_transito.txt', 'r', encoding='utf-8') as file:
    sentences = [line.strip().split() for line in file]

# tokenizar cada oracion en palabras
word_tokens = [nltk.tokenize.word_tokenize(' '.join(sentence).lower()) for sentence in sentences]

#Quitar stopwords
stopword = nltk.corpus.stopwords.words('spanish')

#Convertir todo a minusculas
word_tokens = [[word.lower() for word in sentence]
                for sentence in word_tokens]

# eliminar las palabras vacias
word_tokens = [[word for word in sentence if word not in stopword]
                for sentence in word_tokens]

#Eliminar todo lo que no sean letras
word_tokens = [[word for word in sentence if word.isalpha()]
                for sentence in word_tokens]
#Eliminar numeros romanos con regex
import re
word_tokens = [[word for word in sentence if not re.match(r'[IVXLCDM]+', word)]
                for sentence in word_tokens]

#Eliminar palabras de longitud 2 o menos
sentences = [[word for word in sentence if len(word) > 2]
                for sentence in word_tokens]

# Crear un vocabulario único de palabras
vocab = list(set(word for sentence in sentences for word in sentence))
vocab.sort()

# Crear una matriz de co-ocurrencia
window_size = 2
co_occurrence_matrix = np.zeros((len(vocab), len(vocab)))

for sentence in sentences:
    for i, target_word in enumerate(sentence):
        target_index = vocab.index(target_word)
        context_window = sentence[max(0, i - window_size):i] + sentence[i + 1:i + window_size + 1]

        for context_word in context_window:
            context_index = vocab.index(context_word)
            co_occurrence_matrix[target_index][context_index] += 1

# Aplicar SVD para obtener vectores de palabras
U, S, Vt = np.linalg.svd(co_occurrence_matrix)

# Reducir la dimensionalidad (opcional)
vector_size = 100
word_vectors = U[:, :vector_size]

# Consultar el vector de una palabra específica
target_word = "peatón"
target_index = vocab.index(target_word)
vector = word_vectors[target_index]
print(f"Vector de '{target_word}':", vector)


similar_words = {}
for word in vocab:
    word_index = vocab.index(word)
    similarity = cosine_similarity(word_vectors[target_index], word_vectors[word_index])
    similar_words[word] = similarity

sorted_similar_words = sorted(similar_words.items(), key=lambda x: x[1], reverse=True)
top_similar_words = sorted_similar_words[:3]
print(f"Palabras similares a '{target_word}': {top_similar_words}")