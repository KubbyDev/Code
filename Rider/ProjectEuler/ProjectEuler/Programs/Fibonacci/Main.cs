using System;

//https://projecteuler.net/problem=2
namespace ProjectEuler.Programs.Fibonacci
{
    // ReSharper disable once UnusedMember.Global
    [Done]
    public static class Main
    {
        // ReSharper disable once UnusedMember.Global
        public static void Run()
        {
            long sum = 0;

            long x = 1;
            long x1 = 1;

            while (x < 4000000)
            {
                long tmp = x;
                x += x1;
                x1 = tmp;

                if (x % 2 == 0)
                    sum += x;
            }
            
            Console.WriteLine(sum);
        }
    }
}