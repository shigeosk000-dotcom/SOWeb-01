INSERT INTO admin_users
  (id, created_at, deleted_at, updated_at, is_active, display_language, name, email, login_id, notification_setting, password_hash, role)
VALUES
  (1, TIMESTAMP '2026-05-20 12:36:40.313467', NULL, TIMESTAMP '2026-05-20 12:36:40.313467', TRUE, 'Japanese', 'AromaTrip Manager', 'admin@aromatripnippon.local', 'AromaTripAdm01', 'ON', '$2a$10$MAFHv9apCzHY29Ix7lYlkuGb8X2Qn6vV6odOSVgW.AxWTlplA/Rji', 'ADMIN');

INSERT INTO customers
  (id, created_at, deleted_at, updated_at, email, name, nationality, note, phone, preferred_language, purpose)
VALUES
  (1, TIMESTAMP '2026-05-20 12:36:40.393402', NULL, TIMESTAMP '2026-05-20 12:36:40.393402', 'emily.chen@example.com', 'Emily Chen', 'Taiwan', 'Booked a fragrance workshop.', '+886-900-000-001', 'English', 'Travel experience'),
  (2, TIMESTAMP '2026-05-20 12:36:40.396445', NULL, TIMESTAMP '2026-05-20 12:36:40.396445', 'lucas.smith@example.com', 'Lucas Smith', 'United States', 'Interested in a take-home gift.', '+1-202-555-0102', 'English', 'Gift purchase'),
  (3, TIMESTAMP '2026-05-20 12:50:10.100000', NULL, TIMESTAMP '2026-05-20 12:50:10.100000', 'isabella.johnson@example.com', 'Isabella Johnson', 'United Kingdom', '希望は桜の香り。', '+44-7700-900001', 'English', 'Personal care'),
  (4, TIMESTAMP '2026-05-20 12:50:10.200000', NULL, TIMESTAMP '2026-05-20 12:50:10.200000', 'noah.williams@example.com', 'Noah Williams', 'United States', 'マッサージ用に使いたい。', '+1-310-555-0110', 'English', 'Relaxation'),
  (5, TIMESTAMP '2026-05-20 12:50:10.300000', NULL, TIMESTAMP '2026-05-20 12:50:10.300000', 'grace.miller@example.com', 'Grace Miller', 'Australia', 'プレゼント用に相談したい。', '+61-400-123-456', 'English', 'Gift purchase'),
  (6, TIMESTAMP '2026-05-20 12:50:10.400000', NULL, TIMESTAMP '2026-05-20 12:50:10.400000', 'ethan.davis@example.com', 'Ethan Davis', 'Canada', '強めの檜の香りが好き。', '+1-416-555-0123', 'English', 'Fragrance creation'),
  (7, TIMESTAMP '2026-05-20 12:50:10.500000', NULL, TIMESTAMP '2026-05-20 12:50:10.500000', 'sophia.brown@example.com', 'Sophia Brown', 'New Zealand', '和の素材を試したい。', '+64-21-555-0145', 'English', 'Wellness'),
  (8, TIMESTAMP '2026-05-20 12:50:10.600000', NULL, TIMESTAMP '2026-05-20 12:50:10.600000', 'yamada.taro@example.jp', '山田太郎', 'Japan', '体験会に参加したい。', '080-1111-2222', 'Japanese', '体験予約'),
  (9, TIMESTAMP '2026-05-20 12:50:10.700000', NULL, TIMESTAMP '2026-05-20 12:50:10.700000', 'sato.misaki@example.jp', '佐藤美咲', 'Japan', '母の日のプレゼント相談。', '080-3333-4444', 'Japanese', 'Gift planning'),
  (10, TIMESTAMP '2026-05-20 12:50:10.800000', NULL, TIMESTAMP '2026-05-20 12:50:10.800000', 'suzuki.ren@example.jp', '鈴木蓮', 'Japan', '週末に予約希望。', '080-5555-6666', 'Japanese', 'Workshop');

INSERT INTO experience_programs
  (id, created_at, deleted_at, updated_at, is_active, description, duration_minutes, material_summary, name, price)
VALUES
  (1, TIMESTAMP '2026-05-20 12:36:40.385449', NULL, TIMESTAMP '2026-05-20 12:36:40.385449', TRUE, 'Create a personal fragrance using Japanese botanical materials.', 90, 'Yuzu, hiba wood, mint, glass bottle', 'Aroma Journey Workshop', 8800.00),
  (2, TIMESTAMP '2026-05-20 12:36:40.390000', NULL, TIMESTAMP '2026-05-20 12:36:40.390000', TRUE, 'Make a hand cream or perfume with seasonal Japanese scents.', 60, 'Hand cream, perfume, glass bottle', 'Aroma Gift Workshop', 6600.00);

