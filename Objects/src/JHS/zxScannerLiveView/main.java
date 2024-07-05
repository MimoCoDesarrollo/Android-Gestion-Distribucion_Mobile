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

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "JHS.zxScannerLiveView", "JHS.zxScannerLiveView.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
		activityBA = new BA(this, layout, processBA, "JHS.zxScannerLiveView", "JHS.zxScannerLiveView.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "JHS.zxScannerLiveView.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
		return main.class;
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
            BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (main) Pause event (activity is not paused). **");
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
            main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
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
public static anywheresoftware.b4a.phone.Phone.PhoneWakeState _awake = null;
public static anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public static String _modo = "";
public anywheresoftware.b4a.objects.EditTextWrapper _etusuario = null;
public anywheresoftware.b4a.objects.EditTextWrapper _etpassword = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnlogin = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelprincipal = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnconfiguracion = null;
public static boolean _esprimeravez = false;
public anywheresoftware.b4a.objects.PanelWrapper _panelconfiguracion = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnguardarconfig = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edtserver = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edtdatabase = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edtuser = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edtpassword = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edtport = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rbtest = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rbprod = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblambiente = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnlog = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btneliminararchivo = null;
public b4a.example.dateutils _dateutils = null;
public JHS.zxScannerLiveView.mod_conexion _mod_conexion = null;
public JHS.zxScannerLiveView.datosglobales _datosglobales = null;
public JHS.zxScannerLiveView.fxglobales _fxglobales = null;
public JHS.zxScannerLiveView.starter _starter = null;
public JHS.zxScannerLiveView.planillaslistado _planillaslistado = null;
public JHS.zxScannerLiveView.planilladetalle _planilladetalle = null;
public JHS.zxScannerLiveView.editbox _editbox = null;
public JHS.zxScannerLiveView.b4xcollections _b4xcollections = null;
public JHS.zxScannerLiveView.xuiviewsutils _xuiviewsutils = null;
public JHS.zxScannerLiveView.httputils2service _httputils2service = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (planillaslistado.mostCurrent != null);
vis = vis | (planilladetalle.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 82;BA.debugLine="Sub activity_create(firsttime As Boolean)";
 //BA.debugLineNum = 84;BA.debugLine="Try";
try { //BA.debugLineNum = 87;BA.debugLine="Activity.LoadLayout(\"Pantalla_Principal\")";
mostCurrent._activity.LoadLayout("Pantalla_Principal",mostCurrent.activityBA);
 //BA.debugLineNum = 89;BA.debugLine="PanelConfiguracion.Visible = False";
mostCurrent._panelconfiguracion.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 90;BA.debugLine="BtnConfiguracion.Visible = False";
mostCurrent._btnconfiguracion.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 97;BA.debugLine="DatosGlobales.CodUsuario = \"\"";
mostCurrent._datosglobales._codusuario /*String*/  = "";
 //BA.debugLineNum = 98;BA.debugLine="EtUsuario.Text = DatosGlobales.CodUsuario";
mostCurrent._etusuario.setText(BA.ObjectToCharSequence(mostCurrent._datosglobales._codusuario /*String*/ ));
 //BA.debugLineNum = 99;BA.debugLine="EtPassword.Text = \"\"";
mostCurrent._etpassword.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 101;BA.debugLine="CargarAmbiente";
_cargarambiente();
 //BA.debugLineNum = 102;BA.debugLine="FxGlobales.DesactivarModoEstricto";
mostCurrent._fxglobales._desactivarmodoestricto /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 103;BA.debugLine="mod_Conexion.Conexion()";
mostCurrent._mod_conexion._conexion /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 104;BA.debugLine="Awake.KeepAlive(True) 'para q no se apague";
_awake.KeepAlive(processBA,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 109;BA.debugLine="DatosGlobales.NombreDispositivo = \"\"";
mostCurrent._datosglobales._nombredispositivo /*String*/  = "";
 //BA.debugLineNum = 114;BA.debugLine="GuardarLog(\"Se creo el activity\")";
_guardarlog("Se creo el activity");
 } 
       catch (Exception e15) {
			processBA.setLastException(e15); //BA.debugLineNum = 117;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 118;BA.debugLine="GuardarLog(\"#ERROR: \" & LastException)";
_guardarlog("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 //BA.debugLineNum = 119;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("5131109",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 133;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 135;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 129;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 131;BA.debugLine="End Sub";
return "";
}
public static String  _btnconfiguracion_click() throws Exception{
JHS.zxScannerLiveView.keyvaluestore _kvs = null;
 //BA.debugLineNum = 273;BA.debugLine="Private Sub BtnConfiguracion_Click";
 //BA.debugLineNum = 275;BA.debugLine="xui.SetDataFolder(\"kvs\")";
_xui.SetDataFolder("kvs");
 //BA.debugLineNum = 276;BA.debugLine="Dim kvs As KeyValueStore";
_kvs = new JHS.zxScannerLiveView.keyvaluestore();
 //BA.debugLineNum = 277;BA.debugLine="kvs.Initialize(xui.DefaultFolder, \"kvs.dat\")";
_kvs._initialize /*String*/ (processBA,_xui.getDefaultFolder(),"kvs.dat");
 //BA.debugLineNum = 279;BA.debugLine="If kvs.get(\"TEST_Server\") = Null Then";
if (_kvs._get /*Object*/ ("TEST_Server")== null) { 
 //BA.debugLineNum = 280;BA.debugLine="kvs.put(\"TEST_Server\", \"//192.168.1.32\")";
_kvs._put /*String*/ ("TEST_Server",(Object)("//192.168.1.32"));
 };
 //BA.debugLineNum = 282;BA.debugLine="If kvs.get(\"TEST_Database\") = Null Then";
if (_kvs._get /*Object*/ ("TEST_Database")== null) { 
 //BA.debugLineNum = 283;BA.debugLine="kvs.put(\"TEST_Database\", \"SIGMAMIRROR\")";
_kvs._put /*String*/ ("TEST_Database",(Object)("SIGMAMIRROR"));
 };
 //BA.debugLineNum = 285;BA.debugLine="If kvs.get(\"TEST_User\") = Null Then";
if (_kvs._get /*Object*/ ("TEST_User")== null) { 
 //BA.debugLineNum = 286;BA.debugLine="kvs.put(\"TEST_User\", \"sa\")";
_kvs._put /*String*/ ("TEST_User",(Object)("sa"));
 };
 //BA.debugLineNum = 288;BA.debugLine="If kvs.get(\"TEST_Password\") = Null Then";
if (_kvs._get /*Object*/ ("TEST_Password")== null) { 
 //BA.debugLineNum = 289;BA.debugLine="kvs.put(\"TEST_Password\", \"1046mimo\")";
_kvs._put /*String*/ ("TEST_Password",(Object)("1046mimo"));
 };
 //BA.debugLineNum = 291;BA.debugLine="If kvs.get(\"TEST_Port\") = Null Then";
if (_kvs._get /*Object*/ ("TEST_Port")== null) { 
 //BA.debugLineNum = 292;BA.debugLine="kvs.put(\"TEST_Port\", \":1433\")";
_kvs._put /*String*/ ("TEST_Port",(Object)(":1433"));
 };
 //BA.debugLineNum = 295;BA.debugLine="If kvs.get(\"PROD_Server\") = Null Then";
if (_kvs._get /*Object*/ ("PROD_Server")== null) { 
 //BA.debugLineNum = 296;BA.debugLine="kvs.put(\"PROD_Server\", \"//192.168.1.20\")";
_kvs._put /*String*/ ("PROD_Server",(Object)("//192.168.1.20"));
 };
 //BA.debugLineNum = 298;BA.debugLine="If kvs.get(\"PROD_Database\") = Null Then";
if (_kvs._get /*Object*/ ("PROD_Database")== null) { 
 //BA.debugLineNum = 299;BA.debugLine="kvs.put(\"PROD_Database\", \"SIGMA\")";
_kvs._put /*String*/ ("PROD_Database",(Object)("SIGMA"));
 };
 //BA.debugLineNum = 301;BA.debugLine="If kvs.get(\"PROD_User\") = Null Then";
if (_kvs._get /*Object*/ ("PROD_User")== null) { 
 //BA.debugLineNum = 302;BA.debugLine="kvs.put(\"PROD_User\", \"sa\")";
_kvs._put /*String*/ ("PROD_User",(Object)("sa"));
 };
 //BA.debugLineNum = 304;BA.debugLine="If kvs.get(\"PROD_Password\") = Null Then";
if (_kvs._get /*Object*/ ("PROD_Password")== null) { 
 //BA.debugLineNum = 305;BA.debugLine="kvs.put(\"PROD_Password\", \"1046mimo\")";
_kvs._put /*String*/ ("PROD_Password",(Object)("1046mimo"));
 };
 //BA.debugLineNum = 307;BA.debugLine="If kvs.get(\"PROD_Port\") = Null Then";
if (_kvs._get /*Object*/ ("PROD_Port")== null) { 
 //BA.debugLineNum = 308;BA.debugLine="kvs.put(\"PROD_Port\", \":1433\")";
_kvs._put /*String*/ ("PROD_Port",(Object)(":1433"));
 };
 //BA.debugLineNum = 311;BA.debugLine="If kvs.get(\"Modo\") = Null Then";
if (_kvs._get /*Object*/ ("Modo")== null) { 
 //BA.debugLineNum = 312;BA.debugLine="kvs.put(\"Modo\", \"TEST\")";
_kvs._put /*String*/ ("Modo",(Object)("TEST"));
 //BA.debugLineNum = 313;BA.debugLine="Modo = \"TEST\"";
_modo = "TEST";
 }else {
 //BA.debugLineNum = 315;BA.debugLine="Modo = kvs.get(\"Modo\")";
_modo = BA.ObjectToString(_kvs._get /*Object*/ ("Modo"));
 };
 //BA.debugLineNum = 319;BA.debugLine="Setear_Valores";
_setear_valores();
 //BA.debugLineNum = 322;BA.debugLine="PanelConfiguracion.Visible = True";
mostCurrent._panelconfiguracion.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 325;BA.debugLine="Log(\"UBICACION: \" & CRLF & xui.DefaultFolder)";
anywheresoftware.b4a.keywords.Common.LogImpl("5458804","UBICACION: "+anywheresoftware.b4a.keywords.Common.CRLF+_xui.getDefaultFolder(),0);
 //BA.debugLineNum = 327;BA.debugLine="Log(\"TEST: \" & CRLF & kvs.Get(\"TEST_Server\") & C";
anywheresoftware.b4a.keywords.Common.LogImpl("5458806","TEST: "+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(_kvs._get /*Object*/ ("TEST_Server"))+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(_kvs._get /*Object*/ ("TEST_Database"))+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(_kvs._get /*Object*/ ("TEST_User"))+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(_kvs._get /*Object*/ ("TEST_Password"))+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(_kvs._get /*Object*/ ("TEST_Port")),0);
 //BA.debugLineNum = 329;BA.debugLine="Log(\"PRODUCTIVO: \" & CRLF & kvs.Get(\"PROD_Server";
anywheresoftware.b4a.keywords.Common.LogImpl("5458808","PRODUCTIVO: "+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(_kvs._get /*Object*/ ("PROD_Server"))+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(_kvs._get /*Object*/ ("PROD_Database"))+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(_kvs._get /*Object*/ ("PROD_User"))+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(_kvs._get /*Object*/ ("PROD_Password"))+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(_kvs._get /*Object*/ ("PROD_Port")),0);
 //BA.debugLineNum = 332;BA.debugLine="End Sub";
return "";
}
public static String  _btneliminararchivo_click() throws Exception{
 //BA.debugLineNum = 510;BA.debugLine="Private Sub BtnEliminarArchivo_Click";
 //BA.debugLineNum = 512;BA.debugLine="File.Delete(xui.DefaultFolder, \"kvs.dat\")";
anywheresoftware.b4a.keywords.Common.File.Delete(_xui.getDefaultFolder(),"kvs.dat");
 //BA.debugLineNum = 514;BA.debugLine="If File.Exists(xui.DefaultFolder, \"kvs.dat\") Then";
if (anywheresoftware.b4a.keywords.Common.File.Exists(_xui.getDefaultFolder(),"kvs.dat")) { 
 //BA.debugLineNum = 515;BA.debugLine="Msgbox(\"El archivo existe\", \"Mensaje del sistema";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("El archivo existe"),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 517;BA.debugLine="Msgbox(\"El archivo NO existe\", \"Mensaje del sist";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("El archivo NO existe"),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 521;BA.debugLine="End Sub";
return "";
}
public static String  _btnguardarconfig_click() throws Exception{
JHS.zxScannerLiveView.keyvaluestore _kvs = null;
 //BA.debugLineNum = 367;BA.debugLine="Private Sub BtnGuardarConfig_Click";
 //BA.debugLineNum = 369;BA.debugLine="xui.SetDataFolder(\"kvs\")";
_xui.SetDataFolder("kvs");
 //BA.debugLineNum = 370;BA.debugLine="Dim kvs As KeyValueStore";
_kvs = new JHS.zxScannerLiveView.keyvaluestore();
 //BA.debugLineNum = 371;BA.debugLine="kvs.Initialize(xui.DefaultFolder, \"kvs.dat\")";
_kvs._initialize /*String*/ (processBA,_xui.getDefaultFolder(),"kvs.dat");
 //BA.debugLineNum = 373;BA.debugLine="Select Modo";
switch (BA.switchObjectToInt(_modo,"TEST","PROD")) {
case 0: {
 //BA.debugLineNum = 375;BA.debugLine="kvs.put(\"TEST_Server\", edtServer.Text)";
_kvs._put /*String*/ ("TEST_Server",(Object)(mostCurrent._edtserver.getText()));
 //BA.debugLineNum = 376;BA.debugLine="kvs.put(\"TEST_Database\", edtDatabase.Text)";
_kvs._put /*String*/ ("TEST_Database",(Object)(mostCurrent._edtdatabase.getText()));
 //BA.debugLineNum = 377;BA.debugLine="kvs.put(\"TEST_User\", edtUser.Text)";
_kvs._put /*String*/ ("TEST_User",(Object)(mostCurrent._edtuser.getText()));
 //BA.debugLineNum = 378;BA.debugLine="kvs.put(\"TEST_Password\", edtPassword.Text)";
_kvs._put /*String*/ ("TEST_Password",(Object)(mostCurrent._edtpassword.getText()));
 //BA.debugLineNum = 379;BA.debugLine="kvs.put(\"TEST_Port\", edtPort.Text)";
_kvs._put /*String*/ ("TEST_Port",(Object)(mostCurrent._edtport.getText()));
 //BA.debugLineNum = 380;BA.debugLine="kvs.put(\"Modo\", \"TEST\")";
_kvs._put /*String*/ ("Modo",(Object)("TEST"));
 break; }
case 1: {
 //BA.debugLineNum = 382;BA.debugLine="kvs.put(\"PROD_Server\", edtServer.Text)";
_kvs._put /*String*/ ("PROD_Server",(Object)(mostCurrent._edtserver.getText()));
 //BA.debugLineNum = 383;BA.debugLine="kvs.put(\"PROD_Database\", edtDatabase.Text)";
_kvs._put /*String*/ ("PROD_Database",(Object)(mostCurrent._edtdatabase.getText()));
 //BA.debugLineNum = 384;BA.debugLine="kvs.put(\"PROD_User\", edtUser.Text)";
_kvs._put /*String*/ ("PROD_User",(Object)(mostCurrent._edtuser.getText()));
 //BA.debugLineNum = 385;BA.debugLine="kvs.put(\"PROD_Password\", edtPassword.Text)";
_kvs._put /*String*/ ("PROD_Password",(Object)(mostCurrent._edtpassword.getText()));
 //BA.debugLineNum = 386;BA.debugLine="kvs.put(\"PROD_Port\", edtPort.Text)";
_kvs._put /*String*/ ("PROD_Port",(Object)(mostCurrent._edtport.getText()));
 //BA.debugLineNum = 387;BA.debugLine="kvs.put(\"Modo\", \"PROD\")";
_kvs._put /*String*/ ("Modo",(Object)("PROD"));
 break; }
default: {
 //BA.debugLineNum = 389;BA.debugLine="Msgbox(\"Error en datos de la aplicacion\", \"Men";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error en datos de la aplicacion"),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 391;BA.debugLine="Return";
if (true) return "";
 break; }
}
;
 //BA.debugLineNum = 393;BA.debugLine="Msgbox(\"Datos Guardados\", \"Mensaje del sistema\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Datos Guardados"),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 395;BA.debugLine="Select Modo";
switch (BA.switchObjectToInt(_modo,"TEST","PROD")) {
case 0: {
 //BA.debugLineNum = 397;BA.debugLine="mod_Conexion.sIp = kvs.Get(\"TEST_Server\")";
mostCurrent._mod_conexion._sip /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("TEST_Server"));
 //BA.debugLineNum = 398;BA.debugLine="mod_Conexion.sDatabase = kvs.Get(\"TEST_Databas";
mostCurrent._mod_conexion._sdatabase /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("TEST_Database"));
 //BA.debugLineNum = 399;BA.debugLine="mod_Conexion.sUser = kvs.Get(\"TEST_User\")";
mostCurrent._mod_conexion._suser /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("TEST_User"));
 //BA.debugLineNum = 400;BA.debugLine="mod_Conexion.sPassword = kvs.Get(\"TEST_Passwor";
mostCurrent._mod_conexion._spassword /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("TEST_Password"));
 //BA.debugLineNum = 401;BA.debugLine="mod_Conexion.sPort = kvs.Get(\"TEST_Port\")";
mostCurrent._mod_conexion._sport /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("TEST_Port"));
 break; }
case 1: {
 //BA.debugLineNum = 403;BA.debugLine="mod_Conexion.sIp = kvs.Get(\"PROD_Server\")";
mostCurrent._mod_conexion._sip /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("PROD_Server"));
 //BA.debugLineNum = 404;BA.debugLine="mod_Conexion.sDatabase = kvs.Get(\"PROD_Databas";
mostCurrent._mod_conexion._sdatabase /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("PROD_Database"));
 //BA.debugLineNum = 405;BA.debugLine="mod_Conexion.sUser = kvs.Get(\"PROD_User\")";
mostCurrent._mod_conexion._suser /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("PROD_User"));
 //BA.debugLineNum = 406;BA.debugLine="mod_Conexion.sPassword = kvs.Get(\"PROD_Passwor";
