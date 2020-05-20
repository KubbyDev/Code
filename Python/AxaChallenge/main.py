import checker
import time
import json
import math
from Builder import randomBuild as builder

def solve(inFile, outFile, maxTime):

    print("Solving " + inFile + "...")
    
    # Parses the input file
    f = open(inFile, "r")
    rawProblem = json.loads(f.read())
    f.close()

    # Preprocessing of the input if necessary
    problem = builder.preprocess(rawProblem)

    # Tests multiple builds to find a good one
    start = time.time()
    best = None
    bestScore = math.inf
    tries = 0
    while time.time() - start < maxTime:
        tries += 1
        solution = builder.build(problem)
        score = checker.getScore(solution, rawProblem)
        if score < bestScore:
            bestScore = score
            best = solution

    # Saves the best solution
    print("Tries: " + str(tries))
    print("Best score: " + str(bestScore))
    f = open(outFile, "w+")
    f.write(json.dumps(best))
    f.close()


#solve("in1.json", "out1.json", 1)
#solve("in2.json", "out2.json", 120)
#solve("in3.json", "out3.json", 1000)
#solve("in4.json", "out4.json", 1000)
#solve("in5.json", "out5.json", 1000)
#solve("in6.json", "out6.json", 1000)
