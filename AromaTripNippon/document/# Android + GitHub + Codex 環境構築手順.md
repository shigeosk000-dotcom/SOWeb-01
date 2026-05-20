# Android + GitHub + Codex 環境構築手順

## 目的

Androidスマホだけで、以下の環境を作ります。

- ChatGPTアプリでCodexを使う
- GitHubにコードを保存する
- Codexにコード作成・修正を依頼する
- AndroidからAI開発を進める

---

## 完成イメージ

```text
Androidスマホ
↓
ChatGPTアプリ
↓
Codex
↓
GitHub Repository
↓
AIがコード作成・修正
```

---

## 必要なもの

| 必要なもの | 説明 |
|---|---|
| Androidスマホ | ChatGPTアプリを使う端末 |
| ChatGPTアカウント | Codexを使うために必要 |
| GitHubアカウント | コードを保存するために必要 |
| Chrome | GitHub操作用 |
| GitHubアプリ | 任意。入れると便利 |

---

# STEP 1：GitHubアカウントを作成する

## 1-1. GitHubを開く

AndroidのChromeで以下を開きます。

```text
https://github.com
```

## 1-2. 「Sign up」を押す

画面右上、または中央付近にある **Sign up** を押します。

## 1-3. 必要情報を入力する

以下を入力します。

| 項目 | 入力内容 |
|---|---|
| Email | メールアドレス |
| Password | パスワード |
| Username | GitHub上の名前 |

## 1-4. メール認証を完了する

GitHubから届いたメールを開き、認証を完了します。

## 1-5. GitHubにログインする

作成したアカウントでGitHubにログインします。

---

# STEP 2：最初のリポジトリを作成する

## 2-1. リポジトリとは

リポジトリとは、**プロジェクトの保管場所**です。

例：

```text
my-first-app
```

というリポジトリを作ると、その中にコードを保存できます。

## 2-2. 「＋」を押す

GitHub画面の右上にある **＋** を押します。

## 2-3. 「New repository」を選ぶ

表示されたメニューから **New repository** を選びます。

## 2-4. リポジトリ情報を入力する

以下のように入力します。

| 項目 | 入力内容 |
|---|---|
| Repository name | my-first-app |
| Description | 空欄でOK |
| Public / Private | Private推奨 |
| Add a README file | ON |

## 2-5. 「Create repository」を押す

これで最初のリポジトリが完成します。

---

# STEP 3：ChatGPTアプリを準備する

## 3-1. Play Storeを開く

Androidで **Google Play Store** を開きます。

## 3-2. ChatGPTアプリを検索する

検索欄に以下を入力します。

```text
ChatGPT
```

## 3-3. ChatGPTアプリをインストールまたは更新する

すでに入っている場合は、最新版に更新します。

## 3-4. ChatGPTにログインする

ChatGPTアカウントでログインします。

GitHubアカウントとは別でも問題ありません。

---

# STEP 4：Codexを開く

## 4-1. ChatGPTアプリを開く

AndroidでChatGPTアプリを起動します。

## 4-2. Codexを探す

アプリ内で **Codex** を探します。

表示場所はバージョンによって変わる可能性があります。

よくある場所は以下です。

- サイドバー
- ツール一覧
- Codexタブ
- 新規チャット作成画面

## 4-3. Codexを開く

**Codex** をタップして開きます。

---

# STEP 5：CodexとGitHubを連携する

## 5-1. GitHub連携ボタンを探す

Codex内で以下のようなボタンを探します。

```text
Connect GitHub
```

または

```text
Connect repository
```

## 5-2. GitHubログイン画面を開く

GitHubの認証画面が表示されたら、GitHubアカウントでログインします。

## 5-3. 連携を許可する

CodexがGitHubにアクセスできるように許可します。

## 5-4. リポジトリを選択する

STEP 2で作成したリポジトリを選びます。

```text
my-first-app
```

## 5-5. 連携完了を確認する

Codex画面にリポジトリ名が表示されれば完了です。

---

# STEP 6：最初のAI開発を実行する

## 6-1. Codexに指示を出す

Codexに以下のように入力します。

```text
HTMLで自己紹介ページを作ってください。
```

## 6-2. もう少し具体的に頼む場合

以下のように指示すると、より良い結果になります。

```text
HTMLとCSSで、スマホ対応の自己紹介ページを作ってください。
名前、プロフィール、スキル、問い合わせリンクを入れてください。
```

## 6-3. Codexの作業内容を確認する

Codexは以下のような作業を行います。

- ファイル作成
- コード生成
- README作成
- 変更内容の提案
- テスト実行
- Pull Request作成

---

# STEP 7：変更内容を確認する

## 7-1. Diffを確認する

Codexが作成した変更差分を確認します。

Diffとは、**どこが変更されたかを表示する画面**です。

## 7-2. 確認するポイント

以下を確認します。

- 変なファイルが追加されていないか
- 指示した内容に合っているか
- エラーが出ていないか
- READMEがわかりやすいか

## 7-3. わからない場合

Codexに以下のように聞きます。

```text
この変更内容を初心者にもわかるように説明してください。
```

---

# STEP 8：変更を承認する

## 8-1. 問題なければApproveする

変更内容に問題がなければ、以下のようなボタンを押します。

```text
Approve
```

## 8-2. 修正したい場合

気になる点があれば、Codexに追加で指示します。

```text
デザインをもっとシンプルにしてください。
```

または

```text
スマホで見やすいレイアウトに修正してください。
```

---

# STEP 9：GitHubに反映する

