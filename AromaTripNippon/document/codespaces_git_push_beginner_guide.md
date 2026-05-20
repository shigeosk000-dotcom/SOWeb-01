# Codespacesでコード修正後、GitHubに反映させる手順

対象リポジトリ: `SOWeb-01`  
作業場所: GitHub Codespaces / VS Code  
対象アプリ例: `AromaTripNippon/main`

---

## 0. この手順でやること

Codespaces上でコードを修正したあと、その変更をGitHubに反映する流れは、基本的に次の4ステップです。

```text
1. 変更内容を確認する
2. 変更ファイルをGitに追加する
3. 変更内容をcommitする
4. GitHubへpushする
```

Gitコマンドでいうと、主に以下を使います。

```bash
git status
git add
git commit
git push
```

---

## 1. まずCodespacesのターミナルを開く

VS Code画面下部の `TERMINAL` を開きます。

ターミナルが以下のような表示になっていればOKです。

```bash
@shigeosk000-dotcom ➜ /workspaces/SOWeb-01 (main) $
```

ここで重要なのは、現在の場所がリポジトリ直下であることです。

```text
/workspaces/SOWeb-01
```

---

## 2. 現在いる場所を確認する

まず、今どのフォルダにいるか確認します。

```bash
pwd
```

期待される表示:

```text
/workspaces/SOWeb-01
```

もし違う場所にいる場合は、以下でリポジトリ直下へ移動します。

```bash
cd /workspaces/SOWeb-01
```

---

## 3. 作業対象アプリの場所

今回のアプリ本体は以下の場所です。

```text
/workspaces/SOWeb-01/AromaTripNippon/main
```

VS Codeのエクスプローラー上では、以下のように見えます。

```text
SOWeb-01
└── AromaTripNippon
    └── main
        ├── .mvn
        ├── data
        ├── src
        ├── target
        ├── Dockerfile
        ├── mvnw
        ├── mvnw.cmd
        ├── pom.xml
        └── README.md
```

アプリのソースコードは主に以下にあります。

```text
/workspaces/SOWeb-01/AromaTripNippon/main/src
```

---

## 4. 修正後、まず変更内容を確認する

コードを修正したら、リポジトリ直下で以下を実行します。

```bash
cd /workspaces/SOWeb-01
git status
```

例:

```text
On branch main
Your branch is up to date with 'origin/main'.

Changes not staged for commit:
  modified:   AromaTripNippon/main/src/main/java/...
```

これは、

```text
ファイルは変更されているが、まだcommit対象には入っていない
```

という意味です。

---

## 5. 変更されたファイル一覧を確認する

変更ファイルだけを簡単に見たい場合は、以下を使います。

```bash
git status --short
```

例:

```text
 M AromaTripNippon/main/src/main/java/com/aromatripnippon/controller/PublicController.java
 M AromaTripNippon/main/src/main/resources/templates/index.html
```

左側の `M` は `Modified`、つまり変更済みという意味です。

---

## 6. 変更差分を確認する

どこを変更したか確認したい場合は、以下を実行します。

```bash
git diff
```

特定のファイルだけ確認したい場合は、パスを指定します。

```bash
git diff AromaTripNippon/main/src/main/java/com/aromatripnippon/controller/PublicController.java
```

差分確認を終了したい場合は、キーボードの `q` を押します。

---

## 7. アプリを起動して動作確認する

GitHubに反映する前に、アプリが動くか確認します。

アプリのディレクトリへ移動します。

```bash
cd /workspaces/SOWeb-01/AromaTripNippon/main
```

Spring Bootアプリを起動します。

```bash
./mvnw spring-boot:run
```

起動成功の目安:

```text
Tomcat started on port 8080
Started AromaTripNipponApplication
```

ブラウザで確認するときは、Codespacesの `PORTS` タブから `8080` を開きます。

確認が終わったら、ターミナルで `Ctrl + C` を押してアプリを停止します。

---

