using System;

//https://projecteuler.net/problem=69
namespace ProjectEuler.Programs.TotientMaximum
{
    // ReSharper disable once UnusedMember.Global
    public static class Main
    {
        // ReSharper disable once UnusedMember.Global
        public static void Run()
        {
            double max = 0;
            long maxN = 0;

            for (long i = 2; i < 1000; i++)
            {
                double val = (double) i / Totient(i);
                if (val > max)
                {
                    max = val;
                    maxN = i;
                }
            }
            
            Console.WriteLine("For n = " + maxN + ": " + max);
        }

        private static long Totient(long n)
        {
            if (n == 2)
                return 1;
            
            long res = 2;
            for(int i = 2; i < n-1; i++)
                if (n.isRelativelyPrimeTo(i))
                    res++;

            return res;
        }

        private static bool isRelativelyPrimeTo(this long a, long b) // a > b pour cet algo
        {
            //Pas besoin de gerer les negatifs pour cet algo
            //if (a == 1 || b == 1) return true;  Pas besoin pour cet algo
            
            if (a % b == 0 || b % a == 0)
                return false;

            //De 2 a b-1 pour cet algo, sinon c'est Math.Min(a, b)-1
            for (long i = 2; i < b; i++)
            {
                if (a % i == 0 && b % i == 0)
                    return false;
            }

            return true;
        }
    }
}