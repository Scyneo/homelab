#include <stdlib.h>
#include <stdio.h>

void to_uppercase(int* p);
void to_lowercase (int* p);

void main(int) {
    int mask = 'a';
    to_uppercase(&mask);
    printf("%c\n", mask);

    to_lowercase(&mask);
    printf("%c\n", mask);

}

void to_uppercase (int* p) {
    *p &= ~(1 << 5);
}

void to_lowercase (int* p) {
    *p |= 1 << 5;
}