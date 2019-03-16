import random, os

highlightColor = 11
textColor = 14;
errorColor = 13;

previousCommands = []
previousCommandsIndex = 0

terminal.setTextColor(10)

welcomeMessage = 'Fantasy Console written by Bobby\nVersion 0.0.1 2019'

drawMode = False

currentInput = ""
lastCommand = ""

terminal.clear()
terminal.setCursorPos(0,0)

print(welcomeMessage)
terminal.setTextColor(highlightColor)
terminal.write(shell.getCWD() + ">")

count = 0;
GPU.clear(0)

def draw():
    terminal.setTextColor(textColor)
    if not drawMode:
        terminal.draw(GPU)

def updateInput():
    terminal.clearLine(terminal.getCursY())
    terminal.setCursorPos(0, terminal.getCursY())
    terminal.setTextColor(highlightColor)
    terminal.write(shell.getCWD() + ">")
    terminal.setTextColor(textColor)
    terminal.write(currentInput)

def key_typed(code):
    global lastCommand, currentInput, previousCommandsIndex, previousCommands
    if(code == 10):
        print("")
        parse_command(currentInput)
        currentInput = ""
        previousCommandsIndex = len(previousCommands)
    elif(code == 8):
        currentInput = currentInput[:-1]
    elif(code == 0):
        pass
    else:
        currentInput += chr(code)
    updateInput()

def key_pressed(code):
    global currentInput, previousCommandsIndex
    if(code == 38):
        if(previousCommandsIndex == 0 and len(previousCommands) != 0):
            previousCommandsIndex = len(previousCommands) - 1
        else:
            if(previousCommandsIndex > 0):
                previousCommandsIndex -= 1;
        currentInput = previousCommands[previousCommandsIndex]
    updateInput()

def parse_command(command):
    global lastCommand

    commands = command.strip().lower().split()
    if(len(commands) > 0):
        try:
            globals()[commands[0]](commands)
        except Exception as e:
            words = str(e).split()
            if(len(words) == 1):
                unknown_command(commands[0])
            else:
                terminal.setTextColor(13)
                print(e)

        if(command != lastCommand):
            previousCommands.append(command)
            lastCommand = previousCommands[len(previousCommands) - 1]



def null(args):
    terminal.write("")

def cwd(args):
    print("\"" + shell.getCWD() + "\"")

def hello(args):
    terminal.setTextColor(random.randint(0,15))
    terminal.print("Hello world\n")

def cd(args):
    if(len(args) < 2):
        print("Usage: cd <path>")
        return
    shell.setCWD(args[1])

def ls(args):
    items = os.listdir("Python/Rom/")
    files = []
    dirs = []
    for item in items:
        if("." in item):
            files.append(item)
        else:
            dirs.append(item)
    terminal.setTextColor(9)
    for file in files:
        terminal.write(file + " ")
    terminal.setTextColor(10)
    for dir in dirs:
        terminal.write(dir + " ")
    print("")


def unknown_command(command):
    terminal.setTextColor(errorColor)
    print("Unknown command: " + command)

def clear(args):
    terminal.clear()
    terminal.setCursorPos(0,0)

def help(args):
    terminal.setTextColor(14)
    terminal.write("[")
    terminal.setTextColor(15)
    terminal.write("help")
    terminal.setTextColor(14)
    terminal.write("] ")
    terminal.setTextColor(10)
    terminal.write('Welcome to the help command, this is just a test command for now.\n')

def run(args):
    if(len(args) < 2):
        print("Usage: run <filename>")
        return
    shell.run("Python/Rom/" + args[1])

def exit(args):
    terminal.setTextColor(14)
    terminal.write("Exiting")
    shell.exit()

def info(args):
    terminal.setTextColor(10)
    print(welcomeMessage)

def edit(args):
    if(len(args) < 2):
        print("Usage: edit <filename>")
        return
    f = open("Python/Rom/" + args[1])
    terminal.setTextColor(15)
    print(f.read())
    f.close()




