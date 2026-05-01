# Aroma Trip Nippon｜和香巡り Phase1 Frontend

## 概要

日本各地の天然素材を使い、自分だけの香りを作る体験型サービス「Aroma Trip Nippon｜和香巡り」のPhase1静的フロントエンドです。GitHub Pagesでそのまま公開できるよう、HTML / CSS / JavaScriptのみで構成しています。

## 対象範囲

- トップページ `index.html`
- 体験プログラム詳細ページ `experience.html`
- 予約ページ `reservation.html`
- 予約完了・確認ページ `reservation-complete.html`
- コンセプトページ `concept.html`

Phase2想定のギャラリー、EC、マイページ、アクセス詳細、管理画面は未実装です。

## 使用技術

- HTML5
- CSS3（CSSカスタムプロパティ、モバイルファースト、901px以上をPCブレークポイント）
- JavaScript（モバイルナビ、画像スライダー、予約フォーム簡易バリデーション、`localStorage`保存）

## ディレクトリ構成

```text
/
├── index.html
├── experience.html
├── reservation.html
├── reservation-complete.html
├── concept.html
├── assets/
│   └── images/
├── css/
│   └── style.css
└── js/
    └── main.js
```

## GitHub Pages公開手順

1. `AromaTrip/main` 配下のファイルを公開対象ブランチ、またはGitHub Pages用ディレクトリに配置します。
2. GitHubのリポジトリ設定で `Settings > Pages` を開きます。
3. 公開元のブランチとディレクトリを選択します。
4. 公開URLにアクセスし、`index.html` から各ページへ遷移できることを確認します。

相対パスのみで構成しているため、サブディレクトリ配信でも表示できます。

## 予約フォーム仕様

Phase1では静的な仮実装です。

- 必須項目未入力チェック
- メール形式チェック
- 人数1名以上チェック
- 日程の過去日選択防止
- 送信後、予約内容を `localStorage` に保存
- `reservation-complete.html` で予約内容を表示

決済、メール送信、DB保存、予約管理画面への連携は行っていません。

## Phase2以降の拡張方針

- ギャラリーページ追加
- EC商品一覧 / 商品詳細 / カート / 購入フロー追加
- マイページ追加
- 体験後の香りレシピ保存
- 予約データのSpring Boot連携
- 決済・確認メール・キャンセルポリシー対応
- アクセス詳細ページ追加

## 未実装・確認事項

- 正式な店舗住所：現在は `東京都台東区浅草X-X-X`
- 正式な予約送信先
- 決済導線
- 確認メール送信
- 英語コピーの最終確認
- 素材ラインナップと季節変更ルール
- キャンセルポリシー

## 参照

- `AromaTrip/document` 配下の企画提案書、サイトマップ、ページ一覧、URL一覧、デザインガイドライン
- `AromaTrip/mockup/user` 配下の既存HTMLモックアップ
- `AromaTrip/mockup/user/assets` 由来の画像素材
