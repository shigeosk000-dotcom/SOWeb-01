# GitHub Codespaces + Spring Boot 開発環境構築ガイド（初心者向け完全版）

# この資料でできること

この手順を完了すると、以下ができるようになります。

- GitHub上のSpring Bootアプリをブラウザだけで開発
- GitHub Codespacesを使う
- Spring Bootアプリを起動する
- ブラウザで動作確認する
- ChatGPTと組み合わせて保守・修正する
- Codespacesを停止して無料枠を節約する

---

# 想定構成

```text
GitHub Repository
 └ Spring Bootアプリ
    └ pom.xml
```

---

# 完成イメージ

```text
Chromeブラウザ
↓
GitHub Codespaces
↓
VS Code風画面
↓
Spring Boot起動
↓
ブラウザ確認
```

---

# 必要なもの

| 必要なもの | 内容 |
|---|---|
| GitHubアカウント | 無料でOK |
| Chrome | 推奨 |
| Spring Boot Repository | GitHub上に存在 |
| ChatGPT | 推奨 |

---

# STEP 1：GitHubへログイン

---

## 1-1. GitHubを開く

Chromeで開きます。

```text
https://github.com
```

---

## 1-2. ログイン

GitHubアカウントでログインします。

---

# STEP 2：Repositoryを開く

---

# Repositoryとは？

プロジェクト保管場所です。

例：

```text
SOWeb-01
```

---

## 2-1. 「Repositories」を開く

GitHubホーム画面で：

```text
Your repositories
```

を探します。

---

## 2-2. Repository名を押す

例：

```text
SOWeb-01
```

をタップ。

---

# STEP 3：Codespacesを作成する

---

## 3-1. 緑の「Code」を押す

Repository画面の右上付近。

---

## 3-2. 「Codespaces」タブを押す

通常：

```text
Local
Codespaces
```

があります。

---

## 3-3. 「Create codespace on main」を押す

数十秒〜数分待ちます。

---

# STEP 4：Codespaces起動確認

成功すると：

- VS Code風画面
- ファイル一覧
- ターミナル

が表示されます。

---

# STEP 5：Spring Bootアプリの場所を探す

---

## 5-1. ターミナルを開く

上メニュー：

```text
Terminal
↓
New Terminal
```

---

## 5-2. pom.xmlを探す

以下を実行。

```bash
find . -name pom.xml
```

---

## 5-3. 結果確認

例：

```text
./AromaTripNippon/main/pom.xml
```

---

# STEP 6：Spring Bootフォルダへ移動

---

## 6-1. cdコマンド実行

例：

```bash
cd AromaTripNippon/main
```

---

## 6-2. pom.xml確認

```bash
ls
```

一覧に：

```text
pom.xml
```

があればOK。

---

# STEP 7：Javaバージョン確認

Spring BootはJava 17要求の場合が多いです。

---

## 7-1. Java確認

```bash
java -version
```

```bash
javac -version
```

---

## 7-2. Java 17でない場合

以下を実行。

---

# STEP 8：Java 17設定

---

## 8-1. Java 17インストール

```bash
sudo apt update
sudo apt install -y openjdk-17-jdk
```

---

## 8-2. JAVA_HOME設定

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
```

---

## 8-3. 確認

```bash
java -version
```

```bash
javac -version
```

両方：

```text
17
```

ならOK。

---

# STEP 9：Spring Boot起動

---

## 9-1. Maven起動

```bash
mvn spring-boot:run
```

---

## 9-2. 初回注意

初回は大量ダウンロードがあります。

数分かかる場合があります。

---

# STEP 10：起動成功確認

成功すると最後に：

```text
Started XXXXXApplication
```

のようなログが出ます。

---

# STEP 11：ブラウザ確認

---

# 11-1. PORTSタブを見る

画面下部：

```text
PORTS
```

を開きます。

---

# 11-2. 8080ポート確認

通常：

```text
8080
```

が表示されます。

---

# 11-3. 「Open in Browser」

を押します。

---

# STEP 12：GitHub URLで表示される

URL例：

```text
https://symmetrical-space-sniffle-7vqwrppqgrw6hpj6x-8080.app.github.dev/
```

---

# localhostじゃない理由

Codespaces内部では：

```text
localhost:8080
```

ですが、

GitHubが外部公開URLへ変換しています。

これは正常です。

---

# STEP 13：動作確認

確認ポイント：

| 確認項目 | 内容 |
|---|---|
| 画面表示 | 正常か |
| CSS | 崩れていないか |
| ボタン | 動くか |
| API | エラー出ないか |
| ログ | エラーないか |

---

# STEP 14：ChatGPTで保守する

---

## 14-1. エラー相談

例：

```text
Spring Boot起動時に以下エラーが出ました。

[エラー内容]
```

---

## 14-2. 修正相談

例：

```text
このControllerをリファクタしてください。
```

---

## 14-3. UI改善

例：

```text
スマホ向けUIに改善してください。
```

---

# STEP 15：ソース修正

---

## 15-1. Explorerでファイル編集

左側から：

```text
src/main/java
```

などを開く。

---

## 15-2. 保存

PC：

```text
Ctrl + S
```

Android：

```text
︙ → Save
```

---

# STEP 16：Gitへ保存

---

## 16-1. Source Controlを開く

左側の枝アイコン。

---

## 16-2. Commit Message入力

例：

```text
fix login bug
```

---

## 16-3. Commit

「Commit」を押す。

---

## 16-4. Push

GitHubへ反映。

---

# STEP 17：Spring Boot停止

---

## 17-1. ターミナル停止

```text
Ctrl + C
```

---

## Androidの場合

- キーボード
- メニュー
- 長押し

などでCtrl+C。

---

# STEP 18：Codespaces停止

無料枠節約に重要。

---

## 18-1. Repository画面へ戻る

---

## 18-2. 「Code」押す

---

## 18-3. 「Codespaces」タブ

---

## 18-4. 「…」押す

---

## 18-5. 「Stop codespace」

を押す。

---

# 停止と削除の違い

| 操作 | 内容 |
|---|---|
| Stop | 一時停止 |
| Delete | 完全削除 |

---

# おすすめ運用

---

# 作業開始

```text
Resume Codespace
↓
mvn spring-boot:run
↓
Open in Browser
```

---

# 作業終了

```text
Ctrl + C
↓
Stop Codespace
```

---

# よくあるエラー

---

# No plugin found for prefix spring-boot

原因：

- pom.xml直下にいない

対処：

```bash
find . -name pom.xml
cd 対象フォルダ
```

---

# release version 17 not supported

原因：

- Java 17未設定

対処：

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
```

---

# localhostではなく github.dev URL

正常。

Codespaces専用URL。

---

# ポートが表示されない

Spring Boot未起動。

---

# 起動が遅い

初回Mavenダウンロード中。

正常。

---

# Androidで操作しづらい

おすすめ：

- 横画面
- Bluetoothキーボード
- タブレット

---

# 今後できること

---

# 保守

- バグ修正
- ログ確認
- UI改善

---

# 開発

- Controller追加
- API追加
- DB接続

---

# AI活用

- ChatGPTレビュー
- コード生成
- README作成
- テスト作成

---

# まとめ

GitHub Codespacesを使うと：

```text
ブラウザだけでSpring Boot開発
```

が可能になります。

しかも：

- PC不要
- インストール不要
- GitHub連携済み
- AI開発可能

です。

初心者はまず：

```text
起動
↓
修正
↓
Commit
↓
Push
```

この流れを覚えるのがおすすめです。