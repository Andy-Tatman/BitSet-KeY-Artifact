# Overview
This repository contains the files that go along with our paper, "Analysis and Formal Specification of OpenJDK's BitSet". Specifically, we have the following:
* The original BitSet.java code file, without edits. 
* The edited BitSet.java code file, which contains our formal specification.
* The other .java files and the necessary .key file in order to use the files with KeY 2.10.0.
* A series of proofs completed in KeY.

The original proof files artifact can be found at [here on Zenodo](https://doi.org/10.5281/zenodo.8043379), and the full paper can be found [here on Springer's website](https://doi.org/10.1007/978-3-031-47705-8_8). 

# Details for the artifact:
* Goal: This artifact provides the original version of the BitSet class, as well as the version annotated with a JML specification. This artifact further includes the KeY prover executable, and a number of completed proofs relevant to the paper.
* Authors: Andy S. Tatman, Hans-Dieter A. Hiep, Stijn de Gouw.
* Cite as: Tatman, Andy S., Hiep, Hans-Dieter A., & de Gouw, Stijn. (2023). Analysis and Formal Specification of OpenJDK's BitSet: Proof files. Zenodo. https://doi.org/10.5281/zenodo.8043379

## Set-up for KeY
You can download KeY (version 2.10.0) from [the KeY project's website](https://www.key-project.org/download/). 
You can then launch the application in a terminal using:

$ java -jar key-2.10.0-exe.jar

Click away the 'Load Examples' dialogue window.
You can now load completed proofs (.proof files) or proof goals (.key files) through File->Load in the top left.
In order to load .proof files, the layout of the directory within the zip must not be changed.

# Accessing completed proofs
Open the KeY executable provided.
In KeY, navigate to File->Load and then select the relevant .proof file.
The following completed proofs can be loaded in:
* /Proofs/BitSet-Methods/BitSet()-private.proof
* /Proofs/BitSet-Methods/BitSet()-public.proof
* /Proofs/BitSet-Methods/BitSet(int)-private.proof
* /Proofs/BitSet-Methods/BitSet(int)-public.proof
* /Proofs/BitSet-Methods/checkInvariants.proof
* /Proofs/BitSet-Methods/checkRange.proof
* /Proofs/BitSet-Methods/clear().proof
* /Proofs/BitSet-Methods/ensureCapacity.proof
* /Proofs/BitSet-Methods/expandTo.proof
* /Proofs/BitSet-Methods/recalculateWordsInUse.proof
* /Proofs/BitSet-Methods/wordindex.proof
* /Proofs/Article-Assertions/FromIndex-ToIndex-Div64.proof
* /Proofs/Article-Assertions/narrower-sourcein-TargetWo-wordsIU.proof
* /Proofs/Article-Assertions/sourcein-TargetWo-wordsIU.proof
* /Proofs/Article-Assertions/targetWords-Bound.proof

Once the proof has loaded in, you can see each proof step carried out in the "Proof" tab on the left of the screen. 
A completed proof will have a green folder at the top of the "Proof" tab (The "Proof Tree" folder should be green.)
Alternatively, there should be no open goals in the Goals tab. 
When clicking on a specific rule in the "Proof" tab, KeY highlights what part of the Sequent this rule was applied to.


# Replicating completed proofs
To reproduce proofs in KeY, the two kinds of settings need to be adjusted: Taclet Options and Proof Search Strategy.
Below in this README, there are two tables that display the settings we used.

We will load in a small proof goal (stored in a .key file) to verify:
In KeY, click File->Load (Ctrl+O), and select Proofs/Article-Assertions/targetWords-Bound.key.
It should be possible for KeY to automaticaly complete this proof.
(Max. Rule Applications: at least 1000; Arithmetic Treatment: DefOps.)
Click on the green play button in the top left, and KeY should automatically complete the proof.

We can also reproduce the other proofs of the .key files in the Proofs/Article-Assertions directory.
Every .key file in the Proofs/Article-Assertions directory contains a single proof that can be proven automatically in KeY.
(Max. Rule Applications: at least 5000; Arithmetic Treatment: DefOps.)
Once the proof goal has been loaded in, it can be completed automatically by pressing the Green Play button in the top left of the window. 

Next, we can load in Edited-BitSet/BitSet.key.
In this case, a list of possible method contracts is displayed.
Most methods in the list can be verified, and thus have completed proofs in the Proofs/BitSet-Methods/ directory. 
These proofs cannot be completed automatically with the current settings, but they can be recreated interactively (this takes some experience with KeY to figure out).

Due to various limitations of KeY (see the article), the following contracts / methods do not have completed proofs: get(int,int), set(int), length().
Furthermore, KeY does show the method wordsToSeq(), but it cannot be verified as it is a model method and does not have a contract.

# KeY settings used
Under Options->Show Taclet options:
```
| JavaCard                     | Off                         |
| Strings                      | On                          |
| Assertions                   | Off                         |
| Bigint                       | On                          |
| Initialisation               | DisableStaticInitialisation |
| intRules                     | JavaSemantics               |
| integerSimplificationRules   | Full                        |
| JavaLoopTreatment            | Efficient                   |
| MergeGenerateIsWeakeningGoal | Off                         |
| MethodExpansion              | ModularOnly                 |
| ModelFields                  | TreatAsAxiom                |
| MoreSeqRules                 | On                          |
| Permissions                  | Off                         |
| ProgramRules                 | Java                        |
| Reach                        | On                          |
| RuntimeExceptions            | Ban                         |
| Sequences                    | On                          |
| WdChecks                     | Off                         |
| WdOperator                   | L                           |
```
In the Proof Search Strategy tab:
```
| Max. Rule Applications    | Varying amounts*       |
| Stop at                   | Default                |
| One Step Simplification   | Disabled               |
| Proof Splitting           | Delayed                |
| Loop Treatment            | Invariant (Loop Scope) |
| Block Treatment           | Internal Contract      |
| Method Treatment          | Contract               |
| Merge Point Statement     | Merge                  |
| Dependency Contracts      | On                     |
| Query Treatment           | Off                    |
| Expand Local Queries      | On                     |
| Arithmetic Treatment      | Basic/DefOps*          |
| Quantifier Treatment      | No Splits with Progs   |
| Class Axiom Rule          | Off                    |
| Auto Induction            | Off                    |
| User-Specific taclet sets | All off                |
```

## Regarding the two asterisks:
* Max. Rule Applications:
Generally, around 5000 seems a reasonable amount when using the 'Close Provable goals' macro. 
The exception to this is when a program contains bitwise shift operations, such as in the wordIndex(int) method.
When shift operations occur, KeY's macros such as 'Finish symbolic execution' appear to get stuck endlessly unfolding the shift operation. 
We advise setting the max rule applications to no more than 100 or 200 at a time when using this macro, and manually unfolding the shift operation. (See Proofs/wordIndex.proof)

* Arithmetic Treatment:
In order to keep proof goals human-readable, we advise setting this option to Basic when using macros such as 'Finish symbolic execution'.
In order to complete the proof, set the option to DefOps after the macro has completed.


