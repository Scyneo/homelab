#include <math.h>
#include "helpers.h"

void calculateSobel(RGBTRIPLEINT *triple, RGBTRIPLE pixel, int G);
void square(RGBTRIPLE *input, RGBTRIPLEINT Gx_triple, RGBTRIPLEINT Gy_triple);

// Convert image to grayscale
void grayscale(int height, int width, RGBTRIPLE image[height][width]) {
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            RGBTRIPLE old_rgb = image[i][j];
            int c = (old_rgb.rgbtBlue + old_rgb.rgbtGreen + old_rgb.rgbtRed) / 3;
            RGBTRIPLE new_rgb = {c, c, c};
            image[i][j] = new_rgb;
        }
    }
    return;
}

// Reflect image horizontally
void reflect(int height, int width, RGBTRIPLE image[height][width]) {
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width / 2; j++) {
            RGBTRIPLE tmp_rgb = image[i][j];

            image[i][j] = image[i][width - j - 1];
            image[i][width - j - 1] = tmp_rgb;
        }
    }
    return;
}

// Blur image
void blur(int height, int width, RGBTRIPLE image[height][width]) {
    RGBTRIPLE output[height][width];
    for (int i = 0; i < height; i++)
    {
        for (int j = 0; j < width; j++)
        {
            output[i][j] = image[i][j];
        }
    }

    for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
            int sum_red = 0;
            int sum_green = 0;
            int sum_blue = 0;
            int count = 0;

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int curr_row = row + i;
                    int curr_col = col + j;
                    // check if we are out of bounds
                    if (curr_row < 0 || curr_row > height || curr_col < 0 || curr_col > width) {
                        continue;
                    }
                    count++;
                    sum_red += output[curr_row][curr_col].rgbtRed;
                    sum_green += output[curr_row][curr_col].rgbtGreen;
                    sum_blue += output[curr_row][curr_col].rgbtBlue;
                }
            }
        image[row][col].rgbtRed = sum_red / count;
        image[row][col].rgbtGreen = sum_green / count;
        image[row][col].rgbtBlue = sum_blue / count;
        }
    }
    return;
}

// Detect edges
void edges(int height, int width, RGBTRIPLE image[height][width]) {
    int Gx[3][3] = {{-1, 0, 1}, 
                    {-2, 0, 2}, 
                    {-1, 0, 1}};
    int Gy[3][3] = {{-1, -2, -1}, 
                    {0, 0, 0}, 
                    {1, 2, 1}};

    RGBTRIPLE temp[height][width];
    for (int i = 0; i < height; i++)
    {
        for (int j = 0; j < width; j++)
        {
            temp[i][j] = image[i][j];
        }
    }

    for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
            RGBTRIPLEINT Gx_triple = {0, 0, 0};
            RGBTRIPLEINT Gy_triple = {0, 0, 0};
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    RGBTRIPLE pixel = {0, 0, 0};
                    int curr_row = row + i;
                    int curr_col = col + j;
                    // check if we are out of bounds
                    if (curr_row > 0 && curr_row < height && curr_col > 0 && curr_col < width) {
                        pixel = temp[curr_row][curr_col];
                    } else {
                        pixel = (RGBTRIPLE){0, 0, 0};
                    }
                    calculateSobel(&Gx_triple, pixel, Gx[1 + i][1 + j]);
                    calculateSobel(&Gy_triple, pixel, Gy[1 + i][1 + j]);
                    
                }
            }
            square(&image[row][col], Gx_triple, Gy_triple);
        }
    }
    return;
}

void calculateSobel(RGBTRIPLEINT *triple, RGBTRIPLE pixel, int G) {
    triple->rgbtRed += pixel.rgbtRed * G;
    triple->rgbtGreen += pixel.rgbtGreen * G;
    triple->rgbtBlue += pixel.rgbtBlue * G;
}

void square(RGBTRIPLE *input, RGBTRIPLEINT Gx_triple, RGBTRIPLEINT Gy_triple) {
    int new_red = round(sqrt(Gx_triple.rgbtRed*Gx_triple.rgbtRed + Gy_triple.rgbtRed*Gy_triple.rgbtRed));
    int new_green = round(sqrt(Gx_triple.rgbtGreen*Gx_triple.rgbtGreen + Gy_triple.rgbtGreen*Gy_triple.rgbtGreen));
    int new_blue = round(sqrt(Gx_triple.rgbtBlue*Gx_triple.rgbtBlue + Gy_triple.rgbtBlue*Gy_triple.rgbtBlue));

    input->rgbtRed = new_red > 255 ? 255 : new_red;
    input->rgbtGreen = new_green> 255 ? 255 : new_green;
    input->rgbtBlue = new_blue > 255 ? 255 : new_blue;
}