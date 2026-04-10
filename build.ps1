$srcFiles = Get-ChildItem -Path "src" -Recurse -Filter "*.java"
$fileList = $srcFiles | ForEach-Object { """$($_.FullName)""" }
$fileListStr = $fileList -join " "
Invoke-Expression "javac -d bin $fileListStr"
