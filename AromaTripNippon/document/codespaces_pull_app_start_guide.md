# CodespacesでのPullからアプリ起動・確認までの手順

対象アプリ: `AromaTripNippon/main`  
想定環境: GitHub Codespaces / VS Code / Maven / Spring Boot

---

## 1. Codespacesを開く

GitHub Codespacesで対象リポジトリを開きます。

ターミナルが以下のような状態になっていれば、Codespaceに接続できています。

```bash
@shigeosk000-dotcom ➜ /workspaces/SOWeb-01 (main) $
```

左下に `Codespaces: ...` と表示されている場合は、Codespacesに接続中または接続済みです。

---

## 2. まず現在の状態を確認する

`pull` する前に、ローカル変更が残っていないか確認します。

```bash
git status
```

### 問題ない状態

以下のように表示されれば、作業ツリーはきれいです。

```text
nothing to commit, working tree clean
```

この場合はそのまま `git pull` できます。

```bash
git pull
```

---

## 3. `git pull` が成功したか確認する

`git pull` 後にエラーが出ず、ターミナルが入力待ちに戻れば基本的に成功です。

例:

```bash
@shigeosk000-dotcom ➜ /workspaces/SOWeb-01 (main) $
```

より確実に確認するには、もう一度以下を実行します。

```bash
git status
```

以下のように出ればOKです。

```text
On branch main
Your branch is up to date with 'origin/main'.

nothing to commit, working tree clean
```

---

## 4. `git pull` 時に出たエラーと対応

### エラー例

```text
error: Your local changes to the following files would be overwritten by merge:
        AromaTripNippon/main/data/aromatripnippon.mv.db
        AromaTripNippon/main/target/classes/...
Please commit your changes or stash them before you merge.
Aborting
```

これは、ローカル変更があり、`pull` によって上書きされる可能性があるため、Gitが処理を中断した状態です。

この場合、`pull` は完全には成功していません。  
リモート情報の取得はできていても、ローカルのブランチには反映されていません。

---

## 5. ローカル変更をどう扱うか

### A. ローカル変更を残したい場合

一旦退避してから `pull` します。

```bash
git stash
git pull
git stash pop
```

`stash pop` 後に競合が出た場合は、表示されたファイルを確認して手動で解消します。

---

### B. ローカル変更を捨ててよい場合

`target/` 配下の `.class` ファイルなどは、通常はビルド生成物です。  
不要であれば戻してから `pull` します。

```bash
git restore AromaTripNippon/main/target
git restore AromaTripNippon/main/data/aromatripnippon.mv.db
git pull
```

すべてのローカル変更を完全に捨ててよい場合だけ、以下を使います。

```bash
git reset --hard
git pull
```

> 注意: `git reset --hard` は未コミットの変更を完全に消します。必要な変更がないことを確認してから実行してください。

---

## 6. Rebuildの確認アラートについて

Codespacesで以下のようなアラートが出ることがあります。

```text
Rebuilding applies updates from your dev container to your codespace,
preserving your code and any pending changes.

A Full Rebuild clears cached images and is recommended to save storage
when dev container images are changed.
```

### ボタンの意味

| ボタン | 意味 |
|---|---|
| Rebuild | 通常の再ビルド。コードや未コミット変更を保持したまま環境を更新 |
| Full Rebuild | キャッシュも削除して完全に再構築 |
| Cancel | 何もしない |

通常は `Rebuild` で大丈夫です。  
環境が壊れている、イメージ変更後にうまく動かない、キャッシュが原因の可能性がある場合は `Full Rebuild` を使います。

---

## 7. Rebuildが終わったか確認する

Rebuild中は、ログに以下のような表示が出ることがあります。

```text
Creating container...
Unable to find image 'mcr.microsoft.com/devcontainers/universal:latest' locally
latest: Pulling from devcontainers/universal
Extracting ...
```

この状態は、コンテナイメージのダウンロードや展開中です。  
5分以上かかることもあります。

### 完了の目安

ターミナルが以下のような入力待ちに戻っていれば、操作可能です。

```bash
@shigeosk000-dotcom ➜ /workspaces/SOWeb-01 (main) $
```

---

## 8. Rebuildが止まったように見える場合

### 状況

- 左下に `Codespaces: ...` と表示されたまま
- ログが動かない
- 5〜15分以上、同じ表示から変わらない

### 対応手順

まずブラウザをリロードします。  
画面だけ止まっていて、裏ではCodespacesが復旧していることがあります。

リロード後、ターミナルが以下のように戻っていればOKです。

```bash
@shigeosk000-dotcom ➜ /workspaces/SOWeb-01 (main) $
```

念のため確認します。

```bash
pwd
git status
```

それでも反応がない場合は、コマンドパレットから再ビルドします。

```text
Codespaces: Rebuild Container
```

まだ直らない場合は、以下を試します。

```text
Codespaces: Full Rebuild Container
```

> 注意: Codespace自体を削除すると作業内容が失われる可能性があります。削除は最後の手段にしてください。

---

## 9. アプリの場所

今回起動したいアプリは以下です。

```text
AromaTripNippon/main
```

このフォルダ内に以下があります。

