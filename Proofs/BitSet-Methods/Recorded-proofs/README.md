All proofs in this folder have been completed in KeY version 2.10.0.
This generally requires that Arithmetic Treatment is set to DefOps. 

The proofs in this folder required more human interaction than the methods in the parent directory, hence why these proofs have also been recorded.

The proof of the set(int) method has been split up into 4 recordings. The proof file at the end of each part of the 4 recording has also been provided. 
The part-4 file is the completed proof.
Part 1: Setting up the proof through using the 'Finish Symbolic Execution' macro and by closing other smaller goals, as well as verifying that the invariant holds at the end of the method.
Part 2: Closing the [ensures wordsToSeq()[bitIndex] == 1;] goal.
Part 3: Closing the [ensures (\forall \bigint j; ..] goal.
Part 4: Closing the [ensures \old(wordsToSeq()).length < wordsToSeq().length ==> ..] goal, thereby completing the whole proof.

Explanation for some initial steps in set(int)-part-1:
	Normally, the 'Finish Symbolic Execution' macro is able to run through the code without manual intervention. However, the KeY prover appears to get stuck on the shift-left operation (1L << bitIndex) in the code. To avoid this, we first limit the number of operations that KeY applies in 1 step to 600 (00:00:05), then apply the 'Finish Symbolic Execution' macro (00:00:09). Once this has finished running, we then de-select the "Hide Non-interactive Proofsteps" option (00:00:14). We then search for the "javaShiftLeftLongDef" rule application (which is the start of KeY applying rules to the shift-left), and prune the proof at this step (00:00:20). We then search for the "shiftleftJlong(1, bitIndex)” term in the sequent, and apply the “pullOut” step (00:00:24). We then hide the “shiftleftJlong(1, bitIndex) = shiftleftJlong_0” term (00:00:26). At this point, we now continue the symbolic execution by re-hiding the Non-interactive Proofsteps and once again running the 'Finish Symbolic Execution' macro (00:00:31). 
In order to return the “shiftleftJlong(1, bitIndex) = shiftleftJlong_0” term” to the sequent, we can apply the “Insert Hidden” rule on the whole sequent, such as at (00:06:46).