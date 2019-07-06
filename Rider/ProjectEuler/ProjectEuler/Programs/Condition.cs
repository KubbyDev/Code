using System;

namespace ProjectEuler.Programs
{
    public class Condition<T>
    {
        public readonly Func<T, bool> Verifier;
        public readonly string ErrorMessage;
        
        public Condition(Func<T, bool> verifier, string errorMessage)
        {
            Verifier = verifier;
            ErrorMessage = errorMessage;
        }

        public bool Verify(T arg) => Verifier(arg);

        public void WriteErrorMessage()
        {
            Console.WriteLine(ErrorMessage);
        }
    }
}