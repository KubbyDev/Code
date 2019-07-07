using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using ProjectEuler.Programs;

namespace ProjectEuler
{
    public static class Launcher
    {
        private static List<Program> programs = new List<Program>();
        public static void Main(string[] args)
        {
            Console.Clear();
            
            //Cree des objects Program dans lesquels sont stockes toutes les methodes Run dans le dossier Program
            foreach (Type program in Assembly.GetExecutingAssembly().GetTypes())
            {
                //Si ce n'est pas une classe qui s'appelle Main et qui est dans ProjectEuler.Programs.<name> on passe
                if (!(program.IsClass && program.Name == "Main" && program.Namespace.Contains("ProjectEuler.Programs")))
                    continue;
                
                //Recupere la methode Run
                MethodInfo runMethod = program.GetMethods()
                    .FirstOrDefault(method => method.Name == "Run" && method.IsPublic && method.IsStatic);
                
                string programName = program.Namespace.Substring(22);
                bool done = program.GetCustomAttributes(typeof(DoneAttribute), true).Length > 0;

                if(runMethod != null)
                    programs.Add(new Program(programName, runMethod, done));
            }
            
            string selected;
            
            //Si le nom du programe voulu est passe en parametre on le lance directement
            if (args.Length > 0)
            {
                selected = args[0];
            }
            //Sinon on demande a l'utilisateur d'en choisir un
            else
            {
                programs.ForEach(prog => prog.Display());
                
                Console.WriteLine("\nWhat do you want to run ?");
                selected = Console.ReadLine();
            }
            Program selectedProgram = programs.FirstOrDefault(prog => prog.Name == selected);
            
            //Si le programe existe on le lance
            if (selectedProgram != null)
            {
                Console.Clear();
                selectedProgram.Run();
            }
            else
                Console.Error.WriteLine("This program doesn't exist !");
        }

        private class Program
        {
            public readonly string Name;
            private MethodInfo RunMethod;
            private bool Done;

            public Program(string name, MethodInfo runMethod, bool done = false)
            {
                RunMethod = runMethod;
                Name = name;
                Done = done;
            }

            public void Run()
            {
                RunMethod.Invoke(new object(), new object[0]);
            }

            public void Display()
            {
                Console.ForegroundColor = ConsoleColor.White;
                Console.Write(Name);
                
                Console.SetCursorPosition(22, Console.CursorTop);
                Console.Write("- ");
                Console.ForegroundColor = Done ? ConsoleColor.Green : ConsoleColor.DarkRed; 
                Console.WriteLine(Done ? "DONE" : "TODO");
                Console.ForegroundColor = ConsoleColor.White;
            }
        }
    }
}