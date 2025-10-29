---
layout: page
title: User Guide
---
**Hi private tutors!**
Welcome to **TutorConnect** — a contact and lesson management tool designed specifically for private tutors.

TutorConnect helps you keep track of students, lessons and payments efficiently. It combines the speed of Command Line Interface (CLI) commands with a simple GUI, making it ideal for tutors who prefer quick keyboard input but still appreciate visual feedback.

For example, a tutor managing 20 students can easily find student's contact information — all through short, easy commands.

**Assumption**: This guide assumes you are somewhat familiar with using command-line (like opening a terminal and running commands).
* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

Before diving into commands, let’s quickly set up TutorConnect so you can start managing your students right away.

**1. Check your Java version**<br>
Make sure your computer has **Java 17 or newer installed**.
   * **Mac users:** Please use the exact JDK version recommended [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).
>  :bulb: **Not sure how to check your Java version? See [this quick guide](https://www.java.com/en/download/help/version_manual.html).**

**2. Download StudentConnect**<br>
Get the latest `.jar` file from [our GitHub releases page](https://github.com/AY2526S1-CS2103T-F09-2/tp).

**3. Set up your home folder**<br>
Move the downloaded `.jar` file into a folder where you’d like TutorConnect to store your data (this will be your “<u>home folder</u>”).

**4. Run the app**<br>
Open a command terminal and navigate to the <u>home folder</u> where you saved the jar file to.<br>
Then run: `java -jar addressbook.jar`
   
After a few seconds, you should see a window like this:<br>![Ui](images/Ui.png)

**5. Try out some basic commands**<br>
Type a command in the box and press Enter to run it. 

Here are a few to get you started:

* `help` &ndash; Opens the help window.

* `list` &ndash; Lists all contacts.

* `addstu n/John Doe p/98765432 e/johnd@example.com` &ndash; Adds a student named `John Doe`.

* `delete 3` &ndash; Deletes the 3rd contact shown in the current list.

* `clear` &ndash; Deletes all contacts.

* `exit` &ndash; Exits the app.

**6. Explore more features**<br>
Check out the [Features](#features) below for a full list of commands and details.

--------------------------------------------------------------------------------------------------------------------

<div id="command-notes" markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by you.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g. `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

--------------------------------------------------------------------------------------------------------------------

## Basic Concepts

To have a better understanding of how we process the commands in StudentConnect, you can take a look at the basic concepts here!

### Student
A student is represented as an item in the student list. You can add a student by callling the [`addstu`](#adding-a-new-student-addstu) command and delete a student by calling the [`delete`](#deleting-a-person--delete) command. Once a student is added, it will appear on the list at the bottom of the panel, with a number index assigned to the student. 
The number index is based on the order that the student is added into the list.

A student has the following attributes, as shown on the example above:
1. name: The name of the student
2. phone number: The phone number of a student
3. email address: The email address of a student
4. address: The address of the student (This is optional, so you do not need to include this if you are hosting online lessons)
4. [tag](#tag): Tags that record extra informations of a student
5. [lesson](#lesson): The lesson that the student has

<div markdown="block" class="alert alert-info">
A student can only have **zero or one lesson**. If you try to add a new lesson to a student which already has a lesson, you will receive an error.
</div>

### Student list
The list of the student that appears at the bottom of the application interface. It shows all students that is stored in the application. These students in the student list can be accessed by commands such as [`addLesson`](#add-a-lesson--addlesson), whenever we need to refer to a specific student that is **already created**.
 
### Tag 
A tag is a piece of text that records a unique characteristic of the student. You can add optionally add any number of tags when you create a new [student](#student) by calling the [`addstu`](#adding-a-new-student-addstu) command.

In StudentConnect, you can use the tag in the following way:
1. Record the subject of the [student](#student) (e.g. `history`, `chemistry`) if you teach multiple subjects
2. Record information about the [student](#student) that you need to remind yourself (e.g. `50$/hr`)
3. Record whether you need to provide feedbacks to the [student](#student)'s parents (e.g. `feedback/no-feedback`)
4. Record the platform that you use if you are hosting online lessons(e.g. `platform:Giggle meet`)

### Lesson

A lesson is represented by a single date, which is the date (in the fixed format of `yyyy-MM-dd`) that the lesson starts. We assume that the lesson will end on the same day
* For instance, a lesson that starts at `November 10th 2024 5pm` should be represented as `2024-11-10` in the system.
 
When a student is created, the student by defualt does not have any lesson. To record an upcoming lesson to the student, use [addLesson](#add-a-lesson--addlesson) command to create a new lesson to the [student](#student) in the [student list](#student-list)

A lesson is considered **passed** if the date of lesson recorded is **before the current date**(Note that if the lesson date is the same as the current date, it is **not considered passed**). If a lesson is **passed**, the lesson will be dropped from the student who had the lesson, so the student will no longer have any lesson again. Lessons are updated whenever the application is launched.

## Advance Concepts

### Recurring lessons

As most tutors conduct lessons regularly—such as every week or every few days—**recurring lessons** help you keep track of lessons that happen on a routine schedule.

A **recurring lesson** has two key attributes:

* **`LESSONDATE`**: the date when the first lesson starts
* **`INTERVAL`**: the number of days between each lesson

For example, if your lesson starts on `2025-10-10` and occurs once every week, then:

* `LESSONDATE` = `2025-10-10`
* `INTERVAL` = `7`

The system automatically updates a recurring lesson whenever its `LESSONDATE` is earlier than the current date. It does this by adding the `INTERVAL` (in days) to the previous `LESSONDATE`, moving it forward to the next scheduled lesson.

For instance:

* Today’s date = `2025-10-20`
* Current `LESSONDATE` = `2025-10-19`
* `INTERVAL` = `7`

Since the recorded lesson date has already passed, the system updates it to **2025-10-26** — one week later.

This ensures your recurring lessons always reflect the *next upcoming* session without requiring manual changes.

### Payment Status
Payment tracking has been an anathema to private tutors, especially when there are multiple students with different payment habits. We use the payment status to make this issue simple.

The payment status records the balance between **the number of lessons that students has paid** or the **number of lessons that has passed**
* If the student has paid for `10` lessons and `13` lessons has passed in total, the payment status will be `10-13 = -3`, which means that the students has **3 outstanding lessons that needs to be paid**. You can combine this with the [tag](#tag) feature to also record the price of each lesson and everything becomes easy calculation.

**The number of lessons that students has paid** is tracked manually. You can update this by using [payment](#payment-status) command.
**The number of lessons passed** is automatically updated whenever a lesson has passed(See [lesson](#lesson) for more details).


--------------------------------------------------------------------------------------------------------------------

## Features

### Getting help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a new student: `addstu`

<div markdown="span" class="alert alert-primary">:bulb: **Warning:**
The `add` command available in the previous versions is deprecated and cannot be used anymore.
Use this command instead to add a new student
</div>

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

Displays or updates the payment status of a person in the address book.

Format: `payment INDEX [s/paid | s/unpaid]`

* Check payment status of the person at the specified `INDEX`.
  * The index refers to the index number shown in the displayed person list.
  * The index **must be a positive integer** 1, 2, 3, …​
* Optionally, you may include a status flag (`s/paid` or `s/unpaid`) to update the student's payment status.
* When a status flag is provided, the system updates the student’s payment record accordingly.

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

Format: `addLesson n/NAME l/LESSON_DATE [every/INTERVAL]`

* The `NAME` refers to the name of the student who you wants to add the lesson. You cannot add a lesson to a student that does not exist in the list
* If the person is a student **with a scheduled lesson**, the command shows an error. This is true also for recurring lessons.
* If they are a student **with no scheduled lesson**, the upcoming lesson will be added and displayed in the address book.
* The `LESSONDATE` refers to the date of the lesson to be added. It follows a strict format of `yyyy-MM-dd`(e.g. `2025-10-05`, note that `2025-10-1` or `2025-1-10` **are considered as wrong formats** because your month and date must be **in two characters**). The start date of the lesson is only considered valid when it is within the range from the **current date where you add the lesson** until **364 days past that current date**. This is to prevent unreasonable inputs.  
* If you want to add a **recurring lesson**, a lesson that refreshes itself after a fixed number of days, you can use the optional `every/` indentifier with a **positive integer** to indicate after how many days will the lesson automatically update itself to the next date instead of deleting itself. When you do not have the `every/` indentifier. The lesson will be counted as a normal lesson, which automatically deletes itself after the date of the lesson has passed.


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
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g. if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

**Q**: How do I edit student information if I input anything wrong?<br>
**A**: StudentConnect so far does not support edit function. You may use the command `delete INDEX` first and then recreate the student.

**Q**: If I add a lesson on the day of the lesson itself, is it counted as being outdated?<br>
**A**: No, your lesson will only delete/update itself (depending on the type of the lesson) after the date of the lesson has passed. For example, if today is `2025-10-29` and your lesson is set on that day. It will only be considered outdated on `2025-10-30`.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**addstu** | `addstu n/NAME p/PHONE_NUMBER e/EMAIL [a/ADDRESS] [t/TAG]…​` <br> e.g. `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**addLesson** | `addLesson n/NAME l/LESSONDATE [every/INTERVAL]​` <br> e.g. `addLesson n/Paul l/2025-11-13`
**cancelLesson** | `cancelLesson INDEX​` <br> e.g. `cancelLesson 3` 
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g. `delete 3`
**CancelLesson** | `cancelLesson INDEX` <br> e.g. `cancelLesson 6`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g. `find James Jake`
**List** | `list`
**PaymentStatus** | `payment INDEX [s/paid\| s/unpaid]`<br> e.g. `payment 1 s/unpaid`
**Help** | `help`
