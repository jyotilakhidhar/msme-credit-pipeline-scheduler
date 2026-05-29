# AI Usage Log — MSME Pipeline Scheduler Assessment

As required by the assessment guidelines, this document records all AI tool interactions during development.

## Tool Used
- Claude (Anthropic) — via claude.ai chat interface

## Interaction Log

| # | Date | Prompt Summary | AI Output Used | How I Used It |
|---|------|---------------|----------------|---------------|
| 1 | 2026-05-29 | Explain NP-hardness reduction from k-Coloring | Reduction construction idea | Understood the logic, wrote np_proof.md in my own words |
| 2 | 2026-05-29 | Explain greedy algorithm analogy (teacher-classroom) | Algorithm concept explanation | Used analogy to understand sorting strategy |
| 3 | 2026-05-29 | Guide Task.java model class structure | Class skeleton suggestion | Wrote all fields and comments myself after understanding |
| 4 | 2026-05-29 | Explain penalty function extension options | Load imbalance term idea | Understood the math, implemented PenaltyCalculator.java myself |
| 5 | 2026-05-29 | Debug import error in ResourceChecker.java | Missing import identified | Fixed the import myself |
| 6 | 2026-05-29 | Explain CommandLineRunner vs main() in Spring Boot | Pattern explanation | Rewrote SchedulerApplication.java myself |
| 7 | 2026-05-29 | Design 4 unit test cases | Test case ideas | Understood each test, wrote assertions myself |
| 8 | 2026-05-29 | Fix Tomcat not shutting down in benchmark | spring.main.web-application-type=none | Applied fix after understanding why Tomcat starts |

## Declaration
All Java source code was written by me after understanding the concepts explained by AI.
AI was used as a learning and debugging aid, not as a code generator.
Every line of code in this repository I can explain and defend in the viva voce.

## Key Concepts I Understand and Can Defend
1. Why sort by weight DESC then window size ASC
2. How ConflictChecker traverses conflict pairs by index
3. Why ResourceChecker sums used[] before checking capacity
4. How PenaltyCalculator computes load imbalance ratio squared
5. Why high conflict density leads to INFEASIBLE (chromatic number > k)
6. The bidirectional proof in NP reduction (forward + backward)
