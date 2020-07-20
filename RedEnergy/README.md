#SimpleNem12Parser
The main objective of this application is to parse a given csv input file and fetch the details accordingly.

** System and Software requirements to run the application **
1. Install Java version "1.8.0_201" or higher.


** Prerequisites **

The system will be initialized with an input file in CSV format containing a list of Meter reading records.
* No quotes or extraneous commas appear in the CSV data.
* Unless the file is missing header/footer we proceed processing the file.
* When a bad record encounters we just skip those particular records(logging the exception in logs) and proceed processing the file.(This is because if a file is big and just because of one bad record we cannot skip the already processed lines and abruptly end)
* No records exist after end record:900

** Instructions to run the application **

Execute TestHarness.java to check the results
