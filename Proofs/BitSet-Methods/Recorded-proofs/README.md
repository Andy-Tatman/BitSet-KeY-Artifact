All proofs in this folder have been completed in KeY version 2.10.0.
This generally requires that Arithmetic Treatment is set to DefOps. 

The proofs in this folder required more human interaction than the methods in the parent directory, hence why these proofs have also been recorded.

The proof of the set(int) method has been split up into 4 recordings. The proof file at the end of each part of the 4 recording has also been provided. 
The part-4 file is the completed proof.
Part 1: Setting up the proof through using the 'Finish Symbolic Execution' macro and by closing other smaller goals, as well as verifying that the invariant holds at the end of the method.
Part 2: Closing the [ensures wordsToSeq()[bitIndex] == 1;] goal.
Part 3: Closing the [ensures (\forall \bigint j; ..] goal.
Part 4: Closing the [ensures \old(wordsToSeq()).length < wordsToSeq().length ==> ..] goal, thereby completing the whole proof.