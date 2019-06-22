using System; 
using Cudafy; 
using Cudafy.Host; 
using Cudafy.Translator;

namespace TestProject
{
    [Cudafy]
    public struct VectorStruct
    {
        public int x;
        public int y;
        public int z;
    }

    class Program
    {
        public static int N = 1000;
        public static int TIMES = 100;

        public static void Main()
        {
            LaunchTest();
            
            TIMES *= 10;
            LaunchTest();
            
            TIMES *= 10;
            LaunchTest();
            
            TIMES *= 10;
            LaunchTest();
        }
        
        public static void LaunchTest()
        {
            Console.WriteLine("N = " + N + ", TIMES = " + TIMES);
            
            CudafyModes.Target = eGPUType.OpenCL;
            CudafyModes.DeviceId = 2;
            CudafyTranslator.Language = eLanguage.OpenCL;

            CudafyModule km = CudafyTranslator.Cudafy(typeof(VectorStruct), typeof(Program));

            GPGPU gpu = CudafyHost.GetDevice(CudafyModes.Target, CudafyModes.DeviceId);
            gpu.LoadModule(km);

            VectorStruct[] a = new VectorStruct[N];
            VectorStruct[] b = new VectorStruct[N];
            VectorStruct[] c = new VectorStruct[N];
            VectorStruct[] info = {new VectorStruct {x = TIMES, y = N}};

            for (int i = 0; i < N; i++)
            {
                a[i].x = i;
                a[i].y = i;
                a[i].z = i;

                b[i].x = 2*i;
                b[i].y = 2*i;
                b[i].z = 2*i;
            }

            VectorStruct[] dev_a = gpu.Allocate<VectorStruct>(N);
            VectorStruct[] dev_b = gpu.Allocate<VectorStruct>(N);
            VectorStruct[] dev_times = gpu.Allocate<VectorStruct>(1);
            gpu.CopyToDevice(a, dev_a);
            gpu.CopyToDevice(b, dev_b);
            gpu.CopyToDevice(info, dev_times);

            VectorStruct[] dev_c = gpu.Allocate(c);
            
            long gpuTime = DateTime.Now.Ticks;
            long gpuMS = DateTime.Now.Millisecond;
            gpu.Launch(1, N, "thekernel", dev_a, dev_b, dev_c, dev_times);
            gpuTime = DateTime.Now.Ticks - gpuTime;
            Console.WriteLine("CPU time: " + gpuTime + ", " + (DateTime.Now.Millisecond-gpuMS) + " ms");
            
            gpu.CopyFromDevice(dev_c, c);
            gpu.FreeAll();

            long cpuTime = DateTime.Now.Ticks;
            long cpuMS = DateTime.Now.Millisecond;
            for (int i = 0; i < N; i++)
                doCalc(i, a, b, c, info);
            cpuTime = DateTime.Now.Ticks - cpuTime;
            Console.WriteLine("CPU time: " + cpuTime + ", " + (DateTime.Now.Millisecond-cpuMS) + " ms");
        }

        [Cudafy]
        public static void thekernel(GThread thread, VectorStruct[] a, VectorStruct[] b, VectorStruct[] c, VectorStruct[] info)
        {
            int tid = thread.threadIdx.x;
            doCalc(tid, a, b, c, info);
        }

        [Cudafy]
        public static void doCalc(int tid, VectorStruct[] a, VectorStruct[] b, VectorStruct[] c, VectorStruct[] info)
        {
            c[tid].x = 0;
            c[tid].y = 0;
            c[tid].z = 0;
            
            for(int i = 0; i < info[0].x; i++)
                if(i < info[0].y)
                {
                    c[tid].x += (int) Math.Cos(Math.Exp(a[tid].x + b[tid].x));
                    c[tid].y += (int) Math.Cos(Math.Exp(a[tid].y + b[tid].y));
                    c[tid].z += (int) Math.Cos(Math.Exp(a[tid].z + b[tid].z));
                }
        }
    }
}