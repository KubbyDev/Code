using System;

//https://projecteuler.net/problem=3
namespace ProjectEuler.Programs.LargestPrimeFactor
{
    [Done]
    // ReSharper disable once UnusedMember.Global
    public static class Main
    {
        // ReSharper disable once UnusedMember.Global
        public static void Run()
        {
            if (!Argument.GetFromUser("Number", out long n, new Condition<long>(x => x > 2, "Must be > 2!"))) 
                return;
            
            long i = 2;
            long res = n;
            
            while (n > 1)
            {
                //Si on a trouve un diviseur
                if (n % i == 0)
                {
                    //On l'ajoute a la liste
                    n /= i;
                    res = i;
                }

                i++;
            }

            Console.WriteLine(res);
        }
    }
}