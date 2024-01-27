import numpy as np
import sys
from mpi4py import MPI
from mpi4py.MPI import ANY_SOURCE

comm = MPI.COMM_WORLD
rank = comm.Get_rank()
size = comm.Get_size()

# takes in command-line arguments [a,b,n]
a = float(sys.argv[1])  # left endpoint
b = float(sys.argv[2])  # right endpoint
n = int(sys.argv[3])  # number of trapezoids

# we arbitrarily define a function to integrate
def f(x):
    return x*x

# this is the serial version of the trapezoidal rule
# parallelization occurs by dividing the range among processes

def integrateRange(a, b, n):
    integral = -(f(a) + f(b))/2.0
    # n+1 endpoints, but n trapazoids
    for x in np.linspace(a, b, int(n) + 1):
        integral = integral + f(x)
    integral = integral* (b-a)/n
    return integral

# h is the step size. n is the total number of trapezoids
h = (b-a)/n
# local_n is the number of trapezoids each process will calculate
# note that size must divide n
local_n = n/size

# we calculate the interval that each process handles
# local_a is the left endpoint of the interval for each process

local_a = a + rank*local_n*h
local_b = local_a + local_n*h

#initializing variables. mpi4py requires that we pass numpy objects.
integral = np.zeros(1)
total = np.zeros(1)

# perform local computation. Each process integrates its own interval
integral[0] = integrateRange(local_a, local_b, local_n)

# communication
# root node receives results with a collective "reduce"
comm.Reduce(integral, total, op=MPI.SUM, root=0)

# root process prints results
if comm.rank == 0:
    print("With n =", n, "trapezoids, our estimate of the integral from", a, "to", b, "is", total[0])

