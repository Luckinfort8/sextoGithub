import nltk

nltk.download('punkt')
nltk.download('stopwords')
import gensim
import numpy as np
import pandas as pd
from nltk.tokenize import word_tokenize
from gensim.test.utils import common_texts
from gensim.models import Word2Vec

#Abrir y tokenizar el documento

with open('Reglamento_transito.txt', 'r', encoding='utf-8') as file:
    document = file.read()
# Tokenizar el texto en palabras
sentences = nltk.sent_tokenize(document)
word_tokens = [nltk.word_tokenize(sentence.lower()) for sentence in sentences]

# Eliminar stopwords y palabras no alfabéticas
stopword = nltk.corpus.stopwords.words('spanish')

word_tokens = [[word.lower() for word in sentence if word.lower() not in stopword and word.isalpha()]
               for sentence in word_tokens]
# Eliminar números romanos con regex
import re

word_tokens = [[word for word in sentence if not re.match(r'[IVXLCDMivxlcdm]+', word)]
               for sentence in word_tokens]

# Eliminar palabras de longitud 2 o menos
word_tokens = [[word for word in sentence if len(word) > 2]
               for sentence in word_tokens]
#Eliminar palabras vacias
word_tokens = [sentence for sentence in word_tokens if len(sentence) > 0]


# Modelo word2vec
modelo_w2v = gensim.models.Word2Vec(sentences=word_tokens, vector_size=32,
                                    window=10, min_count=1, workers=4)
modelo_w2v.save("word2vec.model")

model = Word2Vec.load("word2vec.model")
model.train(sentences, total_examples=1, epochs=100)

#sims = modelo_w2v.wv.most_similar('carretera', topn=10)
sims = model.wv.most_similar('auto', topn=10)
print(type(sims))
df = pd. DataFrame (sims). T
print(df)
