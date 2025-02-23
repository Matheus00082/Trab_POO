@echo off
echo Procurando e removendo arquivos .class...
for /r %%f in (*.class) do del "%%f"
echo Arquivos .class removidos com sucesso!
pause