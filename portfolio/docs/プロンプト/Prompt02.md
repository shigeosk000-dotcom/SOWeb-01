
SOWEB-01\portfolio\AromaTripNippon\main に AromaTripNippon Phase1 のSpring Bootアプリ基盤を構築してください。

前提:
- 仕様は SOWEB-01\portfolio\docs を参照
- UIは mockup_phase2 のHTML/CSS/JSを参考にする
- Phase1以外の機能は実装しない
- 不明点は推測せず確認する

追加確定事項:
- Spring Bootで実装する。
- DBはMySQLを優先する。ただし環境構築や接続設定の手間が大きい場合はH2で実装してよい。
- 予約完了メール送信はPhase1に含めない。
- ユーザー予約は確認画面を作らず、送信前にJavaScriptの確認ポップアップを表示する。OKの場合のみ予約登録し、完了画面へ遷移する。
- 管理画面の削除は物理削除ではなく論理削除とする。
- 商品管理は管理画面のみ実装する。ユーザー向け商品一覧・商品詳細・EC導線はPhase1対象外とする。

作成内容:
- Spring Boot プロジェクト
- Thymeleaf
- Spring Web
- Spring Data JPA
- Validation
- Spring Security
- DB接続設定
- static/css/js/images 配置
- templates/user と templates/management の分離
- 共通レイアウト、ヘッダー、フッター、管理サイドバー
- エラー画面、完了画面

注意:
- mockup_phase2/user と mockup_phase2/management の見た目をなるべく維持する
- 配置先以外の既存ファイルは変更しない
- Phase2ページはコピーしてもリンクから除外、または未実装扱いにする