INSERT INTO inventory_items
  (id, created_at, deleted_at, updated_at, category, item_name, last_stocked_date, memo, stock_quantity, storage_location, supplier, threshold_quantity, unit)
VALUES
  (1, TIMESTAMP '2026-05-20 12:36:40.411229', NULL, TIMESTAMP '2026-05-20 12:36:40.511207', '素材', 'Yuzu essential oil', DATE '2026-05-20', NULL, 1000.00, 'Shelf A', 'Kyushu Botanicals', 100.00, 'ml'),
  (2, TIMESTAMP '2026-05-20 12:36:40.415395', NULL, TIMESTAMP '2026-05-20 12:36:40.511207', '素材', 'Aomori hiba essential oil', DATE '2026-05-20', NULL, 840.00, 'Shelf A', 'Aomori Wood Lab', 80.00, 'ml'),
  (3, TIMESTAMP '2026-05-20 12:36:40.418733', NULL, TIMESTAMP '2026-05-20 12:36:40.418733', '素材', 'Japanese mint oil', DATE '2026-05-20', NULL, 360.00, 'Shelf A', 'Hokkaido Herbs', 80.00, 'ml'),
  (4, TIMESTAMP '2026-05-20 12:36:40.420000', NULL, TIMESTAMP '2026-05-20 12:36:40.420000', '素材', 'Sakura absolute', DATE '2026-05-20', NULL, 520.00, 'Shelf A', 'Tokyo Blossom Co.', 80.00, 'ml'),
  (5, TIMESTAMP '2026-05-20 12:36:40.422000', NULL, TIMESTAMP '2026-05-20 12:36:40.422000', '素材', 'Hinoki wood oil', DATE '2026-05-20', NULL, 680.00, 'Shelf A', 'Nagano Forest Works', 80.00, 'ml'),
  (6, TIMESTAMP '2026-05-20 12:36:40.424000', NULL, TIMESTAMP '2026-05-20 12:36:40.424000', '素材', 'Matcha green tea extract', DATE '2026-05-20', NULL, 450.00, 'Shelf A', 'Uji Herbal Lab', 80.00, 'ml'),
  (7, TIMESTAMP '2026-05-20 12:36:40.426000', NULL, TIMESTAMP '2026-05-20 12:36:40.426000', '素材', 'White cedar resin', DATE '2026-05-20', NULL, 380.00, 'Shelf A', 'Kiso Cedar Co.', 80.00, 'g'),
  (8, TIMESTAMP '2026-05-20 12:36:40.428000', NULL, TIMESTAMP '2026-05-20 12:36:40.428000', '容器', 'Glass bottle 30ml', DATE '2026-05-20', NULL, 240.00, 'Shelf B', 'Tokyo Bottle Works', 30.00, 'pcs'),
  (9, TIMESTAMP '2026-05-20 12:36:40.430000', NULL, TIMESTAMP '2026-05-20 12:36:40.430000', '容器', 'Aluminum tube 50ml', DATE '2026-05-20', NULL, 180.00, 'Shelf B', 'Nagoya Pack Co.', 20.00, 'pcs'),
  (10, TIMESTAMP '2026-05-20 12:36:40.432000', NULL, TIMESTAMP '2026-05-20 12:36:40.432000', '製品', '和香ハンドクリーム - ゆず', DATE '2026-05-20', NULL, 60.00, 'Warehouse C', 'AromaCraft Japan', 10.00, 'pcs'),
  (11, TIMESTAMP '2026-05-20 12:36:40.434000', NULL, TIMESTAMP '2026-05-20 12:36:40.434000', '製品', '和香ハンドクリーム - ひば', DATE '2026-05-20', NULL, 55.00, 'Warehouse C', 'AromaCraft Japan', 10.00, 'pcs'),
  (12, TIMESTAMP '2026-05-20 12:36:40.436000', NULL, TIMESTAMP '2026-05-20 12:36:40.436000', '製品', '和香ハンドクリーム - 抹茶', DATE '2026-05-20', NULL, 70.00, 'Warehouse C', 'AromaCraft Japan', 10.00, 'pcs'),
  (13, TIMESTAMP '2026-05-20 12:36:40.438000', NULL, TIMESTAMP '2026-05-20 12:36:40.438000', '製品', '和香フレグランス - 桜', DATE '2026-05-20', NULL, 40.00, 'Warehouse C', 'AromaCraft Japan', 5.00, 'pcs'),
  (14, TIMESTAMP '2026-05-20 12:36:40.440000', NULL, TIMESTAMP '2026-05-20 12:36:40.440000', '製品', '和香フレグランス - 檜', DATE '2026-05-20', NULL, 38.00, 'Warehouse C', 'AromaCraft Japan', 5.00, 'pcs'),
  (15, TIMESTAMP '2026-05-20 12:36:40.442000', NULL, TIMESTAMP '2026-05-20 12:36:40.442000', '製品', '和香フレグランス - 白杉', DATE '2026-05-20', NULL, 42.00, 'Warehouse C', 'AromaCraft Japan', 5.00, 'pcs');

