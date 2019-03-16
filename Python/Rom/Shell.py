import random

highlightColor = 11
textColor = 14;
errorColor = 13;

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
    GPU.setColor(random.randint(0,15))
    x = random.randint(0, GPU.getWidth())
    y = random.randint(0, GPU.getHeight())
    GPU.drawCircle(x, y, random.randint(2,5))
    if not drawMode:
        terminal.draw(GPU)

def key_pressed(code):
    global lastCommand, currentInput
    if(code == 10):
        print("")
        parse_command(currentInput)
        currentInput = ""
    elif(code == 8):
        currentInput = currentInput[:-1]
    else:
        currentInput += chr(code)

    terminal.clearLine(terminal.getCursY())
    terminal.setCursorPos(0, terminal.getCursY())
    terminal.setTextColor(highlightColor)
    terminal.write(shell.getCWD() + ">")
    terminal.setTextColor(textColor)
    terminal.write(currentInput)

def parse_command(command):
    global lastCommand

    commands = command.strip().lower().split()
    if(len(commands) > 0):
        try:
            globals()[commands[0]](commands)
        except Exception as e:
            unknown_command(commands[0])
        lastCommand = commands[0]



def null(args):
    terminal.write("")

def ls(args):
    terminal.setTextColor(8)
    terminal.write("fake directories and ")
    terminal.setTextColor(9)
    terminal.write("files.py lol.txt\n")

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




