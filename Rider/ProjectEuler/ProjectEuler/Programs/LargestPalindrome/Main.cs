using System;

//https://projecteuler.net/problem=4
namespace ProjectEuler.Programs.LargestPalindrome
{
    [Done]
    // ReSharper disable once UnusedMember.Global
    public static class Main
    {
        // ReSharper disable once UnusedMember.Global
        public static void Run()
        {
            if (!Argument.GetFromUser("Number of digits", out int n, new Condition<int>(a => a > 0, "Must be > 0!")))
                return;

            long x = (long)Math.Pow(10,n);
            long y = (long)Math.Pow(10,n);

            long maxI = 1;
            long maxJ = 1;

            for(int i = 1; i < x; i++)
                for(int j = 1; j < y; j++)
                    if (i*j > maxI*maxJ && IsPalindrome(i*j))
                    {
                        maxI = i;
                        maxJ = j;
                    }
            
            Console.WriteLine("The largest palindrome made of the product of " + n + " digits numbers is " + maxI*maxJ + " (" + maxI + "*" + maxJ + ")");
        }

        private static bool IsPalindrome(long n) => IsPalindrome(n.ToString());
        private static bool IsPalindrome(string s)
        {
            int i = 0;
            int length = s.Length;
            while (i < length - 1 - i)
            {
                if (s[i] != s[length - 1 - i])
                    return false;
                i++;
            }

            return true;
        }
    }
}