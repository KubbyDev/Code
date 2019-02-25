using System;

namespace ProgrammesAlakon
{
    internal class Program
    {
        public static void Main(string[] args)
        {
            int max = 4;
            int seed = 0;
            int[] res = new int[max];

            for (int i = 0; i < 100050; i++)
            {
                seed = LCG(seed);
                res[seed*max/1000]++;
            }
            
            foreach(int i in res)
                Console.WriteLine(i);
         }

        public static int LCG(int seed)
        {
            return (seed * 131217 + 281) % 1000;
        }
    }
}