## 8. commitする前に、もう一度リポジトリ直下へ戻る

アプリ起動のために `AromaTripNippon/main` に移動している場合があります。  
Git操作はリポジトリ直下で行うとわかりやすいです。

```bash
cd /workspaces/SOWeb-01
```

確認:

```bash
pwd
```

期待される表示:

```text
/workspaces/SOWeb-01
```

---

## 9. Gitに追加する

変更したファイルをcommit対象に追加します。

### 方法A: 変更ファイルを全部追加する

```bash
git add .
```

初心者にはこの方法がわかりやすいですが、不要なファイルまで追加される可能性があります。

追加前に必ず確認しましょう。

```bash
git status --short
```

---

### 方法B: 必要なファイルだけ追加する

特定のファイルだけ追加する場合は、パスを指定します。

例:

```bash
git add AromaTripNippon/main/src/main/java/com/aromatripnippon/controller/PublicController.java
```

複数ファイルを追加する場合:

```bash
git add AromaTripNippon/main/src/main/java/com/aromatripnippon/controller/PublicController.java
git add AromaTripNippon/main/src/main/resources/templates/index.html
```

---

### 方法C: src配下だけ追加する

ソースコードだけ追加したい場合は、以下のようにします。

```bash
git add AromaTripNippon/main/src
```

---

## 10. 追加されたか確認する

```bash
git status
```

例:

```text
Changes to be committed:
  modified:   AromaTripNippon/main/src/main/java/com/aromatripnippon/controller/PublicController.java
```

`Changes to be committed` に表示されていれば、commit対象に入っています。

---

## 11. commitする

commitとは、変更内容に名前をつけてGit上に保存する操作です。

```bash
git commit -m "管理画面の表示を修正"
```

メッセージは日本語でも大丈夫です。

例:

```bash
git commit -m "予約一覧画面のレイアウトを修正"
```

```bash
git commit -m "トップページの文言を修正"
```

```bash
git commit -m "在庫管理機能の不具合を修正"
```

---

## 12. GitHubへpushする

commitできたら、GitHubへ反映します。

```bash
git push
```

これでGitHub上のリポジトリに変更が反映されます。

---

## 13. 最短の基本手順

変更後、問題なくGitHubに反映するだけなら、基本は以下です。

```bash
cd /workspaces/SOWeb-01
git status
git diff
git add .
git status
git commit -m "変更内容を説明するメッセージ"
git push
```

---

## 14. アプリ確認も含めたおすすめ手順

初心者向けには、以下の流れがおすすめです。

```bash
# リポジトリ直下へ移動
cd /workspaces/SOWeb-01

# 変更状態を確認
git status
git diff

# アプリを起動して確認
cd /workspaces/SOWeb-01/AromaTripNippon/main
./mvnw spring-boot:run

# 確認後、Ctrl + Cで停止

# Git操作のためリポジトリ直下へ戻る
cd /workspaces/SOWeb-01

# 変更を追加
git add .

# commit対象を確認
git status

# commit
git commit -m "変更内容を説明するメッセージ"

# GitHubへ反映
git push
```

---

## 15. `git add .` の注意点

`git add .` は、現在のフォルダ以下の変更をすべて追加します。

そのため、以下のようなファイルが混ざる場合があります。

```text
AromaTripNippon/main/target/...
AromaTripNippon/main/data/aromatripnippon.mv.db
```

`target/` はビルド生成物です。  
`.class` ファイルなどは通常、GitHubに反映する必要はありません。

`.mv.db` はH2データベースファイルで、アプリを起動すると変更されることがあります。  
これも通常、意図せずcommitしない方が安全です。

---

## 16. 不要なファイルをcommit対象から外す

`git add .` したあとに、不要なファイルが入ってしまった場合は、commit対象から外せます。

### targetを外す

```bash
git restore --staged AromaTripNippon/main/target
```

### H2データベースファイルを外す

