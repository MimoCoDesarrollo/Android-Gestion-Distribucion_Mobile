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

End Sub

Sub Left(Text As String, Length As Int)As String
	If Length>Text.Length Then Length=Text.Length
	Return Text.SubString2(0, Length)
End Sub

Sub Right(Text As String, Length As Int) As String
	If Length>Text.Length Then Length=Text.Length
	Return Text.SubString(Text.Length-Length)
End Sub

Sub Mid(Text As String, Start As Int, Length As Int) As String
	Return Text.SubString2(Start-1,Start+Length-1)
End Sub

Sub Split(Text As String, Delimiter As String) As String()
	Return Regex.Split(Delimiter,Text)
End Sub






Sub CStr(o As Object) As String
	Return "" & o
End Sub

Sub CInt(o As Object) As Int
	Return Floor(o)
End Sub

Sub CLng(o As Object) As Long
	Return Floor(o)
End Sub









Public Sub DesactivarModoEstricto
	Dim jo As JavaObject
	jo.InitializeStatic("android.os.Build.VERSION")
	If jo.GetField("SDK_INT") > 9 Then
		Dim policy As JavaObject
		policy = policy.InitializeNewInstance("android.os.StrictMode.ThreadPolicy.Builder", Null)
		policy = policy.RunMethodJO("permitAll", Null).RunMethodJO("build", Null)
		Dim sm As JavaObject
		sm.InitializeStatic("android.os.StrictMode").RunMethod("setThreadPolicy", Array(policy))
	End If
End Sub