INSERT INTO product_categories
  (id, created_at, deleted_at, updated_at, is_active, category_name, display_order)
VALUES
  (1, TIMESTAMP '2026-05-20 12:36:40.425463', NULL, TIMESTAMP '2026-05-20 12:36:40.425463', TRUE, '製品', 1),
  (2, TIMESTAMP '2026-05-20 12:36:40.428461', NULL, TIMESTAMP '2026-05-20 12:36:40.428461', TRUE, '素材', 2),
  (3, TIMESTAMP '2026-05-20 12:36:40.430462', NULL, TIMESTAMP '2026-05-20 12:36:40.430462', TRUE, '容器', 3);

INSERT INTO products
  (id, created_at, deleted_at, updated_at, is_active, category, description, english_name, image_path, price, product_name, inventory_item_id)
VALUES
  (1, TIMESTAMP '2026-05-20 12:36:40.450000', NULL, TIMESTAMP '2026-05-20 12:36:40.450000', TRUE, '製品', 'Hand cream with refreshing yuzu scent.', 'Yuzu Hand Cream', '/assets/images/product_handcream_yuzu.png', 2400.00, '和香ハンドクリーム - ゆず', 10),
  (2, TIMESTAMP '2026-05-20 12:36:40.451000', NULL, TIMESTAMP '2026-05-20 12:36:40.451000', TRUE, '製品', 'Warm hinoki-scented hand cream.', 'Hiba Hand Cream', '/assets/images/product_handcream_hiba.png', 2500.00, '和香ハンドクリーム - ひば', 11),
  (3, TIMESTAMP '2026-05-20 12:36:40.452000', NULL, TIMESTAMP '2026-05-20 12:36:40.452000', TRUE, '製品', 'Soothing matcha hand cream.', 'Matcha Hand Cream', '/assets/images/product_handcream_matcha.png', 2600.00, '和香ハンドクリーム - 抹茶', 12),
  (4, TIMESTAMP '2026-05-20 12:36:40.453000', NULL, TIMESTAMP '2026-05-20 12:36:40.453000', TRUE, '製品', 'A gentle cherry blossom perfume.', 'Sakura Perfume', '/assets/images/product_perfume_sakura.png', 4200.00, '和香フレグランス - 桜', 13),
  (5, TIMESTAMP '2026-05-20 12:36:40.454000', NULL, TIMESTAMP '2026-05-20 12:36:40.454000', TRUE, '製品', 'Earthy hinoki fragrance.', 'Hinoki Perfume', '/assets/images/product_perfume_hinoki.png', 4500.00, '和香フレグランス - 檜', 14),
  (6, TIMESTAMP '2026-05-20 12:36:40.455000', NULL, TIMESTAMP '2026-05-20 12:36:40.455000', TRUE, '製品', 'Fresh white cedar perfume.', 'Cedar Perfume', '/assets/images/product_perfume_cedar.png', 4300.00, '和香フレグランス - 白杉', 15);

INSERT INTO fragrance_recipes
  (id, created_at, deleted_at, updated_at, concept_note, memo, recipe_name, total_amount, customer_id)
