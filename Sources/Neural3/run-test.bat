@Echo Off

set currentDir=%CD%\
set rootDir=%~dp0..\..\

set libsDir=%rootDir%Libraries\
set jmulDir=%libsDir%Jmul\

set CLP=%jmulDir%jmul-2.1.2.jar
set CLP=%CLP%;%jmulDir%jmul-properties\
set CLP=%CLP%;%jmulDir%jmul-numbers-0.8.0.jar
set CLP=%CLP%;%jmulDir%jmul-numbers-properties\
set CLP=%CLP%;%currentDir%classes\

for %%a in ("%CLP:;=" "%") do (
   echo DEBUG::%%a
)

echo DEBUG::java -classpath %CLP% test.jmul.neural.WeightReadjustmentTest
@rem java -verbose -classpath %CLP% test.jmul.neural.WeightReadjustmentTest
java -classpath %CLP% test.jmul.neural.WeightReadjustmentTest
