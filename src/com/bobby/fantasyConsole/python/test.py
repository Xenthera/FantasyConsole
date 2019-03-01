import java

count = 0

while(True):
    count += 1
    java.lang.Thread.sleep(1000/10)
    GPU.clear(15)
    GPU.setColor(3)
    GPU.drawCircle(count,count, 10)
