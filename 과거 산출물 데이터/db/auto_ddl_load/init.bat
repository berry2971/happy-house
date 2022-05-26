@echo off

CALL settings.bat



echo [93mCreate database schema and tables...[0m

mysql -u %ID% -p%PW% -P %PORT% < lib/ddl.sql

echo [93mCreated database schema and tables successfully.[0m

echo/
echo ------------------------------
echo/

echo [93mImport districts...[0m

type lib\frag3.fsql > lib\temp\cur.sql
echo|set /p=districts.csv >> lib/temp/cur.sql
type lib\frag4.fsql >> lib\temp\cur.sql
mysql --local-infile=1 -u %ID% -p%PW% -P %PORT% happyhouse < lib\temp\cur.sql

echo [93mImported districts successfully.[0m

echo/
echo ------------------------------
echo/

echo [93mImport deals...[0m

dir "C:\ProgramData\MySQL\MySQL Server 8.0\Uploads" /b | findstr /r "deal.*.csv" > lib/temp/deals.ls

echo/
echo Deal csv files to load on MySQL database
echo ---------------
for /F "tokens=*" %%t in (lib\temp\deals.ls) do (
	echo %%t
)
echo ---------------
echo/

for /F "tokens=*" %%t in (lib\temp\deals.ls) do (
	echo Load %%t to database...

	type lib\frag1.fsql > lib\temp\cur.sql
	echo|set /p=%%t >> lib/temp/cur.sql
	type lib\frag2.fsql >> lib\temp\cur.sql

	mysql --local-infile=1 -u %ID% -p%PW% -P %PORT% happyhouse < lib\temp\cur.sql
	echo Loaded successfully.
	echo/
)

echo [93mImported deals successfully.[0m


echo/
echo/
echo Completed.
pause