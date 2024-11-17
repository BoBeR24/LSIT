# Here we keep track of what is stated in UML diagram in more details

## Submitting ship specs and issues:
Customer submits [service request](src/main/java/lsit/Models/ServiceRequest.java) forma where they state all the necessary information about their ship
and issues they want to address. <br>

This forma is being transferred to diagnostic team

## Diagnosis process
### Initial scan
Diagnostic team reads through the forma submitted by the customer and realises where potential problem may lay.
### System Check
Diagnostic team inspects the ship and evaluates the potential problem. If issue is considered minor - they address it
by themselves. <br>
If issue requires more complex approach - they distribute assessment among tech teams. <br>
Diagnosis team submits their assessment in the special [form](src/main/java/lsit/Models/DiagnosticAssessment.java)

## (**TODO:**  Next step would to create entities for all tech groups each of whom submit their work and report to the final assembly team)
