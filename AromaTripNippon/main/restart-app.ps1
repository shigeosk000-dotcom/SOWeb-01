$AppDir = "c:\academia\src\soweb-01\AromaTripNippon\main"
$LogFile = "$AppDir\app-run.log"

function Write-Log {
    param([string]$Message)
    try {
        Add-Content -Path $LogFile -Value "$Message" -ErrorAction SilentlyContinue
    } catch {
        Write-Host "Log locked: $Message"
    }
}

# User環境変数を現在プロセスへ同期（IDE内蔵ターミナル差異対策）
$EnvKeys = @(
    "APP_BASE_URL",
    "APP_MAIL_FROM",
    "SPRING_MAIL_HOST",
    "SPRING_MAIL_PORT",
    "SPRING_MAIL_USERNAME",
    "SPRING_MAIL_PASSWORD"
)
foreach ($key in $EnvKeys) {
    $userValue = [Environment]::GetEnvironmentVariable($key, "User")
    if (-not [string]::IsNullOrWhiteSpace($userValue)) {
        [Environment]::SetEnvironmentVariable($key, $userValue, "Process")
    }
}

# 1. 停止・クリーンアップスクリプトを実行
Write-Log "--- Restarting AromaTripNippon at $(Get-Date) ---"
& "$AppDir\stop-app.ps1"
Start-Sleep -Seconds 2

# 2. アプリケーションを起動
Set-Location -Path $AppDir
Start-Process -FilePath "powershell" -ArgumentList "-NoProfile", "-Command", "./mvnw.cmd spring-boot:run > app-run.log 2>&1"

Write-Log "Restart process initiated."
