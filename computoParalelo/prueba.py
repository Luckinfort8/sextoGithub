import re
import unicodedata

def preprocesar_texto(texto):
    # Dividir el texto en párrafos
    parrafos = re.split(r'\n\s*\n', texto)

    # Eliminar caracteres no alfanuméricos y caracteres no ASCII en cada párrafo
    parrafos = [re.sub(r'[^a-zA-Z0-9\sáéíóúÁÉÍÓÚüÜñÑ]', '', p) for p in parrafos]

    # Eliminar letras chinas u otros caracteres no deseados en cada párrafo
    parrafos = [''.join(char for char in p if unicodedata.category(char) != 'Lo') for p in parrafos]

    # Convertir múltiples espacios a uno solo en cada párrafo
    parrafos = [' '.join(p.split()) for p in parrafos]

    # Unir los párrafos de nuevo
    texto_preprocesado = '\n\n'.join(parrafos)

    return texto_preprocesado

# Lee tu archivo de texto
with open('pruebas.txt', 'r', encoding='utf-8') as file:
    contenido = file.read()

# Aplica la función de preprocesamiento
contenido_preprocesado = preprocesar_texto(contenido)

# Guarda el resultado en un nuevo archivo
with open('archivo_procesado.txt', 'w', encoding='utf-8') as file:
    file.write(contenido_preprocesado)
