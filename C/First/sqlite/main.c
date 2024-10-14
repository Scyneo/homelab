#include <stdio.h>
#include <stdlib.h>

int main(int argc, char* argv[]) {

    char* buffer = malloc(8);
    sprintf(buffer, "%03d.jpg", 6);
    printf("%s\n", buffer);
}