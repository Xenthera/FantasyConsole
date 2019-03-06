import sys

class Logger(object):
    def write(self, message):
        terminal.print(message)


sys.stdout = Logger()