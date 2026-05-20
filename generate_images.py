#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
写真風のコンセプト画像を生成するスクリプト
"""
from pathlib import Path
from PIL import Image, ImageDraw, ImageFilter
import random

WIDTH = 1088
HEIGHT = 420

OUTPUT_DIRS = [
    Path(r"c:\academia\src\soweb-01\AromaTripNippon\main\src\main\resources\static\assets\images"),
    Path(r"c:\academia\src\soweb-01\AromaTrip\main\assets\images")
]

FILENAME_MAP = {
    "citrus_harvest": "concept_fruits_yuzu.png",
    "tea_plantation": "concept_tea_plantation.png",
    "rural_landscape": "concept_bamboo_grove.png",
    "mountain_range": "concept_orchard.png",
    "terraced_fields": "concept_cedar_forest.png",
}


def add_grain(img, strength=0.18):
    noise = Image.effect_noise((WIDTH, HEIGHT), 64).convert('L')
    noise = noise.filter(ImageFilter.GaussianBlur(radius=0.6))
    noise_rgb = Image.merge('RGB', (noise, noise, noise))
    return Image.blend(img, noise_rgb, strength)


def add_vignette(img, radius=1.8):
    vignette = Image.new('L', (WIDTH, HEIGHT), 0)
    draw = ImageDraw.Draw(vignette)
    for i in range(int(WIDTH * 0.7)):
        alpha = int(255 * ((i / (WIDTH * 0.7)) ** radius))
        draw.ellipse(
            [(WIDTH/2 - i, HEIGHT/2 - i), (WIDTH/2 + i, HEIGHT/2 + i)],
            outline=alpha,
        )
    return Image.composite(img, Image.new('RGB', img.size, (20, 20, 20)), vignette)


def add_film_texture(img, intensity=16):
    return add_grain(img, 0.16)


def blur_region(img, radius=8):
    return img.filter(ImageFilter.GaussianBlur(radius))


def create_gradient_background(top, bottom):
    img = Image.new('RGB', (WIDTH, HEIGHT), color=0)
    draw = ImageDraw.Draw(img)
    for y in range(HEIGHT):
        ratio = y / (HEIGHT - 1)
        r = int(top[0] * (1 - ratio) + bottom[0] * ratio)
        g = int(top[1] * (1 - ratio) + bottom[1] * ratio)
        b = int(top[2] * (1 - ratio) + bottom[2] * ratio)
        draw.line([(0, y), (WIDTH, y)], fill=(r, g, b))
    return img


def add_sunflare(draw):
    x = int(WIDTH * 0.18)
    y = int(HEIGHT * 0.18)
    for r in range(100, 10, -20):
        alpha = int(20 * (1 - r / 100))
        draw.ellipse([(x - r, y - r), (x + r, y + r)], fill=(255, 235, 180, alpha))


def create_photo_tree(draw, x, y, size):
    trunk_w = max(6, size // 10)
    draw.rectangle([(x - trunk_w, y), (x + trunk_w, y + size)], fill=(90, 55, 35, 220))
    for i in range(6):
        offset = random.randint(-int(size * 0.25), int(size * 0.25))
        draw.ellipse(
            [(x - size + offset, y - size * 0.6 + i * 12), (x + size + offset, y - size * 0.1 + i * 12)],
            fill=(30, 105, 52, 220),
        )


def create_citrus_harvest():
    img = create_gradient_background((190, 210, 235), (175, 150, 105))
    draw = ImageDraw.Draw(img, 'RGBA')
    add_sunflare(draw)
    for row in range(3):
        y_base = int(HEIGHT * (0.48 + row * 0.08))
        for tree in range(7):
            x = 80 + tree * 135 + (row % 2) * 40
            size = random.randint(50, 70)
            create_photo_tree(draw, x, y_base, size)
            for _ in range(6):
                fx = x + random.randint(-35, 35)
                fy = y_base - random.randint(15, 55)
                r = random.randint(8, 14)
                draw.ellipse([(fx - r, fy - r), (fx + r, fy + r)], fill=(245, 160, 55, 240))
    farmer_x = WIDTH - 230
    farmer_y = int(HEIGHT * 0.53)
    draw.ellipse([(farmer_x - 16, farmer_y - 36), (farmer_x + 16, farmer_y - 8)], fill=(210, 175, 135, 240))
    draw.rectangle([(farmer_x - 10, farmer_y - 8), (farmer_x + 10, farmer_y + 36)], fill=(95, 70, 52, 230))
    draw.line([(farmer_x + 10, farmer_y + 4), (farmer_x + 34, farmer_y - 10)], fill=(95, 70, 52, 230), width=6)
    img = add_film_texture(img.filter(ImageFilter.GaussianBlur(radius=0.55)))
    return add_vignette(img)


def create_tea_plantation():
    img = create_gradient_background((165, 195, 225), (110, 160, 105))
    draw = ImageDraw.Draw(img, 'RGBA')
    for row in range(6):
        top = int(HEIGHT * (0.40 + row * 0.06))
        bottom = top + 38
        draw.rectangle([(0, top), (WIDTH, bottom)], fill=(32, 108 + row * 10, 36 + row * 7, 220))
        draw.line([(0, top + 12), (WIDTH, top + 12)], fill=(200, 230, 200, 80), width=3)
    for block in range(7):
        x = 90 + block * 145
        draw.polygon([(x, HEIGHT * 0.25), (x + 30, HEIGHT * 0.18), (x + 60, HEIGHT * 0.25)], fill=(100, 105, 70, 200))
    draw.ellipse([(WIDTH * 0.67, HEIGHT * 0.50), (WIDTH * 0.71, HEIGHT * 0.53)], fill=(225, 185, 150, 240))
    draw.rectangle([(WIDTH * 0.69, HEIGHT * 0.53), (WIDTH * 0.71, HEIGHT * 0.67)], fill=(85, 66, 50, 230))
    img = add_film_texture(img.filter(ImageFilter.GaussianBlur(radius=0.52)))
    return add_vignette(img)


def create_rural_landscape():
    img = create_gradient_background((180, 210, 235), (145, 180, 125))
    draw = ImageDraw.Draw(img, 'RGBA')
    for layer in range(4):
        base_y = int(HEIGHT * (0.26 + layer * 0.08))
        points = [(0, HEIGHT), (0, base_y + 30)]
        for x in range(0, WIDTH + 120, 120):
            points.append((x, base_y + random.randint(-18, 18)))
        points.extend([(WIDTH, HEIGHT)])
        draw.polygon(points, fill=(118 - layer * 12, 145 - layer * 10, 88 - layer * 6, 230))
    for i in range(4):
        x = 120 + i * 150
        draw.rectangle([(x, HEIGHT * 0.62), (x + 36, HEIGHT * 0.7)], fill=(190, 170, 140, 240))
        draw.polygon([(x - 4, HEIGHT * 0.62), (x + 18, HEIGHT * 0.57), (x + 38, HEIGHT * 0.62)], fill=(145, 115, 82, 240))
    img = add_film_texture(img.filter(ImageFilter.GaussianBlur(radius=0.68)))
    return add_vignette(img)


def create_mountain_range():
    img = create_gradient_background((175, 205, 230), (105, 130, 145))
    draw = ImageDraw.Draw(img, 'RGBA')
    for idx, color in enumerate([(78, 108, 120), (98, 128, 142), (118, 148, 160), (142, 170, 180)]):
        offset = idx * 28
        points = [(0, HEIGHT), (0, HEIGHT * 0.44 + offset)]
        for x in range(0, WIDTH + 100, 130):
            points.append((x, HEIGHT * 0.44 + offset + random.randint(-30, 30)))
        points.extend([(WIDTH, HEIGHT)])
        draw.polygon(points, fill=color)
    draw.polygon([(370, HEIGHT * 0.46), (470, HEIGHT * 0.27), (560, HEIGHT * 0.46)], fill=(245, 245, 245, 230))
    draw.polygon([(630, HEIGHT * 0.51), (705, HEIGHT * 0.32), (785, HEIGHT * 0.51)], fill=(245, 245, 245, 230))
    img = add_film_texture(img.filter(ImageFilter.GaussianBlur(radius=0.63)))
    return add_vignette(img)


def create_terraced_fields():
    img = create_gradient_background((160, 200, 220), (130, 165, 110))
    draw = ImageDraw.Draw(img, 'RGBA')
    for level in range(6):
        top = int(HEIGHT * (0.48 + level * 0.055))
        bottom = top + 40
        draw.rectangle([(0, top), (WIDTH, bottom)], fill=(45 + level * 12, 92 + level * 10, 52 + level * 8, 230))
        draw.line([(0, top + 18), (WIDTH, top + 18)], fill=(210, 230, 200, 90), width=3)
    draw.polygon([(WIDTH * 0.72, HEIGHT * 0.55), (WIDTH * 0.9, HEIGHT * 0.59), (WIDTH * 0.68, HEIGHT * 0.67)], fill=(180, 160, 132, 220))
    draw.ellipse([(WIDTH * 0.765, HEIGHT * 0.485), (WIDTH * 0.795, HEIGHT * 0.515)], fill=(220, 182, 145, 240))
    draw.rectangle([(WIDTH * 0.775, HEIGHT * 0.515), (WIDTH * 0.785, HEIGHT * 0.645)], fill=(85, 65, 48, 230))
    img = add_film_texture(img.filter(ImageFilter.GaussianBlur(radius=0.65)))
    return add_vignette(img)


def save_image(img, name):
    for output_dir in OUTPUT_DIRS:
        output_dir.mkdir(parents=True, exist_ok=True)
        path = output_dir / name
        img.save(path)
        print(f"Saved: {path}")


def main():
    scenes = [
        (create_citrus_harvest(), FILENAME_MAP["citrus_harvest"]),
        (create_tea_plantation(), FILENAME_MAP["tea_plantation"]),
        (create_rural_landscape(), FILENAME_MAP["rural_landscape"]),
        (create_mountain_range(), FILENAME_MAP["mountain_range"]),
        (create_terraced_fields(), FILENAME_MAP["terraced_fields"]),
    ]

    for img, filename in scenes:
        save_image(img, filename)

    print("\nAll images generated and saved to all target asset folders.")


if __name__ == "__main__":
    main()