```text
.mvn/
data/
src/
target/
Dockerfile
mvnw
mvnw.cmd
pom.xml
README.md
```

`pom.xml` があるので、このディレクトリをMavenプロジェクトのルートとして扱います。

---

## 10. アプリを起動する

ターミナルでアプリのディレクトリへ移動します。

```bash
cd AromaTripNippon/main
```

Maven Wrapperで起動します。

```bash
./mvnw spring-boot:run
```

もし `mvn` を使う場合は以下でも起動できます。

```bash
mvn spring-boot:run
```

ただし、Codespacesでは `./mvnw` の方が安定です。

---

## 11. 起動成功の確認

ターミナルに以下のような表示が出れば、Spring Bootアプリは起動しています。

```text
Tomcat started on port 8080
Started AromaTripNipponApplication
```

起動中はターミナルがアプリ実行に使われるため、入力待ちに戻らなくても問題ありません。

---

## 12. ブラウザで開く

Codespacesでは、アプリが `8080` 番ポートで起動すると、VS Codeの通知や `PORTS` タブから開けます。

### PORTSタブから開く

1. VS Code下部またはパネルの `PORTS` を開く
2. `8080` が表示されているか確認
3. `Open in Browser` または地球アイコンをクリック

### 表示されない場合

`PORTS` タブで手動追加します。

```text
Forward a Port → 8080
```

---

## 13. アクセス先

| 画面 | URL |
|---|---|
| 公開画面 | `http://localhost:8080/` |
| 管理画面 | `http://localhost:8080/management/login` |
| H2コンソール | `http://localhost:8080/h2-console` |

Codespacesの場合、実際には `localhost:8080` ではなく、Codespacesが発行する転送URLで開かれることがあります。

---

## 14. 管理画面ログイン情報

管理画面に入る場合は、以下を使います。

```text
ログインID: Adm01
パスワード: password
```

---

## 15. よくあるエラーと対応

### `Permission denied` が出る

`./mvnw` に実行権限がない可能性があります。

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

---

### `mvn: command not found` が出る

`mvn` が環境に入っていない可能性があります。  
Maven Wrapperを使います。

```bash
./mvnw spring-boot:run
```

---

### 8080番ポートが使われている

すでにアプリが起動している可能性があります。  
まず、起動中のターミナルで `Ctrl + C` を押して停止します。

その後、再起動します。

```bash
./mvnw spring-boot:run
```

---

### 起動後にブラウザで開けない

`PORTS` タブを確認します。

- `8080` がない → `Forward a Port` で `8080` を追加
- `8080` がある → `Open in Browser`
- それでも開けない → アプリが本当に起動しているかターミナルログを確認

確認するログ例:

```text
Tomcat started on port 8080
Started AromaTripNipponApplication
```

---

### `git pull` でまた同じエラーが出る

まず状態を確認します。

```bash
git status
```

`target/` や `.class` などの生成物だけであれば、以下で戻してから再度 `pull` します。

```bash
git restore AromaTripNippon/main/target
git restore AromaTripNippon/main/data/aromatripnippon.mv.db
git pull
```

必要な変更がある場合は、消さずに退避します。

```bash
git stash
git pull
git stash pop
```

---

## 16. 一連の最短コマンドまとめ

ローカル変更がなく、通常起動するだけなら以下です。

```bash
git status
git pull
cd AromaTripNippon/main
./mvnw spring-boot:run
```

ローカル変更が邪魔して `pull` できないが、変更を退避したい場合:

```bash
git stash
git pull
git stash pop
cd AromaTripNippon/main
./mvnw spring-boot:run
```

生成物を捨てて `pull` したい場合:

```bash
git restore AromaTripNippon/main/target
git restore AromaTripNippon/main/data/aromatripnippon.mv.db
git pull
cd AromaTripNippon/main
./mvnw spring-boot:run
```

---

## 17. 作業時の注意

- `target/` 配下は通常、ビルド生成物です。
- `.class` ファイルは基本的にソースではなくコンパイル結果です。
- `.mv.db` はH2データベースファイルで、ローカル実行により更新されることがあります。
- 大事なソース変更がある場合は、`git reset --hard` を使う前に必ず確認してください。
- Codespaceの削除は最後の手段です。RebuildやFull Rebuildとは別物です。

---

## 18. 今回の流れ

今回の作業では、以下の流れで進めました。

1. `git pull` を実行
2. ローカル変更により一度 `pull` が中断
3. CodespacesのRebuild確認が表示
4. Rebuild中に画面が止まったように見えた
5. ブラウザリロードでCodespacesに再接続
6. ターミナルが入力可能な状態に戻った
7. `git pull` が成功
8. `AromaTripNippon/main` に移動
9. `./mvnw spring-boot:run` でアプリ起動
10. アプリ起動を確認

---

## 19. 困ったときに最初に見るコマンド

迷ったら、まず以下を確認します。

```bash
pwd
git status
ls
```

アプリフォルダにいるか確認するには:

```bash
pwd
```

`pom.xml` があるか確認するには:

```bash
ls
```

アプリ起動は、`AromaTripNippon/main` にいる状態で実行します。

```bash
./mvnw spring-boot:run
```
