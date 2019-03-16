terminal.clear()
terminal.setCursorPos(0,0)
for i in range(32):
    terminal.setTextColor(i % 7 + 7)
    print(i)