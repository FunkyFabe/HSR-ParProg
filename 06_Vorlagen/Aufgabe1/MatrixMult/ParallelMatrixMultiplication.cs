using System;
using System.Threading.Tasks;

namespace ParallelMatrixMultiplication
{
    public class ParallelMatrixMultiplication
    {
        // Result = A * B, A has dimN x dimK, B has dimK x dimM, Result has dimN x dimM.
        public static decimal[,] Multiply(decimal[,] matrixA, decimal[,] matrixB)
        {
            var dimN = matrixA.GetLength(0);
            var dimK = matrixA.GetLength(1);
            if (dimK != matrixB.GetLength(0))
            {
                throw new ArgumentException("Dimensions for matrix multiplication do not match");
            }

            var dimM = matrixB.GetLength(1);
            var matrixC = new decimal[dimN, dimM];
            Parallel.For(0, dimN, i =>
            {
                Parallel.For(0, dimM, j =>
                {
                    matrixC[i, j] = 0;
                    for (var k = 0; k < dimK; k++)
                    {
                        matrixC[i, j] += matrixA[i, k] * matrixB[k, j];
                    }
                });
            });
            return matrixC;
        }
    }
}