import numpy as np
import nltk
import re
from collections import defaultdict

# Calcular similitud de palabras
def cosine_similarity(vec1, vec2):
    dot_product = np.dot(vec1, vec2)
    norm1 = np.linalg.norm(vec1)
    norm2 = np.linalg.norm(vec2)
    similarity = dot_product / (norm1 * norm2)
    return similarity

# Cargar el texto y preprocesarlo
with open('Reglamento_transito.txt', 'r', encoding='utf-8') as file:
    document = file.read()

# Tokenización de palabras y eliminación de stopwords, números romanos y palabras cortas
stopwords = set(nltk.corpus.stopwords.words('spanish'))
sentences = nltk.sent_tokenize(document)
word_tokens = [nltk.word_tokenize(sentence.lower()) for sentence in sentences]
word_tokens = [[word for word in sentence if word not in stopwords and word.isalpha() and len(word) > 2 and not re.match(r'[IVXLCDMivxlcdm]+', word)] for sentence in word_tokens]
#Eliminar palabras vacias
word_tokens = [sentence for sentence in word_tokens if len(sentence) > 0]

print(word_tokens[:100])

# Crear un vocabulario único de palabras y asignar índices
word_index = {word: idx for idx, word in enumerate(set(word for sentence in word_tokens for word in sentence))}
vocab_size = len(word_index)
vocab = list(word_index.keys())

# Crear una matriz de co-ocurrencia
window_size = 2
co_occurrence_matrix = np.zeros((vocab_size, vocab_size))

for sentence in word_tokens:
    for i, target_word in enumerate(sentence):
        target_index = word_index[target_word]
        context_window = sentence[max(0, i - window_size):i] + sentence[i + 1:i + window_size + 1]

        for context_word in context_window:
            context_index = word_index[context_word]
            co_occurrence_matrix[target_index][context_index] += 1

# Aplicar SVD para obtener vectores de palabras
U, S, Vt = np.linalg.svd(co_occurrence_matrix)

# Reducir la dimensionalidad (opcional)
vector_size = 100
word_vectors = U[:, :vector_size]

# Consultar el vector de una palabra específica
target_word = "peatón"
target_index = word_index[target_word]
vector = word_vectors[target_index]
print(f"Vector de '{target_word}':", vector)

# Calcular similitud de coseno para todas las palabras
similar_words = {}
for word, idx in word_index.items():
    similarity = cosine_similarity(word_vectors[target_index], word_vectors[idx])
    similar_words[word] = similarity

# Mostrar las 10 palabras más similares a "peatón"
sorted_similar_words = sorted(similar_words.items(), key=lambda x: x[1], reverse=True)
top_similar_words = sorted_similar_words[:10]
print(f"Palabras similares a '{target_word}':")
for word, similarity in top_similar_words:
    print(f"{word}: {similarity:.4f}")
