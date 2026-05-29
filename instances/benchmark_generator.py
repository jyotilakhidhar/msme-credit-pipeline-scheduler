import json, random, os

def generate_instance(n, k, conflict_density, seed):
    random.seed(seed)
    tasks = []
    for i in range(n):
        lb = random.randint(0, max(0, k // 4))
        ub = random.randint(k * 3 // 4, k - 1)
        resources = [round(random.uniform(0.5,2),1), round(random.uniform(1,4),1),
                     round(random.uniform(0.2,1),1), round(random.uniform(0.1,0.5),1)]
        weight = round(random.uniform(1, 10), 1)
        tasks.append({"id": f"T{i}", "resources": resources,
                      "lower_bound": lb, "upper_bound": ub, "weight": weight})
    conflicts = []
    for i in range(n):
        for j in range(i+1, n):
            if random.random() < conflict_density:
                conflicts.append([i, j])
    cap = n * 6.0
    capacities = [[cap, cap*2, cap, cap] for _ in range(k)]
    return {"k": k, "tasks": tasks, "conflicts": conflicts, "capacities": capacities}

# Low/medium density → feasible | High density → infeasible (expected!)
configs = [
    (8,  6, 0.15, 42),  # small, easy
    (8,  6, 0.30, 43),  # small, medium
    (8,  6, 0.60, 44),  # small, hard
    (15, 8, 0.15, 45),  # medium, easy
    (15, 8, 0.30, 46),  # medium, medium
    (15, 8, 0.60, 47),  # medium, hard
    (25, 10, 0.15, 48), # large, easy
    (25, 10, 0.30, 49), # large, medium
    (25, 10, 0.60, 50), # large, hard
]

out_dir = "/workspaces/msme-credit-pipeline-scheduler/instances"
for n, k, density, seed in configs:
    instance = generate_instance(n, k, density, seed)
    fname = f"instance_n{n}_k{k}_d{int(density*100)}_s{seed}.json"
    with open(f"{out_dir}/{fname}", "w") as f:
        json.dump(instance, f, indent=2)
    print(f"Generated: {fname}")
print("Done!")
