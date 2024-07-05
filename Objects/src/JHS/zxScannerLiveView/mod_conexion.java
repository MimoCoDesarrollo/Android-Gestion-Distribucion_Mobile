package JHS.zxScannerLiveView;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class mod_conexion {
private static mod_conexion mostCurrent = new mod_conexion();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4j.objects.SQL _sql1 = null;
public static String _sip = "";
public static String _sport = "";
public static String _sdatabase = "";
public static String _suser = "";
public static String _spassword = "";
public static anywheresoftware.b4a.sql.SQL _sql = null;
public b4a.example.dateutils _dateutils = null;
public JHS.zxScannerLiveView.main _main = null;
public JHS.zxScannerLiveView.datosglobales _datosglobales = null;
public JHS.zxScannerLiveView.fxglobales _fxglobales = null;
public JHS.zxScannerLiveView.starter _starter = null;
public JHS.zxScannerLiveView.planillaslistado _planillaslistado = null;
public JHS.zxScannerLiveView.planilladetalle _planilladetalle = null;
public JHS.zxScannerLiveView.editbox _editbox = null;
public JHS.zxScannerLiveView.b4xcollections _b4xcollections = null;
public JHS.zxScannerLiveView.xuiviewsutils _xuiviewsutils = null;
public JHS.zxScannerLiveView.httputils2service _httputils2service = null;
public static String  _conexion(anywheresoftware.b4a.BA _ba) throws Exception{
 //BA.debugLineNum = 24;BA.debugLine="Public Sub Conexion()";
 //BA.debugLineNum = 25;BA.debugLine="Try";
try { //BA.debugLineNum = 42;BA.debugLine="sql1.InitializeAsync(\"sql1\", \"net.sourceforge.jt";
_sql1.InitializeAsync((_ba.processBA == null ? _ba : _ba.processBA),"sql1","net.sourceforge.jtds.jdbc.Driver","jdbc:jtds:sqlserver:"+_sip+_sport+";databaseName="+_sdatabase+";user="+_suser+";password="+_spassword+";appname=SKMJL;wsid=TEST;loginTimeout=10","","");
 } 
       catch (Exception e4) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e4); //BA.debugLineNum = 47;BA.debugLine="Log(\"Error de conexion \")";
anywheresoftware.b4a.keywords.Common.LogImpl("51048599","Error de conexion ",0);
 //BA.debugLineNum = 48;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("51048600",anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage(),0);
 //BA.debugLineNum = 49;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
return "";
}
public static String  _conexionsql(anywheresoftware.b4a.BA _ba) throws Exception{
 //BA.debugLineNum = 56;BA.debugLine="Public Sub ConexionSql()";
 //BA.debugLineNum = 57;BA.debugLine="Try";
try { //BA.debugLineNum = 59;BA.debugLine="Sql.Initialize(\"\", \"\", 1)";
_sql.Initialize("","",BA.ObjectToBoolean(1));
 } 
       catch (Exception e4) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e4); //BA.debugLineNum = 68;BA.debugLine="Log(\"Error de conexion \")";
anywheresoftware.b4a.keywords.Common.LogImpl("51114124","Error de conexion ",0);
 //BA.debugLineNum = 69;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("51114125",anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage(),0);
 //BA.debugLineNum = 70;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Dim sql1 As JdbcSQL";
_sql1 = new anywheresoftware.b4j.objects.SQL();
 //BA.debugLineNum = 14;BA.debugLine="Dim sIp As String";
_sip = "";
 //BA.debugLineNum = 15;BA.debugLine="Dim sPort As String";
_sport = "";
 //BA.debugLineNum = 16;BA.debugLine="Dim sDatabase As String";
_sdatabase = "";
 //BA.debugLineNum = 17;BA.debugLine="Dim sUser As String";
_suser = "";
 //BA.debugLineNum = 18;BA.debugLine="Dim sPassword As String";
_spassword = "";
 //BA.debugLineNum = 20;BA.debugLine="Dim Sql As SQL";
_sql = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
}
