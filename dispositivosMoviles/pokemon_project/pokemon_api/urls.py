from django.urls import path, include
from rest_framework import routers
from pokemon_api import views

router = routers.DefaultRouter()
router.register(r'Pokemon', views.PokemonViewSet)

urlpatterns = [
    path('', include(router.urls))
]
