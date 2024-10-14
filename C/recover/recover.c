#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
 
int main(int argc, char *argv[]) {
 // input check arg, if we can open it, if it's empty
 // create jpeg file
 // read from card with blocks of 512 Bytes
 // put into file until we find zeros
    if (argc != 2) {
        printf("Provide filename\n");
        return 1;
    }
    FILE* input = fopen(argv[1], "r");
    if (input == NULL) {
        printf("Cannot open file %s\n", argv[1]);
        return 1;
    }

    FILE* output = fopen("000.jpg", "w");
    uint8_t buffer[512];
    char* filename = malloc(8);
    int idx = 0;
    while (fread(buffer, sizeof(uint8_t), 512, input) == 512) {
        if ((buffer[0] == 0xff) && (buffer[1] == 0xd8) && (buffer[2] == 0xff) && ((buffer[3] & 0xf0) == 0xe0)) {
            // we have a new file
            fclose(output);
            sprintf(filename, "%03d.jpg", ++idx);
            output = fopen(filename, "w");
        }
        fwrite(buffer, sizeof(uint8_t), 512, output);
    }
    fclose(output);
    fclose(input);
}