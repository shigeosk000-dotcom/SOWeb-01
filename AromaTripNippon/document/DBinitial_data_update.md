# AromaTripNippon DB初期データ更新

**更新日時**: 2026-05-21  
**対象**: `AromaTripNippon/main/src/main/resources/data.sql`

## 概要

DBテーブルデータの初期化を実施しました。`admin_users` と `product_categories` はそのまま保持し、その他のテーブルは整合性を取った初期データに置き換えました。

---

## 変更内容

### 1. 顧客管理 (`customers`) - 10件

| ID | 名前 | 言語 | 国 |
|---|---|---|---|
| 1 | Emily Chen | English | Taiwan |
| 2 | Lucas Smith | English | United States |
| 3 | Isabella Johnson | English | United Kingdom |
| 4 | Noah Williams | English | United States |
| 5 | Grace Miller | English | Australia |
| 6 | Ethan Davis | English | Canada |
| 7 | Sophia Brown | English | New Zealand |
| 8 | 山田太郎 | Japanese | Japan |
| 9 | 佐藤美咲 | Japanese | Japan |
| 10 | 鈴木蓮 | Japanese | Japan |

**構成**: 英語名7名 + 日本語名3名

---

### 2. 体験プログラム (`experience_programs`) - 2件

| ID | プログラム名 | 価格 | 所要時間 |
|---|---|---|---|
| 1 | Aroma Journey Workshop | ¥8,800 | 90分 |
| 2 | Aroma Gift Workshop | ¥6,600 | 60分 |

---

### 3. 在庫管理 (`inventory_items`) - 15件

**素材カテゴリ（7点）**:
- Yuzu essential oil
- Aomori hiba essential oil
- Japanese mint oil
- Sakura absolute
- Hinoki wood oil
- Matcha green tea extract
- White cedar resin

**容器カテゴリ（2点）**:
- Glass bottle 30ml
- Aluminum tube 50ml

**製品カテゴリ（6点）**:
- 和香ハンドクリーム - ゆず
- 和香ハンドクリーム - ひば
- 和香ハンドクリーム - 抹茶
- 和香フレグランス - 桜
- 和香フレグランス - 檜
- 和香フレグランス - 白杉

---

### 4. 製品カテゴリ (`product_categories`) - 3件（既存のまま）

| ID | カテゴリ名 |
|---|---|
| 1 | 製品 |
| 2 | 素材 |
| 3 | 容器 |

---

### 5. 商品マスタ (`products`) - 6件

| ID | 商品名 | 英語名 | 価格 | 在庫ID |
|---|---|---|---|---|
| 1 | 和香ハンドクリーム - ゆず | Yuzu Hand Cream | ¥2,400 | 10 |
| 2 | 和香ハンドクリーム - ひば | Hiba Hand Cream | ¥2,500 | 11 |
| 3 | 和香ハンドクリーム - 抹茶 | Matcha Hand Cream | ¥2,600 | 12 |
| 4 | 和香フレグランス - 桜 | Sakura Perfume | ¥4,200 | 13 |
| 5 | 和香フレグランス - 檜 | Hinoki Perfume | ¥4,500 | 14 |
| 6 | 和香フレグランス - 白杉 | Cedar Perfume | ¥4,300 | 15 |

---

### 6. 香りレシピ (`fragrance_recipes`) - 17件

**顧客1（Emily Chen）- 5件**:
- Citrus Forest（ゆず+檜+ミント）
- Forest Breeze（白杉+ミント+ゆず）
- Sakura Citrus（桜+ゆず+ミント）
- Green Cedar（抹茶+白杉+檜）
- Mint Sunshine（ミント+ゆず+桜）

**顧客2（Lucas Smith）- 5件**:
- Hinoki Blossom（檜+桜+ゆず）
- Yuzu Cedar（白杉+ゆず+ミント）
- Gift Harmony（ゆず+ひば+桜）
- Tea Forest（白杉+抹茶+ミント）
- Everyday Fresh（ゆず+ミント+桜）

**顧客3（Isabella Johnson）- 5件**:
- Sakura Trail（桜+檜+抹茶）
- Warm Hinoki（檜+白杉+ミント）
- Cedar Matcha（白杉+抹茶+ミント）
- Sakura Wood（ミント+ゆず+白杉）
- Bright Calm（抹茶+ゆず+ミント）

**顧客8（山田太郎）- 2件**:
- Light Harmony（ゆず+ミント+檜）
- Forest Comfort（白杉+檜+抹茶）

**材料組み合わせ**: 各レシピ2〜4材料をランダムに組み合わせ

---

### 7. 予約管理 (`reservations`) - 5件

| ID | 顧客 | プログラム | 日付 | 人数 | ステータス |
|---|---|---|---|---|---|
| 1 | Emily Chen | Aroma Journey | 2026-06-02 | 2 | RESERVED |
| 2 | Lucas Smith | Aroma Journey | 2026-06-05 | 1 | RESERVED |
| 3 | Isabella Johnson | Aroma Gift | 2026-06-10 | 2 | RESERVED |
| 4 | Noah Williams | Aroma Journey | 2026-06-12 | 3 | RESERVED |
| **5** | **山田太郎** | **Aroma Gift** | **2026-05-10** | **1** | **RESERVED** |

**注**: 予約ID5は期日超過（2026-05-10）

---

### 8. 在庫トランザクション (`inventory_transactions`) - 15件

全15個の在庫アイテムに対して、初期入庫（IN）トランザクションを記録。

---

### 9. 管理者ユーザー (`admin_users`) - 1件（既存のまま）

| ID | ログインID | 名前 | メール |
|---|---|---|---|
| 1 | AromaTripAdm01 | AromaTrip Manager | admin@aromatripnippon.local |

---

### 10. 監査ログ (`audit_logs`) - 1件

- SEED: 'Seeded initial database data for AromaTripNippon.'

---

## IDシーケンス設定

各テーブルの次のID生成開始値:

```sql
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
```

---

## ファイル変更

- **ファイルパス**: `AromaTripNippon/main/src/main/resources/data.sql`
- **変更概要**: 
  - 既存の`admin_users`および`product_categories`は保持
  - その他テーブルのデータを新規シードデータに置き換え
  - 整合性を確保したレシピ・材料・在庫の構成

---

## 確認事項

- ✅ 顧客数: 10件（英語7名、日本語3名）
- ✅ 製品: 6件（ハンドクリーム3点、香水3点）
- ✅ 素材: 7点
- ✅ 容器: 2点
- ✅ レシピ: 17件（4顧客分）
- ✅ 予約: 5件（期日超過1件含）
- ✅ 外部キー整合性: 全て確認済み