VALUES
  (1, TIMESTAMP '2026-05-20 12:55:00.100000', NULL, TIMESTAMP '2026-05-20 12:55:00.100000', 'Bright yuzu top with soft hinoki depth.', NULL, 'Citrus Forest', 100.00, 1),
  (2, TIMESTAMP '2026-05-20 12:55:00.200000', NULL, TIMESTAMP '2026-05-20 12:55:00.200000', 'Warm cedar and mint balance.', NULL, 'Forest Breeze', 100.00, 1),
  (3, TIMESTAMP '2026-05-20 12:55:00.300000', NULL, TIMESTAMP '2026-05-20 12:55:00.300000', 'Soft sakura petals with yuzu zest.', NULL, 'Sakura Citrus', 100.00, 1),
  (4, TIMESTAMP '2026-05-20 12:55:00.400000', NULL, TIMESTAMP '2026-05-20 12:55:00.400000', 'Matcha calmness with white cedar base.', NULL, 'Green Cedar', 100.00, 1),
  (5, TIMESTAMP '2026-05-20 12:55:00.500000', NULL, TIMESTAMP '2026-05-20 12:55:00.500000', 'Cool mint lift and sunlit yuzu.', NULL, 'Mint Sunshine', 100.00, 1),
  (6, TIMESTAMP '2026-05-20 12:55:00.600000', NULL, TIMESTAMP '2026-05-20 12:55:00.600000', 'Hinoki clarity with cherry blossom sweetness.', NULL, 'Hinoki Blossom', 100.00, 2),
  (7, TIMESTAMP '2026-05-20 12:55:00.700000', NULL, TIMESTAMP '2026-05-20 12:55:00.700000', 'Yuzu and white cedar for modern calm.', NULL, 'Yuzu Cedar', 100.00, 2),
  (8, TIMESTAMP '2026-05-20 12:55:00.800000', NULL, TIMESTAMP '2026-05-20 12:55:00.800000', 'Mint, hinoki and sakura for a fresh gift.', NULL, 'Gift Harmony', 100.00, 2),
  (9, TIMESTAMP '2026-05-20 12:55:00.900000', NULL, TIMESTAMP '2026-05-20 12:55:00.900000', 'Cedar resin depth with green tea lift.', NULL, 'Tea Forest', 100.00, 2),
  (10, TIMESTAMP '2026-05-20 12:55:01.000000', NULL, TIMESTAMP '2026-05-20 12:55:01.000000', 'Fresh yuzu and mint for everyday wear.', NULL, 'Everyday Fresh', 100.00, 2),
  (11, TIMESTAMP '2026-05-20 12:55:01.100000', NULL, TIMESTAMP '2026-05-20 12:55:01.100000', 'Soft sakura trail with a mint base.', NULL, 'Sakura Trail', 100.00, 3),
  (12, TIMESTAMP '2026-05-20 12:55:01.200000', NULL, TIMESTAMP '2026-05-20 12:55:01.200000', 'Hinoki and yuzu for a warm finish.', NULL, 'Warm Hinoki', 100.00, 3),
  (13, TIMESTAMP '2026-05-20 12:55:01.300000', NULL, TIMESTAMP '2026-05-20 12:55:01.300000', 'White cedar anchored by matcha and mint.', NULL, 'Cedar Matcha', 100.00, 3),
  (14, TIMESTAMP '2026-05-20 12:55:01.400000', NULL, TIMESTAMP '2026-05-20 12:55:01.400000', 'Sakura, yuzu and hiba blend.', NULL, 'Sakura Wood', 100.00, 3),
  (15, TIMESTAMP '2026-05-20 12:55:01.500000', NULL, TIMESTAMP '2026-05-20 12:55:01.500000', 'Mint, yuzu and cedar for bright calm.', NULL, 'Bright Calm', 100.00, 3),
  (16, TIMESTAMP '2026-05-20 12:55:01.600000', NULL, TIMESTAMP '2026-05-20 12:55:01.600000', 'Simple yuzu and mint for a light mood.', NULL, 'Light Harmony', 100.00, 8),
  (17, TIMESTAMP '2026-05-20 12:55:01.700000', NULL, TIMESTAMP '2026-05-20 12:55:01.700000', 'Hinoki and white cedar for grounded comfort.', NULL, 'Forest Comfort', 100.00, 8);

INSERT INTO fragrance_recipe_materials
  (id, created_at, deleted_at, updated_at, amount, blend_ratio, display_order, material_name_snapshot, fragrance_recipe_id, inventory_item_id)
