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

# 1. 停止・クリーンアップスクリプトを実行
Write-Log "--- Restarting AromaTripNippon at $(Get-Date) ---"
& "$AppDir\stop-app.ps1"
Start-Sleep -Seconds 2

# 2. アプリケーションの起動
Set-Location -Path $AppDir
Start-Process -FilePath "powershell" -ArgumentList "-NoProfile", "-Command", "./mvnw.cmd spring-boot:run > app-run.log 2>&1"

Write-Log "Restart process initiated."
