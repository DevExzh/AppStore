# Assignment 2
![License](https://img.shields.io/badge/License-Apache--2.0-blue)
![JUnit](https://img.shields.io/badge/JUnit-5.9.0-blue)
![XStream](https://img.shields.io/badge/XStream-1.4.19-blue)
![ZHU He](https://img.shields.io/badge/Author-Ryker%20Zhu-blueviolet)
![SETU](https://img.shields.io/badge/Lecturer-Mairead%20Meagher-9cf)
![GitHub](https://img.shields.io/github/stars/DevExzh/AppStore?style=social)

__A simple implementation of assignment of _Programming Fundamentals in Practice_, just for being evaluated for marks so feel free to copy the code :satisfied:__

The program may still have some :bug: bugs. If you find them, please let me know (via e-mail).

----
### Overview
Its main ambition is to develop an App Store system. It is a menu-driven, console app that can store apps of the following type:
* `EducationApp`
* `GameApp`
* `ProductivityApp`

An app can only be added for existing developers.
The information entered via the app will be persisted in XML files.

### Tests
| _Class Name_ | _Code Coverage_ | _Test Results_ |
|:----------:|:-------------:|:-------:|
|<code>AppStoreAPI</code>|<img src="https://img.shields.io/badge/coverage-100%25-brightgreen"/>|<img src="https://img.shields.io/badge/tests-31%20passed-green"/>|
|<code>EducationApp</code>|<img src="https://img.shields.io/badge/coverage-100%25-brightgreen"/>|<img src="https://img.shields.io/badge/tests-18%20passed-green"/>|
|<code>GameApp</code>|<img src="https://img.shields.io/badge/coverage-100%25-brightgreen"/>|<img src="https://img.shields.io/badge/tests-21%20passed-green"/>|
|<code>ProductivityApp</code>|<img src="https://img.shields.io/badge/coverage-100%25-brightgreen"/>|<img src="https://img.shields.io/badge/tests-16%20passed-green"/>|

### Classes
* Newly added
    | _Class Name_ | _Responsibility_ |
    |:----------:|:---------------:|
    | `FoundationClassUtility` | Extend the functionality of utils package, providing more convenient tools like function pointers, statistics, etc. |
    |`Language`| Enumerate all the languages that are available on the App Store. |
    |`Genre`|Enumerate the genres that a `GameApp` belongs to.|
    
    Function pointers are provided in the package, you can use them in this way:
    ```java
    // Declaring a function using FunctionPointer class
    void function(FunctionPointer<Void, Integer> callback) {
        callback.invoke(1234); // Call the method
    }

    // Employing lambda (Since Java 8)
    function((Integer parameter) -> parameter + 20)
    ```
* Modified
    | _Class Name_ | _Modification_ |
    |:---:|:---|
    | `DeveloperAPI`, `AppStoreAPI` | Since the `XStream.setupDefaultSecurity()` method is deprecated in version 1.4.18 and above, the implementation of the `load()` method is modified to avoid using the deprecated method. |
    | `DeveloperAPI`, `AppStoreAPI` | Because `FileNotFoundException` will be thrown if the two XML files do not exist in the specified path, the `fileName()` method has now been modified to create the file if it does not exist. |
* Additional fields
    | _Class Name_ | _Extra fields_ | _Description_ |
    |:------------:|:--------------:|:--:|
    |`App`| String `description`|Describe the functionality, target age groups, etc. of an app with brief information.|
    |`App`| HashSet&lt;Language&gt; `languages`|Indicate the languages that an app support.|
    |`App`| String `currencySymbol`|The current currency symbol employed in the App Store system (CNY/EUR/USD/GBP).|
    | `GameApp` | HashSet&lt;Genre&gt; `genres` |Types or styles of a `GameApp`.|

### Extra functionality
There are several additional functionalities I have written in class `main.Driver`:
* Add a private field `clearScreen` which is a function pointer that will be instantiated when the `start()` method is called. On Windows platform, it will clear the console by using command `cls` and on macOS or other UNIX-like OS it will do the same operation by print special characters to the console (Not tested).

    Use the following statement when you want to clear all outputs on the console:
    ```java
    clearScreen.invoke();
    ```

* ___Platform Specified Feature___: When the program run on Windows, the window title will be changed into App Store

### License
[Apache 2.0](LICENSE) © Ryker Zhu (ZHU He)