VALUES
  (1, TIMESTAMP '2026-05-20 12:56:00.100000', NULL, TIMESTAMP '2026-05-20 12:56:00.100000', 40.00, 40.00, 1, 'Yuzu essential oil', 1, 1),
  (2, TIMESTAMP '2026-05-20 12:56:00.100000', NULL, TIMESTAMP '2026-05-20 12:56:00.100000', 30.00, 30.00, 2, 'Hinoki wood oil', 1, 5),
  (3, TIMESTAMP '2026-05-20 12:56:00.100000', NULL, TIMESTAMP '2026-05-20 12:56:00.100000', 30.00, 30.00, 3, 'Japanese mint oil', 1, 3),
  (4, TIMESTAMP '2026-05-20 12:56:00.200000', NULL, TIMESTAMP '2026-05-20 12:56:00.200000', 50.00, 50.00, 1, 'White cedar resin', 2, 7),
  (5, TIMESTAMP '2026-05-20 12:56:00.200000', NULL, TIMESTAMP '2026-05-20 12:56:00.200000', 30.00, 30.00, 2, 'Japanese mint oil', 2, 3),
  (6, TIMESTAMP '2026-05-20 12:56:00.200000', NULL, TIMESTAMP '2026-05-20 12:56:00.200000', 20.00, 20.00, 3, 'Yuzu essential oil', 2, 1),
  (7, TIMESTAMP '2026-05-20 12:56:00.300000', NULL, TIMESTAMP '2026-05-20 12:56:00.300000', 35.00, 35.00, 1, 'Sakura absolute', 3, 4),
  (8, TIMESTAMP '2026-05-20 12:56:00.300000', NULL, TIMESTAMP '2026-05-20 12:56:00.300000', 35.00, 35.00, 2, 'Yuzu essential oil', 3, 1),
  (9, TIMESTAMP '2026-05-20 12:56:00.300000', NULL, TIMESTAMP '2026-05-20 12:56:00.300000', 30.00, 30.00, 3, 'Japanese mint oil', 3, 3),
  (10, TIMESTAMP '2026-05-20 12:56:00.400000', NULL, TIMESTAMP '2026-05-20 12:56:00.400000', 30.00, 30.00, 1, 'Matcha green tea extract', 4, 6),
  (11, TIMESTAMP '2026-05-20 12:56:00.400000', NULL, TIMESTAMP '2026-05-20 12:56:00.400000', 40.00, 40.00, 2, 'White cedar resin', 4, 7),
  (12, TIMESTAMP '2026-05-20 12:56:00.400000', NULL, TIMESTAMP '2026-05-20 12:56:00.400000', 30.00, 30.00, 3, 'Hinoki wood oil', 4, 5),
  (13, TIMESTAMP '2026-05-20 12:56:00.500000', NULL, TIMESTAMP '2026-05-20 12:56:00.500000', 50.00, 50.00, 1, 'Japanese mint oil', 5, 3),
  (14, TIMESTAMP '2026-05-20 12:56:00.500000', NULL, TIMESTAMP '2026-05-20 12:56:00.500000', 30.00, 30.00, 2, 'Yuzu essential oil', 5, 1),
  (15, TIMESTAMP '2026-05-20 12:56:00.500000', NULL, TIMESTAMP '2026-05-20 12:56:00.500000', 20.00, 20.00, 3, 'Sakura absolute', 5, 4),
  (16, TIMESTAMP '2026-05-20 12:56:00.600000', NULL, TIMESTAMP '2026-05-20 12:56:00.600000', 45.00, 45.00, 1, 'Hinoki wood oil', 6, 5),
  (17, TIMESTAMP '2026-05-20 12:56:00.600000', NULL, TIMESTAMP '2026-05-20 12:56:00.600000', 35.00, 35.00, 2, 'Sakura absolute', 6, 4),
  (18, TIMESTAMP '2026-05-20 12:56:00.600000', NULL, TIMESTAMP '2026-05-20 12:56:00.600000', 20.00, 20.00, 3, 'Yuzu essential oil', 6, 1),
  (19, TIMESTAMP '2026-05-20 12:56:00.700000', NULL, TIMESTAMP '2026-05-20 12:56:00.700000', 40.00, 40.00, 1, 'White cedar resin', 7, 7),
  (20, TIMESTAMP '2026-05-20 12:56:00.700000', NULL, TIMESTAMP '2026-05-20 12:56:00.700000', 30.00, 30.00, 2, 'Yuzu essential oil', 7, 1),
  (21, TIMESTAMP '2026-05-20 12:56:00.700000', NULL, TIMESTAMP '2026-05-20 12:56:00.700000', 30.00, 30.00, 3, 'Japanese mint oil', 7, 3),
  (22, TIMESTAMP '2026-05-20 12:56:00.800000', NULL, TIMESTAMP '2026-05-20 12:56:00.800000', 40.00, 40.00, 1, 'Yuzu essential oil', 8, 1),
  (23, TIMESTAMP '2026-05-20 12:56:00.800000', NULL, TIMESTAMP '2026-05-20 12:56:00.800000', 35.00, 35.00, 2, 'Aomori hiba essential oil', 8, 2),
  (24, TIMESTAMP '2026-05-20 12:56:00.800000', NULL, TIMESTAMP '2026-05-20 12:56:00.800000', 25.00, 25.00, 3, 'Sakura absolute', 8, 4),
  (25, TIMESTAMP '2026-05-20 12:56:00.900000', NULL, TIMESTAMP '2026-05-20 12:56:00.900000', 50.00, 50.00, 1, 'White cedar resin', 9, 7),
  (26, TIMESTAMP '2026-05-20 12:56:00.900000', NULL, TIMESTAMP '2026-05-20 12:56:00.900000', 30.00, 30.00, 2, 'Matcha green tea extract', 9, 6),
  (27, TIMESTAMP '2026-05-20 12:56:00.900000', NULL, TIMESTAMP '2026-05-20 12:56:00.900000', 20.00, 20.00, 3, 'Japanese mint oil', 9, 3),
  (28, TIMESTAMP '2026-05-20 12:56:01.000000', NULL, TIMESTAMP '2026-05-20 12:56:01.000000', 60.00, 60.00, 1, 'Yuzu essential oil', 10, 1),
  (29, TIMESTAMP '2026-05-20 12:56:01.000000', NULL, TIMESTAMP '2026-05-20 12:56:01.000000', 25.00, 25.00, 2, 'Japanese mint oil', 10, 3),
  (30, TIMESTAMP '2026-05-20 12:56:01.000000', NULL, TIMESTAMP '2026-05-20 12:56:01.000000', 15.00, 15.00, 3, 'Sakura absolute', 10, 4),
  (31, TIMESTAMP '2026-05-20 12:56:01.100000', NULL, TIMESTAMP '2026-05-20 12:56:01.100000', 50.00, 50.00, 1, 'Sakura absolute', 11, 4),
  (32, TIMESTAMP '2026-05-20 12:56:01.100000', NULL, TIMESTAMP '2026-05-20 12:56:01.100000', 30.00, 30.00, 2, 'Hinoki wood oil', 11, 5),
  (33, TIMESTAMP '2026-05-20 12:56:01.100000', NULL, TIMESTAMP '2026-05-20 12:56:01.100000', 20.00, 20.00, 3, 'Matcha green tea extract', 11, 6),
  (34, TIMESTAMP '2026-05-20 12:56:01.200000', NULL, TIMESTAMP '2026-05-20 12:56:01.200000', 45.00, 45.00, 1, 'Hinoki wood oil', 12, 5),
  (35, TIMESTAMP '2026-05-20 12:56:01.200000', NULL, TIMESTAMP '2026-05-20 12:56:01.200000', 30.00, 30.00, 2, 'White cedar resin', 12, 7),
  (36, TIMESTAMP '2026-05-20 12:56:01.200000', NULL, TIMESTAMP '2026-05-20 12:56:01.200000', 25.00, 25.00, 3, 'Japanese mint oil', 12, 3),
  (37, TIMESTAMP '2026-05-20 12:56:01.300000', NULL, TIMESTAMP '2026-05-20 12:56:01.300000', 40.00, 40.00, 1, 'Sakura absolute', 13, 4),
  (38, TIMESTAMP '2026-05-20 12:56:01.300000', NULL, TIMESTAMP '2026-05-20 12:56:01.300000', 35.00, 35.00, 2, 'Yuzu essential oil', 13, 1),
  (39, TIMESTAMP '2026-05-20 12:56:01.300000', NULL, TIMESTAMP '2026-05-20 12:56:01.300000', 25.00, 25.00, 3, 'Hinoki wood oil', 13, 5),
  (40, TIMESTAMP '2026-05-20 12:56:01.400000', NULL, TIMESTAMP '2026-05-20 12:56:01.400000', 50.00, 50.00, 1, 'Japanese mint oil', 14, 3),
  (41, TIMESTAMP '2026-05-20 12:56:01.400000', NULL, TIMESTAMP '2026-05-20 12:56:01.400000', 35.00, 35.00, 2, 'Yuzu essential oil', 14, 1),
  (42, TIMESTAMP '2026-05-20 12:56:01.400000', NULL, TIMESTAMP '2026-05-20 12:56:01.400000', 15.00, 15.00, 3, 'White cedar resin', 14, 7),
  (43, TIMESTAMP '2026-05-20 12:56:01.500000', NULL, TIMESTAMP '2026-05-20 12:56:01.500000', 35.00, 35.00, 1, 'Matcha green tea extract', 15, 6),
  (44, TIMESTAMP '2026-05-20 12:56:01.500000', NULL, TIMESTAMP '2026-05-20 12:56:01.500000', 35.00, 35.00, 2, 'Yuzu essential oil', 15, 1),
  (45, TIMESTAMP '2026-05-20 12:56:01.500000', NULL, TIMESTAMP '2026-05-20 12:56:01.500000', 30.00, 30.00, 3, 'Japanese mint oil', 15, 3),
  (46, TIMESTAMP '2026-05-20 12:56:01.600000', NULL, TIMESTAMP '2026-05-20 12:56:01.600000', 50.00, 50.00, 1, 'Yuzu essential oil', 16, 1),
  (47, TIMESTAMP '2026-05-20 12:56:01.600000', NULL, TIMESTAMP '2026-05-20 12:56:01.600000', 25.00, 25.00, 2, 'Japanese mint oil', 16, 3),
  (48, TIMESTAMP '2026-05-20 12:56:01.600000', NULL, TIMESTAMP '2026-05-20 12:56:01.600000', 15.00, 15.00, 3, 'Hinoki wood oil', 16, 5),
  (49, TIMESTAMP '2026-05-20 12:56:01.700000', NULL, TIMESTAMP '2026-05-20 12:56:01.700000', 45.00, 45.00, 1, 'White cedar resin', 17, 7),
  (50, TIMESTAMP '2026-05-20 12:56:01.700000', NULL, TIMESTAMP '2026-05-20 12:56:01.700000', 35.00, 35.00, 2, 'Hinoki wood oil', 17, 5),
  (51, TIMESTAMP '2026-05-20 12:56:01.700000', NULL, TIMESTAMP '2026-05-20 12:56:01.700000', 20.00, 20.00, 3, 'Matcha green tea extract', 17, 6);

