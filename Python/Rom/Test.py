terminal.clear()
terminal.setCursorPos(0,0)
for i in range(32):
    terminal.setTextColor(i % 5 + 10)
    print(i)