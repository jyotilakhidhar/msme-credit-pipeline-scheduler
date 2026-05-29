import subprocess, json, os

instances_dir = "/workspaces/msme-credit-pipeline-scheduler/instances"
results_dir = "/workspaces/msme-credit-pipeline-scheduler/results"
jar = "/workspaces/msme-credit-pipeline-scheduler/scheduler/target/scheduler-0.0.1-SNAPSHOT.jar"

files = sorted([f for f in os.listdir(instances_dir) if f.startswith("instance_")])

print(f"{'File':<40} {'Feasible':<10} {'Penalty':<12} {'Time(ms)'}")
print("-" * 75)

for fname in files:
    inp = os.path.join(instances_dir, fname)
    out = os.path.join(results_dir, fname.replace(".json", "_result.json"))
    result = subprocess.run(
        ["java", "-jar", jar, inp, out],
        capture_output=True, text=True
    )
    try:
        with open(out) as f:
            data = json.load(f)
        print(f"{fname:<40} {str(data['feasible']):<10} {data['penalty']:<12.2f} {data['runtimeMs']}")
    except:
        print(f"{fname:<40} ERROR")
