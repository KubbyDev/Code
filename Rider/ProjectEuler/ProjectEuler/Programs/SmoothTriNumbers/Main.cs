using System;

//https://projecteuler.net/problem=581
namespace ProjectEuler.Programs.SmoothTriNumbers
{
    /* TODO
     * Trouver le nombre a mettre dans la for 
     */
    
    // ReSharper disable once UnusedMember.Global
    public class Main
    {
        // ReSharper disable once UnusedMember.Global
        public static void Run()
        {
            long T(long n) => n * (n + 1) / 2;
            long sum = 0;
            
            for(int i = 1; i < 100000000; i++)
                if (Is47Smooth(T(i)))
                {
                    sum += i;
                    Console.WriteLine(i);
                }

            
            Console.WriteLine(sum);
        }

        private static bool Is47Smooth(long n)
        {
            int i = 2;
            while (n > 1)
            {
                if (i > 47)
                    return false;
                
                while (n % i == 0)
                    n /= i;

                i++;
            }

            return true;
        }
    }
}