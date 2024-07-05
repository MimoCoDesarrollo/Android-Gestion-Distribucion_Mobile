package JHS.zxScannerLiveView;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class datosglobales {
private static datosglobales mostCurrent = new datosglobales();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static int _idusuario = 0;
public static String _codusuario = "";
public static String _descusuario = "";
public static int _planillaseleccionada = 0;
public static int _idcoleccion = 0;
public static String _desccoleccion = "";
public static boolean _esproduccion = false;
public static String _nombredispositivo = "";
public b4a.example.dateutils _dateutils = null;
public JHS.zxScannerLiveView.main _main = null;
public JHS.zxScannerLiveView.mod_conexion _mod_conexion = null;
public JHS.zxScannerLiveView.fxglobales _fxglobales = null;
public JHS.zxScannerLiveView.starter _starter = null;
public JHS.zxScannerLiveView.planillaslistado _planillaslistado = null;
public JHS.zxScannerLiveView.planilladetalle _planilladetalle = null;
public JHS.zxScannerLiveView.editbox _editbox = null;
public JHS.zxScannerLiveView.b4xcollections _b4xcollections = null;
public JHS.zxScannerLiveView.xuiviewsutils _xuiviewsutils = null;
public JHS.zxScannerLiveView.httputils2service _httputils2service = null;
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Dim IdUsuario As Int";
_idusuario = 0;
 //BA.debugLineNum = 8;BA.debugLine="Dim CodUsuario As String";
_codusuario = "";
 //BA.debugLineNum = 9;BA.debugLine="Dim DescUsuario As String";
_descusuario = "";
 //BA.debugLineNum = 11;BA.debugLine="Dim PlanillaSeleccionada As Int";
_planillaseleccionada = 0;
 //BA.debugLineNum = 13;BA.debugLine="Dim IdColeccion As Int";
_idcoleccion = 0;
 //BA.debugLineNum = 14;BA.debugLine="Dim DescColeccion As String";
_desccoleccion = "";
 //BA.debugLineNum = 16;BA.debugLine="Dim EsProduccion As Boolean = True";
_esproduccion = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 18;BA.debugLine="Dim NombreDispositivo As String";
_nombredispositivo = "";
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
}
