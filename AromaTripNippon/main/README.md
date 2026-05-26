# AromaTripNippon Phase1

`AromaTripNippon/main` 配下にある Spring Boot 実装です。
本READMEは UTF-8（日本語）で記載しています。
ソースコード等、テキストファイルはすべてUTF-8（日本語）BOMなしで記載します。

## Phase1実装範囲

- 公開画面:
  - トップ
  - 体験詳細
  - コンセプト
  - 予約フォーム
  - 予約完了
- 管理画面:
  - ログイン
  - ダッシュボード
  - 予約管理
  - 顧客管理
  - 香りレシピ管理
  - 商品管理
  - 在庫管理
  - アカウント設定
- 対象テーブル:
  - `customers`
  - `experience_programs`
  - `reservations`
  - `fragrance_recipes`
  - `fragrance_recipe_materials`
  - `inventory_items`
  - `inventory_transactions`
  - `products`
  - `admin_users`
  - `audit_logs`
- Phase1対象外:
  - EC注文フロー
  - カート
  - マイページ
  - レビュー
  - ギャラリー
  - 多言語文言管理
  - 画像アップロード

## ログイン情報

- 管理画面URL: `http://localhost:8080/management/login`
- ログインID: `AromaTripAdm01`
- パスワード: `password`

## 起動手順（H2デフォルト）

1. JDK 17以上を使用します。
   `pom.xml` では Java release `17` が指定されています。
2. 以下を実行します。

```bash
mvn spring-boot:run
```

- 公開画面: `http://localhost:8080/`
- 管理画面: `http://localhost:8080/management/login`
- H2コンソール: `http://localhost:8080/h2-console`

## MySQLで起動する場合

`src/main/resources/application-mysql.properties` を環境に合わせて更新し、以下を実行します。

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## テスト実行

```bash
mvn test
```

追加済みテスト:

- `src/test/java/com/aromatripnippon/service/ReservationServiceTest.java`
- `src/test/java/com/aromatripnippon/service/InventoryServiceTest.java`
- `src/test/java/com/aromatripnippon/controller/PublicControllerTest.java`
- `src/test/java/com/aromatripnippon/controller/ManagementFlowIntegrationTest.java`
- `src/test/java/com/aromatripnippon/TemplateScopeTest.java`

## 確認観点（Phase1）

- ユーザー予約フォームから予約登録できること
- 登録予約が管理画面の予約一覧・詳細に表示されること
- 顧客情報が予約と紐づくこと
- 管理画面で予約・顧客・レシピ・商品・在庫を登録編集できること
- 在庫更新履歴が残ること
- 管理者ログイン制御（`/management/**`）が効いていること
- Phase2機能へのリンクや未実装導線が混入していないこと

## 補足

- 本環境では `mvn` コマンドが利用できないため、この端末セッション内では `mvn test` とブラウザ手動確認（PC/スマホ）は未実行です。
- Mavenが利用できる環境で上記コマンドを実行し、最終確認を行ってください。
