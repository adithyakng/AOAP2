def find_square(p, m, n, h, k):
    dp = [[[] for j in range(n)] for i in range(m)]
    for i in range(m):
        for j in range(n):
            if p[i][j] < h:
                dp[i][j].append((i,j))
    for i in range(1,m):
        for j in range(1,n):
            if p[i][j] >= h:
                indices = dp[i-1][j-1] + dp[i-1][j] + dp[i][j-1]
                indices = list(set(indices))
                if len(indices) <= k:
                    dp[i][j] = indices
    max_size = 0
    for i in range(m):
        for j in range(n):
            size = len(dp[i][j])
            if size > max_size:
                max_size = size
                bottom_right = (i,j)
    top_left = (bottom_right[0]-max_size+1, bottom_right[1]-max_size+1)
    return top_left, bottom_right

p = [[2,3,2,3,2],[9,8,20,6,5],[1,50,40,30,3],[2,3,20,5,6]];
print(find_square(p,4,5,6,2));