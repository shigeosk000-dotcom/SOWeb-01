# ファイル名例: set-ollama-timeout.ps1
#
#使い方
#上記コードを set-ollama-timeout.ps1 として保存
#PowerShell で保存先に移動
#
#実行（例: タイムアウトを 300 秒に設定）
#Powershell
#.\set-ollama-timeout.ps1 -TimeoutSeconds 300
#そのまま Ollama が再起動し、新しいタイムアウト設定が反映されます

param(
    [int]$TimeoutSeconds = 120
)

# 設定ファイルのパス
$ConfigPath = "$env:C:\Users\sosak\.ollama\config.yaml"

# 設定ファイルが存在しない場合は作成
if (-not (Test-Path $ConfigPath)) {
    New-Item -ItemType File -Path $ConfigPath -Force | Out-Null
}

# YAML の内容を更新（既存の server.timeout を置き換え or 新規追加）
$ConfigContent = @"
server:
  timeout: $TimeoutSeconds
"@

Set-Content -Path $ConfigPath -Value $ConfigContent -Encoding UTF8

Write-Host "✅ Ollama のタイムアウトを $TimeoutSeconds 秒に設定しました。"

# Ollama プロセスを終了
Write-Host "⏹ Ollama を停止中..."
taskkill /IM ollama.exe /F 2>$null

# 再起動
Write-Host "🚀 Ollama を再起動中..."
Start-Process "ollama" "serve"

Write-Host "✅ 設定完了！"