mostCurrent._mod_conexion._spassword /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("PROD_Password"));
 //BA.debugLineNum = 407;BA.debugLine="mod_Conexion.sPort = kvs.Get(\"PROD_Port\")";
mostCurrent._mod_conexion._sport /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("PROD_Port"));
 break; }
default: {
 //BA.debugLineNum = 409;BA.debugLine="Msgbox(\"Error en datos de la aplicacion\", \"Men";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error en datos de la aplicacion"),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 411;BA.debugLine="Return";
if (true) return "";
 break; }
}
;
 //BA.debugLineNum = 414;BA.debugLine="CargarAmbiente";
_cargarambiente();
 //BA.debugLineNum = 416;BA.debugLine="mod_Conexion.Conexion()";
mostCurrent._mod_conexion._conexion /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 418;BA.debugLine="PanelConfiguracion.Visible = False";
mostCurrent._panelconfiguracion.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 420;BA.debugLine="End Sub";
return "";
}
public static String  _btnlog_click() throws Exception{
String _resultado = "";
 //BA.debugLineNum = 492;BA.debugLine="Private Sub BtnLog_Click";
 //BA.debugLineNum = 496;BA.debugLine="Dim Resultado As String";
_resultado = "";
 //BA.debugLineNum = 497;BA.debugLine="Resultado = File.ReadString(File.DirInternal, \"Lo";
_resultado = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"LogAplicacion.txt");
 //BA.debugLineNum = 501;BA.debugLine="Log(File.DirInternal)";
