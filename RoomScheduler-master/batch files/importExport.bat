::author: Himaja
@echo off
call input.json

::Importing the data
echo 6
FOR /F "tokens=*" %%i IN (input.json) DO @ECHO %%i

:: trying schedule
echo 3
echo room4
echo 2017-11-12
echo 12:30
echo 2017-12-12
echo 12:45
echo subjectNew

::Try to export
echo 7
echo /tmp