INSERT INTO inventory_transactions
  (id, created_at, deleted_at, updated_at, quantity, reason, transaction_date, transaction_type, created_by, inventory_item_id)
VALUES
  (1, TIMESTAMP '2026-05-20 12:57:00.100000', NULL, TIMESTAMP '2026-05-20 12:57:00.100000', 1000.00, 'Initial stock', DATE '2026-05-20', 'IN', 1, 1),
  (2, TIMESTAMP '2026-05-20 12:57:00.200000', NULL, TIMESTAMP '2026-05-20 12:57:00.200000', 840.00, 'Initial stock', DATE '2026-05-20', 'IN', 1, 2),
  (3, TIMESTAMP '2026-05-20 12:57:00.300000', NULL, TIMESTAMP '2026-05-20 12:57:00.300000', 360.00, 'Initial stock', DATE '2026-05-20', 'IN', 1, 3),
  (4, TIMESTAMP '2026-05-20 12:57:00.400000', NULL, TIMESTAMP '2026-05-20 12:57:00.400000', 520.00, 'Initial stock', DATE '2026-05-20', 'IN', 1, 4),
  (5, TIMESTAMP '2026-05-20 12:57:00.500000', NULL, TIMESTAMP '2026-05-20 12:57:00.500000', 680.00, 'Initial stock', DATE '2026-05-20', 'IN', 1, 5),
  (6, TIMESTAMP '2026-05-20 12:57:00.600000', NULL, TIMESTAMP '2026-05-20 12:57:00.600000', 450.00, 'Initial stock', DATE '2026-05-20', 'IN', 1, 6),
  (7, TIMESTAMP '2026-05-20 12:57:00.700000', NULL, TIMESTAMP '2026-05-20 12:57:00.700000', 380.00, 'Initial stock', DATE '2026-05-20', 'IN', 1, 7),
  (8, TIMESTAMP '2026-05-20 12:57:00.800000', NULL, TIMESTAMP '2026-05-20 12:57:00.800000', 240.00, 'Initial stock', DATE '2026-05-20', 'IN', 1, 8),
  (9, TIMESTAMP '2026-05-20 12:57:00.900000', NULL, TIMESTAMP '2026-05-20 12:57:00.900000', 180.00, 'Initial stock', DATE '2026-05-20', 'IN', 1, 9),
  (10, TIMESTAMP '2026-05-20 12:57:01.000000', NULL, TIMESTAMP '2026-05-20 12:57:01.000000', 60.00, 'Initial stock', DATE '2026-05-20', 'IN', 1, 10),
  (11, TIMESTAMP '2026-05-20 12:57:01.100000', NULL, TIMESTAMP '2026-05-20 12:57:01.100000', 55.00, 'Initial stock', DATE '2026-05-20', 'IN', 1, 11),
  (12, TIMESTAMP '2026-05-20 12:57:01.200000', NULL, TIMESTAMP '2026-05-20 12:57:01.200000', 70.00, 'Initial stock', DATE '2026-05-20', 'IN', 1, 12),
  (13, TIMESTAMP '2026-05-20 12:57:01.300000', NULL, TIMESTAMP '2026-05-20 12:57:01.300000', 40.00, 'Initial stock', DATE '2026-05-20', 'IN', 1, 13),
  (14, TIMESTAMP '2026-05-20 12:57:01.400000', NULL, TIMESTAMP '2026-05-20 12:57:01.400000', 38.00, 'Initial stock', DATE '2026-05-20', 'IN', 1, 14),
  (15, TIMESTAMP '2026-05-20 12:57:01.500000', NULL, TIMESTAMP '2026-05-20 12:57:01.500000', 42.00, 'Initial stock', DATE '2026-05-20', 'IN', 1, 15);

