# About

This was created as a final project for IST440W at Penn State. The intent was to allow a law enforcement agency the ability to photograph encrypted messages found at crime scenes and then use this software to determine the root message. A long term goal that was not achieved was multiple language support for decryption. Currently the photograph can be digitized in any language supported by both Tesseract and Microsoft Translator. Translation requires a Microsoft Azure key.

# Testing
The resources folder includes an example message encrypted using arbitrary characters.

# Special Thanks
Decryption is achieved by utilizing Luca Campese's Cipher Salad. https://github.com/campesel/cipher-salad
