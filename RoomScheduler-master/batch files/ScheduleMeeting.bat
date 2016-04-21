::author: Himaja
@echo off
::calling roomcreation bat
call RoomCreation.bat
::Declaring variable
 set /a "x = 1"
::Scheduling Meetings
:while1
    if %x% leq 3 (
	echo 4
    echo room%x%	
	echo 2016-03-%x%
	echo 12:%x%
	echo 2016-04-%x%
	echo 01:00
	echo subject%x%
	set /a "x = x + 1"	
	goto :while1
    )
::Printing scheduled rooms
set /a "x = 1"
:while2
    if %x% leq 3 (
	echo 3
	echo room%x%
	
	set /a "x = x + 1"	
		goto :while2
    )
	endlocal
	
	