INSERT INTO reservations
  (id, created_at, deleted_at, updated_at, number_of_people, preferred_language, request_note, status, reservation_time, reservation_date, customer_id, experience_program_id)
VALUES
  (1, TIMESTAMP '2026-05-20 12:58:00.100000', NULL, TIMESTAMP '2026-05-20 12:58:00.100000', 2, 'English', 'Please prepare English guidance.', 'RESERVED', '13:00', DATE '2026-06-02', 1, 1),
  (2, TIMESTAMP '2026-05-20 12:58:00.200000', NULL, TIMESTAMP '2026-05-20 12:58:00.200000', 1, 'English', 'Looking for a gift workshop.', 'RESERVED', '10:00', DATE '2026-06-05', 2, 1),
  (3, TIMESTAMP '2026-05-20 12:58:00.300000', NULL, TIMESTAMP '2026-05-20 12:58:00.300000', 2, 'English', '希望は桜の香りです。', 'RESERVED', '14:00', DATE '2026-06-10', 3, 2),
  (4, TIMESTAMP '2026-05-20 12:58:00.400000', NULL, TIMESTAMP '2026-05-20 12:58:00.400000', 3, 'English', 'Need a workshop for friends.', 'RESERVED', '11:00', DATE '2026-06-12', 4, 1),
  (5, TIMESTAMP '2026-05-20 12:58:00.500000', NULL, TIMESTAMP '2026-05-20 12:58:00.500000', 1, 'Japanese', '以前キャンセルした分の再予約です。', 'RESERVED', '15:00', DATE '2026-05-10', 8, 2);

