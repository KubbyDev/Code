using System;
using System.Linq;

namespace ProgrammesAlakon
{
    internal class Program
    {
        public static void Main(string[] args)
        {
            Disp(SeparateFirstArg(""));
            Disp(SeparateFirstArg(" "));
            Disp(SeparateFirstArg(" a"));
            Disp(SeparateFirstArg("a "));
            Disp(SeparateFirstArg("a a"));
            Disp(SeparateFirstArg(" a a"));
        }

        public static void Disp(string[] str)
        {
            foreach (string s in str)
                Console.Write(s + ",");
            Console.WriteLine();
        }

        public static string RobustSubstring(string s, int from) => RobustSubstring(s, from, s.Length - from);
        public static string RobustSubstring(string s, int from, int length)
        {
            if (from >= s.Length)
                return "";

            if (from < 0)
            {
                length += from;
                from = 0;
            }

            if (length + from > s.Length)
                length = s.Length - from;

            return s.Substring(from, length);
        }
        
        public static string[] SeparateFirstArg(string order)
        {
            int i = 0;
            while (i < order.Length && order[i] != ' ')
                i += 1;

            return new string[] {RobustSubstring(order, 0, i), RobustSubstring(order, i + 1)};
        }

        public static void GenerateRandomNums()
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