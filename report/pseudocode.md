# Algorithm Pseudocode — Priority-Aware Conflict-First Greedy

## Problem Statement
Given:
- n tasks, each with resource vector r(t), SLA window [l,u], priority weight w(t)
- conflict pairs E (tasks that cannot share a slot)
- k slots, each with capacity vector C(s)

Find: assignment σ: T → {0..k-1} minimizing P(σ), satisfying F1+F2+F3.

## Pseudocode
## Penalty Function
P(σ) = Σᵢ w(tᵢ)×σ(tᵢ)  +  λ × Σ_s Σ_d (used[s][d] / C[s][d])²

- P_base: high-priority tasks in early slots = lower cost
- Imbalance term (λ=0.5): penalizes uneven resource usage across dimensions

## Complexity
- Sorting: O(n log n)
- Assignment: O(n × k × |E|) worst case
- Total: polynomial — O(n² k) for dense conflict graphs
