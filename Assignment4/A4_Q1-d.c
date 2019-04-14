#include <stdio.h>

float initial[] = {0.375, 0.125, 0.125, 0.375};

float transitions[][4] = {{0.167, 0.111, 0.25, 0.1},{0.5, 0.222, 0.25, 0.4}, {0.167, 0.222, 0.25, 0.1}, {0.167, 0.444, 0.25, 0.4}};

float pNot[] = {0.143, 0.182, 0.167, 0.154};
float pThat[] = {0.429, 0.364, 0.167, 0.154};
float pGood[] = {0.143, 0.143, 0.333, 0.077};

float tPNot[] = {0, 0, 0, 0};
float tPThat[] = {0, 0, 0, 0};
float tPGood[] = {0, 0, 0, 0};

int main(void){
    int i;
    int j;
    
    for (i=0; i<4; i++){
        for (j=0; j<4; j++){
            if (tPNot[i] < (pNot[i] * transitions[i][j]* initial[j])){
                tPNot[i] = pNot[i] * transitions[i][j]* initial[j];
            }
        }
        printf("Total Probability of Not['%d'] = '%f',\n", i+1, tPNot[i]);
    }

    for (i=0; i<4; i++){
        for (j=0; j<4; j++){
            if (tPThat[i] < (pThat[i] * transitions[i][j]* tPNot[j])){
                tPThat[i] = pThat[i] * transitions[i][j]* tPNot[j];
            }
        }
        printf("Total Probability of That['%d'] = '%f',\n", i+1, tPThat[i]);
    }

    for (i=0; i<4; i++){
        for (j=0; j<4; j++){
            if (tPGood[i] < (pGood[i] * transitions[i][j]* tPThat[j])){
                tPGood[i] = pGood[i] * transitions[i][j]* tPThat[j];
            }       
        }  
        printf("Total Probability of Good['%d'] = '%f',\n", i+1, tPGood[i]);   
    }        

    return 0;
}