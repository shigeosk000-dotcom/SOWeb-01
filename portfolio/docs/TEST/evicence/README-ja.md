# 09-02 Function Test エビデンス確認ガイド（2026-05-27実行分）

このフォルダは `09-02-functiontest.html` の「evicence配下Surefire参照」の実体です。  
本ガイドは **2026-05-27 実施分のみ** を対象にしています。

## 重要（旧ファイルの扱い）

- `evidence.html` は `2026-05-15` 更新の旧ファイルです。
- 5/27実行分の証跡確認には **使用しません**（前回実行の残骸）。

## 1. 5/27実行分でまず見るファイル

- `09-02-functiontest-execution-summary-20260527.txt`
  - 09-02のIT-IDとテストメソッド名の対応表
  - 「IT-010はどの自動テストで担保したか」を確認するときに使う

## 2. 元データ（監査用の一次証跡）

以下がMaven Surefireの生XMLです（すべて `2026-05-27 16:14` 台に更新）。
必要時のみ参照してください。

- `TEST-com.aromatripnippon.controller.ManagementFlowIntegrationTest.xml`
- `TEST-com.aromatripnippon.controller.PublicControllerTest.xml`
- `TEST-com.aromatripnippon.service.InventoryServiceTest.xml`
- `TEST-com.aromatripnippon.service.ReservationServiceTest.xml`
- `TEST-com.aromatripnippon.TemplateScopeTest.xml`

## 3. どのXMLを見ればよいか（IT-ID別）

`09-02-functiontest.html` で OK になっているIDの主な確認先は次の通りです。

- `IT-010`, `IT-011`, `IT-041`
  - `TEST-com.aromatripnippon.controller.PublicControllerTest.xml`
- `IT-013`, `IT-014`, `IT-016`, `IT-017`, `IT-018`, `IT-021`, `IT-022`, `IT-023`, `IT-024`, `IT-026`, `IT-028`, `IT-029`, `IT-031`, `IT-033`, `IT-036`, `IT-037`, `IT-055`, `IT-057`, `IT-058`
  - `TEST-com.aromatripnippon.controller.ManagementFlowIntegrationTest.xml`

補足:
- サービス層の補助証跡として以下も全件PASSです。
  - `TEST-com.aromatripnippon.service.InventoryServiceTest.xml`
  - `TEST-com.aromatripnippon.service.ReservationServiceTest.xml`
  - `TEST-com.aromatripnippon.TemplateScopeTest.xml`

## 4. 確認手順（最短）

1. `09-02-functiontest-execution-summary-20260527.txt` を開いて IT-ID とメソッド名の対応を確認  
2. 該当 `TEST-*.xml` を開き、`<testsuite ... failures="0" errors="0">` を確認  
3. さらに該当 `<testcase name="...">` の存在を確認
