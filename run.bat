move D:\MyFit\src\myfit
xcopy /E /I  D:\workspace2\Test\src\myfit D:\MyFit\src\myfit

cd D:\MyFit

javac -cp D:\MyFit\src -classpath lib\fitnesse.jar  src\myfit\AdderTest.java -d .\bin
@REM java -cp D:\MyFit\bin fit.Adder

cd bin
jar cvf D:\MyFit\myfit.jar myfit/*

cd ../



...move D:\MyFit\src\myfit
move D:\MyFit\src\com
xcopy /E /I  D:\workspace2\Test\src\myfit D:\MyFit\src\myfit
xcopy /E /I  D:\workspace2\Test\src\com D:\MyFit\src\com
