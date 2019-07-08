using System;
using System.Diagnostics;

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

            Stopwatch time = new Stopwatch();
            time.Start();
            
            for (long i = 2; i < 10000; i++)
            {
                double val = (double) i / Totient(i);
                
                if (val <= max) 
                    continue;
                
                max = val;
                maxN = i;
            }
            
            Console.WriteLine(time.ElapsedMilliseconds);
            Console.WriteLine("For n = " + maxN + ": " + max);
        }

        private static long Totient(long n)
        {
            //pgcd(a,b) = pgcd(b-a,b)
            //pgcd(k,2k) = k != 1
            //Les nombres premiers ont leur totient = n-1
            
            if (n == 2)
                return 1;

            if (IsPrime(n)) 
                return n - 1;

            long res = 2;
            for(int i = 2; i < (n+1)/2; i++)
                if (n.IsRelativelyPrimeTo(i))
                    res+=2;

            return res;
        }

        private static bool IsPrime(long n)
        {
            long max = (long) Math.Ceiling(Math.Sqrt(n));
            for(long i = 2; i < max; i++)
                if (n % i == 0)
                    return false;

            return true;
        }
        
        private static bool IsRelativelyPrimeTo(this long a, long b) => GCD(a, b) == 1;
        private static long GCD(long a, long b)
        {
            while(b != 0){
                long t = a;
                a = b;
                b = t%b;
            }
            return a;
        }
    }
}