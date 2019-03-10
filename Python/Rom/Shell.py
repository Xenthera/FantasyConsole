import random

highlightColor = 11
textColor = 15;
errorColor = 13;

terminal.setTextColor(10)

welcomeMessage = 'Fantasy Console written by Bobby\nVersion 0.0.1 2019\n'


drawMode = False

currentInput = ""
lastCommand = ""

commands = ["run", "help", "clear", "ls", "exit"]


terminal.write(welcomeMessage)
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
        parse_command()
        if(lastCommand.strip().lower() != "clear"):
            print("")
        terminal.setTextColor(highlightColor)
        terminal.write(shell.getCWD() + ">")
        terminal.setTextColor(textColor)
    elif(code == 8):
        if(len(currentInput) > 0):
            terminal.setCursorPos(terminal.getCursX() - 1, terminal.getCursY())
            terminal.write(" ")
            terminal.setCursorPos(terminal.getCursX() - 1, terminal.getCursY())
            currentInput = currentInput[:-1]
    else:
        terminal.write(chr(code))
        currentInput += chr(code)

def parse_command():
    global currentInput
    global lastCommand
    global commands
    if(currentInput.lower() in commands):
        try:
            globals()[currentInput.strip().lower()]()
        except Exception as e:
            print(e)
    else:
        unknown_command()
    lastCommand = currentInput
    currentInput = ""


def null():
    terminal.write("")

def ls():
    terminal.setTextColor(8)
    terminal.write("fake directories and ")
    terminal.setTextColor(9)
    terminal.write("files.py lol.txt")

def unknown_command():
    global currentInput
    terminal.setTextColor(errorColor)
    terminal.write("Unknown command: " + currentInput)

def clear():
    terminal.clear()
    terminal.setCursorPos(0,0)

def help():
    terminal.setTextColor(14)
    terminal.write("[")
    terminal.setTextColor(15)
    terminal.write("help")
    terminal.setTextColor(14)
    terminal.write("] ")
    terminal.setTextColor(10)
    terminal.write('Welcome to the help command, this is just a test command for now.')

def run():
    terminal.setTextColor(9)
    terminal.write("placeholder for the run command")

def exit():
    terminal.setTextColor(14)
    terminal.write("This will exit in the future")




