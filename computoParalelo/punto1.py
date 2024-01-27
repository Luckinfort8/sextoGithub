from mpi4py import MPI
import numpy as np

# Inicializar MPI
comm = MPI.COMM_WORLD
rank = comm.Get_rank()
size = comm.Get_size()

# Tamaño del arreglo
medida_arreglo = 40

# Calcular el rango de valores a procesar para cada proceso
rango = medida_arreglo // size
inicio = rank * rango
fin = inicio + rango

# Asegurarse de que el último proceso se encargue de los elementos restantes
if rank == size - 1:
    fin = medida_arreglo

# Arreglo local con valores aleatorios
arreglo_local = np.random.rand(fin - inicio)

# Loos arreglos locales en el proceso maestro
if rank == 0:
    arreglo_completo = np.empty(medida_arreglo, dtype=float)
else:
    arreglo_completo = None

comm.Gather(sendbuf=arreglo_local, recvbuf=arreglo_completo, root=0)

# Arreglos parciales
print(f"Proceso {rank} - Arreglo parcial: {arreglo_local}")
comm.Barrier()

# Tamaño de los arreglos parciales y finales
print(f"Proceso {rank} - Tamano del arreglo parcial: {len(arreglo_local)}")
comm.Barrier()

if rank == 0:
    print(f"Arreglo final: {arreglo_completo}")
    print(f"Tamano del arreglo final: {len(arreglo_completo)}")

# Finalizar el MPI
MPI.Finalize()
