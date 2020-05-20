import random

# Format of the problem after preprocessing:
"""
([
    2,
    1
],[
    [
        (name, morningOptions, eveningOptions),
        (name, morningOptions, eveningOptions),
        (name, morningOptions, eveningOptions),
    ],[
        (name, morningOptions, eveningOptions),
        (name, morningOptions, eveningOptions),
    ]
])

"""

def preprocess(problem):
    quotas = problem['quotas']
    res = ([],[])
    domainIndex = -1
    for domain in quotas:
        domainIndex += 1
        # Saves the quota
        res[0].append(quotas[domain])
        # Finds all the workers in that domain
        workersList = []
        res[1].append(workersList)
        workers = problem['workers']
        for workerName in workers:
            worker = workers[workerName]
            if worker['domain'] == domain:
                res[1][domainIndex].append((workerName, len(worker['morningOptions']), len(worker['eveningOptions'])))
    return res
 
def build(problem):

    def addWorker(worker, selection):
        morningOption = random.randrange(worker[1])
        eveningOption = random.randrange(worker[2])
        selected.append({"name":worker[0],"morningOptionIndex":morningOption,"eveningOptionIndex":eveningOption})
    
    quotas = problem[0]
    workers = problem[1]
    selected = []
    for i in range(len(quotas)):
        selectedDomainWorkers = random.sample(workers[i], quotas[i])
        for selectedDomainWorker in selectedDomainWorkers:
            addWorker(selectedDomainWorker, selected)

    return selected
