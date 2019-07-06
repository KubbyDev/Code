using System;

//Not from ProjectEuler but still fun
namespace ProjectEuler.Programs.PiCalculator
{
    // ReSharper disable once UnusedMember.Global
    public class Main
    {
        // ReSharper disable once UnusedMember.Global
        public static void Run()
        {
            //Demande le nombre de segments a l'utilisateur
            if (!Argument.GetFromUser("Segments number", out long segments,
                new Condition<long>(x => x > 2, "Segments number must be greater than 2 !")))
                return;

            double pi = 0;
            double prevX = 0.5;
            double prevY = 0;
            double sqr(double x) => x * x;
            for (long i = 1; i <= segments; i++)
            {
                double x = Math.Cos(2*Math.PI * ((double) i / segments)) /2;
                double y = Math.Sin(2*Math.PI * ((double) i / segments)) /2;

                pi += Math.Sqrt(sqr(x-prevX) + sqr(y-prevY));
                
                prevX = x;
                prevY = y;
            }

            Console.WriteLine(pi);
        }
    }
}