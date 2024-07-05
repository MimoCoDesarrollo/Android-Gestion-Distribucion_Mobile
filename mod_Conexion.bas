B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=StaticCode
Version=11
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim sql1 As JdbcSQL
	
'	Dim sIp As String = "//192.168.1.20"
'	Dim sPort As String = ":1433"
'	Dim sDatabase As String = "SIGMA"
'	Dim sUser As String = "sa"
'	Dim sPassword As String = "1046mimo"

	Dim sIp As String 
	Dim sPort As String 
	Dim sDatabase As String
	Dim sUser As String 
	Dim sPassword As String 

	Dim Sql As SQL

End Sub

Public Sub Conexion()
	Try
'		If DatosGlobales.EsProduccion = True Then
'			sIp = "//192.168.1.20"
'			sPort = ":1433"
'			sDatabase= "SIGMA"
'			sUser = "sa"
'			sPassword = "1046mimo"
'		Else
'			sIp = "//192.168.1.32"
'			sPort = ":1433"
'			sDatabase= "SIGMAMIRROR"
'			sUser = "sa"
'			sPassword = "1046mimo"
'		End If

		
	
		sql1.InitializeAsync("sql1", "net.sourceforge.jtds.jdbc.Driver", "jdbc:jtds:sqlserver:" & sIp & sPort & ";databaseName=" & sDatabase & ";user="& sUser & ";password=" & sPassword & ";appname=SKMJL;wsid=TEST;loginTimeout=10", "", "")
		
		'ProgressDialogShow("Intentando conectar")
		
	Catch
		Log("Error de conexion ")
		Log(LastException.Message)
		Return
	End Try
	
End Sub



Public Sub ConexionSql()
	Try
	
		Sql.Initialize("", "", 1)
		
	
		
		'InitializeAsync("sql1", "net.sourceforge.jtds.jdbc.Driver", "jdbc:jtds:sqlserver:" & sIp & sPort & ";databaseName=" & sDatabase & ";user="& sUser & ";password=" & sPassword & ";appname=SKMJL;wsid=TEST;loginTimeout=10", "", "")
		
		'ProgressDialogShow("Intentando conectar")
		
	Catch
		Log("Error de conexion ")
		Log(LastException.Message)
		Return
	End Try
	
End Sub




