from mpi4py import MPI

def odd(number):
    if (number % 2) == 0:
        return False
    else :
        return True

def main():
    comm = MPI.COMM_WORLD
    id = comm.Get_rank()
    numProcesses = comm.Get_size()
    myHostName = MPI.Get_processor_name()

    if numProcesses > 1 and not odd(numProcesses):
        sendValue = id

        if odd(id):
            # Procesos impares
            comm.send(sendValue, dest=id - 1)
            receivedValue = comm.recv(source=id - 1)
        else:
            # Procesos pares
            comm.send(sendValue, dest=id + 1)
            receivedValue = comm.recv(source=id + 1)

        comm.barrier()  # Barrera de sincronización para asegurar que todos los procesos estén listos

        print("Process {} of {} on {} computed {} and received {}".format(id, numProcesses, myHostName, sendValue, receivedValue))
    else:
        if id == 0:
            print("Please run this program with a positive and even number of processes")

if __name__ == '__main__':
    main()
