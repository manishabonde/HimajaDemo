::author:Himaja
@echo off
::Declaring value
set /a "x = 1"
::Creating rooms
:while1
    if %x% leq 2 (
	 echo 1
    echo room%x%
    echo 12
    echo Marist%x%
    echo HC%x%
    set /a "x = x + 1"		    
    echo room%x% added!
		goto :while1
    )
endlocal

echo 4