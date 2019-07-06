using System;

namespace ProjectEuler.Programs
{
    public static class Argument
    {
        public static bool GetFromUser<T>(string name, out T arg, params Condition<T>[] conditions)
        {
            Console.Write(name + ": ");
            string userInput = Console.ReadLine();
            
            try
            {
                //Tentative de conversion
                arg = (T) Convert.ChangeType(userInput, typeof(T));
            }
            catch (Exception)
            {
                Console.WriteLine("The argument must be of type " + typeof(T) + " !");
                arg = default(T); //null dans la plupart des cas mais pas toujours
                return false;
            }

            //Verifie toutes les conditions sur l'entree de l'utilisateur
            foreach (Condition<T> condition in conditions)
                //Si une condition n'est pas verifiee on lance un message d'erreur
                if (!condition.Verify(arg))
                {
                    condition.WriteErrorMessage();
                    return false;
                }

            return true;
        }
    }
}