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

            for (long i = 2; i < 10000; i++)
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

        private static bool isRelativelyPrimeTo(this long a, long b)
        {
            return GCD(a, b) == 1;
        }
        
        private static long GCD(long a, long b)
        {
            long t;
            while(b != 0){
                t = a;
                a = b;
                b = t%b;
            }
            return a;
        }
    }
}