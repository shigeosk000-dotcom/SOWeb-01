# Spring Boot アプリの再起動手順
## 躓きポイントと対処方法つき

対象は、Maven で Spring Boot アプリを起動するケースです。

```powershell
mvn spring-boot:run
```

または

```powershell
mvn clean spring-boot:run
```

---

## 1. まず現在のアプリを停止する

Spring Boot アプリを再起動する前に、**すでに起動しているアプリを停止**します。

通常、`mvn spring-boot:run` を実行している PowerShell / ターミナル上で、以下を押します。

```text
Ctrl + C
```

停止確認が出た場合は、`Y` を入力します。

```text
バッチ ジョブを終了しますか (Y/N)? Y
```

---

## 2. 8080番ポートが空いているか確認する

Spring Boot の起動時に以下のエラーが出ることがあります。

```text
Web server failed to start. Port 8080 was already in use.
```

これは、**8080番ポートがすでに別プロセスに使われている**状態です。

PowerShell で確認します。

```powershell
netstat -ano | findstr :8080
```

例：

```text
TCP    0.0.0.0:8080    0.0.0.0:0    LISTENING    12345
```

一番右の数字 `12345` が PID です。

---

## 3. 8080番ポートを使っているプロセスを停止する

PID が分かったら、以下で停止します。

```powershell
taskkill /PID 12345 /F
```

実際には `12345` の部分を、`netstat` で表示された PID に置き換えます。

```powershell
taskkill /PID <PID> /F
```

---

## 4. 必要に応じて clean を実行する

通常の再起動だけなら、必ずしも `clean` は必要ありません。

```powershell
mvn spring-boot:run
```

ただし、以下のような場合は `clean` を付けた方が安全です。

- `data.sql` を修正した
- `application.properties` を修正した
- Java ファイルを大きく変更した
- `target` 配下の古いファイルが残っていそう
- 前回のビルド結果がおかしい

その場合は以下を実行します。

```powershell
mvn clean spring-boot:run
```

`mvn clean` は `target` フォルダを削除して再ビルドします。  
ただし、**起動中の別プロセスやポート使用状態までは解消しません**。

---

## 5. アプリを再起動する

通常は以下で起動します。

```powershell
cd C:\academia\src\soweb-01\AromaTripNippon\main
mvn spring-boot:run
```

クリーンビルドも行う場合：

```powershell
cd C:\academia\src\soweb-01\AromaTripNippon\main
mvn clean spring-boot:run
```

---

# よくある躓きポイントと対処方法

## 躓きポイント1：`pom.xml` が無い場所で Maven を実行している

### エラー例

```text
The goal you specified requires a project to execute but there is no POM in this directory
```

### 原因

`mvn` コマンドを実行しているディレクトリに `pom.xml` がありません。

Maven は基本的に、**`pom.xml` があるディレクトリ**で実行する必要があります。

### 対処方法

`pom.xml` がある場所に移動してから実行します。

```powershell
cd C:\academia\src\soweb-01\AromaTripNippon\main
dir pom.xml
mvn spring-boot:run
```

---

## 躓きポイント2：`data.sql` の先頭に BOM がある

### エラー例

```text
Syntax error in SQL statement "[*]\feffINSERT INTO admin_users ...
```

### 原因

`data.sql` の先頭に **BOM（\uFEFF）** という見えない文字が入っています。

そのため H2 が、

```sql
INSERT INTO admin_users
```

ではなく、

```sql
﻿INSERT INTO admin_users
```

のように解釈してしまい、SQL 文法エラーになります。

### 対処方法

`src/main/resources/data.sql` を **UTF-8 BOMなし** で保存します。

VS Code の場合：

1. `data.sql` を開く
2. 右下の文字コードをクリック
3. `Save with Encoding` を選択
4. `UTF-8` を選択  
   ※ `UTF-8 with BOM` ではない方

PowerShell で直す場合：

```powershell
$file = "src\main\resources\data.sql"
$content = Get-Content $file -Raw
[System.IO.File]::WriteAllText((Resolve-Path $file), $content, [System.Text.UTF8Encoding]::new($false))
```

その後：

```powershell
mvn clean spring-boot:run
```

---

## 躓きポイント3：8080番ポートがすでに使われている

### エラー例

```text
Web server failed to start. Port 8080 was already in use.
```

### 原因

前回起動した Spring Boot アプリ、または別のサーバーが `8080` 番ポートを使っています。

### 対処方法A：8080を使っているプロセスを停止する

```powershell
netstat -ano | findstr :8080
```

表示された PID を指定して停止します。

```powershell
taskkill /PID <PID> /F
```

その後、再起動します。

```powershell
mvn spring-boot:run
```

### 対処方法B：別ポートで起動する

8080 を使っているプロセスを止めたくない場合は、別ポートで起動します。

```powershell
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

ブラウザでは以下にアクセスします。

```text
http://localhost:8081
```

---

## 躓きポイント4：`clean` したのに同じエラーが出る

### 原因

`mvn clean` は `target` フォルダを削除して再ビルドしますが、**起動中の別プロセスやポート使用状態までは解消しません**。

そのため、8080 番ポートが使用中の場合、`clean` しても同じように起動失敗します。

### 対処方法

`clean` の前後に、ポート確認を入れます。

```powershell
netstat -ano | findstr :8080
```

8080 が使われていれば停止します。

```powershell
taskkill /PID <PID> /F
```

その後に起動します。

```powershell
mvn clean spring-boot:run
```

---

# おすすめの再起動手順

普段はこの流れで大丈夫です。

```powershell
cd C:\academia\src\soweb-01\AromaTripNippon\main
netstat -ano | findstr :8080
taskkill /PID <PID> /F
mvn spring-boot:run
```

ただし、`PID` は実際に表示された数字に置き換えてください。

---

# clean ありの安全な再起動手順

`data.sql` や設定ファイルを変更した後は、こちらがおすすめです。

```powershell
cd C:\academia\src\soweb-01\AromaTripNippon\main
netstat -ano | findstr :8080
taskkill /PID <PID> /F
mvn clean spring-boot:run
```

---

# 別ポートで起動する手順

8080 を止めずに起動したい場合はこちらです。

```powershell
cd C:\academia\src\soweb-01\AromaTripNippon\main
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

アクセス先：

```text
http://localhost:8081
```

---

# 今回の状況まとめ

今回の流れでは、最初に `data.sql` の BOM が原因で SQL エラーが発生していました。  
その後、BOM 問題は解消され、`mvn clean`、コンパイル、H2 接続、JPA 初期化までは進んでいます。

現在の主な問題はこれです。

```text
Port 8080 was already in use.
```

そのため、次にやるべきことは **8080番ポートを使っているプロセスを停止すること**です。
