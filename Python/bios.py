#This file is going to be used for setting up the python environment.

import sys

class Logger(object):
    def write(self, message):
        terminal.print(message)


sys.stdout = Logger()