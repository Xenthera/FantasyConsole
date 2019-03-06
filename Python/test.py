for i in range(16):
    if i == 0:
        terminal.setBackgroundColor(15)
    else:
        terminal.setBackgroundColor(0)
    terminal.setTextColor(i)
    print(i)

terminal.setTextColor(11)

print("\n\n\nmsg = \"\"\ntry:\n\texec(_code)\nexcept Exception as e:\n\tmsg = str(e)")