```bash
git restore --staged AromaTripNippon/main/data/aromatripnippon.mv.db
```

これは「commit対象から外す」だけで、ファイルの中身は消しません。

---

## 17. 不要なローカル変更自体を戻す

不要な変更を完全に元に戻したい場合は、以下を使います。

### target配下を元に戻す

```bash
git restore AromaTripNippon/main/target
```

### H2データベースファイルを元に戻す

```bash
git restore AromaTripNippon/main/data/aromatripnippon.mv.db
```

> 注意: `git restore` はそのファイルのローカル変更を消します。必要な変更がないことを確認してから実行してください。

---

## 18. commit前に入っているファイルを確認する

commit前は必ず以下を実行してください。

```bash
git status
```

理想的には、`Changes to be committed` に入っているのは、自分が意図して修正したファイルだけです。

例:

```text
Changes to be committed:
  modified:   AromaTripNippon/main/src/main/java/com/aromatripnippon/controller/PublicController.java
  modified:   AromaTripNippon/main/src/main/resources/templates/index.html
```

もし以下のようなものが入っていたら、不要な可能性があります。

```text
AromaTripNippon/main/target/classes/...
AromaTripNippon/main/data/aromatripnippon.mv.db
```

---

## 19. commitメッセージの書き方

commitメッセージは、あとから見たときに「何を変えたか」がわかる内容にします。

良い例:

```bash
git commit -m "予約一覧画面の表示崩れを修正"
```

```bash
git commit -m "管理画面ログイン後の遷移先を修正"
```

```bash
git commit -m "商品登録フォームの入力チェックを追加"
```

避けたい例:

```bash
git commit -m "修正"
```

```bash
git commit -m "test"
```

```bash
git commit -m "aaa"
```

---

## 20. push後に確認する

`git push` が成功すると、以下のような表示になります。

```text
Enumerating objects: ...
Counting objects: ...
Writing objects: ...
To https://github.com/...
   xxxxxxx..yyyyyyy  main -> main
```

その後、GitHubのリポジトリ画面を開き、commitが反映されているか確認します。

---

## 21. よくあるエラーと対応

### エラー1: `nothing to commit, working tree clean`

表示例:

```text
nothing to commit, working tree clean
```

意味:

```text
commitする変更がありません
```

対応:

- すでにcommit済み
- 変更したと思ったファイルが保存されていない
- 別のフォルダを見ている

確認:

```bash
pwd
git status
```

---

### エラー2: `no changes added to commit`

表示例:

```text
no changes added to commit
```

意味:

```text
変更はあるが、git addされていません
```

対応:

```bash
git add .
git commit -m "変更内容を説明するメッセージ"
```

---

### エラー3: `failed to push some refs`

表示例:

```text
failed to push some refs to 'https://github.com/...'
```

意味:

```text
GitHub側に自分の手元より新しい変更があります
```

対応:

まずリモートの変更を取り込みます。

```bash
git pull
```

その後、もう一度pushします。

```bash
git push
```

---

### エラー4: `Your local changes would be overwritten by merge`

表示例:

```text
error: Your local changes to the following files would be overwritten by merge:
Please commit your changes or stash them before you merge.
Aborting
```

意味:

```text
ローカル変更があるため、pullすると上書きされる可能性があります
```

対応A: 自分の変更を先にcommitする

```bash
git status
git add .
git commit -m "作業中の変更を保存"
git pull
git push
```

対応B: 一時退避する

```bash
git stash
git pull
git stash pop
```

対応C: 不要な変更を捨てる

```bash
git restore AromaTripNippon/main/target
git restore AromaTripNippon/main/data/aromatripnippon.mv.db
git pull
```

---

### エラー5: commit後にpushし忘れた

commitは手元のCodespaces内に保存する操作です。  
GitHubに反映するには、必ずpushが必要です。

確認:

```bash
git status
```

以下のように出た場合:

```text
Your branch is ahead of 'origin/main' by 1 commit.
```

