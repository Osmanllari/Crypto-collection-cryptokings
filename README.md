# crypto-collection-cryptokings

Project Submission for Information Security Unit.

Note: For simplicity the source files have been created under the _default_ package. This project uses the BoncyCastle API.

Instructions to run the project:
1. Create a Java project 
2. Copy the source (.java) files to the default package of the src folder.
3. Open Project Properties and navigate to configure the Java Build Path
4. Add External BouncyCastle JAR (provided under _crypto-collection-cryptokings/JARfiles_)
5. Run the **WelcomeGUI.java** class.

Alternatively, a JAR executable named **CryptoTool.jar** has been provided through github under _crypto-collection-cryptokings/JARfiles_.

This application provides: Symmetric Encryption/Decryption, Asymmetric Encryption/Decryption, and Digital Signing/Verification.
Most of the logic for these functionalities can be found under the classes: 
  * SymmetricEncryption.java 
  * AsymmetricEncryption.java
  * DigitalSigning.java

The rest of the classes contain some helper methods and the necessary code for the Graphical User Interface.
  
