using System;

//https://projecteuler.net/problem=1
namespace ProjectEuler.Programs.Multiples3And5
{
    // ReSharper disable once UnusedMember.Global
    [Done]
    public static class Main
    {
        // ReSharper disable once UnusedMember.Global
        public static void Run()
        {
            long sum = 0;
            for(int i = 1; i < 1000; i++)
                if (i % 3 == 0 || i % 5 == 0)
                    sum += i;
            
            Console.WriteLine(sum);
        }
    }
}