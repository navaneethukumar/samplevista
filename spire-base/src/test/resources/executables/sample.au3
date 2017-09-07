WinWait("[CLASS:#32770]","",10)
ControlSetText("Choose File to Upload", "", "[CLASS:Edit; INSTANCE:1]", $CmdLine[1])
Send($CmdLine[1]) ;location of the file you want to attach to the form and submit
Sleep(2000)
ControlClick("File Upload", "","[CLASS:Button; INSTANCE:1]");