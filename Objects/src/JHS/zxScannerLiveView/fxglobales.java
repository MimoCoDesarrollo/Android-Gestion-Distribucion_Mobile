package JHS.zxScannerLiveView;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class fxglobales {
private static fxglobales mostCurrent = new fxglobales();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public b4a.example.dateutils _dateutils = null;
public JHS.zxScannerLiveView.main _main = null;
public JHS.zxScannerLiveView.mod_conexion _mod_conexion = null;
public JHS.zxScannerLiveView.datosglobales _datosglobales = null;
public JHS.zxScannerLiveView.starter _starter = null;
public JHS.zxScannerLiveView.planillaslistado _planillaslistado = null;
public JHS.zxScannerLiveView.planilladetalle _planilladetalle = null;
public JHS.zxScannerLiveView.editbox _editbox = null;
public JHS.zxScannerLiveView.b4xcollections _b4xcollections = null;
public JHS.zxScannerLiveView.xuiviewsutils _xuiviewsutils = null;
public JHS.zxScannerLiveView.httputils2service _httputils2service = null;
public static int  _cint(anywheresoftware.b4a.BA _ba,Object _o) throws Exception{
 //BA.debugLineNum = 36;BA.debugLine="Sub CInt(o As Object) As Int";
 //BA.debugLineNum = 37;BA.debugLine="Return Floor(o)";
if (true) return (int) (anywheresoftware.b4a.keywords.Common.Floor((double)(BA.ObjectToNumber(_o))));
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return 0;
}
public static long  _clng(anywheresoftware.b4a.BA _ba,Object _o) throws Exception{
 //BA.debugLineNum = 40;BA.debugLine="Sub CLng(o As Object) As Long";
 //BA.debugLineNum = 41;BA.debugLine="Return Floor(o)";
if (true) return (long) (anywheresoftware.b4a.keywords.Common.Floor((double)(BA.ObjectToNumber(_o))));
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return 0L;
}
public static String  _cstr(anywheresoftware.b4a.BA _ba,Object _o) throws Exception{
 //BA.debugLineNum = 32;BA.debugLine="Sub CStr(o As Object) As String";
 //BA.debugLineNum = 33;BA.debugLine="Return \"\" & o";
if (true) return ""+BA.ObjectToString(_o);
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _desactivarmodoestricto(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4j.object.JavaObject _jo = null;
anywheresoftware.b4j.object.JavaObject _policy = null;
anywheresoftware.b4j.object.JavaObject _sm = null;
 //BA.debugLineNum = 52;BA.debugLine="Public Sub DesactivarModoEstricto";
 //BA.debugLineNum = 53;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 54;BA.debugLine="jo.InitializeStatic(\"android.os.Build.VERSION\")";
_jo.InitializeStatic("android.os.Build.VERSION");
 //BA.debugLineNum = 55;BA.debugLine="If jo.GetField(\"SDK_INT\") > 9 Then";
if ((double)(BA.ObjectToNumber(_jo.GetField("SDK_INT")))>9) { 
 //BA.debugLineNum = 56;BA.debugLine="Dim policy As JavaObject";
_policy = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 57;BA.debugLine="policy = policy.InitializeNewInstance(\"android.o";
_policy = _policy.InitializeNewInstance("android.os.StrictMode.ThreadPolicy.Builder",(Object[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 58;BA.debugLine="policy = policy.RunMethodJO(\"permitAll\", Null).R";
_policy = _policy.RunMethodJO("permitAll",(Object[])(anywheresoftware.b4a.keywords.Common.Null)).RunMethodJO("build",(Object[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 59;BA.debugLine="Dim sm As JavaObject";
_sm = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 60;BA.debugLine="sm.InitializeStatic(\"android.os.StrictMode\").Run";
_sm.InitializeStatic("android.os.StrictMode").RunMethod("setThreadPolicy",new Object[]{(Object)(_policy.getObject())});
 };
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
public static String  _left(anywheresoftware.b4a.BA _ba,String _text,int _length) throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Sub Left(Text As String, Length As Int)As String";
 //BA.debugLineNum = 10;BA.debugLine="If Length>Text.Length Then Length=Text.Length";
if (_length>_text.length()) { 
_length = _text.length();};
 //BA.debugLineNum = 11;BA.debugLine="Return Text.SubString2(0, Length)";
if (true) return _text.substring((int) (0),_length);
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _mid(anywheresoftware.b4a.BA _ba,String _text,int _start,int _length) throws Exception{
 //BA.debugLineNum = 19;BA.debugLine="Sub Mid(Text As String, Start As Int, Length As In";
 //BA.debugLineNum = 20;BA.debugLine="Return Text.SubString2(Start-1,Start+Length-1)";
if (true) return _text.substring((int) (_start-1),(int) (_start+_length-1));
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
public static String  _right(anywheresoftware.b4a.BA _ba,String _text,int _length) throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Right(Text As String, Length As Int) As String";
 //BA.debugLineNum = 15;BA.debugLine="If Length>Text.Length Then Length=Text.Length";
if (_length>_text.length()) { 
_length = _text.length();};
 //BA.debugLineNum = 16;BA.debugLine="Return Text.SubString(Text.Length-Length)";
if (true) return _text.substring((int) (_text.length()-_length));
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
public static String[]  _split(anywheresoftware.b4a.BA _ba,String _text,String _delimiter) throws Exception{
 //BA.debugLineNum = 23;BA.debugLine="Sub Split(Text As String, Delimiter As String) As";
 //BA.debugLineNum = 24;BA.debugLine="Return Regex.Split(Delimiter,Text)";
if (true) return anywheresoftware.b4a.keywords.Common.Regex.Split(_delimiter,_text);
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return null;
}
}
