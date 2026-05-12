SOWEB-01\portfolio\docs 配下のドキュメントを読み込み、AromaTripNippon Phase1 の構築範囲を整理してください。

構築対象:
- Phase1のみ
- 配置先: SOWEB-01\portfolio\AromaTripNippon\main
- フロント画面参照: SOWEB-01\AromaTrip\mockup_phase2\user
- バックエンド管理画面参照: SOWEB-01\AromaTrip\mockup_phase2\management

Phase1範囲:
- フロント: トップ、体験詳細、コンセプト、予約フォーム、予約完了/確認
- 管理: ログイン、ダッシュボード、予約管理、顧客管理、香りレシピ管理、商品管理、在庫管理、アカウント設定
- DB: customers, experience_programs, reservations, fragrance_recipes, fragrance_recipe_materials, inventory_items, inventory_transactions, products, admin_users, audit_logs

除外:
- EC注文、カート、マイページ、レビュー、ギャラリー、多言語文言管理などPhase2機能

追加確定事項:
- Spring Bootで実装する。
- DBはMySQLを優先する。ただし環境構築や接続設定の手間が大きい場合はH2で実装してよい。
- 予約完了メール送信はPhase1に含めない。
- ユーザー予約は確認画面を作らず、送信前にJavaScriptの確認ポップアップを表示する。OKの場合のみ予約登録し、完了画面へ遷移する。
- 管理画面の削除は物理削除ではなく論理削除とする。
- 商品管理は管理画面のみ実装する。ユーザー向け商品一覧・商品詳細・EC導線はPhase1対象外とする。
これで前回のプロンプト群から「不明点」はほぼ消

成果物:
- Phase1機能一覧
- 画面一覧
- テーブル一覧
- 実装順序
- 確認事項一覧
