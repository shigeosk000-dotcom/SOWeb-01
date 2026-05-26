以下の仕様変更を Spring Boot プロジェクトに反映してください。

仕様変更:
- User 一覧APIで、通常は deleted=false のみ返す
- includeDeleted=true のときは削除済みも返す
- デフォルト動作は従来どおり

やってほしいこと:
1. 影響範囲を整理
2. Controller / Service / Repository / Test を必要に応じて更新
3. 必要なら DTO / README も更新
4. 最後に変更ファイル一覧とテスト観点を出す

制約:
- 既存互換性を壊さない
- 変更差分は最小限
- 無関係な整理や設計変更はしない
- まず短い計画を出してから実装する