anywheresoftware.b4a.keywords.Common.LogImpl("5851977",anywheresoftware.b4a.keywords.Common.File.getDirInternal(),0);
 //BA.debugLineNum = 502;BA.debugLine="Log(Resultado)";
anywheresoftware.b4a.keywords.Common.LogImpl("5851978",_resultado,0);
 //BA.debugLineNum = 505;BA.debugLine="Msgbox(Resultado, \"Mensaje del sistema\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_resultado),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 508;BA.debugLine="End Sub";
return "";
}
public static String  _btnlogin_click() throws Exception{
boolean _usuariovalido = false;
boolean _pswvalida = false;
boolean _autoriza = false;
String _unusuario = "";
String _unacontraseña = "";
anywheresoftware.b4j.objects.SQL.ResultSetWrapper _cursor = null;
String _unsp = "";
 //BA.debugLineNum = 137;BA.debugLine="Private Sub BtnLogin_Click";
 //BA.debugLineNum = 139;BA.debugLine="Try";
try { //BA.debugLineNum = 141;BA.debugLine="GuardarLog(\"Clickeo para loguear\")";
_guardarlog("Clickeo para loguear");
 //BA.debugLineNum = 144;BA.debugLine="Dim usuarioValido As Boolean = False";
_usuariovalido = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 145;BA.debugLine="Dim pswValida As Boolean = False";
_pswvalida = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 146;BA.debugLine="Dim Autoriza As Boolean = False";
_autoriza = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 148;BA.debugLine="If(EtUsuario.Text = \"\") Then";
if (((mostCurrent._etusuario.getText()).equals(""))) { 
 //BA.debugLineNum = 149;BA.debugLine="ToastMessageShow(\"No se ha ingresado un usuario";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se ha ingresado un usuario válido."),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 151;BA.debugLine="usuarioValido = True";
_usuariovalido = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 154;BA.debugLine="If(EtPassword.Text = \"\") Then";
if (((mostCurrent._etpassword.getText()).equals(""))) { 
 //BA.debugLineNum = 156;BA.debugLine="If usuarioValido = True Then";
if (_usuariovalido==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 159;BA.debugLine="ToastMessageShow(\"No se ha ingresado una clave";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No se ha ingresado una clave válida para el usuario: "+mostCurrent._etusuario.getText()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 161;BA.debugLine="GuardarLog(\"No se ha ingresado una clave válid";
_guardarlog("No se ha ingresado una clave válida para el usuario: "+mostCurrent._etusuario.getText());
 };
 }else {
 //BA.debugLineNum = 166;BA.debugLine="pswValida = True";
_pswvalida = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 169;BA.debugLine="If usuarioValido = True And pswValida=True Then";
if (_usuariovalido==anywheresoftware.b4a.keywords.Common.True && _pswvalida==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 174;BA.debugLine="If EsPrimeraVez = True Then";
if (_esprimeravez==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 175;BA.debugLine="If ((EtUsuario.Text).ToUpperCase = \"ADMIN\") Or";
if ((((mostCurrent._etusuario.getText()).toUpperCase()).equals("ADMIN")) || (((mostCurrent._etusuario.getText()).toUpperCase()).equals("ADMINAUTO"))) { 
 //BA.debugLineNum = 176;BA.debugLine="BtnConfiguracion.Visible = True";
mostCurrent._btnconfiguracion.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 177;BA.debugLine="EsPrimeraVez = False";
_esprimeravez = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 178;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 188;BA.debugLine="Dim unUsuario As String = EtUsuario.Text";
_unusuario = mostCurrent._etusuario.getText();
 //BA.debugLineNum = 189;BA.debugLine="Dim unaContraseña As String = EtPassword.Text";
_unacontraseña = mostCurrent._etpassword.getText();
 //BA.debugLineNum = 191;BA.debugLine="Dim Cursor As JdbcResultSet";
_cursor = new anywheresoftware.b4j.objects.SQL.ResultSetWrapper();
 //BA.debugLineNum = 192;BA.debugLine="Dim unSP As String = \"EXEC SP_MOBILE_GESTION_DE";
_unsp = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_LOGIN '"+_unusuario+"', '"+_unacontraseña+"'";
 //BA.debugLineNum = 194;BA.debugLine="Cursor = mod_Conexion.sql1.ExecQuery(unSP)";
_cursor = mostCurrent._mod_conexion._sql1 /*anywheresoftware.b4j.objects.SQL*/ .ExecQuery(_unsp);
 //BA.debugLineNum = 195;BA.debugLine="Do While Cursor.NextRow";
while (_cursor.NextRow()) {
 //BA.debugLineNum = 196;BA.debugLine="If (Cursor.GetInt(\"RESULTADO\") > 0) Then";
if ((_cursor.GetInt("RESULTADO")>0)) { 
 //BA.debugLineNum = 198;BA.debugLine="DatosGlobales.IdUsuario = Cursor.GetString(\"I";
mostCurrent._datosglobales._idusuario /*int*/  = (int)(Double.parseDouble(_cursor.GetString("IDUSUARIO")));
 //BA.debugLineNum = 199;BA.debugLine="DatosGlobales.CodUsuario = Cursor.GetString(\"";
mostCurrent._datosglobales._codusuario /*String*/  = _cursor.GetString("CODUSUARIO");
 //BA.debugLineNum = 200;BA.debugLine="DatosGlobales.DescUsuario = Cursor.GetString(";
mostCurrent._datosglobales._descusuario /*String*/  = _cursor.GetString("DESCUSUARIO");
 //BA.debugLineNum = 202;BA.debugLine="Autoriza = True";
_autoriza = anywheresoftware.b4a.keywords.Common.True;
 };
 }
;
 //BA.debugLineNum = 206;BA.debugLine="Cursor.Close";
_cursor.Close();
 //BA.debugLineNum = 216;BA.debugLine="If Autoriza = True Then";
if (_autoriza==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 218;BA.debugLine="GuardarLog(\"Logeo correctamente para el usuari";
_guardarlog("Logeo correctamente para el usuario: "+mostCurrent._etusuario.getText());
 //BA.debugLineNum = 220;BA.debugLine="StartActivity(PlanillasListado)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._planillaslistado.getObject()));
 //BA.debugLineNum = 222;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else {
 //BA.debugLineNum = 225;BA.debugLine="ToastMessageShow(\"#ERROR: usuario o clave inco";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("#ERROR: usuario o clave incorrecta."),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 226;BA.debugLine="GuardarLog(\"#ERROR: usuario o clave incorrecta";
_guardarlog("#ERROR: usuario o clave incorrecta para el usuario: "+mostCurrent._etusuario.getText());
 };
 };
 } 
       catch (Exception e51) {
			processBA.setLastException(e51); //BA.debugLineNum = 235;BA.debugLine="ToastMessageShow(\"#ERROR: ha ocurrido un error n";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("#ERROR: ha ocurrido un error no controlado. - "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 236;BA.debugLine="GuardarLog(\"#ERROR: ha ocurrido un error no cont";
_guardarlog("#ERROR: ha ocurrido un error no controlado. - "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)));
 };
 //BA.debugLineNum = 241;BA.debugLine="End Sub";
return "";
}
public static String  _cargarambiente() throws Exception{
JHS.zxScannerLiveView.keyvaluestore _kvs = null;
 //BA.debugLineNum = 423;BA.debugLine="Private Sub CargarAmbiente";
 //BA.debugLineNum = 425;BA.debugLine="xui.SetDataFolder(\"kvs\")";
_xui.SetDataFolder("kvs");
 //BA.debugLineNum = 426;BA.debugLine="Dim kvs As KeyValueStore";
_kvs = new JHS.zxScannerLiveView.keyvaluestore();
 //BA.debugLineNum = 427;BA.debugLine="kvs.Initialize(xui.DefaultFolder, \"kvs.dat\")";
_kvs._initialize /*String*/ (processBA,_xui.getDefaultFolder(),"kvs.dat");
 //BA.debugLineNum = 429;BA.debugLine="Dim Modo As String";
_modo = "";
 //BA.debugLineNum = 430;BA.debugLine="If kvs.get(\"Modo\") = Null Then";
if (_kvs._get /*Object*/ ("Modo")== null) { 
 }else {
 //BA.debugLineNum = 433;BA.debugLine="Modo = kvs.get(\"Modo\")";
_modo = BA.ObjectToString(_kvs._get /*Object*/ ("Modo"));
 };
 //BA.debugLineNum = 436;BA.debugLine="LblAmbiente.Text = \"  Ambiente: Producción\"";
mostCurrent._lblambiente.setText(BA.ObjectToCharSequence("  Ambiente: Producción"));
 //BA.debugLineNum = 437;BA.debugLine="If Modo = \"TEST\" Then";
if ((_modo).equals("TEST")) { 
 //BA.debugLineNum = 438;BA.debugLine="LblAmbiente.Text = \"  Ambiente: TEST\"";
mostCurrent._lblambiente.setText(BA.ObjectToCharSequence("  Ambiente: TEST"));
 //BA.debugLineNum = 439;BA.debugLine="DatosGlobales.EsProduccion = False";
mostCurrent._datosglobales._esproduccion /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 441;BA.debugLine="DatosGlobales.EsProduccion = True";
mostCurrent._datosglobales._esproduccion /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 444;BA.debugLine="Select Modo";
switch (BA.switchObjectToInt(_modo,"TEST","PROD")) {
case 0: {
 //BA.debugLineNum = 446;BA.debugLine="mod_Conexion.sIp = kvs.Get(\"TEST_Server\")";
mostCurrent._mod_conexion._sip /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("TEST_Server"));
 //BA.debugLineNum = 447;BA.debugLine="mod_Conexion.sDatabase = kvs.Get(\"TEST_Databas";
mostCurrent._mod_conexion._sdatabase /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("TEST_Database"));
 //BA.debugLineNum = 448;BA.debugLine="mod_Conexion.sUser = kvs.Get(\"TEST_User\")";
mostCurrent._mod_conexion._suser /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("TEST_User"));
 //BA.debugLineNum = 449;BA.debugLine="mod_Conexion.sPassword = kvs.Get(\"TEST_Passwor";
mostCurrent._mod_conexion._spassword /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("TEST_Password"));
 //BA.debugLineNum = 450;BA.debugLine="mod_Conexion.sPort = kvs.Get(\"TEST_Port\")";
mostCurrent._mod_conexion._sport /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("TEST_Port"));
 break; }
case 1: {
 //BA.debugLineNum = 452;BA.debugLine="mod_Conexion.sIp = kvs.Get(\"PROD_Server\")";
mostCurrent._mod_conexion._sip /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("PROD_Server"));
 //BA.debugLineNum = 453;BA.debugLine="mod_Conexion.sDatabase = kvs.Get(\"PROD_Databas";
mostCurrent._mod_conexion._sdatabase /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("PROD_Database"));
 //BA.debugLineNum = 454;BA.debugLine="mod_Conexion.sUser = kvs.Get(\"PROD_User\")";
