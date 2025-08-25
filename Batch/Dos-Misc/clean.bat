@Echo off


@rem --------------------------------------------------------------------------------
@rem ---
@rem ---   void main()
@rem ---
@rem ---   Deletes all directories within this project which contain temporary data
@rem ---   (e.g. compiler output, log files, etc.).
@rem ---

:main

	call:defineMacros
	call:resetErrorlevel
	call:setUp

	echo.
	echo project dir .... %projectDir%
	echo.

	for /L %%i in (1, 1, %directories.length%) do (

		setlocal EnableDelayedExpansion

			call:deleteDirectory !directories[%%i]!

		endlocal
	)

	for /L %%i in (1, 1, %files.length%) do (

		setlocal EnableDelayedExpansion

			call:deleteFile !files[%%i]!

		endlocal
	)

	call:tearDown

	pause

%return%


@rem ================================================================================
@rem ===
@rem ===   Internal Subroutines
@rem ===

@rem --------------------------------------------------------------------------------
@rem ---
@rem ---   void defineMacros()
@rem ---
@rem ---   The subroutine defines required macros.
@rem ---

:defineMacros

	set "ifError=set foundErr=1&(if errorlevel 0 if not errorlevel 1 set foundErr=)&if defined foundErr"
	
	set "cprintln=echo"
	set "cprint=echo|set /p="
	
	set "return=exit /b"

%return%


@rem --------------------------------------------------------------------------------
@rem ---
@rem ---   void resetErrorlevel()
@rem ---
@rem ---   The subroutine resets the errorlevel.
@rem ---

:resetErrorlevel

%return% 0


@rem --------------------------------------------------------------------------------
@rem ---
@rem ---   void setUp()
@rem ---
@rem ---   Declares and initializes variables and constants.
@rem ---

:setUp

	set rootDir=%~dp0..\..\
	set projectDir=%rootDir%Sources\

	set directories.length=7

	set directories[1]=%rootDir%Batch\classes\
	set directories[2]=%projectDir%.data\
	set directories[3]=%projectDir%Chapter_1.2\classes\
	set directories[4]=%projectDir%Encog\classes\
	set directories[5]=%projectDir%Neural\classes\
	set directories[6]=%projectDir%Neural2\classes\
	set directories[7]=%projectDir%Neural3\classes\

	set files.length=3

	set files[1]=%projectDir%Neural\myprogram.hprof
	set files[2]=%projectDir%Neural\myprogram.hprof
	set files[3]=%projectDir%Neural\myprogram.hprof

%return%


@rem --------------------------------------------------------------------------------
@rem ---
@rem ---   void tearDown()
@rem ---
@rem ---   Declared variables and constants are cleaned up.
@rem ---

:tearDown

	for /L %%i in (1, 1, %directories.length%) do (

		set directories[%%i]=
	)

	set directories.length=


	for /L %%i in (1, 1, %files.length%) do (

		set files[%%i]=
	)

	set files.length=


	set projectDir=
	set rootDir=
	
%return%


@rem --------------------------------------------------------------------------------
@rem ---
@rem ---   void deleteDirectory(String _path)
@rem ---
@rem ---   Deletes the specified directory and all its content.
@rem ---
@rem ---
@rem ---   @param _path
@rem ---          the specified path represents a directory
@rem ---

:deleteDirectory

	set "_path=%1"
	if '%_path%'=='' (

		echo No directory path has been specified! >&2
		%return% 2
	)
	set "_path=%_path:"=%"


	if exist %_path% (

		echo deleting %_path%
		rmdir /S /Q %_path%

	) else (

		echo %_path% doesn't exist.
	)


	set _path=

%return%


@rem --------------------------------------------------------------------------------
@rem ---
@rem ---   void deleteFile(String _path)
@rem ---
@rem ---   Deletes the specified file.
@rem ---
@rem ---
@rem ---   @param _path
@rem ---          the specified path represents a file
@rem ---

:deleteFile

	set "_path=%1"
	if '%_path%'=='' (

		echo No file path has been specified! >&2
		%return% 2
	)
	set "_path=%_path:"=%"


	if exist %_path% (

		echo deleting %_path%
		del /S /Q %_path%

	) else (

		echo %_path% doesn't exist.
	)


	set _path=

%return%
