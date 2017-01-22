#include<stdio.h>

int main() {
    char ch,ch1;
    printf("d_b\n");
    scanf("%c",&ch);
    if(ch) {
        printf("I am alive!\n");
        printf("Press x to kill me :o\n");
        scanf(" %c",&ch1); 
        if(ch1=='x' || ch1=='X') {
            printf("Ok bye!\n");
        }
    }
    return 0;
}
