---
layout: page
title: User Guide
---
**Hi private tutors!**
**TutorConnect** is a **desktop app for managing student contacts for private tutors, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). With a few simple commands, you can unleash the potential of the powerful commands in **TutorConnect** that manages all your students, your lessons and your payments at ease!
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-F09-2/tp).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `addstu n/John Doe p/98765432 e/johnd@example.com` : Adds a student named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a new student: `addstu`

Adds a person to the address book.

Format: `addstu n/NAME p/PHONE_NUMBER e/EMAIL [a/ADDRESS] [t/TAG]…​ [edu/EDUCATION_LEVEL]

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have 0 or 1 address
A person can have any number of tags (including 0)
All other fields are compulsory
When a student is initialised, *by default* : 
1. the student has not paid for any lesson(refer to track for more info)
2. the student does not have any lesson (refer to addLesson for more info)
3. If `edu/` is omitted, the education level defaults to `UNKNOWN`. </div>

Examples:
* `addstu n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 edu/primary 3`
* `addstu n/Betsy Crowe t/friend e/betsycrowe@example.com p/1234567 t/history edu/sec 2`
* `addstu n/Cindy Wong p/12355677 e/12345@example.com edu/j1`
* `addstu n/Choo P p/81112222 e/choo@example.com a/Blk 1 edu/other`
* `addstu n/Alex Tan p/91223344 e/alex@example.com`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​ [edu/EDUCATION_LEVEL]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without specifying any tags after it.
* To change the education level, provide edu/EDUCATION_LEVEL. To clear it back to UNKNOWN, pass edu/ with nothing after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.
*  `edit 3 edu/sec 4` Changes the 3rd person's education level to SEC_4.
*  `edit 4 edu/` Clears the 4th person's education level back to UNKNOWN.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Payment Status of a person : `payment`

Displays an existing person's payment status in the address book. Optionally, can mark 1 lesson as paid or unpaid using `s/` flag.

Format: `payment INDEX [optional: s/paid|unpaid]`

* Check payment status of the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* Optionally, you may include a status flag using s/paid or s/unpaid to update the person's lesson payment status.
* When the status is updated using s/, the system will automatically adjust the internal payment count (e.g., increment or decrement based on the change to "paid" or "unpaid").

Examples:
* `list` followed by `payment 2` displays payment status of the 2nd person in the address book.
* `list` followed by `payment 2 s/paid` marks 1 lesson of 2nd person in the address book as paid. Then displays the updated payment status of the 2nd person in the address book.
* `list` followed by `payment 2 s/unpaid` marks 1 lesson of 2nd person in the address book as unpaid. Then displays the updated payment status of the 2nd person in the address book.
### Locating persons by tag: `searchtag`

Finds persons whose tags contain any of the given keywords.

Format: `searchtag KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `chem` will match `Chem`
* The order of the keywords does not matter. e.g. `chemistry physics` will match `physics chemistry`
* Only tags are searched.
* Partial matches are allowed. e.g. `chem` will match `chemistry`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. physics math will return all persons with either tag.

Examples:
* `searchtag chemistry` returns all persons tagged with `chemistry`
* `searchtag fri col` returns all persons with tags containing `fri`(e.g. `friends`) or `col` (e.g. `colleagues`)
  ![result for 'searchtag fri col'](images/searchtagFriCol.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Add a lesson : `addLesson`

Add the scheduled upcoming lesson for the specified person.

Format: `addLesson n/NAME l/LESSON_DATE`

* The name refers to the name shown in the displayed person list.
* This command checks if the person exists in the address book.
* If the person is a student with a scheduled lesson, the command shows an error.
* If they are a student with no scheduled lesson, the upcoming lesson will be added and displayed in the address book.

Examples:
* `list` followed by `addLesson n/John l/Wednesday` add John's upcoming lesson and displays it in the address book.
* `find Betsy` followed by `addLesson n/Betsy l/Monday` adds Betsy's lesson and displays it in the address book.

### Cancel a lesson : `cancelLesson`

Cancels the scheduled upcoming lesson at the specified index.

Format: `cancelLesson INDEX`

* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** not greater than the total number of persons in the addressbook.
* This command checks if the person at the specified index is a student.
* If the person is not a student, the command returns an error message.
* If the person is a student with a scheduled lesson, the command cancels that lesson.
* If they are a student with no scheduled lesson, an error message will be displayed.

Examples:
* `list` followed by `cancelLesson 6` cancels the 6th person's upcoming lesson if the 6th person is a student with a scheduled lesson.
![result for 'cancelLesson 6'](images/CancelLesson6.png)
* `find Betsy` followed by `cancelLesson 1` cancels the lesson of the 1st person in the results of the `find` command if they are a student with a scheduled lesson.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**CancelLesson** | `cancelLesson INDEX` <br> e.g. `cancelLesson 6`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**PaymentStatus** | `payment INDEX [optional: s/paid\|unpaid]`<br> e.g., `payment 1 s/unpaid`
**Help** | `help`
