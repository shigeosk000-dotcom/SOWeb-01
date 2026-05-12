AromaTripNippon Phase1 のDB、Entity、Repository、Serviceを実装してください。

参照:
- SOWEB-01\portfolio\docs\08-db-design.html
- SOWEB-01\portfolio\docs\07-specification.html

対象テーブル:
- customers
- experience_programs
- reservations
- fragrance_recipes
- fragrance_recipe_materials
- inventory_items
- inventory_transactions
- products
- admin_users
- audit_logs

実装内容:
- Entity
- Repository
- Service
- バリデーション
- 論理削除が定義されている場合は deleted_at を考慮
- 初期データ投入
- 予約、顧客、レシピ、在庫、商品、管理者のサンプルデータ

禁止:
- Phase2用の orders, order_items, reviews, gallery_posts, localization_messages, user_accounts は実装しない。ただし既存仕様との整合上どうしても必要なら指示者に確認する。


追加確定事項:
- Spring Bootで実装する。
- DBはMySQLを優先する。ただし環境構築や接続設定の手間が大きい場合はH2で実装してよい。
- 予約完了メール送信はPhase1に含めない。
- ユーザー予約は確認画面を作らず、送信前にJavaScriptの確認ポップアップを表示する。OKの場合のみ予約登録し、完了画面へ遷移する。
- 管理画面の削除は物理削除ではなく論理削除とする。
- 商品管理は管理画面のみ実装する。ユーザー向け商品一覧・商品詳細・EC導線はPhase1対象外とする。
