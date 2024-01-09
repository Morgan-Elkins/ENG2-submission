# Model(s) of the system

Include the models conforming to your metamodel which describe the microservices in your system, and any other models you may have developed to test your editor, viewer, constraints, or transformations.

## Model file(s)

These can follow the `.model`, `.Y1234`, or `.flexmi` extensions.

The extension will depend on the tool used: Exeed uses `.model`, the EMF-generated tree editor would use `.Y1234`, and the Flexmi editor would use `.flexmi`.

## Representation file

If you are using Sirius, this folder should include the `representations.aird` file that it maintains for you with all the Sirius representations of your model elements.

If you are using Picto instead of Sirius, delete the `representations.aird` file, and include the necessary files for the Picto visualisation here.
