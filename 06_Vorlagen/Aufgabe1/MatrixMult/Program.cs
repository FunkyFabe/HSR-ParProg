using System;
using System.Diagnostics;

namespace ParallelMatrixMultiplication {
    public class Program {
        public static void Main() {
            // A has dimension N x K, B has K x M, C has N x M
            const int N = 200;
            const int M = 400;
            const int K = 600;
            var random = new Random(4711);
            var matrixA = CreateRandomMatrix(random, N, K);
            var matrixB = CreateRandomMatrix(random, K, M);
            var watch = Stopwatch.StartNew();
            var matrixC = ParallelMatrixMultiplication.Multiply(matrixA, matrixB);
            Console.WriteLine($"Total computing time: {watch.ElapsedMilliseconds} ms");
            CheckCorrectness(matrixA, matrixB, matrixC);
        }

        private static decimal[,] CreateRandomMatrix(Random random, long len1, long len2) {
            var matrix = new decimal[len1, len2];
            for (var i = 0; i < len1; i++) {
                for (var j = 0; j < len2; j++) {
                    matrix[i, j] = (decimal)random.NextDouble();
                }
            }
            return matrix;
        }

        private static void CheckCorrectness(decimal[,] matrixA, decimal[,] matrixB, decimal[,] matrixC) {
            for (var i = 0; i < matrixC.GetLength(0); i++) {
                for (var j = 0; j < matrixC.GetLength(1); j++) {
                    decimal sum = 0.0M;
                    for (var k = 0; k < matrixA.GetLength(1); k++) {
                        sum += matrixA[i, k] * matrixB[k, j];
                    }
                    if (Math.Abs(sum - matrixC[i, j]) > (decimal)1E-3)  {
                        throw new Exception("Incorrect matrix multiplication");
                    }
                }
            }
        }
    }
}
