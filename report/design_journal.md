# Design Journal — MSME Pipeline Scheduler

## Hardest Design Decision
The hardest decision was the task sorting strategy. Initially I considered sorting only by priority weight (descending), but realized that two tasks with equal weight but different SLA window sizes need different treatment. A task with window=[2,2] has exactly one valid slot, while window=[0,5] has six options. Assigning the tight-window task first prevents it from being blocked later by a high-priority task that could have gone elsewhere. The final comparator sorts by descending weight first, then ascending window size — this combination handles both priority and constraint tightness simultaneously.

## Where the Algorithm Failed
On high-density instances (conflict_density=0.60, n=25, k=10), the algorithm correctly reported INFEASIBLE for some seeds. The conflict graph becomes so dense that its chromatic number exceeds k — no valid k-coloring exists, so no feasible assignment is possible. This is not a bug but the correct behavior: the NP-hardness proof shows exactly this case. A local search with random restarts might escape some infeasible declarations, but cannot overcome a genuinely uncolorable graph.

## Connection to ScoreMe Production System
ScoreMe's NiFi pipeline schedules bureau API pulls from CIBIL, Equifax, and CRIF for MSME credit scoring. Each pull is a task, each time window is a slot, and the conflict constraint models the rule that the same customer cannot be pulled from two bureaus simultaneously (data consistency requirement). Resource constraints map directly to API rate limits per bureau. The penalty function's load imbalance term models real infrastructure cost — uneven API usage causes burst charges. This scheduling problem is not academic; it is the exact operational challenge ScoreMe faces at scale.

## What Surprised Me
The greedy algorithm performed far better than expected on medium-density instances (density=0.30). I expected it to struggle when many tasks compete for the same slots, but the priority-window sorting heuristic naturally distributes tasks across slots without backtracking. The penalty values scaled smoothly with n (n=8: ~38, n=15: ~96, n=25: ~228), confirming that the algorithm finds consistent quality solutions. The runtime never exceeded 6ms even for n=25 — polynomial complexity is clearly visible in practice.