これは、

```text
手元にはcommitがあるが、GitHubにはまだ送っていない
```

という意味です。

対応:

```bash
git push
```

---

### エラー6: 間違えて不要なファイルをcommitしてしまった

まだpushしていない場合は、直前のcommitを取り消せます。

```bash
git reset --soft HEAD~1
```

これでcommitだけが取り消され、変更ファイルは残ります。

不要ファイルをcommit対象から外します。

```bash
git restore --staged AromaTripNippon/main/target
git restore --staged AromaTripNippon/main/data/aromatripnippon.mv.db
```

必要なファイルだけ追加し直します。

```bash
git add AromaTripNippon/main/src
git commit -m "必要な変更だけをcommit"
git push
```

> 注意: すでにpush済みの場合は、無理に履歴を書き換えず、追加修正commitで対応する方が安全です。

---

## 22. ブランチ確認

今どのブランチにいるか確認します。

```bash
git branch
```

例:

```text
* main
```

`*` が付いているものが現在のブランチです。

今回の作業では、基本的に `main` で作業しています。

---

## 23. GitHub反映までの安全チェックリスト

push前に、以下を確認してください。

```text
□ /workspaces/SOWeb-01 にいる
□ git status で変更内容を確認した
□ git diff で差分を確認した
□ アプリを起動して動作確認した
□ target/ や .class がcommit対象に入っていない
□ aromatripnippon.mv.db を意図せずcommitしようとしていない
□ commitメッセージがわかりやすい
□ git push まで実行した
```

---

## 24. 初心者向けのおすすめ運用

### こまめに確認する

迷ったら、すぐに以下を実行します。

```bash
git status
```

`git status` は何度実行しても安全です。

---

### いきなりpushしない

以下の順で確認すると安全です。

```bash
git status
git diff
git add .
git status
git commit -m "..."
git push
```

---

### 生成物はなるべくcommitしない

以下は通常、commitしない方が安全です。

```text
AromaTripNippon/main/target/
AromaTripNippon/main/target/classes/
AromaTripNippon/main/data/aromatripnippon.mv.db
```

---

## 25. 実際によく使うコマンド集

### 状態確認

```bash
cd /workspaces/SOWeb-01
git status
```

### 差分確認

```bash
git diff
```

### 全部追加

```bash
git add .
```

### srcだけ追加

```bash
git add AromaTripNippon/main/src
```

### commit

```bash
git commit -m "変更内容を説明するメッセージ"
```

### push

```bash
git push
```

### 不要な生成物を元に戻す

```bash
git restore AromaTripNippon/main/target
git restore AromaTripNippon/main/data/aromatripnippon.mv.db
```

### commit対象から外す

```bash
git restore --staged AromaTripNippon/main/target
git restore --staged AromaTripNippon/main/data/aromatripnippon.mv.db
```

---

## 26. 一番おすすめの実行例

以下は、アプリ確認後にGitHubへ反映する基本形です。

```bash
# リポジトリ直下へ移動
cd /workspaces/SOWeb-01

# 状態確認
git status

# 差分確認
git diff

# アプリ起動確認
cd /workspaces/SOWeb-01/AromaTripNippon/main
./mvnw spring-boot:run

# 確認後、Ctrl + Cで停止

# リポジトリ直下へ戻る
cd /workspaces/SOWeb-01

# 不要な生成物が変更されている場合は戻す
git restore AromaTripNippon/main/target
git restore AromaTripNippon/main/data/aromatripnippon.mv.db

# 必要な変更だけ追加
git add AromaTripNippon/main/src

# commit対象を確認
git status

# commit
git commit -m "変更内容を説明するメッセージ"

# GitHubへ反映
git push
```

---

## 27. 最後に

Gitで一番大事なのは、操作前後に `git status` を見ることです。

```bash
git status
```

これは安全な確認コマンドです。  
迷ったらまず `git status` を実行し、今の状態を確認してください。
