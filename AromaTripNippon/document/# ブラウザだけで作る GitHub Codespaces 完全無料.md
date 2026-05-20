# ブラウザだけで作る GitHub Codespaces 完全無料 開発環境ガイド

## このガイドでできること

この手順を完了すると、以下ができるようになります。

- PCやAndroidだけで開発できる
- VS Code風の開発環境をブラウザで使える
- ChatGPTと組み合わせてAI開発できる
- React / Python / HTML開発ができる
- GitHubでコード管理できる
- 完全無料で始められる

---

# 完成イメージ

```text
Chromeブラウザ
↓
GitHub
↓
Codespaces
↓
VS Code風開発環境
↓
ChatGPTでAI開発
```

---

# 必要なもの

| 必要なもの | 必須 | 内容 |
|---|---|---|
| GitHubアカウント | 必須 | 無料でOK |
| Chrome | 必須 | Android/PCどちらでもOK |
| ChatGPT | 推奨 | AI開発用 |
| キーボード | 推奨 | 長文入力が楽 |

---

# Codespacesとは？

GitHubが提供する、

> 「ブラウザだけで使えるVS Code」

です。

インストール不要で：

- コード編集
- ターミナル
- npm
- Python
- Git

などが使えます。

---

# 無料で使えるの？

はい。

GitHub Freeでも利用できます。

ただし無料枠があります。

2026年時点では概ね：

| 内容 | 無料枠 |
|---|---|
| CPU時間 | 月一定時間 |
| ストレージ | 数GB程度 |

小規模学習なら十分です。

---

# STEP 1：GitHubアカウントを作る

---

## 1-1. GitHubを開く

Chromeで以下を開きます。

```text
https://github.com
```

---

## 1-2. 「Sign up」を押す

画面右上の：

```text
Sign up
```

を押します。

---

## 1-3. 必要情報を入力

| 項目 | 内容 |
|---|---|
| Email | メールアドレス |
| Password | パスワード |
| Username | GitHub名 |

---

## 1-4. メール認証

GitHubから届くメールを確認し、認証を完了します。

---

## 1-5. ログイン

GitHubへログインします。

---

# STEP 2：最初のRepositoryを作る

---

# Repositoryとは？

プロジェクト保管場所です。

例：

```text
my-first-app
```

---

## 2-1. 「＋」を押す

GitHub右上。

---

## 2-2. 「New repository」を押す

---

## 2-3. Repository情報を入力

| 項目 | 入力内容 |
|---|---|
| Repository name | my-first-app |
| Public / Private | Private推奨 |
| Add README | ON |

---

## 2-4. 「Create repository」を押す

これでRepository完成。

---

# STEP 3：Codespacesを作成する

ここが重要です。

---

## 3-1. Repositoryを開く

作成した：

```text
my-first-app
```

を開きます。

---

## 3-2. 「Code」を押す

緑色のボタンです。

---

## 3-3. 「Codespaces」タブを選ぶ

通常：

```text
Local
Codespaces
```

などのタブがあります。

---

## 3-4. 「Create codespace on main」を押す

数十秒〜数分待ちます。

---

# STEP 4：VS Code風画面が開く

これで成功です。

ブラウザ内に：

- エディタ
- ターミナル
- ファイル一覧

が表示されます。

---

# STEP 5：最初のファイルを作る

---

## 5-1. Explorerを開く

左側のファイル一覧。

---

## 5-2. 新規ファイル作成

例：

```text
index.html
```

---

## 5-3. HTMLを書く

以下を貼ります。

```html
<!DOCTYPE html>
<html>
<head>
  <title>My First App</title>
</head>
<body>
  <h1>Hello Codespaces!</h1>
</body>
</html>
```

---

# STEP 6：保存する

---

## 6-1. 保存

Android：

```text
︙ → Save
```

PC：

```text
Ctrl + S
```

---

# STEP 7：ターミナルを使う

Codespacesの強みです。

---

## 7-1. Terminalを開く

上メニュー：

```text
Terminal
↓
New Terminal
```

---

## 7-2. コマンド実行

例：

```bash
ls
```

現在ファイル一覧が表示されます。

---

# STEP 8：Gitへ保存する

---

