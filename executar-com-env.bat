@echo off
cd /d %~dp0

echo Carregando variáveis do .env...
for /f "usebackq tokens=1,2 delims==" %%i in (".env") do (
    set "%%i=%%j"
)

echo Compilando o projeto (ignorando testes)...
call mvnw.cmd clean package -DskipTests

echo.
echo Iniciando o sistema de gestao...
java -jar target\sistema.gestao-0.0.1-SNAPSHOT.jar
pause
