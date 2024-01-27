from mpi4py import MPI
import numpy as np
import sys

comm = MPI.COMM_WORLD
rank = comm.Get_rank()
size = comm.Get_size()

# read in from command line
n = int(sys.argv[1])  # length of vectors

# arbitrary example vectors, generated to be evenly divided by the number of
# processes for convenience

x = np.linspace(0, 100, n) if comm.rank == 0 else None
y = np.linspace(20, 300, n) if comm.rank == 0 else None

# initialize as numpy arrays
dot = np.array([0.])
local_n = np.array([0])

if rank == 0:
    if n != y.size:
        print("vector length mismatch")
        comm.Abort()
    if n % size != 0:
        print("the number of processors must evenly divide n.")
        comm.Abort()

    # length of each process's portion of the original vector
    local_n = np.array([n / size])

# communicate local array size to all processes
comm.Bcast(local_n, root=0)

# initialize as numpy arrays
x_local = np.zeros(int(local_n[0]))
y_local = np.zeros(int(local_n[0]))



# divide up vectors
comm.Scatter(x, x_local, root=0)
comm.Scatter(y, y_local, root=0)

# local computation of dot product
local_dot = np.array([np.dot(x_local, y_local)])

# sum the results of each
comm.Reduce(local_dot, dot, op=MPI.SUM, root=0)

if rank == 0:
    print("The dot product is", dot[0], "computed in parallel.")
    print("and", np.dot(x, y), "computed serially.")

