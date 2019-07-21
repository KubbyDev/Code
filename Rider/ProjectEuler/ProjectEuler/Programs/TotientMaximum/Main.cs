using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

//https://projecteuler.net/problem=69
namespace ProjectEuler.Programs.TotientMaximum
{
    [Done]
    // ReSharper disable once UnusedMember.Global
    public static class Main
    {
        //45 minutes de calcul
        private static int nbBatches = 20;
        private static int limit = 1000000;
        
        // ReSharper disable once UnusedMember.Global
        public static void Run()
        {
            while (nbBatches <= 0)
                Argument.GetFromUser("Batches number", out nbBatches, new Condition<int>(n => n > 0, "Must be > 0!"));
            
            while (limit <= 0)
                Argument.GetFromUser("Limit", out limit, new Condition<int>(n => n > 0, "Must be > 0!"));
            
            double max = 0;
            int maxN = 0;

            Stopwatch time = new Stopwatch();
            time.Start();
            
            //Lance toutes les unites de calcul
            Task<double[]>[] batches = new Task<double[]>[nbBatches];
            for(int i = nbBatches-1; i >= 0; i--)
            {
                int i1 = i;
                batches[i] = new Task<double[]>(
                    () => GetMaxTotient(Math.Max(2,i1*(limit/nbBatches)+1), Math.Min(limit, (i1+1)*(limit/nbBatches)))
                );
                batches[i].Start();
            }
            
            //On attend que toutes les unites aient fini leurs calculs
            while (!batches.All(task => task.IsCompleted))
                Thread.Sleep(100);

            //On calcule le maximum des maximums
            foreach (Task<double[]> batch in batches)
            {
                double[] val = batch.Result;
                
                if (val[0] <= max) 
                    continue;
                
                max = val[0];
                maxN = (int) val[1];
            }

            Console.WriteLine(time.ElapsedMilliseconds);
            Console.WriteLine("For n = " + maxN + ": " + max);
        }

        //Renvoie la valeur maximale de n/Totient(n) pour n allant de <from> a <to>
        private static double[] GetMaxTotient(int from, int to)
        {
            Console.WriteLine("Calculating from " + from + " to " + to);
            
            double max = 0;
            int maxN = 0;
            
            for (int i = from; i < to; i++)
            {
                double val = (double) i / Totient(i);
                
                if (val <= max) 
                    continue;
                
                max = val;
                maxN = i;
            }

            Console.ForegroundColor = ConsoleColor.Green;
            Console.WriteLine(from + " to " + to + " - DONE !");
            Console.ForegroundColor = ConsoleColor.White;
            
            return new double[] { max, maxN };
        }

        private static int Totient(int n)
        {
            if (n == 2)
                return 1;

            List<int> primeFators = GetPrimeFactors(n);
            
            //Si c'est un nombre premier
            //Les nombres premiers ont leur totient = n-1
            if (primeFators.Count == 1 && primeFators[0] == n) 
                return n - 1;

            //Si ce n'est pas un nombre premier
            //Il est premier avec n ssi aucun des facteurs premiers de n ne le divise
            int res = 2;
            for (int i = 2; i < (n + 1) / 2; i++) //pgcd(a,b) = pgcd(b-a,b) et pgcd(k,2k) = k != 1 => on a pas besoin d'aller plus loin que (n+1)/2
                if (primeFators.TrueForAll(factor => i % factor != 0))
                    res += 2;

            return res;
        }

        //Renvoie une liste des facteurs premiers de n (18 => {2,3})
        private static List<int> GetPrimeFactors(int n)
        {
            List<int> res = new List<int>();

            int sqrt = (int) Math.Ceiling(Math.Sqrt(n));
            int i = 2;

            while (n > 1)
            {
                //Si c'est un nombre premier on s'arrete
                if (i > sqrt)
                {
                    res.Add(n);
                    break;
                }

                //Si on a trouve un diviseur
                if (n % i == 0)
                {
                    //On l'ajoute a la liste
                    res.Add(i);
                    n /= i;

                    //Et on divise autant que possible
                    while (n % i == 0)
                        n /= i;

                   //Ce programme marche alors qu'il n'est pas cense marcher
                   //Avec ça je pense que c'est bon, mais j'ai pas teste
                   //res.Add(n);
                }

                i++;
            }

            return res;
        }
    }
}