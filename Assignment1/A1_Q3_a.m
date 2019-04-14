clear all;
syms x;
syms y;
countArray = zeros(100,4);
maximumValues = zeros(100,4);
min = 0;
max = 10;
step = [0.01, 0.05, 0.1, 0.2];
for i = 1:4
    X = (max-min).*rand(100,1) + min;       %generating random value within range
    Y = (max-min).*rand(100,1) + min;
    for loop1 = 1:100
        x = X(loop1,1);
        y = Y(loop1,1);
        %maxValue = sin(x/2) + cos(2*y);
        maxValue = -abs(x-2)-abs(0.5*y+1)+3;
        while 1
            x = x + step(i);
            y = y + step(i);
            %f1 = sin(x/2)+cos(2*y);
            f2 = -abs(x-2)-abs(0.5*y+1)+3;
            countArray(loop1,i) = countArray(loop1,i) + 1;
            if (maxValue < f2)
                maximumValues(loop1,i) = f2;
                break;
            end
            if (x == 0 || y == 0 || x == 10 || y == 10)
                maximumValues(loop1,i) = maxValue;
                break;
            end
        end
    end
end

meanCount = mean(countArray);
stdCount = std(countArray);
meanMaxValue = mean(maximumValues);
stdMaxValue = std(maximumValues);