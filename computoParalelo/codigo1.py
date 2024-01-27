import nltk

nltk.download('punkt')
import gensim
import numpy as np
import pandas as pd
from nltk.tokenize import word_tokenize
from gensim.test.utils import common_texts
from gensim.models import Word2Vec

with open('Reglamento_transito.txt', 'r',
          encoding='utf-8') as file:
    document = file.read()
# tokenizar el documento en oraciones
sentences = nltk.sent_tokenize(document)
# tokenizar cada oracion en palabras
word_tokens = [nltk.tokenize.word_tokenize(sentence.lower()) for
               sentence in sentences]
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
