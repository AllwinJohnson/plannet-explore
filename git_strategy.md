# 🚀 Anti-Cosmic – Git Strategy (Phase 2 and Beyond)

## 1. Branching Model

We follow a simplified, controlled GitFlow model.

### Permanent Branches

main  
→ Production-ready releases only  
→ Tagged with semantic version  
→ Protected branch  

feature  
→ Integration branch  
→ All task branches merge here first  

---

## 2. Task Branch Model

Every feature, refactor, UI enhancement, animation, or fix must:

1. Start from origin/feature
2. Use naming convention:

origin/tasks/<short-descriptive-name>

Examples:

origin/tasks/home-carousel-ui  
origin/tasks/planet-card-redesign  
origin/tasks/detail-page-animation  
origin/tasks/explore-screen-rework  

---

## 3. Phase-Based Execution

Each phase is divided into controlled tasks.

Each task:

- Small scope
- Single responsibility
- Single commit group
- Clean diff
- Reviewed before merge

---

## 4. Merge Flow

tasks/<task-name>  
        ↓  
feature  
        ↓ (Phase completed)  
main  

Rules:

- No direct commits to main
- No direct commits to feature
- Always merge task branch → feature
- Only merge feature → main when phase is complete

---

## 5. Commit Convention (Mandatory)

Use Conventional Commits:

feat: add home carousel layout  
feat: implement horizontal pager animation  
refactor: restructure planet card composable  
fix: correct gradient overlay alignment  
chore: update version to 0.2.0  

No vague messages allowed.

---

## 6. Versioning Standard

We use Semantic Versioning:

MAJOR.MINOR.PATCH(build)

Examples:

0.1.0(1) – Phase 1 foundation  
0.2.0(2) – Phase 2 UI overhaul  
0.2.1(3) – Bug fixes  
1.0.0(10) – Production-ready milestone  

Rules:

- Only tag from main
- Tag format: vX.Y.Z
- Build number increments every release
- Never reuse tag

---

## 7. Phase Completion Protocol

When all Phase 2 tasks are merged into feature:

1. Ensure feature branch is stable
2. Run:

./gradlew build

3. Merge feature → main
4. Create tag:

git tag -a v0.2.0 -m "Phase 2 - UI Overhaul with Carousel Design"
git push origin v0.2.0

5. Push main

---

## 8. Branch Protection Rules

main must:

- Require PR approval
- Require build passing
- Disallow force push
- Disallow direct commits

feature should:

- Require build passing
- Allow controlled merges

---

## 9. Definition of Done (Per Task)

A task is complete when:

- UI matches design reference
- Branding rules followed
- Animations implemented
- No architecture violation
- No TODOs
- Build passes
- Code reviewed
- Merged into feature

---

## 10. Definition of Done (Per Phase)

A phase is complete when:

- All tasks merged into feature
- Feature tested end-to-end
- Feature merged into main
- Version updated
- Tag created
- Release notes written

---

# End of Git Strategy
