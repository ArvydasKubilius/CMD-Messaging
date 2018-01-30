AXK680
https://git.cs.bham.ac.uk/axk680/Software-WorkShop-Login

# Solution
Now they should run "java Client host-name". - YES

The difference is that a user name is now omitted from the command line. - YES

They can now do the following once the client is running:

1.They can type "register" newline user-name newline:
Yes. When you run the client it ask's of you to either login or register. If you type register it asks for a username(note the username may be taken, and that will give an error) and a password.
Usernames and passwords are stored in a txt file named Secret.txt

This may succeed or fail. It should succeed if there is no such user previously registered. -YES

2.They can type "login" newline user-name newline:
Yes. When the user runs the client and types in login it asks for a username, which is then checked if it's either existing or already loggend in, in these cases it gives an error.
If the username is valid it asks for a password, the password being entered is invisible for security purposes. Having the username and password, it then checks with the txt file.
If it's correct you are logged in.

This may succeed or fail. It should succeed if this user is registered. - Yes

3.They can type "message" newline another-user-name newline text newline. - Yes when you are logged in.
This should succeed if there is a user logged in and the other user exists and is logged in, and send the text to them. - Yes

4.They can type "logout". This should succeed if there is a user logged in. Then the client doesn't end, and still accepts further commands:
Yes. logout logs you out if you are logged in and presents you with the login or register option again and awaits your command.

5.They can type "quit". Then the client logs out and quits, as in part 1. - Yes quit works both in the login/register phase and in the logged in phase.

The above will give you a comfortable 1st class mark for this part, up to 85% of this part.
To get above 85%, namely 34 marks, you should also:
6. Implement passwords for registration and login, in a sensible
  way that you will explain in SOLUTION.md.
  I built my program from the ground up with passwords, so it doesnt feel like an add-on. Login information is checked on the Client, and it loops until you either login succesfully (then it creates the appropriate threads) or you quit.
7. Save registration information in a file, in a sensible way that
  you will explain in SOLUTION.md.
  Yes. I store usernames - passwords in a txt file named Secret.txt, I found this approach to be the most straight-forward one, as it lets you inspect the username/passw data on the fly, and if need be you can even export the txt.
  
ADD-ON FEATURES : 
Firstly, I have added the ability to change password by writing "change password" when you are logged in - this as the name suggest's, lets you change your password.  
Secondly, I do not let two users with the same name login, for this I have created a logged in users txt file, that is called OnlineUsers.txt.
Thirdly, I have added a shutdownhook at the client class, making it so that even in forced termination, the logged in users are removed from the Logged in user's txt.
Fourthly, I made it so that when logging in the password is invisible, for real-world security reasons.

Functions of classes I have either changed or added :
#CLIENT
The client class, now works as the login or register buffer, starting the client threads only if the user actually logins.
It also starts the Quitter thread, which I used for the previous exercise to "quit".
#CLIENTPASW
Is a new class that handles everything that has to do with stored information in txt files - login information, and logged in users. It can check for validity of data. Remove users and so forth.
#CLIENTSENDER
Now checks if you want to message someone, quit, logout or change your password.Besides that not much has changed from the previous itteration.
#QUITTER AND QUITTERSERVER
These classes, were used to make quit work in my previous exercise, I have also added the functionality to logout with them, using similar methods.
#SERVERRECEIVER
Can know take appropriate actions in case of need to logout.
