from rest_framework import viewsets
from .models import Pokemon
from .serializer import PokemonSerializer


# Lista y crea Pokémon
class PokemonViewSet(viewsets.ModelViewSet):
    queryset = Pokemon.objects.all()
    serializer_class = PokemonSerializer
