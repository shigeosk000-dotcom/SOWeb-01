# AromaTripNippon Renderデプロイ対応・検証レポート

作成日: 2026-05-27  
対象プロジェクト: `AromaTripNippon/main`  
前提: Spring Boot（Maven）を Render（Docker デプロイ）で安定稼働させる

---

## 1. 変更したファイル一覧

- 変更なし（既存設定が要件を満たしていたため）

---

## 2. Dockerfile（最終内容）

```dockerfile
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw
RUN ./mvnw -B -DskipTests dependency:go-offline

COPY src/ src/
RUN ./mvnw -B -DskipTests clean package

FROM eclipse-temurin:17-jre AS runtime

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 10000

ENTRYPOINT ["java", "-Dserver.port=10000", "-jar", "app.jar"]
```

---

## 3. .dockerignore（最終内容）

```dockerignore
.git
.idea
.vscode
target
build
out

.mvn/wrapper/maven-wrapper.jar
*.log
```

---

## 4. BOM除去実施結果

- 対象: `src/main/java/**/*.java`
- 結果: BOM検出なし
- BOM除去を行ったファイル: なし

---

## 5. ルーティング確認（`/` の404対策）

- `GET /` は既に `PublicController#index` で実装済み
- 404 は発生しない構成のため、`/management` への追加リダイレクト修正は不要

---

## 6. 検証結果

### 6.1 `./mvnw test`

- 結果: 成功
- サマリー:
  - `Tests run: 19`
  - `Failures: 0`
  - `Errors: 0`
  - `Skipped: 0`
  - `BUILD SUCCESS`

### 6.2 `docker build`

- ローカル検証結果: 未実施（実行環境に `docker` コマンドが存在しないため）
- エラー種別: `CommandNotFoundException`

---

## 7. Render側で確認すべきポイント

1. Root Directory を `AromaTripNippon/main` に設定する
2. デプロイ方式は `Docker` を選択する
3. コンテナ待受ポートが `10000`（`-Dserver.port=10000`）であることを確認する
4. デプロイログで `chmod +x ./mvnw` 後に Maven ビルドが正常進行することを確認する
5. デプロイ後に `/` へアクセスし、200応答でトップ画面が表示されることを確認する