INSERT INTO audit_logs
  (id, created_at, deleted_at, updated_at, action_type, description, target_id, target_table, admin_user_id)
VALUES
  (1, TIMESTAMP '2026-05-20 12:59:00.100000', NULL, TIMESTAMP '2026-05-20 12:59:00.100000', 'SEED', 'Seeded initial database data for AromaTripNippon.', NULL, 'seed_data', 1);

ALTER TABLE admin_users ALTER COLUMN id RESTART WITH 2;
ALTER TABLE audit_logs ALTER COLUMN id RESTART WITH 2;
ALTER TABLE customers ALTER COLUMN id RESTART WITH 11;
ALTER TABLE experience_programs ALTER COLUMN id RESTART WITH 3;
ALTER TABLE fragrance_recipe_materials ALTER COLUMN id RESTART WITH 52;
ALTER TABLE fragrance_recipes ALTER COLUMN id RESTART WITH 18;
ALTER TABLE inventory_items ALTER COLUMN id RESTART WITH 16;
ALTER TABLE inventory_transactions ALTER COLUMN id RESTART WITH 16;
ALTER TABLE product_categories ALTER COLUMN id RESTART WITH 4;
ALTER TABLE products ALTER COLUMN id RESTART WITH 7;
ALTER TABLE reservations ALTER COLUMN id RESTART WITH 6;
