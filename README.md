# MSME Credit Pipeline Scheduler

A Java Spring Boot implementation of an NP-Hard pipeline scheduling algorithm for MSME credit scoring systems, developed as part of the ScoreMe Solutions technical assessment.

## Problem Statement
Schedule n pipeline tasks (Bureau API pulls, OCR jobs, GST verification) across k time slots such that:
- **F1:** No two conflicting tasks share the same slot (GPU/Kafka clash)
- **F2:** No slot exceeds its resource capacity (CPU, RAM, GPU, Network)
- **F3:** Every task runs within its SLA time window

## Algorithm — Priority-Aware Conflict-First Greedy
1. Sort tasks by descending priority weight, then ascending SLA window size
2. For each task, find earliest valid slot satisfying F1 + F2 + F3
3. Report INFEASIBLE if no valid slot exists

## Penalty Function
P(σ) = Σ w(tᵢ)×σ(tᵢ) + λ × Σ_s Σ_d (used[s][d] / C[s][d])²

- **P_base:** High-priority tasks in early slots = lower cost
- **Imbalance term (λ=0.5):** Penalizes uneven resource usage across dimensions

## Project Structure
## How to Run
```bash
cd scheduler
mvn package -DskipTests
java -jar target/scheduler-0.0.1-SNAPSHOT.jar <input.json> <output.json>
```

## Run All Benchmarks
```bash
python3 instances/benchmark_generator.py
python3 instances/run_benchmarks.py
```

## Run Tests
```bash
cd scheduler && mvn test
```

## Benchmark Results Summary
| Instance | n | k | Density | Feasible | Penalty | Time(ms) |
|----------|---|---|---------|----------|---------|----------|
| n8_k6_d15 | 8 | 6 | 15% | True | 38.42 | 3 |
| n8_k6_d30 | 8 | 6 | 30% | True | 32.52 | 2 |
| n8_k6_d60 | 8 | 6 | 60% | True | 52.21 | 2 |
| n15_k8_d15 | 15 | 8 | 15% | True | 89.92 | 1 |
| n15_k8_d30 | 15 | 8 | 30% | True | 96.01 | 2 |
| n15_k8_d60 | 15 | 8 | 60% | True | 213.61 | 1 |
| n25_k10_d15 | 25 | 10 | 15% | True | 202.11 | 2 |
| n25_k10_d30 | 25 | 10 | 30% | True | 228.71 | 3 |
| n25_k10_d60 | 25 | 10 | 60% | False | — | 6 |

High conflict density (60%) on large instances correctly returns INFEASIBLE — chromatic number exceeds k.

## Tech Stack
- Java 17, Spring Boot 3.5.14, Maven
- Jackson (JSON parsing)
- JUnit 5 (unit tests)
- Python 3 (benchmark generation)

## NP-Hardness
Proven by polynomial reduction from Graph k-Coloring. See `report/np_proof.md`.
