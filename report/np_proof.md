# NP-Hardness Proof — MSME Pipeline Scheduling

## Claim
The compound scheduling problem (F1 + F2 + F3) is NP-Hard.

## Proof by Reduction from Graph k-Coloring

Graph k-Coloring is NP-Complete [Karp, 1972]. We reduce it to our scheduling problem.

### Construction
Given a Graph k-Coloring instance G = (V, E) with |V| = n vertices and target k colors, construct a scheduling instance as follows:

- **Tasks:** For each vertex v_i in V, create task t_i with:
  - resource vector r(t_i) = [epsilon, epsilon, epsilon, epsilon] (tiny, negligible)
  - SLA window [l_i, u_i] = [0, k-1] (all slots valid)
  - weight w(t_i) = 1

- **Conflicts:** For each edge (v_i, v_j) in E, add conflict pair (i, j).

- **Slots:** k slots, each with large capacity C(s) = [INF, INF, INF, INF].

- **Key:** Resource and SLA constraints are never binding by construction. Only F1 (conflict) is the active constraint.

### Forward Direction
If G has a valid k-coloring c: V → {0..k-1}, define σ(t_i) = c(v_i).
Since c is a valid coloring, no two adjacent vertices share a color.
Therefore no two conflicting tasks share a slot → F1 satisfied.
F2 satisfied (tiny resources, infinite capacity). F3 satisfied (all windows = [0,k-1]).
Thus σ is a feasible assignment.

### Backward Direction
If σ is a feasible assignment, define c(v_i) = σ(t_i).
Since F1 guarantees no conflicting tasks share a slot,
no two adjacent vertices in G share a color → c is a valid k-coloring.

### Conclusion
A feasible scheduling assignment exists if and only if G has a valid k-coloring.
Since k-Coloring is NP-Complete, our scheduling problem is NP-Hard. QED.

## Implication
No polynomial-time exact algorithm exists (unless P=NP).
This justifies our greedy heuristic approach — it runs in O(n^2 * k) time
and finds high-quality feasible solutions when they exist.
