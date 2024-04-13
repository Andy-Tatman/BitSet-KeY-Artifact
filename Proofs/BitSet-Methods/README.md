All proofs in this folder have been completed in KeY version 2.10.0.
This generally requires that Arithmetic Treatment is set to DefOps. 

The proofs in this folder have been completed with minimal or no human interaction with KeY.
The proofs in the Recorded-Proofs folder have had considerably more human interaction. For this reason, we have also recorded the proofs being made.

The only methods that have contracts but have not been verified are get(int,int), set(int), and the length() methods.
The get(int,int) and set(int) methods require extensions to KeY before they can be verified (see the article).
Verification of the length() method is also difficult in the current version of KeY due to the large number of bitshift operations in the methods length() calls. (See article)