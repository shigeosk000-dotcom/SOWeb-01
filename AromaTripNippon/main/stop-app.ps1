$AppDir = "c:\academia\src\soweb-01\AromaTripNippon\main"
$LogFile = "$AppDir\app-run.log"

# ログファイルがロックされていてもエラーにならないように try-catch で囲む
function Write-Log {
    param([string]$Message)
    try {
        Add-Content -Path $LogFile -Value "$Message" -ErrorAction SilentlyContinue
    } catch {
        # ロックされている場合は標準出力にのみ出す
        Write-Host "Log locked: $Message"
    }
}

Write-Log "--- Stopping and Cleaning Up AromaTripNippon at $(Get-Date) ---"

# 1. ポート8080を使用しているプロセスを停止
try {
    $PortConnections = Get-NetTCPConnection -LocalPort 8080 -State Listening -ErrorAction SilentlyContinue
    if ($PortConnections) {
        foreach ($conn in $PortConnections) {
            $PidToKill = $conn.OwningProcess
            Write-Log "Stopping process $PidToKill using port 8080..."
            Stop-Process -Id $PidToKill -Force -ErrorAction SilentlyContinue
        }
        # プロセス停止後、ファイルロックが解除されるまで少し待機
        Start-Sleep -Seconds 2
    }
} catch {
    Write-Log "Error during port cleanup: $_"
}

# 2. Java関連のプロセスでこのディレクトリに関連するものを停止 (ゾンビ対策)
try {
    $JavaProcesses = Get-CimInstance Win32_Process -Filter "Name = 'java.exe' OR Name = 'cmd.exe' OR Name = 'powershell.exe'"
    foreach ($proc in $JavaProcesses) {
        if ($proc.CommandLine -like "*aromatripnippon*" -or $proc.CommandLine -like "*mvnw*") {
            # 自分自身を殺さないようにチェック
            if ($proc.ProcessId -ne $PID) {
                Write-Log "Cleaning up related process: $($proc.ProcessId) ($($proc.Name))"
                Stop-Process -Id $proc.ProcessId -Force -ErrorAction SilentlyContinue
            }
        }
    }
    Start-Sleep -Seconds 1
} catch {
    Write-Log "Error during process cleanup: $_"
}

Write-Log "Cleanup completed."
