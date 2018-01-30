
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