mostCurrent._mod_conexion._suser /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("PROD_User"));
 //BA.debugLineNum = 455;BA.debugLine="mod_Conexion.sPassword = kvs.Get(\"PROD_Passwor";
mostCurrent._mod_conexion._spassword /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("PROD_Password"));
 //BA.debugLineNum = 456;BA.debugLine="mod_Conexion.sPort = kvs.Get(\"PROD_Port\")";
mostCurrent._mod_conexion._sport /*String*/  = BA.ObjectToString(_kvs._get /*Object*/ ("PROD_Port"));
 break; }
default: {
 //BA.debugLineNum = 459;BA.debugLine="Msgbox(\"Error en datos de la aplicacion\", \"Men";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error en datos de la aplicacion"),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 463;BA.debugLine="BtnConfiguracion_Click";
_btnconfiguracion_click();
 break; }
}
;
 //BA.debugLineNum = 467;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 54;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 57;BA.debugLine="Private EtUsuario As EditText";
mostCurrent._etusuario = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private EtPassword As EditText";
mostCurrent._etpassword = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Private BtnLogin As Button";
mostCurrent._btnlogin = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Private PanelPrincipal As Panel";
mostCurrent._panelprincipal = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private BtnConfiguracion As Button";
mostCurrent._btnconfiguracion = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private EsPrimeraVez As Boolean = True";
_esprimeravez = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 65;BA.debugLine="Private PanelConfiguracion As Panel";
mostCurrent._panelconfiguracion = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Private BtnGuardarConfig As Button";
mostCurrent._btnguardarconfig = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Private edtServer As EditText";
mostCurrent._edtserver = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private edtDatabase As EditText";
mostCurrent._edtdatabase = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Private edtUser As EditText";
mostCurrent._edtuser = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Private edtPassword As EditText";
mostCurrent._edtpassword = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Private edtPort As EditText";
mostCurrent._edtport = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Private RbTest As RadioButton";
mostCurrent._rbtest = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 73;BA.debugLine="Private RbProd As RadioButton";
mostCurrent._rbprod = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 74;BA.debugLine="Private LblAmbiente As Label";
mostCurrent._lblambiente = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 78;BA.debugLine="Private BtnLog As Button";
mostCurrent._btnlog = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Private BtnEliminarArchivo As Button";
mostCurrent._btneliminararchivo = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}
public static String  _guardarlog(String _unmensaje) throws Exception{
String _fechahoratexto = "";
String _linealog = "";
String _resultado = "";
 //BA.debugLineNum = 246;BA.debugLine="Private Sub GuardarLog(unMensaje As String)";
 //BA.debugLineNum = 247;BA.debugLine="Dim fechaHoraTexto As String = \"\"";
_fechahoratexto = "";
 //BA.debugLineNum = 248;BA.debugLine="DateTime.TimeFormat=\"dd/MM/yyyy HH:mm:ss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("dd/MM/yyyy HH:mm:ss");
 //BA.debugLineNum = 249;BA.debugLine="fechaHoraTexto = DateTime.Time(DateTime.Now)";
_fechahoratexto = anywheresoftware.b4a.keywords.Common.DateTime.Time(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 253;BA.debugLine="Dim LineaLog As String = \" - \" & fechaHoraTexto";
_linealog = " - "+_fechahoratexto+" - "+_unmensaje;
 //BA.debugLineNum = 255;BA.debugLine="Dim Resultado As String";
_resultado = "";
 //BA.debugLineNum = 261;BA.debugLine="Log(\"LOG LINEA GUARDADA --> \" & LineaLog)";
anywheresoftware.b4a.keywords.Common.LogImpl("5393231","LOG LINEA GUARDADA --> "+_linealog,0);
 //BA.debugLineNum = 265;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        b4a.example.dateutils._process_globals();
main._process_globals();
mod_conexion._process_globals();
datosglobales._process_globals();
fxglobales._process_globals();
starter._process_globals();
planillaslistado._process_globals();
planilladetalle._process_globals();
editbox._process_globals();
b4xcollections._process_globals();
xuiviewsutils._process_globals();
httputils2service._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 41;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 47;BA.debugLine="Dim Awake As PhoneWakeState";
_awake = new anywheresoftware.b4a.phone.Phone.PhoneWakeState();
 //BA.debugLineNum = 49;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 50;BA.debugLine="Public Modo As String = \"TEST\"";
_modo = "TEST";
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
return "";
}
public static String  _rbprod_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 476;BA.debugLine="Private Sub RbProd_CheckedChange(Checked As Boole";
 //BA.debugLineNum = 477;BA.debugLine="If Checked Then";
if (_checked) { 
 //BA.debugLineNum = 478;BA.debugLine="Modo = \"PROD\"";
_modo = "PROD";
 //BA.debugLineNum = 479;BA.debugLine="Setear_Valores";
_setear_valores();
 };
 //BA.debugLineNum = 481;BA.debugLine="End Sub";
return "";
}
public static String  _rbtest_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 469;BA.debugLine="Private Sub RbTest_CheckedChange(Checked As Boole";
 //BA.debugLineNum = 470;BA.debugLine="If Checked Then";
