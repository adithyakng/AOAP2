

import random


input = [10,50,100]
for i in range(len(input)):
    inputList = []
    m = input[i]
    n = input[i]
    h = random.randint(1,100)
    k = random.randint(1,100)
    for j in range(0,m):
        intputRow = [];
        for k in range(0,n):
            intputRow.append(random.randint(1,100));
        inputList.append(intputRow)

    f = open("input3_"+str(n)+".txt","w")
    f.write(str(m)+" "+str(n)+" "+str(h)+" "+str(k)+"\n")
    for j in range(0,m):
        for k in range(0,n):
            f.write(str(inputList[j][k])+" ")
        f.write("\n")
    f.close()