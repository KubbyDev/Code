using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

//https://projecteuler.net/problem=79
namespace ProjectEuler.Programs.PasscodeDerivation
{
    /*
     * TODO
     * Construire la chaine a la fin
     * Mais ca marche quand meme
     */
    
    // ReSharper disable once UnusedMember.Global
    [Done]
    public static class Main
    {
        private static string filePath = "PasscodeDerivation/data.txt";
        
        // ReSharper disable once UnusedMember.Global
        public static void Run()
        {
            //Liste des nombres rencontres avec les infos d'ordre
            HashSet<Number> numbers = new HashSet<Number>();
            void AddNumber(int val, IEnumerable<int> before, IEnumerable<int> after)
            {
                //On ajoute un nombre avec la bonne valeur si il existe pas
                if (numbers.All(n => n.Value != val))
                    numbers.Add(new Number { Value = val });

                //On trouve le nombre ayant la bonne valeur
                Number number = numbers.First(n => n.Value == val);
                
                //On ajoute les nombres avant et apres
                foreach (int info in before)
                    number.Before.Add(info);
                foreach (int info in after)
                    number.After.Add(info);
            }
            
            foreach (int entry in GetFileData())
            {
                List<int> values = entry.ToString().ToCharArray().Select(c => int.Parse(c.ToString())).ToList();
                
                AddNumber(values[0], new int[]{}, new int[]{ values[1], values[2] });
                AddNumber(values[1], new int[]{ values[0] }, new int[]{ values[2] });
                AddNumber(values[2], new int[]{ values[0], values[1] }, new int[]{});
            }

            /* Debug
            foreach (Number n in numbers)
            {
                foreach (int val in n.Before)
                    Console.Write(val + ", ");
                Console.WriteLine("\n" + n.Value);
                foreach (int val in n.After)
                    Console.Write(val + ", ");
                
                Console.WriteLine();
                Console.WriteLine();
            }
            */
            
            
        }

        private static int[] GetFileData()
        {
            string data;
            using (StreamReader sr = new StreamReader(filePath))
                data = sr.ReadToEnd();

            return data.Split(new[] {"\n"}, StringSplitOptions.RemoveEmptyEntries)
                .Select(int.Parse).ToArray();
        }

        private class Number
        {
            public HashSet<int> Before = new HashSet<int>();
            public HashSet<int> After = new HashSet<int>();
            public int Value;
        }
    }
}