import numpy as np

mod  = 1000000007

def f(n):
    a = np.array([[1, 1], [1, 0]])
    b = np.array([[1, 1], [1, 0]])
    while n != 0:
        if n % 2 == 1:
            a = a.dot(b)
        b = b.dot(b)
        n = n >> 1
    print a[0][1] % mod
    # print a[0][0] % mod


if __name__ == '__main__':
    for i in range(1, 20):
        f(i-1)