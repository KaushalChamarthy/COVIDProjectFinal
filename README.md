# COVIDProjectFinal
Collects health/user data and determines optimal hospital to visit, if needed.

We will be collecting Heart rate and Oxygen levels through user input. Other collected data includes body temperature, and age/gender (used as baselines). Additional symptom information will also be presented.

Then, other conditions will be checked, creating a number for the patient based on the severity of their symptoms, which will be compared to a threshold to determine whether the patient should go to the hospital.

If the system deems it necessary, then the user's address and range will be asked to create an optimized list of hospitals (anywhere in the US and a few countries outside as well). Otherwise, a message will appear to tell the user their options and email a doctor, if needed.

Please view https://devpost.com/software/quarantine-support-application for demonstration of application.

Built with Java, JavaFX, TomTomAPI, MapQuest API, Javax.mail, Jackson, and JSON for information storage.

This application is by no means approved by the CDC, WHO, or any other health organization. Its purpose is to serve as a framework for other applications to build off of.

Please cite any code, if you do intend to use, by linking to this repository.
