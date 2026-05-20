# Cursor Free 節約構成ガイド（Windows / Spring Boot + Thymeleaf + H2）

## 目的

このドキュメントは、CODEXのトークン不足時に一時避難先として利用できるように、  
Cursor Free を Windows 環境へ低コスト・省トークンで構築する手順をまとめたものです。

対象技術：

- Spring Boot
- Thymeleaf
- H2 Database
- Maven
- Java 17 / 21

---

# 1. Cursor のインストール

## 手順

1. Cursor公式サイトからWindows版をダウンロード
2. インストーラを実行
3. Cursorへログイン
4. 初回起動時に VS Code 設定・拡張機能をインポート

---

# 2. 推奨拡張機能

## 必須

| 拡張機能 | 用途 |
|---|---|
| Extension Pack for Java | Java開発一式 |
| Spring Boot Extension Pack | Spring Boot支援 |
| Thymeleaf | HTMLテンプレート補完 |
| Maven for Java | Maven操作 |
| Lombok Annotations Support | Lombok利用時 |

---

# 3. JDK確認

PowerShell：

```powershell
java -version
javac -version
```

推奨：

- JDK 17
- JDK 21

---

# 4. プロジェクトを開く

```powershell
cd C:\path\to\project
cursor .
```

または：

```text
File > Open Folder
```

---

# 5. 最重要： .cursorignore を作成

プロジェクト直下：

```text
.cursorignore
```

内容：

```gitignore
# build outputs
target/
build/
out/
bin/

# IDE / OS
.idea/
.vscode/
*.iml
.DS_Store
Thumbs.db

# logs
logs/
*.log

# database / runtime
*.db
*.mv.db
*.trace.db
*.lock.db
data/
h2-data/

# generated files
generated/
src/main/generated/
src/test/generated/

# frontend deps
node_modules/
dist/
coverage/

# large/static assets
*.zip
*.jar
*.war
*.class
*.png
*.jpg
*.jpeg
*.gif
*.svg
*.pdf
*.xlsx
*.csv

# secrets
.env
.env.*
application-local.properties
application-secret.properties
application-prod.properties
```

---

# 6. Spring Boot向け読込範囲制限

AIへの指示例：

```text
まず以下だけ見てください。

- src/main/java
- src/main/resources/templates
- src/main/resources/application.properties
- pom.xml

target/ や H2 DBファイルは見ないでください。
```

---

# 7. Cursor Rules を作成

作成：

```text
.cursor/rules/spring-boot-thymeleaf-h2.md
```

内容：

```markdown
# Project Rules

このプロジェクトは Spring Boot + Thymeleaf + H2 Database のWebアプリです。

## 技術構成
- Java
- Spring Boot
- Thymeleaf
- H2 Database
- Maven
- MVC構成

## コーディング方針
- 既存構成を尊重
- 変更は最小差分
- URLやフォーム名を不用意に変更しない
- 既存HTML構造を維持
- H2固有挙動へ依存しすぎない
- DB設定を勝手に変更しない
- 修正前に影響範囲を説明する

## 無料枠節約ルール
- まず原因分析のみ
- 必要ファイルだけ読む
- 全体リファクタ禁止
- 大量生成禁止
- diff中心で回答
```

---

# 8. Cursor側設定

## モデル

```text
Auto
```

推奨。

---

## Agent利用

必要時のみ使用。

普段：

```text
Chat
```

複数ファイル修正時：

```text
Agent
```

---

## Plan Mode

まず：

```text
Plan Mode
```

で調査・計画だけ作成。

---

# 9. 無料枠を節約する使い方

## 悪い例

```text
このシステム全体を調査して全部修正して
```

---

## 良い例

```text
以下3ファイルだけ見て調査してください。

- LoginController.java
- login.html
- UserService.java

まだ修正しないでください。
```

---

# 10. Spring Boot向け節約プロンプト

## 不具合調査

```text
まず原因分析だけしてください。
修正コードはまだ出さないでください。

対象:
- src/main/java/...
- src/main/resources/templates/...

観点:
- Controllerからmodelへ値が渡っているか
- Thymeleafのth:if / th:text / th:object
- form name属性とDTO/Entity整合性
```

---

## H2調査

```text
H2関連だけ調査してください。

読まない:
- target/
- *.mv.db
- *.trace.db

確認:
- application.properties
- schema.sql
- data.sql
- Entity
- Repository
```

---

## 最小修正

```text
最小差分で修正してください。
変更ファイルは最大3つまで。
unified diff形式で出してください。
```

---

# 11. H2推奨設定

```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

注意：

- 既存設定を不用意に変更しない
- DBファイルは .cursorignore に追加

---

# 12. 推奨運用

通常：

```text
VSCode + Codex
```

トークン切れ：

```text
Cursor Free
```

さらに不足：

```text
Roo Code + Gemini API
```

---

# 最終推奨構成

```text
Cursor Free
+ VSCode設定インポート
+ Java Extension Pack
+ Spring Boot Extension Pack
+ .cursorignore
+ .cursor/rules
+ Autoモデル
+ Plan Mode
+ 最小diff修正
```

この構成で、Spring Boot + Thymeleaf + H2 の小〜中規模改修をかなり低コストで運用可能です。
