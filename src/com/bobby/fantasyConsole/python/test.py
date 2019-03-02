import random

count = 0
i = 0

GPU.clear(13)
for i in range(20):

    GPU.setColor(3)
    GPU.drawCircle(random.randint(0,100),random.randint(0,100), 10)
