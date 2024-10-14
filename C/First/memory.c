#include <stdlib.h>
#include <stdio.h>

int main(void) {
    char* p = "Hello";

    char* t = malloc(strlen(p) + 1); // +1 for null character

    for (int i=0; i < strlen(p) + 1; i++) {
        t[i] = p[i];
    }
    strcpy(t, p);

    t[0] = t[0] + 32;

    printf("%s\n", p);
    printf("%s\n", t);
}