## 8-1. Source Controlを開く

左側の枝アイコン。

---

## 8-2. Commitメッセージを書く

例：

```text
first commit
```

---

## 8-3. Commitする

「Commit」を押します。

---

## 8-4. Pushする

GitHubへ保存されます。

---

# STEP 9：ChatGPTと連携する

おすすめ構成。

---

## 9-1. ChatGPTを開く

別タブで開きます。

---

## 9-2. Repositoryを説明する

例：

```text
HTMLの自己紹介サイトを作っています。
スマホ向けにデザイン改善してください。
```

---

## 9-3. AI提案をCodespacesへ貼る

ChatGPTが提案したコードを貼ります。

---

# STEP 10：React環境を作る

ここから本格的。

---

## 10-1. ターミナルを開く

---

## 10-2. React作成コマンド

```bash
npx create-vite@latest
```

---

## 10-3. 質問に回答

例：

| 質問 | 回答 |
|---|---|
| Project name | my-react-app |
| Framework | React |
| Variant | JavaScript |

---

## 10-4. フォルダ移動

```bash
cd my-react-app
```

---

## 10-5. npm install

```bash
npm install
```

---

## 10-6. 起動

```bash
npm run dev
```

---

# STEP 11：Webアプリを表示する

---

## 11-1. Portsタブを開く

下側に：

```text
PORTS
```

があります。

---

## 11-2. Open in Browser

表示されたURLを開く。

---

## 11-3. Reactアプリ表示成功

ブラウザでReactが動きます。

---

# STEP 12：ChatGPTでAI開発する

---

## React改善依頼

例：

```text
ReactのTODOアプリを作っています。
スマホ向けUIに改善してください。
```

---

## バグ修正依頼

```text
このReactエラーを修正してください。
```

---

## README生成

```text
READMEを書いてください。
```

---

# Androidだけで使うコツ

---

# ChromeをPC表示にする

Codespacesは：

```text
PC版サイト
```

表示推奨。

---

# Bluetoothキーボード推奨

かなり快適になります。

---

# 横画面推奨

特にタブレット。

---

# 完全無料で使うコツ

---

# 使い終わったら停止する

重要。

Codespacesを放置すると無料枠を消費します。

---

## 停止方法

GitHub：

```text
Code
↓
Codespaces
↓
Stop Codespace
```

---

# 不要になったら削除

使わないCodespacesは削除。

---

# 無料枠で向いている用途

---

## 向いている

- HTML/CSS
- JavaScript
- React学習
- Python
- 小規模Webアプリ
- AI開発
- README作成

---

## 厳しい

- Unity
- Unreal
- Android Studio
- Docker大量利用
- GPU利用

---

# 初心者おすすめ構成

---

# 最強無料構成

| 用途 | サービス |
|---|---|
| AI | ChatGPT |
| コード管理 | GitHub |
| 開発環境 | Codespaces |
| ブラウザ | Chrome |

---

# 最初に試すおすすめプロンプト

```text
Reactでスマホ向けTODOアプリを作ってください。

機能：
- タスク追加
- タスク削除
- 完了チェック
- モダンUI
- README作成
```

---

# よくあるトラブル

---

# Codespacesが表示されない

確認：

- GitHubログイン済みか
- Repository作成済みか
- ブラウザが古くないか

---

# npm installが失敗する

ターミナルで：

```bash
node -v
```

確認。

---

# Reactが表示されない

```bash
npm run dev
```

後に：

```text
PORTS
```

からURLを開く。

---

# Androidで操作しづらい

対策：

- 横画面
- Bluetoothキーボード
- タブレット利用

---

# おすすめ学習順

---

# 初級

- HTML
- CSS
- GitHub

---

# 中級

- JavaScript
- React

---

# 上級

- Firebase
- Supabase
- FastAPI

---

# まとめ

GitHub Codespacesを使うと、

> ブラウザだけでVS Code開発環境

を作れます。

さらにChatGPTを組み合わせることで：

- AIコード生成
- バグ修正
- README生成
- UI改善

まで可能になります。

しかも：

- PC不要
- インストール不要
- 完全無料スタート可能

です。

まずは：

```text
HTML → React → AI開発
```

の順で進めるのがおすすめです。