## 9-1. Pull Requestを確認する

CodexがPull Requestを作成した場合、GitHubで確認します。

Pull Requestとは、**変更提案**のことです。

## 9-2. 内容を確認する

GitHub上で以下を確認します。

- 変更ファイル
- 変更内容
- コメント
- エラー表示

## 9-3. Mergeする

問題なければ **Merge** を押します。

これで変更がリポジトリに反映されます。

---

# STEP 10：次の開発を依頼する

## 10-1. 追加機能を依頼する

例：

```text
この自己紹介ページにお問い合わせフォームを追加してください。
```

## 10-2. デザイン改善を依頼する

例：

```text
全体をもっとおしゃれで見やすいデザインにしてください。
```

## 10-3. README作成を依頼する

例：

```text
このプロジェクトのREADMEを初心者向けに書いてください。
```

---

# 初心者におすすめの練習課題

## HTML練習

```text
スマホ対応のポートフォリオサイトを作ってください。
```

## CSS練習

```text
このページをカード型デザインにしてください。
```

## Python練習

```text
Pythonで簡単なTODOアプリを作ってください。
```

## React練習

```text
ReactでTODOリストアプリを作ってください。
```

## README練習

```text
このリポジトリの使い方をREADMEにまとめてください。
```

---

# Codexへの指示テンプレート

## 新規作成

```text
〇〇を作ってください。
初心者にもわかるように、READMEも追加してください。
```

## バグ修正

```text
このエラーを修正してください。
原因も初心者向けに説明してください。
```

## デザイン改善

```text
スマホで見やすいデザインに改善してください。
```

## コード整理

```text
コードを読みやすく整理してください。
不要なコードがあれば削除してください。
```

## テスト追加

```text
この機能にテストを追加してください。
テストの実行方法もREADMEに書いてください。
```

---

# Androidだけで入れておくと便利なアプリ

| アプリ | 用途 |
|---|---|
| ChatGPT | Codex操作 |
| GitHub | PR確認・Issue確認 |
| Chrome | GitHub操作 |
| Google Drive | ファイル管理 |
| Termux | 上級者向けターミナル |
| Acode | Android上のコードエディタ |

---

# よく使う用語

## Repository

プロジェクトの保管場所です。

## Commit

変更を保存することです。

## Pull Request

変更を反映してよいか確認するための提案です。

## Merge

Pull Requestの変更を本体に取り込むことです。

## Diff

変更前と変更後の差分です。

## Branch

作業用の分岐です。

## README

プロジェクトの説明書です。

---

# よくあるトラブル

## Codexが表示されない

確認すること：

- ChatGPTアプリが最新版か
- Codex対応アカウントか
- 一度ログアウトして再ログインしたか
- Androidアプリ側にまだ反映されていない可能性があるか

## GitHub連携できない

確認すること：

- GitHubにログインできているか
- リポジトリを作成済みか
- CodexにGitHubアクセスを許可したか
- Private repositoryへのアクセス権限を許可したか

## Codexがうまく作業できない

指示を具体的にします。

悪い例：

```text
アプリ作って
```

良い例：

```text
Reactでスマホ対応のTODOアプリを作ってください。
タスク追加、削除、完了チェックができるようにしてください。
READMEも追加してください。
```

---

# Androidだけで向いている開発

## 向いているもの

- Webサイト
- HTML/CSS
- JavaScript
- React
- Pythonツール
- README作成
- 小規模アプリ
- AIツール
- 自動化スクリプト

## あまり向いていないもの

- Android Studioを使う本格Androidアプリ開発
- iOSアプリ開発
- Unity開発
- Unreal Engine開発
- GPUを使う開発
- 大規模な複数画面アプリ開発

---

# 慣れてきたら追加するとよいもの

## GitHub Codespaces

Androidブラウザ上でVS Codeのような開発環境を使えます。

できること：

- ファイル編集
- ターミナル操作
- npm実行
- Python実行
- Webアプリ確認

## Termux

Android上でLinux風のターミナルを使えます。

できること：

- Git操作
- Python実行
- Node.js実行
- 簡単な開発作業

## Bluetoothキーボード

長い指示やコード確認がかなり楽になります。

---

# おすすめの学習順

## 1段階目

- GitHubに慣れる
- Repositoryを作る
- READMEを書く
- HTMLページを作る

## 2段階目

- CSSでデザインする
- JavaScriptを少し触る
- Codexに修正を頼む

## 3段階目

- Pythonツールを作る
- Reactアプリを作る
- GitHub Pagesで公開する

## 4段階目

- Firebaseを使う
- Supabaseを使う
- APIを作る
- 本格的なWebアプリを作る

---

# 最初に試すおすすめプロンプト

```text
GitHubリポジトリに、HTML/CSS/JavaScriptで動くスマホ対応のTODOアプリを作ってください。

要件：
- タスクを追加できる
- タスクを削除できる
- タスクを完了済みにできる
- スマホで見やすいデザインにする
- READMEに使い方を書く
```

---

# まとめ

Android + GitHub + Codex を使うと、スマホだけでも以下ができます。

- AIにコードを書かせる
- GitHubでコードを管理する
- Pull Requestを確認する
- 小規模アプリを作る
- READMEやテストもAIに作らせる

最初はコードが完全に理解できなくても大丈夫です。

まずは以下の流れで慣れていきます。

```text
作らせる
↓
読んでみる
↓
少し直す
↓
またAIに頼む
```

この流れを繰り返すことで、Androidだけでも十分にAI開発を始められます。