if (_checked) { 
 //BA.debugLineNum = 471;BA.debugLine="Modo = \"TEST\"";
_modo = "TEST";
 //BA.debugLineNum = 472;BA.debugLine="Setear_Valores";
_setear_valores();
 };
 //BA.debugLineNum = 474;BA.debugLine="End Sub";
return "";
}
public static String  _setear_valores() throws Exception{
JHS.zxScannerLiveView.keyvaluestore _kvs = null;
 //BA.debugLineNum = 335;BA.debugLine="Private Sub Setear_Valores";
 //BA.debugLineNum = 336;BA.debugLine="xui.SetDataFolder(\"kvs\")";
_xui.SetDataFolder("kvs");
 //BA.debugLineNum = 337;BA.debugLine="Dim kvs As KeyValueStore";
_kvs = new JHS.zxScannerLiveView.keyvaluestore();
 //BA.debugLineNum = 338;BA.debugLine="kvs.Initialize(xui.DefaultFolder, \"kvs.dat\")";
_kvs._initialize /*String*/ (processBA,_xui.getDefaultFolder(),"kvs.dat");
 //BA.debugLineNum = 341;BA.debugLine="Select Modo";
switch (BA.switchObjectToInt(_modo,"TEST","PROD")) {
case 0: {
 //BA.debugLineNum = 343;BA.debugLine="edtServer.Text = kvs.Get(\"TEST_Server\")";
mostCurrent._edtserver.setText(BA.ObjectToCharSequence(_kvs._get /*Object*/ ("TEST_Server")));
 //BA.debugLineNum = 344;BA.debugLine="edtDatabase.Text = kvs.Get(\"TEST_Database\")";
mostCurrent._edtdatabase.setText(BA.ObjectToCharSequence(_kvs._get /*Object*/ ("TEST_Database")));
 //BA.debugLineNum = 345;BA.debugLine="edtUser.Text = kvs.Get(\"TEST_User\")";
mostCurrent._edtuser.setText(BA.ObjectToCharSequence(_kvs._get /*Object*/ ("TEST_User")));
 //BA.debugLineNum = 346;BA.debugLine="edtPassword.Text = kvs.Get(\"TEST_Password\")";
mostCurrent._edtpassword.setText(BA.ObjectToCharSequence(_kvs._get /*Object*/ ("TEST_Password")));
 //BA.debugLineNum = 347;BA.debugLine="edtPort.Text = kvs.Get(\"TEST_Port\")";
mostCurrent._edtport.setText(BA.ObjectToCharSequence(_kvs._get /*Object*/ ("TEST_Port")));
 //BA.debugLineNum = 348;BA.debugLine="RbTest.Checked = True";
mostCurrent._rbtest.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 349;BA.debugLine="RbProd.Checked = False";
mostCurrent._rbprod.setChecked(anywheresoftware.b4a.keywords.Common.False);
 break; }
case 1: {
 //BA.debugLineNum = 351;BA.debugLine="edtServer.Text = kvs.Get(\"PROD_Server\")";
mostCurrent._edtserver.setText(BA.ObjectToCharSequence(_kvs._get /*Object*/ ("PROD_Server")));
 //BA.debugLineNum = 352;BA.debugLine="edtDatabase.Text = kvs.Get(\"PROD_Database\")";
mostCurrent._edtdatabase.setText(BA.ObjectToCharSequence(_kvs._get /*Object*/ ("PROD_Database")));
 //BA.debugLineNum = 353;BA.debugLine="edtUser.Text = kvs.Get(\"PROD_User\")";
mostCurrent._edtuser.setText(BA.ObjectToCharSequence(_kvs._get /*Object*/ ("PROD_User")));
 //BA.debugLineNum = 354;BA.debugLine="edtPassword.Text = kvs.Get(\"PROD_Password\")";
mostCurrent._edtpassword.setText(BA.ObjectToCharSequence(_kvs._get /*Object*/ ("PROD_Password")));
 //BA.debugLineNum = 355;BA.debugLine="edtPort.Text = kvs.Get(\"PROD_Port\")";
mostCurrent._edtport.setText(BA.ObjectToCharSequence(_kvs._get /*Object*/ ("PROD_Port")));
 //BA.debugLineNum = 356;BA.debugLine="RbTest.Checked = False";
mostCurrent._rbtest.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 357;BA.debugLine="RbProd.Checked = True";
mostCurrent._rbprod.setChecked(anywheresoftware.b4a.keywords.Common.True);
 break; }
default: {
 //BA.debugLineNum = 359;BA.debugLine="Msgbox(\"Error en datos de la aplicacion\", \"Men";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error en datos de la aplicacion"),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 360;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 break; }
}
;
 //BA.debugLineNum = 362;BA.debugLine="End Sub";
return "";
}
}
