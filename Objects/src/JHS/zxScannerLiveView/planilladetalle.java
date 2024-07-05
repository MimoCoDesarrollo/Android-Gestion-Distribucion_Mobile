package JHS.zxScannerLiveView;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class planilladetalle extends Activity implements B4AActivity{
	public static planilladetalle mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "JHS.zxScannerLiveView", "JHS.zxScannerLiveView.planilladetalle");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (planilladetalle).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "JHS.zxScannerLiveView", "JHS.zxScannerLiveView.planilladetalle");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "JHS.zxScannerLiveView.planilladetalle", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (planilladetalle) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (planilladetalle) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return planilladetalle.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        if (!dontPause)
            BA.LogInfo("** Activity (planilladetalle) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (planilladetalle) Pause event (activity is not paused). **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        if (!dontPause) {
            processBA.setActivityPaused(true);
            mostCurrent = null;
        }

        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            planilladetalle mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (planilladetalle) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.phone.Phone.PhoneWakeState _awake = null;
public anywheresoftware.b4a.objects.LabelWrapper _planilla_detalle_label_nro_planilla = null;
public b4a.example3.customlistview _planilla_detalle_clvresultado = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _itemlblremito = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _itembtnremito = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnvolver = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelcab = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelcabobs = null;
public anywheresoftware.b4a.objects.LabelWrapper _labelcab2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblusuariodato = null;
public JHS.zxScannerLiveView.b4xtable _b4xtable1 = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public static int _paginaactual = 0;
public static int _cantfilas = 0;
public static int _cantcol = 0;
public anywheresoftware.b4a.objects.ButtonWrapper _btnplanillafinalizar = null;
public static boolean _finalizoplanilla = false;
public anywheresoftware.b4a.objects.ButtonWrapper _btnreset = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnevegacion = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scvencabezado = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelcontenido = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbcontenido = null;
public anywheresoftware.b4a.objects.WebViewWrapper _wvcontenido = null;
public b4a.example.dateutils _dateutils = null;
public JHS.zxScannerLiveView.main _main = null;
public JHS.zxScannerLiveView.mod_conexion _mod_conexion = null;
public JHS.zxScannerLiveView.datosglobales _datosglobales = null;
public JHS.zxScannerLiveView.fxglobales _fxglobales = null;
public JHS.zxScannerLiveView.starter _starter = null;
public JHS.zxScannerLiveView.planillaslistado _planillaslistado = null;
public JHS.zxScannerLiveView.editbox _editbox = null;
public JHS.zxScannerLiveView.b4xcollections _b4xcollections = null;
public JHS.zxScannerLiveView.xuiviewsutils _xuiviewsutils = null;
public JHS.zxScannerLiveView.httputils2service _httputils2service = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 72;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 73;BA.debugLine="Try";
try { //BA.debugLineNum = 76;BA.debugLine="B4XTable1.MaximumRowsPerPage = 5000";
mostCurrent._b4xtable1._maximumrowsperpage /*int*/  = (int) (5000);
 //BA.debugLineNum = 77;BA.debugLine="Activity.LoadLayout(\"planilladetalle\")";
mostCurrent._activity.LoadLayout("planilladetalle",mostCurrent.activityBA);
 //BA.debugLineNum = 79;BA.debugLine="FxGlobales.DesactivarModoEstricto";
mostCurrent._fxglobales._desactivarmodoestricto /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 81;BA.debugLine="Awake.KeepAlive(True) 'para q no se apague";
mostCurrent._awake.KeepAlive(processBA,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 83;BA.debugLine="ScvEncabezado.Panel.LoadLayout(\"ContenidoEnca";
mostCurrent._scvencabezado.getPanel().LoadLayout("ContenidoEncabezado",mostCurrent.activityBA);
 } 
       catch (Exception e8) {
			processBA.setLastException(e8); //BA.debugLineNum = 86;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 87;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("53932175",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
String _unresu = "";
String _unmensaje = "";
int _estnuevo = 0;
int _estant = 0;
anywheresoftware.b4j.objects.SQL.ResultSetWrapper _cursor = null;
String _unsp = "";
 //BA.debugLineNum = 118;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 119;BA.debugLine="Try";
try { //BA.debugLineNum = 120;BA.debugLine="Dim unResu As String = \"\"";
_unresu = "";
 //BA.debugLineNum = 121;BA.debugLine="Dim unMensaje As String = \"\"";
_unmensaje = "";
 //BA.debugLineNum = 122;BA.debugLine="Dim EstNuevo As Int";
_estnuevo = 0;
 //BA.debugLineNum = 123;BA.debugLine="Dim EstAnt As Int";
_estant = 0;
 //BA.debugLineNum = 125;BA.debugLine="If FinalizoPlanilla = False Then";
if (_finalizoplanilla==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 126;BA.debugLine="Log(\"Paso por --> Activity_Pause --> NO FINALIZ";
anywheresoftware.b4a.keywords.Common.LogImpl("54063240","Paso por --> Activity_Pause --> NO FINALIZO",0);
 //BA.debugLineNum = 127;BA.debugLine="EstAnt = 1 ' 1 - PLANILLA EN PROCESO";
_estant = (int) (1);
 //BA.debugLineNum = 128;BA.debugLine="EstNuevo= 2 ' 2 - PLANILLA EN PAUSA";
_estnuevo = (int) (2);
 }else {
 //BA.debugLineNum = 130;BA.debugLine="Log(\"Paso por --> Activity_Pause --> SI FINALIZ";
anywheresoftware.b4a.keywords.Common.LogImpl("54063244","Paso por --> Activity_Pause --> SI FINALIZO",0);
 //BA.debugLineNum = 131;BA.debugLine="EstAnt = 1 ' 1 - PLANILLA EN PROCESO";
_estant = (int) (1);
 //BA.debugLineNum = 132;BA.debugLine="EstNuevo= 3 ' 3 - PLANILLA FINALIZADA";
_estnuevo = (int) (3);
 };
 //BA.debugLineNum = 137;BA.debugLine="Dim Cursor As JdbcResultSet";
_cursor = new anywheresoftware.b4j.objects.SQL.ResultSetWrapper();
 //BA.debugLineNum = 138;BA.debugLine="Dim unSP As String = \"EXEC SP_MOBILE_GESTION_DE_";
_unsp = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_CABECERA_CAMBIAR_ESTADO "+BA.NumberToString(mostCurrent._datosglobales._planillaseleccionada /*int*/ )+", "+BA.NumberToString(mostCurrent._datosglobales._idusuario /*int*/ )+", "+BA.NumberToString(_estant)+", "+BA.NumberToString(_estnuevo);
 //BA.debugLineNum = 140;BA.debugLine="Log(unSP)";
anywheresoftware.b4a.keywords.Common.LogImpl("54063254",_unsp,0);
 //BA.debugLineNum = 146;BA.debugLine="Cursor = mod_Conexion.sql1.ExecQuery(unSP)";
_cursor = mostCurrent._mod_conexion._sql1 /*anywheresoftware.b4j.objects.SQL*/ .ExecQuery(_unsp);
 //BA.debugLineNum = 147;BA.debugLine="Do While Cursor.NextRow";
while (_cursor.NextRow()) {
 //BA.debugLineNum = 148;BA.debugLine="Dim unResu As String = Cursor.GetString(\"RESULT";
_unresu = _cursor.GetString("RESULTADO");
 //BA.debugLineNum = 149;BA.debugLine="Dim unMensaje As String = Cursor.GetString(\"MEN";
_unmensaje = _cursor.GetString("MENSAJE");
 }
;
 //BA.debugLineNum = 151;BA.debugLine="Cursor.Close";
_cursor.Close();
 //BA.debugLineNum = 155;BA.debugLine="If unResu = False Then";
if ((_unresu).equals(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.False))) { 
 //BA.debugLineNum = 156;BA.debugLine="Log(unMensaje)";
anywheresoftware.b4a.keywords.Common.LogImpl("54063270",_unmensaje,0);
 //BA.debugLineNum = 157;BA.debugLine="Msgbox(unMensaje, \"Mensaje del sistema\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_unmensaje),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 };
 } 
       catch (Exception e29) {
			processBA.setLastException(e29); //BA.debugLineNum = 162;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 163;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("54063277",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 166;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 91;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 92;BA.debugLine="Try";
try { //BA.debugLineNum = 93;BA.debugLine="LblUsuarioDato.Text = DatosGlobales.DescUsuario";
mostCurrent._lblusuariodato.setText(BA.ObjectToCharSequence(mostCurrent._datosglobales._descusuario /*String*/ ));
 //BA.debugLineNum = 94;BA.debugLine="FxGlobales.DesactivarModoEstricto";
mostCurrent._fxglobales._desactivarmodoestricto /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 95;BA.debugLine="Planilla_Detalle_Label_Nro_Planilla.Text = \" Pla";
mostCurrent._planilla_detalle_label_nro_planilla.setText(BA.ObjectToCharSequence(" Planilla N°: "+BA.NumberToString(mostCurrent._datosglobales._planillaseleccionada /*int*/ )));
 //BA.debugLineNum = 97;BA.debugLine="CargarCabecera(DatosGlobales.PlanillaSeleccionad";
_cargarcabecera(mostCurrent._datosglobales._planillaseleccionada /*int*/ );
 //BA.debugLineNum = 99;BA.debugLine="B4XTable1.Clear";
mostCurrent._b4xtable1._clear /*void*/ ();
 //BA.debugLineNum = 101;BA.debugLine="CargarDetalle(DatosGlobales.PlanillaSeleccionada";
_cargardetalle(mostCurrent._datosglobales._planillaseleccionada /*int*/ );
 //BA.debugLineNum = 103;BA.debugLine="B4XTable1_DataUpdated";
_b4xtable1_dataupdated();
 //BA.debugLineNum = 105;BA.debugLine="If DatosGlobales.EsProduccion = False Then";
if (mostCurrent._datosglobales._esproduccion /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 106;BA.debugLine="LblNevegacion.Text = \"    Navegación\" & \" - AMB";
mostCurrent._lblnevegacion.setText(BA.ObjectToCharSequence("    Navegación"+" - AMBIENTE: TEST"));
 }else {
 //BA.debugLineNum = 108;BA.debugLine="LblNevegacion.Text = \"    Navegación\"";
mostCurrent._lblnevegacion.setText(BA.ObjectToCharSequence("    Navegación"));
 };
 } 
       catch (Exception e15) {
			processBA.setLastException(e15); //BA.debugLineNum = 112;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 113;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("53997718",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return "";
}
public static String  _b4xtable1_cellclicked(String _columnid,long _rowid) throws Exception{
anywheresoftware.b4a.objects.collections.Map _row = null;
String _valorcelda = "";
 //BA.debugLineNum = 219;BA.debugLine="Private Sub B4XTable1_CellClicked(ColumnId As Str";
 //BA.debugLineNum = 220;BA.debugLine="Try";
try { //BA.debugLineNum = 221;BA.debugLine="Log(\"Evento 'B4XTable1_CellClicked' ---> Culumn";
anywheresoftware.b4a.keywords.Common.LogImpl("54259842","Evento 'B4XTable1_CellClicked' ---> Culumna: "+_columnid+" - Fila: "+BA.NumberToString(_rowid),0);
 //BA.debugLineNum = 224;BA.debugLine="Dim row  As Map = B4XTable1.GetRow(RowId)";
_row = new anywheresoftware.b4a.objects.collections.Map();
_row = mostCurrent._b4xtable1._getrow /*anywheresoftware.b4a.objects.collections.Map*/ (_rowid);
 //BA.debugLineNum = 225;BA.debugLine="Dim ValorCelda As String = row.Get(ColumnId) 'O";
_valorcelda = BA.ObjectToString(_row.Get((Object)(_columnid)));
 //BA.debugLineNum = 226;BA.debugLine="Log(\"Valor celda: \" & ValorCelda)";
anywheresoftware.b4a.keywords.Common.LogImpl("54259847","Valor celda: "+_valorcelda,0);
 //BA.debugLineNum = 228;BA.debugLine="If ValorCelda <> 0 Then";
if ((_valorcelda).equals(BA.NumberToString(0)) == false) { 
 //BA.debugLineNum = 229;BA.debugLine="PintarCelda(ColumnId,RowId, Colors.Red) 'pinto";
_pintarcelda(_columnid,_rowid,anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 } 
       catch (Exception e10) {
			processBA.setLastException(e10); //BA.debugLineNum = 232;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 233;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("54259854",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 236;BA.debugLine="End Sub";
return "";
}
public static String  _b4xtable1_cellclicked2(String _columnid,long _rowid) throws Exception{
anywheresoftware.b4a.objects.collections.Map _row = null;
String _valorcelda = "";
 //BA.debugLineNum = 239;BA.debugLine="Private Sub B4XTable1_CellClicked2(ColumnId As St";
 //BA.debugLineNum = 240;BA.debugLine="Try";
try { //BA.debugLineNum = 244;BA.debugLine="Dim row  As Map = B4XTable1.GetRow(RowId)";
_row = new anywheresoftware.b4a.objects.collections.Map();
_row = mostCurrent._b4xtable1._getrow /*anywheresoftware.b4a.objects.collections.Map*/ (_rowid);
 //BA.debugLineNum = 245;BA.debugLine="Dim ValorCelda As String = row.Get(ColumnId) 'O";
_valorcelda = BA.ObjectToString(_row.Get((Object)(_columnid)));
 //BA.debugLineNum = 247;BA.debugLine="PintarCeldaTransparente(ColumnId,RowId)";
_pintarceldatransparente(_columnid,_rowid);
 } 
       catch (Exception e6) {
			processBA.setLastException(e6); //BA.debugLineNum = 250;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 251;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("54325388",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 253;BA.debugLine="End Sub";
return "";
}
public static String  _b4xtable1_dataupdated() throws Exception{
 //BA.debugLineNum = 256;BA.debugLine="Private Sub B4XTable1_DataUpdated";
 //BA.debugLineNum = 257;BA.debugLine="Try";
try { //BA.debugLineNum = 258;BA.debugLine="If PaginaActual <> B4XTable1.CurrentPage Then";
if (_paginaactual!=mostCurrent._b4xtable1._getcurrentpage /*int*/ ()) { 
 //BA.debugLineNum = 259;BA.debugLine="PaginaActual = B4XTable1.CurrentPage";
_paginaactual = mostCurrent._b4xtable1._getcurrentpage /*int*/ ();
 //BA.debugLineNum = 260;BA.debugLine="LimpiarColorTabla 'blanquea la seleccion de co";
_limpiarcolortabla();
 //BA.debugLineNum = 261;BA.debugLine="CargarTalleGuardado";
_cargartalleguardado();
 };
 } 
       catch (Exception e8) {
			processBA.setLastException(e8); //BA.debugLineNum = 264;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 265;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("54390921",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 267;BA.debugLine="End Sub";
return "";
}
public static String  _btnplanillafinalizar_click() throws Exception{
String _unresultado = "";
anywheresoftware.b4j.objects.SQL.ResultSetWrapper _cursor = null;
String _unsp = "";
 //BA.debugLineNum = 271;BA.debugLine="Private Sub BtnPlanillaFinalizar_Click";
 //BA.debugLineNum = 272;BA.debugLine="Try";
try { //BA.debugLineNum = 274;BA.debugLine="Dim unResultado As String = \"\"";
_unresultado = "";
 //BA.debugLineNum = 276;BA.debugLine="Select Msgbox2(\"¿Está seguro que desea finalizar";
switch (BA.switchObjectToInt(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("¿Está seguro que desea finalizar la planilla?"),BA.ObjectToCharSequence("ATENCIÓN"),"SI","","NO",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE)) {
case 0: {
 //BA.debugLineNum = 281;BA.debugLine="Dim Cursor As JdbcResultSet";
_cursor = new anywheresoftware.b4j.objects.SQL.ResultSetWrapper();
 //BA.debugLineNum = 282;BA.debugLine="Dim unSP As String = \"EXEC SP_MOBILE_GESTION_D";
_unsp = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_FINALIZAR_PLANILLA_DEFINITIVO_FINAL "+BA.NumberToString(mostCurrent._datosglobales._planillaseleccionada /*int*/ )+", "+BA.NumberToString(mostCurrent._datosglobales._idusuario /*int*/ );
 //BA.debugLineNum = 283;BA.debugLine="Cursor = mod_Conexion.sql1.ExecQuery(unSP)";
_cursor = mostCurrent._mod_conexion._sql1 /*anywheresoftware.b4j.objects.SQL*/ .ExecQuery(_unsp);
 //BA.debugLineNum = 285;BA.debugLine="Do While Cursor.NextRow";
while (_cursor.NextRow()) {
 //BA.debugLineNum = 286;BA.debugLine="Log(\"Paso por este sp --> SP_MOBILE_GESTION_D";
anywheresoftware.b4a.keywords.Common.LogImpl("54456463","Paso por este sp --> SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_FINALIZAR_PLANILLA_DEFINITIVO_FINAL",0);
 }
;
 //BA.debugLineNum = 291;BA.debugLine="Cursor.Close";
_cursor.Close();
 //BA.debugLineNum = 299;BA.debugLine="Dim Cursor As JdbcResultSet";
_cursor = new anywheresoftware.b4j.objects.SQL.ResultSetWrapper();
 //BA.debugLineNum = 300;BA.debugLine="Dim unSP As String = \"EXEC SP_MOBILE_GESTION_D";
_unsp = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_FINALIZAR_MENSAJE "+BA.NumberToString(mostCurrent._datosglobales._planillaseleccionada /*int*/ );
 //BA.debugLineNum = 301;BA.debugLine="Cursor = mod_Conexion.sql1.ExecQuery(unSP)";
_cursor = mostCurrent._mod_conexion._sql1 /*anywheresoftware.b4j.objects.SQL*/ .ExecQuery(_unsp);
 //BA.debugLineNum = 303;BA.debugLine="Do While Cursor.NextRow";
while (_cursor.NextRow()) {
 //BA.debugLineNum = 304;BA.debugLine="Log(Cursor.GetString(\"RESULTADO\"))";
anywheresoftware.b4a.keywords.Common.LogImpl("54456481",_cursor.GetString("RESULTADO"),0);
 //BA.debugLineNum = 305;BA.debugLine="unResultado = \"\"";
_unresultado = "";
 //BA.debugLineNum = 306;BA.debugLine="unResultado = Cursor.GetString(\"RESULTADO\")";
_unresultado = _cursor.GetString("RESULTADO");
 }
;
 //BA.debugLineNum = 309;BA.debugLine="Cursor.Close";
_cursor.Close();
 //BA.debugLineNum = 314;BA.debugLine="If unResultado <> \"OK\" Then";
if ((_unresultado).equals("OK") == false) { 
 //BA.debugLineNum = 315;BA.debugLine="Msgbox(\"#ERROR: \" & unResultado, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+_unresultado),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 316;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 318;BA.debugLine="FinalizoPlanilla = True";
_finalizoplanilla = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 319;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 break; }
}
;
 } 
       catch (Exception e30) {
			processBA.setLastException(e30); //BA.debugLineNum = 326;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 327;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("54456504",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 330;BA.debugLine="End Sub";
return "";
}
public static String  _btnreset_click() throws Exception{
String _unarespuesta = "";
boolean _resu = false;
anywheresoftware.b4j.objects.SQL.ResultSetWrapper _cursor = null;
String _unsp = "";
 //BA.debugLineNum = 334;BA.debugLine="Private Sub BtnReset_Click";
 //BA.debugLineNum = 335;BA.debugLine="Try";
try { //BA.debugLineNum = 336;BA.debugLine="Dim unaRespuesta As String = \"\"";
_unarespuesta = "";
 //BA.debugLineNum = 337;BA.debugLine="Select Msgbox2(\"¿Está seguro que desea resetear";
switch (BA.switchObjectToInt(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("¿Está seguro que desea resetear la planilla (borrar todo lo pintado)?"),BA.ObjectToCharSequence("ATENCIÓN"),"SI","","NO",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE)) {
case 0: {
 //BA.debugLineNum = 342;BA.debugLine="Dim Resu As Boolean = False";
_resu = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 343;BA.debugLine="Dim Cursor As JdbcResultSet";
_cursor = new anywheresoftware.b4j.objects.SQL.ResultSetWrapper();
 //BA.debugLineNum = 344;BA.debugLine="Dim unSP As String = \"EXEC SP_MOBILE_GESTION_";
_unsp = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_DETALLE_RESETEAR "+BA.NumberToString(mostCurrent._datosglobales._planillaseleccionada /*int*/ )+", "+BA.NumberToString(mostCurrent._datosglobales._idusuario /*int*/ );
 //BA.debugLineNum = 346;BA.debugLine="Cursor = mod_Conexion.sql1.ExecQuery(unSP)";
_cursor = mostCurrent._mod_conexion._sql1 /*anywheresoftware.b4j.objects.SQL*/ .ExecQuery(_unsp);
 //BA.debugLineNum = 347;BA.debugLine="Do While Cursor.NextRow";
while (_cursor.NextRow()) {
 //BA.debugLineNum = 348;BA.debugLine="If (Cursor.GetInt(\"RESULTADO\") > 0) Then";
if ((_cursor.GetInt("RESULTADO")>0)) { 
 //BA.debugLineNum = 350;BA.debugLine="B4XTable1.Clear";
mostCurrent._b4xtable1._clear /*void*/ ();
 //BA.debugLineNum = 351;BA.debugLine="CargarDetalle(DatosGlobales.PlanillaSelecci";
_cargardetalle(mostCurrent._datosglobales._planillaseleccionada /*int*/ );
 };
 //BA.debugLineNum = 353;BA.debugLine="unaRespuesta = Cursor.GetString(\"MENSAJE\")";
_unarespuesta = _cursor.GetString("MENSAJE");
 }
;
 //BA.debugLineNum = 355;BA.debugLine="Cursor.Close";
_cursor.Close();
 //BA.debugLineNum = 359;BA.debugLine="Msgbox(unaRespuesta, \"Mensaje del sistema\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_unarespuesta),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 break; }
}
;
 } 
       catch (Exception e20) {
			processBA.setLastException(e20); //BA.debugLineNum = 363;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 364;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("54522014",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 366;BA.debugLine="End Sub";
return "";
}
public static String  _btnvolver_click() throws Exception{
 //BA.debugLineNum = 205;BA.debugLine="Private Sub BtnVolver_Click";
 //BA.debugLineNum = 206;BA.debugLine="Try";
try { //BA.debugLineNum = 207;BA.debugLine="Select Msgbox2(\"¿Está seguro que desea pausar l";
switch (BA.switchObjectToInt(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("¿Está seguro que desea pausar la planilla?"),BA.ObjectToCharSequence("ATENCIÓN"),"SI","","NO",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE)) {
case 0: {
 //BA.debugLineNum = 209;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 break; }
}
;
 } 
       catch (Exception e7) {
			processBA.setLastException(e7); //BA.debugLineNum = 212;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 213;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("54194312",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 215;BA.debugLine="End Sub";
return "";
}
public static String  _cargarcabecera(int _unaplanilla) throws Exception{
anywheresoftware.b4j.objects.SQL.ResultSetWrapper _cursor = null;
String _unsp = "";
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
String _unaprioridad = "";
String _uncoleccion = "";
String _unestado = "";
String _unarticulo = "";
String _uncolor = "";
String _unaobservacion = "";
String _unafechacreacion = "";
String _unafechadistri = "";
String _unusuarioconfeccion = "";
anywheresoftware.b4a.objects.LabelWrapper _lblinfo = null;
 //BA.debugLineNum = 378;BA.debugLine="Public Sub CargarCabecera(unaPlanilla As Int)";
 //BA.debugLineNum = 382;BA.debugLine="FxGlobales.DesactivarModoEstricto";
mostCurrent._fxglobales._desactivarmodoestricto /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 384;BA.debugLine="mod_Conexion.Conexion()";
mostCurrent._mod_conexion._conexion /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 388;BA.debugLine="Dim Cursor As JdbcResultSet";
_cursor = new anywheresoftware.b4j.objects.SQL.ResultSetWrapper();
 //BA.debugLineNum = 389;BA.debugLine="Dim unSP As String = \"EXEC SP_MOBILE_GESTION_DE_";
_unsp = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_CABECERA "+BA.NumberToString(_unaplanilla);
 //BA.debugLineNum = 391;BA.debugLine="Cursor = mod_Conexion.sql1.ExecQuery(unSP)";
_cursor = mostCurrent._mod_conexion._sql1 /*anywheresoftware.b4j.objects.SQL*/ .ExecQuery(_unsp);
 //BA.debugLineNum = 393;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 394;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 395;BA.debugLine="sb.Append(\"<html><body style='background-color:";
_sb.Append("<html><body style='background-color: rgb(213, 213, 249);'>");
 //BA.debugLineNum = 397;BA.debugLine="Do While Cursor.NextRow";
while (_cursor.NextRow()) {
 //BA.debugLineNum = 398;BA.debugLine="Dim unaPrioridad As String = Cursor.GetString(\"";
_unaprioridad = _cursor.GetString("PRIORIDAD");
 //BA.debugLineNum = 399;BA.debugLine="Dim unColeccion As String = Cursor.GetString(\"C";
_uncoleccion = _cursor.GetString("COLECCION");
 //BA.debugLineNum = 400;BA.debugLine="Dim unEstado As String = Cursor.GetString(\"ESTA";
_unestado = _cursor.GetString("ESTADO");
 //BA.debugLineNum = 401;BA.debugLine="Dim unArticulo As String = Cursor.GetString(\"AR";
_unarticulo = _cursor.GetString("ARTICULO");
 //BA.debugLineNum = 402;BA.debugLine="Dim unColor As String = Cursor.GetString(\"COLOR";
_uncolor = _cursor.GetString("COLOR");
 //BA.debugLineNum = 403;BA.debugLine="Dim unaObservacion As String = Cursor.GetString";
_unaobservacion = _cursor.GetString("OBSERVACION");
 //BA.debugLineNum = 404;BA.debugLine="Dim unaFechaCreacion As String = Cursor.GetStri";
_unafechacreacion = _cursor.GetString("CREACION");
 //BA.debugLineNum = 405;BA.debugLine="Dim unaFechaDistri As String = Cursor.GetString";
_unafechadistri = _cursor.GetString("A_DISTRI");
 //BA.debugLineNum = 406;BA.debugLine="Dim unUsuarioConfeccion As String = Cursor.GetS";
_unusuarioconfeccion = _cursor.GetString("CONFECCION");
 //BA.debugLineNum = 420;BA.debugLine="unaObservacion = unaObservacion.Replace(CRLF, \"";
_unaobservacion = _unaobservacion.replace(anywheresoftware.b4a.keywords.Common.CRLF,"<br/>");
 //BA.debugLineNum = 422;BA.debugLine="sb.Append(\"<b>Colección:</b> \").Append(unColecc";
_sb.Append("<b>Colección:</b> ").Append(_uncoleccion).Append("<br/>");
 //BA.debugLineNum = 423;BA.debugLine="sb.Append(\"<b>Prioridad:</b> \").Append(unaPrior";
_sb.Append("<b>Prioridad:</b> ").Append(_unaprioridad).Append("<br/>");
 //BA.debugLineNum = 424;BA.debugLine="sb.Append(\"<b>Artículo:</b> \").Append(unArticul";
_sb.Append("<b>Artículo:</b> ").Append(_unarticulo).Append("<br/>");
 //BA.debugLineNum = 425;BA.debugLine="sb.Append(\"<b>Color:</b> \").Append(unColor).App";
_sb.Append("<b>Color:</b> ").Append(_uncolor).Append("<br/>");
 //BA.debugLineNum = 426;BA.debugLine="sb.Append(\"<b>Estado:</b> \").Append(unEstado).A";
_sb.Append("<b>Estado:</b> ").Append(_unestado).Append("<br/>");
 //BA.debugLineNum = 427;BA.debugLine="sb.Append(\"<b>Creación:</b> \").Append(unaFechaC";
_sb.Append("<b>Creación:</b> ").Append(_unafechacreacion).Append("<br/>");
 //BA.debugLineNum = 428;BA.debugLine="sb.Append(\"<b>A Distribución:</b> \").Append(una";
_sb.Append("<b>A Distribución:</b> ").Append(_unafechadistri).Append("<br/>");
 //BA.debugLineNum = 429;BA.debugLine="sb.Append(\"<b>Confección:</b> \").Append(unUsuar";
_sb.Append("<b>Confección:</b> ").Append(_unusuarioconfeccion).Append("<br/>");
 //BA.debugLineNum = 430;BA.debugLine="sb.Append(\"<b>Observación:</b> \").Append(unaObs";
_sb.Append("<b>Observación:</b> ").Append(_unaobservacion).Append("<br/><br/>");
 }
;
 //BA.debugLineNum = 433;BA.debugLine="Cursor.Close";
_cursor.Close();
 //BA.debugLineNum = 435;BA.debugLine="sb.Append(\"</body></html>\")";
_sb.Append("</body></html>");
 //BA.debugLineNum = 440;BA.debugLine="Dim lblInfo As Label ' Crear un control Label";
_lblinfo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 441;BA.debugLine="lblInfo.Initialize(\"\") ' Inicializar el Label";
_lblinfo.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 442;BA.debugLine="lblInfo.Text = sb.ToString ' Asignar el texto";
_lblinfo.setText(BA.ObjectToCharSequence(_sb.ToString()));
 //BA.debugLineNum = 444;BA.debugLine="WvContenido.LoadHtml(sb.ToString)";
mostCurrent._wvcontenido.LoadHtml(_sb.ToString());
 //BA.debugLineNum = 446;BA.debugLine="LabelCab.Text = \" Colección: \" & unColeccion & C";
mostCurrent._labelcab.setText(BA.ObjectToCharSequence(" Colección: "+_uncoleccion+anywheresoftware.b4a.keywords.Common.CRLF+" Prioridad: "+_unaprioridad+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+" Artículo: "+_unarticulo+anywheresoftware.b4a.keywords.Common.CRLF+" Color: "+_uncolor+anywheresoftware.b4a.keywords.Common.CRLF+" Estado: "+_unestado));
 //BA.debugLineNum = 448;BA.debugLine="LabelCab2.Text = \"Creación: \" & unaFechaCreacion";
mostCurrent._labelcab2.setText(BA.ObjectToCharSequence("Creación: "+_unafechacreacion+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"A Distrib: "+_unafechadistri+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"Confecc.: "+_unusuarioconfeccion));
 //BA.debugLineNum = 450;BA.debugLine="LabelCabObs.text= \" Observación: \" & unaObservac";
mostCurrent._labelcabobs.setText(BA.ObjectToCharSequence(" Observación: "+_unaobservacion));
 //BA.debugLineNum = 452;BA.debugLine="End Sub";
return "";
}
public static String  _cargardetalle(int _unaplanilla) throws Exception{
anywheresoftware.b4a.objects.collections.List _data = null;
int _canttalles = 0;
anywheresoftware.b4a.objects.collections.List _listatalles = null;
anywheresoftware.b4j.objects.SQL.ResultSetWrapper _cursor = null;
String _unsp = "";
JHS.zxScannerLiveView.b4xtable._b4xtablecolumn _colcliente = null;
int _cantcolumnas = 0;
int _i = 0;
JHS.zxScannerLiveView.b4xtable._b4xtablecolumn _colcantidad = null;
JHS.zxScannerLiveView.b4xtable._b4xtablecolumn _coltot = null;
int _cantelementos = 0;
Object[] _row = null;
 //BA.debugLineNum = 456;BA.debugLine="Public Sub CargarDetalle(unaPlanilla As Int)";
 //BA.debugLineNum = 458;BA.debugLine="Dim Data As List";
_data = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 459;BA.debugLine="Data.Initialize";
_data.Initialize();
 //BA.debugLineNum = 463;BA.debugLine="FxGlobales.DesactivarModoEstricto";
mostCurrent._fxglobales._desactivarmodoestricto /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 465;BA.debugLine="mod_Conexion.Conexion()";
mostCurrent._mod_conexion._conexion /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 467;BA.debugLine="Dim CantTalles As Int = 0";
_canttalles = (int) (0);
 //BA.debugLineNum = 468;BA.debugLine="Dim ListaTalles As List";
_listatalles = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 473;BA.debugLine="Dim Cursor As JdbcResultSet";
_cursor = new anywheresoftware.b4j.objects.SQL.ResultSetWrapper();
 //BA.debugLineNum = 474;BA.debugLine="Dim unSP As String = \"EXEC SP_MOBILE_GESTION_DE_";
_unsp = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_DETALLE_ARMADO_GRILLA "+BA.NumberToString(_unaplanilla);
 //BA.debugLineNum = 476;BA.debugLine="Cursor = mod_Conexion.sql1.ExecQuery(unSP)";
_cursor = mostCurrent._mod_conexion._sql1 /*anywheresoftware.b4j.objects.SQL*/ .ExecQuery(_unsp);
 //BA.debugLineNum = 478;BA.debugLine="ListaTalles.Initialize";
_listatalles.Initialize();
 //BA.debugLineNum = 480;BA.debugLine="Do While Cursor.NextRow";
while (_cursor.NextRow()) {
 //BA.debugLineNum = 483;BA.debugLine="ListaTalles.Add(Cursor.GetString(\"TALLE\"))";
_listatalles.Add((Object)(_cursor.GetString("TALLE")));
 }
;
 //BA.debugLineNum = 487;BA.debugLine="Cursor.Close";
_cursor.Close();
 //BA.debugLineNum = 489;BA.debugLine="CantTalles = ListaTalles.Size";
_canttalles = _listatalles.getSize();
 //BA.debugLineNum = 495;BA.debugLine="Dim ColCliente As B4XTableColumn = B4XTable1.Add";
_colcliente = mostCurrent._b4xtable1._addcolumn /*JHS.zxScannerLiveView.b4xtable._b4xtablecolumn*/ ("CLIENTE",mostCurrent._b4xtable1._column_type_text /*int*/ );
 //BA.debugLineNum = 496;BA.debugLine="ColCliente.Id = 0";
_colcliente.Id /*String*/  = BA.NumberToString(0);
 //BA.debugLineNum = 497;BA.debugLine="ColCliente.Width = 160dip";
_colcliente.Width /*int*/  = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160));
 //BA.debugLineNum = 499;BA.debugLine="Dim CantColumnas As Int";
_cantcolumnas = 0;
 //BA.debugLineNum = 501;BA.debugLine="For	i= 0 To CantTalles-1";
{
final int step20 = 1;
final int limit20 = (int) (_canttalles-1);
_i = (int) (0) ;
for (;_i <= limit20 ;_i = _i + step20 ) {
 //BA.debugLineNum = 503;BA.debugLine="Dim ColCantidad As B4XTableColumn = B4XTable1.A";
_colcantidad = mostCurrent._b4xtable1._addcolumn /*JHS.zxScannerLiveView.b4xtable._b4xtablecolumn*/ (BA.ObjectToString(_listatalles.Get(_i)),mostCurrent._b4xtable1._column_type_text /*int*/ );
 //BA.debugLineNum = 504;BA.debugLine="ColCantidad.Id = i + 1";
_colcantidad.Id /*String*/  = BA.NumberToString(_i+1);
 //BA.debugLineNum = 505;BA.debugLine="ColCantidad.Width = 40dip";
_colcantidad.Width /*int*/  = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40));
 //BA.debugLineNum = 507;BA.debugLine="CantColumnas = ColCantidad.Id";
_cantcolumnas = (int)(Double.parseDouble(_colcantidad.Id /*String*/ ));
 }
};
 //BA.debugLineNum = 512;BA.debugLine="Dim ColTot As B4XTableColumn = B4XTable1.AddColu";
_coltot = mostCurrent._b4xtable1._addcolumn /*JHS.zxScannerLiveView.b4xtable._b4xtablecolumn*/ ("TOT",mostCurrent._b4xtable1._column_type_text /*int*/ );
 //BA.debugLineNum = 513;BA.debugLine="ColTot.Id = CantColumnas + 1";
_coltot.Id /*String*/  = BA.NumberToString(_cantcolumnas+1);
 //BA.debugLineNum = 514;BA.debugLine="ColTot.Width = 40dip";
_coltot.Width /*int*/  = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40));
 //BA.debugLineNum = 530;BA.debugLine="Dim Cursor As JdbcResultSet";
_cursor = new anywheresoftware.b4j.objects.SQL.ResultSetWrapper();
 //BA.debugLineNum = 531;BA.debugLine="Dim unSP As String = \"EXEC SP_MOBILE_GESTION_DE_";
_unsp = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_DETALLE "+BA.NumberToString(_unaplanilla);
 //BA.debugLineNum = 533;BA.debugLine="Cursor = mod_Conexion.sql1.ExecQuery(unSP)";
_cursor = mostCurrent._mod_conexion._sql1 /*anywheresoftware.b4j.objects.SQL*/ .ExecQuery(_unsp);
 //BA.debugLineNum = 535;BA.debugLine="Dim Data As List";
_data = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 536;BA.debugLine="Data.Initialize";
_data.Initialize();
 //BA.debugLineNum = 538;BA.debugLine="Dim CantElementos As Int = CantTalles + 2 'Todos";
_cantelementos = (int) (_canttalles+2);
 //BA.debugLineNum = 540;BA.debugLine="CantCol = CantElementos";
_cantcol = _cantelementos;
 //BA.debugLineNum = 544;BA.debugLine="Do While Cursor.NextRow";
while (_cursor.NextRow()) {
 //BA.debugLineNum = 545;BA.debugLine="Dim row(CantElementos) As Object";
_row = new Object[_cantelementos];
{
int d0 = _row.length;
for (int i0 = 0;i0 < d0;i0++) {
_row[i0] = new Object();
}
}
;
 //BA.debugLineNum = 547;BA.debugLine="For	i= 0 To CantElementos-1";
{
final int step38 = 1;
final int limit38 = (int) (_cantelementos-1);
_i = (int) (0) ;
for (;_i <= limit38 ;_i = _i + step38 ) {
 //BA.debugLineNum = 548;BA.debugLine="row(i) = Cursor.GetString(Cursor.GetColumnName";
_row[_i] = (Object)(_cursor.GetString(_cursor.GetColumnName(_i)));
 }
};
 //BA.debugLineNum = 552;BA.debugLine="Data.Add(row)";
_data.Add((Object)(_row));
 //BA.debugLineNum = 554;BA.debugLine="CantFilas = CantFilas +1";
_cantfilas = (int) (_cantfilas+1);
 }
;
 //BA.debugLineNum = 558;BA.debugLine="Cursor.Close";
_cursor.Close();
 //BA.debugLineNum = 561;BA.debugLine="B4XTable1.SetData(Data)";
mostCurrent._b4xtable1._setdata /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (_data);
 //BA.debugLineNum = 570;BA.debugLine="B4XTable1.Refresh";
mostCurrent._b4xtable1._refresh /*String*/ ();
 //BA.debugLineNum = 590;BA.debugLine="End Sub";
return "";
}
public static String  _cargartalleguardado() throws Exception{
int _filareal = 0;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _row = null;
String _codcte = "";
String _ctecompleto = "";
int _j = 0;
JHS.zxScannerLiveView.b4xtable._b4xtablecolumn _colsel = null;
 //BA.debugLineNum = 772;BA.debugLine="Private Sub CargarTalleGuardado()";
 //BA.debugLineNum = 773;BA.debugLine="Dim FilaReal As Int";
_filareal = 0;
 //BA.debugLineNum = 774;BA.debugLine="For	i = 1 To B4XTable1.RowsPerPage";
{
final int step2 = 1;
final int limit2 = mostCurrent._b4xtable1._getrowsperpage /*int*/ ();
_i = (int) (1) ;
for (;_i <= limit2 ;_i = _i + step2 ) {
 //BA.debugLineNum = 775;BA.debugLine="If (B4XTable1.CurrentPage) = 1 Then";
if ((mostCurrent._b4xtable1._getcurrentpage /*int*/ ())==1) { 
 //BA.debugLineNum = 776;BA.debugLine="Dim row  As Map = B4XTable1.GetRow(i)";
_row = new anywheresoftware.b4a.objects.collections.Map();
_row = mostCurrent._b4xtable1._getrow /*anywheresoftware.b4a.objects.collections.Map*/ ((long) (_i));
 //BA.debugLineNum = 777;BA.debugLine="FilaReal = i";
_filareal = _i;
 }else {
 //BA.debugLineNum = 779;BA.debugLine="FilaReal = i + (B4XTable1.RowsPerPage * (B4XTa";
_filareal = (int) (_i+(mostCurrent._b4xtable1._getrowsperpage /*int*/ ()*(mostCurrent._b4xtable1._getcurrentpage /*int*/ ()-1)));
 //BA.debugLineNum = 782;BA.debugLine="If FilaReal <= B4XTable1.Size Then";
if (_filareal<=mostCurrent._b4xtable1._getsize /*int*/ ()) { 
 //BA.debugLineNum = 783;BA.debugLine="Dim row  As Map = B4XTable1.GetRow(i + (B4XTa";
_row = new anywheresoftware.b4a.objects.collections.Map();
_row = mostCurrent._b4xtable1._getrow /*anywheresoftware.b4a.objects.collections.Map*/ ((long) (_i+(mostCurrent._b4xtable1._getrowsperpage /*int*/ ()*(mostCurrent._b4xtable1._getcurrentpage /*int*/ ()-1))));
 };
 };
 //BA.debugLineNum = 787;BA.debugLine="If FilaReal <= B4XTable1.Size Then";
if (_filareal<=mostCurrent._b4xtable1._getsize /*int*/ ()) { 
 //BA.debugLineNum = 788;BA.debugLine="Dim CodCte As String = \"\"";
_codcte = "";
 //BA.debugLineNum = 789;BA.debugLine="Dim CteCompleto As String = row.Get(\"0\") 'Obte";
_ctecompleto = BA.ObjectToString(_row.Get((Object)("0")));
 //BA.debugLineNum = 790;BA.debugLine="For	j = 1 To CantCol -2 'para no mostrar el to";
{
final int step15 = 1;
final int limit15 = (int) (_cantcol-2);
_j = (int) (1) ;
for (;_j <= limit15 ;_j = _j + step15 ) {
 //BA.debugLineNum = 792;BA.debugLine="Dim ColSel As B4XTableColumn = B4XTable1.GetC";
_colsel = mostCurrent._b4xtable1._getcolumn /*JHS.zxScannerLiveView.b4xtable._b4xtablecolumn*/ (BA.NumberToString(_j));
 //BA.debugLineNum = 793;BA.debugLine="CodCte = FxGlobales.Left(CteCompleto, CteComp";
_codcte = mostCurrent._fxglobales._left /*String*/ (mostCurrent.activityBA,_ctecompleto,(int) (_ctecompleto.indexOf("-")-1));
 //BA.debugLineNum = 795;BA.debugLine="Log(\"Se va a cargar la seleccion para el clie";
anywheresoftware.b4a.keywords.Common.LogImpl("54980759","Se va a cargar la seleccion para el cliente: "+_codcte+" y talle: "+_colsel.Title /*String*/ ,0);
 //BA.debugLineNum = 798;BA.debugLine="If ConsultarTalleParaCliente(CodCte, ColSel.T";
if (_consultartalleparacliente(_codcte,_colsel.Title /*String*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 799;BA.debugLine="PintarCelda(j, FilaReal, Colors.Red)";
_pintarcelda(BA.NumberToString(_j),(long) (_filareal),anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 }
};
 };
 }
};
 //BA.debugLineNum = 807;BA.debugLine="End Sub";
return "";
}
public static boolean  _consultartalleparacliente(String _uncodcliente,String _uncodtalle) throws Exception{
boolean _resu = false;
anywheresoftware.b4j.objects.SQL.ResultSetWrapper _cursor = null;
String _unsp = "";
 //BA.debugLineNum = 811;BA.debugLine="Private Sub ConsultarTalleParaCliente(unCodClient";
 //BA.debugLineNum = 815;BA.debugLine="Dim Resu As Boolean = False";
_resu = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 816;BA.debugLine="Dim Cursor As JdbcResultSet";
_cursor = new anywheresoftware.b4j.objects.SQL.ResultSetWrapper();
 //BA.debugLineNum = 817;BA.debugLine="Dim unSP As String = \"EXEC SP_MOBILE_GESTION_DE_";
_unsp = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_DETALLE_CONSULTAR_TALLE "+BA.NumberToString(mostCurrent._datosglobales._planillaseleccionada /*int*/ )+", '"+_uncodcliente+"', '"+_uncodtalle+"'";
 //BA.debugLineNum = 819;BA.debugLine="Cursor = mod_Conexion.sql1.ExecQuery(unSP)";
_cursor = mostCurrent._mod_conexion._sql1 /*anywheresoftware.b4j.objects.SQL*/ .ExecQuery(_unsp);
 //BA.debugLineNum = 820;BA.debugLine="Do While Cursor.NextRow";
while (_cursor.NextRow()) {
 //BA.debugLineNum = 821;BA.debugLine="If (Cursor.GetInt(\"RESULTADO\") > 0) Then";
if ((_cursor.GetInt("RESULTADO")>0)) { 
 //BA.debugLineNum = 822;BA.debugLine="Resu = True";
_resu = anywheresoftware.b4a.keywords.Common.True;
 };
 }
;
 //BA.debugLineNum = 826;BA.debugLine="Cursor.Close";
_cursor.Close();
 //BA.debugLineNum = 830;BA.debugLine="Return Resu";
if (true) return _resu;
 //BA.debugLineNum = 832;BA.debugLine="End Sub";
return false;
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 19;BA.debugLine="Dim Awake As PhoneWakeState";
mostCurrent._awake = new anywheresoftware.b4a.phone.Phone.PhoneWakeState();
 //BA.debugLineNum = 21;BA.debugLine="Private Planilla_Detalle_Label_Nro_Planilla As La";
mostCurrent._planilla_detalle_label_nro_planilla = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private Planilla_Detalle_ClvResultado As CustomLi";
mostCurrent._planilla_detalle_clvresultado = new b4a.example3.customlistview();
 //BA.debugLineNum = 25;BA.debugLine="Private ItemLblRemito As B4XView";
mostCurrent._itemlblremito = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private ItemBtnRemito As B4XView";
mostCurrent._itembtnremito = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private BtnVolver As Button";
mostCurrent._btnvolver = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private LabelCab As Label";
mostCurrent._labelcab = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private LabelCabObs As Label";
mostCurrent._labelcabobs = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private LabelCab2 As Label";
mostCurrent._labelcab2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private LblUsuarioDato As Label";
mostCurrent._lblusuariodato = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private B4XTable1 As B4XTable";
mostCurrent._b4xtable1 = new JHS.zxScannerLiveView.b4xtable();
 //BA.debugLineNum = 43;BA.debugLine="Private xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 48;BA.debugLine="Private PaginaActual As Int = 1";
_paginaactual = (int) (1);
 //BA.debugLineNum = 49;BA.debugLine="Private CantFilas As Int = 0";
_cantfilas = (int) (0);
 //BA.debugLineNum = 50;BA.debugLine="Dim CantCol As Int";
_cantcol = 0;
 //BA.debugLineNum = 53;BA.debugLine="Private BtnPlanillaFinalizar As Button";
mostCurrent._btnplanillafinalizar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private FinalizoPlanilla As Boolean = False";
_finalizoplanilla = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 58;BA.debugLine="Private BtnReset As Button";
mostCurrent._btnreset = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Private LblNevegacion As Label";
mostCurrent._lblnevegacion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Private ScvEncabezado As ScrollView";
mostCurrent._scvencabezado = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private PanelContenido As Panel";
mostCurrent._panelcontenido = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private LbContenido As Label";
mostCurrent._lbcontenido = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private WvContenido As WebView";
mostCurrent._wvcontenido = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return "";
}
public static boolean  _guardarseleccion(String _columnid,long _rowid,boolean _activo) throws Exception{
boolean _resu = false;
anywheresoftware.b4a.objects.collections.Map _row = null;
String _codcte = "";
String _ctecompleto = "";
JHS.zxScannerLiveView.b4xtable._b4xtablecolumn _colsel = null;
anywheresoftware.b4j.objects.SQL.ResultSetWrapper _cursor = null;
String _unsp = "";
 //BA.debugLineNum = 731;BA.debugLine="Private Sub GuardarSeleccion(ColumnId As String,";
 //BA.debugLineNum = 733;BA.debugLine="Log(\"Entro a --> GuardarSeleccion\")";
anywheresoftware.b4a.keywords.Common.LogImpl("54915202","Entro a --> GuardarSeleccion",0);
 //BA.debugLineNum = 735;BA.debugLine="Dim Resu As Boolean = False";
_resu = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 737;BA.debugLine="Dim row  As Map = B4XTable1.GetRow(RowId)";
_row = new anywheresoftware.b4a.objects.collections.Map();
_row = mostCurrent._b4xtable1._getrow /*anywheresoftware.b4a.objects.collections.Map*/ (_rowid);
 //BA.debugLineNum = 738;BA.debugLine="Dim CodCte As String = \"\"";
_codcte = "";
 //BA.debugLineNum = 739;BA.debugLine="Dim CteCompleto As String = row.Get(\"0\") 'Obteng";
_ctecompleto = BA.ObjectToString(_row.Get((Object)("0")));
 //BA.debugLineNum = 740;BA.debugLine="Dim ColSel As B4XTableColumn = B4XTable1.GetColu";
_colsel = mostCurrent._b4xtable1._getcolumn /*JHS.zxScannerLiveView.b4xtable._b4xtablecolumn*/ (_columnid);
 //BA.debugLineNum = 746;BA.debugLine="CodCte = FxGlobales.Left(CteCompleto, CteComplet";
_codcte = mostCurrent._fxglobales._left /*String*/ (mostCurrent.activityBA,_ctecompleto,(int) (_ctecompleto.indexOf("-")-1));
 //BA.debugLineNum = 752;BA.debugLine="Dim Cursor As JdbcResultSet";
_cursor = new anywheresoftware.b4j.objects.SQL.ResultSetWrapper();
 //BA.debugLineNum = 753;BA.debugLine="Dim unSP As String = \"EXEC SP_MOBILE_GESTION_DE_";
_unsp = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_DETALLE_GUARDAR_SELECCION "+BA.NumberToString(mostCurrent._datosglobales._planillaseleccionada /*int*/ )+", '"+_codcte+"', '"+_colsel.Title /*String*/ +"', "+BA.NumberToString(mostCurrent._datosglobales._idusuario /*int*/ )+", "+BA.ObjectToString(_activo);
 //BA.debugLineNum = 755;BA.debugLine="Cursor = mod_Conexion.sql1.ExecQuery(unSP)";
_cursor = mostCurrent._mod_conexion._sql1 /*anywheresoftware.b4j.objects.SQL*/ .ExecQuery(_unsp);
 //BA.debugLineNum = 756;BA.debugLine="Do While Cursor.NextRow";
while (_cursor.NextRow()) {
 //BA.debugLineNum = 757;BA.debugLine="If (Cursor.GetInt(\"RESULTADO\") > 0) Then";
if ((_cursor.GetInt("RESULTADO")>0)) { 
 //BA.debugLineNum = 758;BA.debugLine="Resu = True";
_resu = anywheresoftware.b4a.keywords.Common.True;
 };
 }
;
 //BA.debugLineNum = 762;BA.debugLine="Cursor.Close";
_cursor.Close();
 //BA.debugLineNum = 767;BA.debugLine="Return Resu";
if (true) return _resu;
 //BA.debugLineNum = 768;BA.debugLine="End Sub";
return false;
}
public static String  _itembtnremito_click() throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _unbtn = null;
int _unindiceseleccionado = 0;
anywheresoftware.b4a.objects.B4XViewWrapper _pnl = null;
Object _v = null;
anywheresoftware.b4a.objects.B4XViewWrapper _b4xv = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
 //BA.debugLineNum = 170;BA.debugLine="Private Sub ItemBtnRemito_Click";
 //BA.debugLineNum = 171;BA.debugLine="Try";
try { //BA.debugLineNum = 172;BA.debugLine="Dim unBtn As Button = Sender ' para obtener el i";
_unbtn = new anywheresoftware.b4a.objects.ButtonWrapper();
_unbtn = (anywheresoftware.b4a.objects.ButtonWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ButtonWrapper(), (android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 173;BA.debugLine="Dim unIndiceSeleccionado As Int";
_unindiceseleccionado = 0;
 //BA.debugLineNum = 174;BA.debugLine="unIndiceSeleccionado = unBtn.Tag";
_unindiceseleccionado = (int)(BA.ObjectToNumber(_unbtn.getTag()));
 //BA.debugLineNum = 176;BA.debugLine="Dim pnl As B4XView";
_pnl = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 177;BA.debugLine="pnl = Planilla_Detalle_ClvResultado.GetPanel(unI";
_pnl = mostCurrent._planilla_detalle_clvresultado._getpanel(_unindiceseleccionado).getParent();
 //BA.debugLineNum = 179;BA.debugLine="For Each v As Object In pnl.GetAllViewsRecursive";
{
final anywheresoftware.b4a.BA.IterableList group7 = _pnl.GetAllViewsRecursive();
final int groupLen7 = group7.getSize()
;int index7 = 0;
;
for (; index7 < groupLen7;index7++){
_v = group7.Get(index7);
 //BA.debugLineNum = 180;BA.debugLine="If V Is Label Then";
if (_v instanceof android.widget.TextView) { 
 //BA.debugLineNum = 181;BA.debugLine="Dim B4xV As B4XView = V";
_b4xv = new anywheresoftware.b4a.objects.B4XViewWrapper();
_b4xv = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_v));
 //BA.debugLineNum = 182;BA.debugLine="B4xV.Color = Colors.Gray";
_b4xv.setColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 //BA.debugLineNum = 183;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 187;BA.debugLine="For Each v As Object In pnl.GetAllViewsRecursive";
{
final anywheresoftware.b4a.BA.IterableList group14 = _pnl.GetAllViewsRecursive();
final int groupLen14 = group14.getSize()
;int index14 = 0;
;
for (; index14 < groupLen14;index14++){
_v = group14.Get(index14);
 //BA.debugLineNum = 188;BA.debugLine="If V Is Button Then";
if (_v instanceof android.widget.Button) { 
 //BA.debugLineNum = 189;BA.debugLine="Dim B4xV As B4XView = V";
_b4xv = new anywheresoftware.b4a.objects.B4XViewWrapper();
_b4xv = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_v));
 //BA.debugLineNum = 190;BA.debugLine="Dim bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 191;BA.debugLine="bmp.Initialize(File.DirAssets,\"plus.png\")";
_bmp.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"plus.png");
 //BA.debugLineNum = 192;BA.debugLine="B4xV.SetBitmap(bmp)";
_b4xv.SetBitmap((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 193;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 } 
       catch (Exception e24) {
			processBA.setLastException(e24); //BA.debugLineNum = 198;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje de";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 199;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("54128797",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 201;BA.debugLine="End Sub";
return "";
}
public static String  _limpiarcolortabla() throws Exception{
int _i = 0;
int _j = 0;
 //BA.debugLineNum = 709;BA.debugLine="Private Sub LimpiarColorTabla";
 //BA.debugLineNum = 711;BA.debugLine="CantFilas = B4XTable1.RowsPerPage '5";
_cantfilas = mostCurrent._b4xtable1._getrowsperpage /*int*/ ();
 //BA.debugLineNum = 713;BA.debugLine="Log(\"Filas: \" & CantFilas & \" - Clumnas: \" & Can";
anywheresoftware.b4a.keywords.Common.LogImpl("54849668","Filas: "+BA.NumberToString(_cantfilas)+" - Clumnas: "+BA.NumberToString(_cantcol),0);
 //BA.debugLineNum = 715;BA.debugLine="For	i = 1 To CantFilas";
{
final int step3 = 1;
final int limit3 = _cantfilas;
_i = (int) (1) ;
for (;_i <= limit3 ;_i = _i + step3 ) {
 //BA.debugLineNum = 717;BA.debugLine="For j = 0 To CantCol + 1";
{
final int step4 = 1;
final int limit4 = (int) (_cantcol+1);
_j = (int) (0) ;
for (;_j <= limit4 ;_j = _j + step4 ) {
 //BA.debugLineNum = 719;BA.debugLine="B4XTable1_CellClicked2(j, i)";
_b4xtable1_cellclicked2(BA.NumberToString(_j),(long) (_i));
 }
};
 }
};
 //BA.debugLineNum = 725;BA.debugLine="End Sub";
return "";
}
public static String  _pintarcelda(String _columnid,long _rowid,int _uncolor) throws Exception{
anywheresoftware.b4a.objects.collections.Map _rowdata = null;
String _cell = "";
anywheresoftware.b4a.objects.CSBuilder _cs = null;
JHS.zxScannerLiveView.b4xtable._b4xtablecolumn _column = null;
int _unafilapag = 0;
anywheresoftware.b4a.objects.B4XViewWrapper _pnl = null;
anywheresoftware.b4a.objects.LabelWrapper _l = null;
anywheresoftware.b4a.objects.collections.Map _row = null;
String _codcte = "";
String _ctecompleto = "";
JHS.zxScannerLiveView.b4xtable._b4xtablecolumn _colsel = null;
 //BA.debugLineNum = 595;BA.debugLine="Private Sub PintarCelda(ColumnId As String, RowId";
 //BA.debugLineNum = 596;BA.debugLine="Dim RowData As Map = B4XTable1.GetRow(RowId)";
_rowdata = new anywheresoftware.b4a.objects.collections.Map();
_rowdata = mostCurrent._b4xtable1._getrow /*anywheresoftware.b4a.objects.collections.Map*/ (_rowid);
 //BA.debugLineNum = 597;BA.debugLine="Dim cell As String = RowData.Get(ColumnId)";
_cell = BA.ObjectToString(_rowdata.Get((Object)(_columnid)));
 //BA.debugLineNum = 598;BA.debugLine="Activity.Title = cell";
mostCurrent._activity.setTitle(BA.ObjectToCharSequence(_cell));
 //BA.debugLineNum = 600;BA.debugLine="Dim cs As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 601;BA.debugLine="cs.Initialize.Color(Colors.RGB(0,128,0)).Size(20";
_cs.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (0),(int) (128),(int) (0))).Size((int) (20)).Bold().Append(BA.ObjectToCharSequence(_cell)).PopAll();
 //BA.debugLineNum = 603;BA.debugLine="Log(\"Voy a pintar la celda de rojo: FILA -> \" &";
anywheresoftware.b4a.keywords.Common.LogImpl("54718600","Voy a pintar la celda de rojo: FILA -> "+BA.NumberToString(_rowid)+" - COLUMNA: "+_columnid,0);
 //BA.debugLineNum = 610;BA.debugLine="Dim column As B4XTableColumn = B4XTable1.GetColu";
_column = mostCurrent._b4xtable1._getcolumn /*JHS.zxScannerLiveView.b4xtable._b4xtablecolumn*/ (_columnid);
 //BA.debugLineNum = 612;BA.debugLine="Dim UnaFilaPag As Int";
_unafilapag = 0;
 //BA.debugLineNum = 613;BA.debugLine="If B4XTable1.CurrentPage = 1 Then";
if (mostCurrent._b4xtable1._getcurrentpage /*int*/ ()==1) { 
 //BA.debugLineNum = 614;BA.debugLine="UnaFilaPag = RowId";
_unafilapag = (int) (_rowid);
 }else {
 //BA.debugLineNum = 617;BA.debugLine="UnaFilaPag = RowId - (B4XTable1.RowsPerPage * (";
_unafilapag = (int) (_rowid-(mostCurrent._b4xtable1._getrowsperpage /*int*/ ()*(mostCurrent._b4xtable1._getcurrentpage /*int*/ ()-1)));
 };
 //BA.debugLineNum = 620;BA.debugLine="Dim pnl As B4XView = column.CellsLayouts.Get(Una";
_pnl = new anywheresoftware.b4a.objects.B4XViewWrapper();
_pnl = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_column.CellsLayouts /*anywheresoftware.b4a.objects.collections.List*/ .Get(_unafilapag)));
 //BA.debugLineNum = 621;BA.debugLine="Dim l As Label    = pnl.GetView(0) 'pnl.GetView(";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
_l = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_pnl.GetView((int) (0)).getObject()));
 //BA.debugLineNum = 627;BA.debugLine="If (pnl.GetView(0).Color = unColor) Then 'Si lo";
if ((_pnl.GetView((int) (0)).getColor()==_uncolor)) { 
 //BA.debugLineNum = 632;BA.debugLine="Dim row  As Map = B4XTable1.GetRow(RowId)";
_row = new anywheresoftware.b4a.objects.collections.Map();
_row = mostCurrent._b4xtable1._getrow /*anywheresoftware.b4a.objects.collections.Map*/ (_rowid);
 //BA.debugLineNum = 633;BA.debugLine="Dim CodCte As String = \"\"";
_codcte = "";
 //BA.debugLineNum = 634;BA.debugLine="Dim CteCompleto As String = row.Get(\"0\") 'Obten";
_ctecompleto = BA.ObjectToString(_row.Get((Object)("0")));
 //BA.debugLineNum = 635;BA.debugLine="Dim ColSel As B4XTableColumn = B4XTable1.GetCol";
_colsel = mostCurrent._b4xtable1._getcolumn /*JHS.zxScannerLiveView.b4xtable._b4xtablecolumn*/ (_columnid);
 //BA.debugLineNum = 636;BA.debugLine="CodCte = FxGlobales.Left(CteCompleto, CteComple";
_codcte = mostCurrent._fxglobales._left /*String*/ (mostCurrent.activityBA,_ctecompleto,(int) (_ctecompleto.indexOf("-")-1));
 //BA.debugLineNum = 638;BA.debugLine="Select Msgbox2(\"¿Desea anular la selección para";
switch (BA.switchObjectToInt(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("¿Desea anular la selección para el cliente: "+_codcte+" y el talle: "+_colsel.Title /*String*/ +"?"),BA.ObjectToCharSequence("ATENCIÓN"),"SI","","NO",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE)) {
case 0: {
 //BA.debugLineNum = 642;BA.debugLine="If (GuardarSeleccion(ColumnId, RowId, False))";
if ((_guardarseleccion(_columnid,_rowid,anywheresoftware.b4a.keywords.Common.False))==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 643;BA.debugLine="l.Color= Colors.Transparent";
_l.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 }else {
 //BA.debugLineNum = 645;BA.debugLine="Msgbox(\"#ERROR: no se ha podido guardar la d";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: no se ha podido guardar la des-seleccion. "),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 };
 break; }
}
;
 }else if((_pnl.GetView((int) (0)).getColor()==anywheresoftware.b4a.keywords.Common.Colors.Transparent)) { 
 //BA.debugLineNum = 656;BA.debugLine="If (GuardarSeleccion(ColumnId, RowId, True)) =";
if ((_guardarseleccion(_columnid,_rowid,anywheresoftware.b4a.keywords.Common.True))==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 657;BA.debugLine="l.Color = unColor";
_l.setColor(_uncolor);
 };
 }else {
 //BA.debugLineNum = 665;BA.debugLine="If (GuardarSeleccion(ColumnId, RowId, True)) =";
if ((_guardarseleccion(_columnid,_rowid,anywheresoftware.b4a.keywords.Common.True))==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 666;BA.debugLine="l.Color = unColor";
_l.setColor(_uncolor);
 };
 };
 //BA.debugLineNum = 679;BA.debugLine="End Sub";
return "";
}
public static String  _pintarceldatransparente(String _columnid,long _rowid) throws Exception{
anywheresoftware.b4a.objects.collections.Map _rowdata = null;
String _cell = "";
anywheresoftware.b4a.objects.CSBuilder _cs = null;
JHS.zxScannerLiveView.b4xtable._b4xtablecolumn _column = null;
anywheresoftware.b4a.objects.B4XViewWrapper _pnl = null;
anywheresoftware.b4a.objects.LabelWrapper _l = null;
 //BA.debugLineNum = 684;BA.debugLine="Private Sub PintarCeldaTransparente(ColumnId As S";
 //BA.debugLineNum = 685;BA.debugLine="Dim RowData As Map = B4XTable1.GetRow(RowId)";
_rowdata = new anywheresoftware.b4a.objects.collections.Map();
_rowdata = mostCurrent._b4xtable1._getrow /*anywheresoftware.b4a.objects.collections.Map*/ (_rowid);
 //BA.debugLineNum = 686;BA.debugLine="Dim cell As String = RowData.Get(ColumnId)";
_cell = BA.ObjectToString(_rowdata.Get((Object)(_columnid)));
 //BA.debugLineNum = 687;BA.debugLine="Activity.Title = cell";
mostCurrent._activity.setTitle(BA.ObjectToCharSequence(_cell));
 //BA.debugLineNum = 689;BA.debugLine="Dim cs As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 690;BA.debugLine="cs.Initialize.Color(Colors.RGB(0,128,0)).Size(20";
_cs.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (0),(int) (128),(int) (0))).Size((int) (20)).Bold().Append(BA.ObjectToCharSequence(_cell)).PopAll();
 //BA.debugLineNum = 693;BA.debugLine="Dim column As B4XTableColumn = B4XTable1.GetColu";
_column = mostCurrent._b4xtable1._getcolumn /*JHS.zxScannerLiveView.b4xtable._b4xtablecolumn*/ (_columnid);
 //BA.debugLineNum = 694;BA.debugLine="Dim pnl As B4XView = column.CellsLayouts.Get(Row";
_pnl = new anywheresoftware.b4a.objects.B4XViewWrapper();
_pnl = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_column.CellsLayouts /*anywheresoftware.b4a.objects.collections.List*/ .Get((int) (_rowid))));
 //BA.debugLineNum = 695;BA.debugLine="Dim l As Label    = pnl.GetView(0) 'pnl.GetView(";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
_l = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_pnl.GetView((int) (0)).getObject()));
 //BA.debugLineNum = 698;BA.debugLine="l.Color= Colors.Transparent";
_l.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 704;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
}
