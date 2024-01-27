from django.db import models


class Pokemon(models.Model):
    nombre = models.CharField(max_length=100)
    altura = models.PositiveSmallIntegerField()
    peso = models.PositiveSmallIntegerField()
