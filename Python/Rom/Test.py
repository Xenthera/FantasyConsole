import math

terminal.clear()
terminal.setCursorPos(0,0)
terminal.setTextColor(0)

GPU.clear(15)
GPU.setColor(0)
lastX = 0
lastY = 0

gradient = [0, 5, 13, 14, 15, 15]

class ball():
    def __init__(self, x, y, char):
        self.x = x
        self.y = y
        self.radius = 7
        self.velx = random.randint(-2,2)
        self.vely = random.randint(-2,2)
        self.char = char
        if(self.velx == 0):
            self.velx = 1;
        if(self.vely == 0):
            self.vely = -1;

    def draw(self):
        GPU.setColor(14)
        GPU.fillCircle(self.x, self.y, self.radius)
        GPU.setColor(6)
        GPU.fillCircle(self.x, self.y, self.radius - 2)
        GPU.setColor(14)
        GPU.drawString(self.x - 2, self.y - 2, self.char)

        self.x += self.velx
        self.y += self.vely

        if(self.x > GPU.getWidth() or self.x < 0):
            self.velx = -self.velx

        if(self.y > GPU.getHeight() or self.y < 0):
            self.vely = -self.vely

def map(value, start1, stop1, start2, stop2):
    return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1))

def distance(x1, y1, x2, y2):
    return math.sqrt((x1 - x2)**2 + (y1 - y2)**2)

balls = []

for i in "$$$$$$$$$$$$$$$$$$":
    balls.append(ball(random.randint(0, GPU.getWidth()), random.randint(0, GPU.getHeight()), i))

def draw():
    GPU.clear(0)
    for ball1 in balls:
        for ball2 in balls:
            if(ball1 != ball2):
                dist = distance(ball1.x, ball1.y, ball2.x, ball2.y)
                if(dist <= 100):
                    idx = map(dist, 0, 100, 0, 5)
                    GPU.setColor(gradient[5 - int(idx)])
                    GPU.drawLine(ball1.x, ball1.y, ball2.x, ball2.y)

    for ball in balls:
        ball.draw()

def key_pressed(code):
    pass

def key_typed(code):
    pass
