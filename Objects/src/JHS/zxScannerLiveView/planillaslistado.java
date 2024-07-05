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

public class planillaslistado extends Activity implements B4AActivity{
	public static planillaslistado mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "JHS.zxScannerLiveView", "JHS.zxScannerLiveView.planillaslistado");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (planillaslistado).");
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
		activityBA = new BA(this, layout, processBA, "JHS.zxScannerLiveView", "JHS.zxScannerLiveView.planillaslistado");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "JHS.zxScannerLiveView.planillaslistado", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (planillaslistado) Create " + (isFirst ? "(first time)" : "") + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (planillaslistado) Resume **");
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
		return planillaslistado.class;
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
            BA.LogInfo("** Activity (planillaslistado) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (planillaslistado) Pause event (activity is not paused). **");
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
            planillaslistado mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (planillaslistado) Resume **");
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
public static anywheresoftware.b4a.objects.collections.List _unalista = null;
public static int _unindice = 0;
public static String _untextosel = "";
public anywheresoftware.b4a.phone.Phone.PhoneWakeState _awake = null;
public anywheresoftware.b4a.objects.PanelWrapper _panellistadoprincipal = null;
public b4a.example3.customlistview _clvresultado = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _itemlblremito = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _itembtnremito = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblusuariodato = null;
public zxscanwrapper.zxScanWrapper _escaner = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnrefrescar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btningman = null;
public JHS.zxScannerLiveView.editbox._cinputbox _eb = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlcoleccion = null;
public JHS.zxScannerLiveView.b4xcombobox _b4xcombobox1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncolecok = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncoleccancel = null;
public anywheresoftware.b4a.objects.collections.Map _map1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcolecciondato = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnevegacion = null;
public b4a.example.dateutils _dateutils = null;
public JHS.zxScannerLiveView.main _main = null;
public JHS.zxScannerLiveView.mod_conexion _mod_conexion = null;
public JHS.zxScannerLiveView.datosglobales _datosglobales = null;
public JHS.zxScannerLiveView.fxglobales _fxglobales = null;
public JHS.zxScannerLiveView.starter _starter = null;
public JHS.zxScannerLiveView.planilladetalle _planilladetalle = null;
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
 //BA.debugLineNum = 56;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 57;BA.debugLine="Try";
try { //BA.debugLineNum = 60;BA.debugLine="ProgressDialogShow2(\"Cargando por favor espere..";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Cargando por favor espere..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 63;BA.debugLine="Activity.LoadLayout(\"planillaslistado\")";
mostCurrent._activity.LoadLayout("planillaslistado",mostCurrent.activityBA);
 //BA.debugLineNum = 65;BA.debugLine="Escaner.HudVisible = True";
mostCurrent._escaner.setHudVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 66;BA.debugLine="Escaner.PlaySound = True";
mostCurrent._escaner.setPlaySound(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 67;BA.debugLine="Escaner.Visible = False";
mostCurrent._escaner.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 69;BA.debugLine="CargarColecciones 'cargar colecciones dsd la bbd";
_cargarcolecciones();
 //BA.debugLineNum = 71;BA.debugLine="PnlColeccion.Visible = True";
mostCurrent._pnlcoleccion.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 73;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 75;BA.debugLine="FxGlobales.DesactivarModoEstricto";
mostCurrent._fxglobales._desactivarmodoestricto /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 77;BA.debugLine="Awake.KeepAlive(True) 'para q no se apague";
mostCurrent._awake.KeepAlive(processBA,anywheresoftware.b4a.keywords.Common.True);
 } 
       catch (Exception e13) {
			processBA.setLastException(e13); //BA.debugLineNum = 81;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 82;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("52359322",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 427;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 428;BA.debugLine="Try";
try { //BA.debugLineNum = 429;BA.debugLine="If KeyCode=KeyCodes.KEYCODE_BACK And eb.Visible";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK && mostCurrent._eb.Visible /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 430;BA.debugLine="EditBox.Hide(eb)";
mostCurrent._editbox._hide /*String*/ (mostCurrent.activityBA,mostCurrent._eb);
 //BA.debugLineNum = 431;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 } 
       catch (Exception e7) {
			processBA.setLastException(e7); //BA.debugLineNum = 434;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 435;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("53407880",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 437;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 112;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 114;BA.debugLine="Log(\"El activity entro en Pausa\")";
anywheresoftware.b4a.keywords.Common.LogImpl("52490370","El activity entro en Pausa",0);
 //BA.debugLineNum = 116;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 88;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 89;BA.debugLine="Try";
try { //BA.debugLineNum = 90;BA.debugLine="Log(\"Pase por --> Activity_Resume\")";
anywheresoftware.b4a.keywords.Common.LogImpl("52424834","Pase por --> Activity_Resume",0);
 //BA.debugLineNum = 92;BA.debugLine="LblUsuarioDato.Text = DatosGlobales.DescUsuario";
mostCurrent._lblusuariodato.setText(BA.ObjectToCharSequence(mostCurrent._datosglobales._descusuario /*String*/ ));
 //BA.debugLineNum = 94;BA.debugLine="ClvResultado.Clear";
mostCurrent._clvresultado._clear();
 //BA.debugLineNum = 95;BA.debugLine="CargarListado";
_cargarlistado();
 //BA.debugLineNum = 97;BA.debugLine="If DatosGlobales.EsProduccion = False Then";
if (mostCurrent._datosglobales._esproduccion /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 98;BA.debugLine="LblNevegacion.Text = \"    Navegación\" & \" - AMB";
mostCurrent._lblnevegacion.setText(BA.ObjectToCharSequence("    Navegación"+" - AMBIENTE: TEST"));
 }else {
 //BA.debugLineNum = 100;BA.debugLine="LblNevegacion.Text = \"    Navegación\"";
mostCurrent._lblnevegacion.setText(BA.ObjectToCharSequence("    Navegación"));
 };
 } 
       catch (Exception e12) {
			processBA.setLastException(e12); //BA.debugLineNum = 104;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 105;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("52424849",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 108;BA.debugLine="Log(\"Termino de ejecutar --> Activity_Resume\")";
anywheresoftware.b4a.keywords.Common.LogImpl("52424852","Termino de ejecutar --> Activity_Resume",0);
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
return "";
}
public static String  _btncoleccancel_click() throws Exception{
 //BA.debugLineNum = 493;BA.debugLine="Private Sub BtnColecCancel_Click";
 //BA.debugLineNum = 494;BA.debugLine="PnlColeccion.Visible = False";
mostCurrent._pnlcoleccion.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 495;BA.debugLine="End Sub";
return "";
}
public static String  _btncolecok_click() throws Exception{
int _idcoleccsel = 0;
 //BA.debugLineNum = 471;BA.debugLine="Private Sub BtnColecOk_Click";
 //BA.debugLineNum = 472;BA.debugLine="Try";
try { //BA.debugLineNum = 473;BA.debugLine="Dim IdColeccSel As Int = Map1.GetKeyAt(B4XComboB";
_idcoleccsel = (int)(BA.ObjectToNumber(mostCurrent._map1.GetKeyAt(mostCurrent._b4xcombobox1._cmbbox /*anywheresoftware.b4a.objects.SpinnerWrapper*/ .getSelectedIndex())));
 //BA.debugLineNum = 474;BA.debugLine="DatosGlobales.IdColeccion = IdColeccSel";
mostCurrent._datosglobales._idcoleccion /*int*/  = _idcoleccsel;
 //BA.debugLineNum = 476;BA.debugLine="If DatosGlobales.IdColeccion > 0 Then";
if (mostCurrent._datosglobales._idcoleccion /*int*/ >0) { 
 //BA.debugLineNum = 478;BA.debugLine="CargarListado";
_cargarlistado();
 };
 //BA.debugLineNum = 483;BA.debugLine="LblColeccionDato.Text = B4XComboBox1.cmbBox.Sele";
mostCurrent._lblcolecciondato.setText(BA.ObjectToCharSequence(mostCurrent._b4xcombobox1._cmbbox /*anywheresoftware.b4a.objects.SpinnerWrapper*/ .getSelectedItem()));
 //BA.debugLineNum = 484;BA.debugLine="PnlColeccion.Visible = False";
mostCurrent._pnlcoleccion.setVisible(anywheresoftware.b4a.keywords.Common.False);
 } 
       catch (Exception e10) {
			processBA.setLastException(e10); //BA.debugLineNum = 487;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 488;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("53604497",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 491;BA.debugLine="End Sub";
return "";
}
public static String  _btnescanear_click() throws Exception{
 //BA.debugLineNum = 190;BA.debugLine="Private Sub BtnEscanear_Click";
 //BA.debugLineNum = 192;BA.debugLine="If (Escaner.Visible = True) Then";
if ((mostCurrent._escaner.getVisible()==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 193;BA.debugLine="Escaner.Visible = False";
mostCurrent._escaner.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 194;BA.debugLine="Escaner.stopScanner";
mostCurrent._escaner.stopScanner();
 }else {
 //BA.debugLineNum = 196;BA.debugLine="Escaner.Visible = True";
mostCurrent._escaner.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 197;BA.debugLine="Escaner.startScanner";
mostCurrent._escaner.startScanner();
 };
 //BA.debugLineNum = 202;BA.debugLine="End Sub";
return "";
}
public static String  _btningman_click() throws Exception{
int _r = 0;
 //BA.debugLineNum = 401;BA.debugLine="Private Sub BtnIngMan_Click";
 //BA.debugLineNum = 402;BA.debugLine="Try";
try { //BA.debugLineNum = 405;BA.debugLine="If eb.Visible = True Then";
if (mostCurrent._eb.Visible /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 406;BA.debugLine="EditBox.Hide(eb)";
mostCurrent._editbox._hide /*String*/ (mostCurrent.activityBA,mostCurrent._eb);
 //BA.debugLineNum = 407;BA.debugLine="Return True";
if (true) return BA.ObjectToString(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 409;BA.debugLine="Dim r As Int";
_r = 0;
 //BA.debugLineNum = 410;BA.debugLine="r=EditBox.Show(eb,Activity,\"Mensaje del sistema";
_r = mostCurrent._editbox._show /*int*/ (mostCurrent.activityBA,mostCurrent._eb,mostCurrent._activity,"Mensaje del sistema","Ingrese número de planilla aquí","OK","Cancel",mostCurrent._eb.cInputBoxEditText /*anywheresoftware.b4a.objects.EditTextWrapper*/ .INPUT_TYPE_NUMBERS);
 //BA.debugLineNum = 411;BA.debugLine="If r=DialogResponse.POSITIVE Then";
if (_r==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 412;BA.debugLine="Log(\"Va a procesar manualmente a la planilla N";
anywheresoftware.b4a.keywords.Common.LogImpl("53342347","Va a procesar manualmente a la planilla N°: "+mostCurrent._eb.Result /*String*/ ,0);
 //BA.debugLineNum = 413;BA.debugLine="If ProcesarEscaneo(eb.Result) = True Then";
if ((_procesarescaneo(mostCurrent._eb.Result /*String*/ )).equals(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.True))) { 
 //BA.debugLineNum = 414;BA.debugLine="ClvResultado.Clear";
mostCurrent._clvresultado._clear();
 //BA.debugLineNum = 415;BA.debugLine="CargarListado";
_cargarlistado();
 };
 };
 };
 } 
       catch (Exception e17) {
			processBA.setLastException(e17); //BA.debugLineNum = 421;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 422;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("53342357",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 425;BA.debugLine="End Sub";
return "";
}
public static String  _btnrefrescar_click() throws Exception{
 //BA.debugLineNum = 385;BA.debugLine="Private Sub BtnRefrescar_Click";
 //BA.debugLineNum = 386;BA.debugLine="Try";
try { //BA.debugLineNum = 387;BA.debugLine="ClvResultado.Clear";
mostCurrent._clvresultado._clear();
 //BA.debugLineNum = 388;BA.debugLine="CargarListado";
_cargarlistado();
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 390;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 391;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("53276806",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 394;BA.debugLine="End Sub";
return "";
}
public static String  _btnsalir_click() throws Exception{
 //BA.debugLineNum = 119;BA.debugLine="Private Sub BtnSalir_Click";
 //BA.debugLineNum = 121;BA.debugLine="Select Msgbox2(\"¿Está seguro que desea salir de l";
switch (BA.switchObjectToInt(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("¿Está seguro que desea salir de la aplicación?"),BA.ObjectToCharSequence("ATENCIÓN"),"SI","","NO",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE)) {
case 0: {
 //BA.debugLineNum = 124;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 break; }
}
;
 //BA.debugLineNum = 129;BA.debugLine="End Sub";
return "";
}
public static String  _cargarcolecciones() throws Exception{
anywheresoftware.b4j.objects.SQL.ResultSetWrapper _cursor = null;
String _unsp = "";
 //BA.debugLineNum = 498;BA.debugLine="Private Sub CargarColecciones";
 //BA.debugLineNum = 499;BA.debugLine="Map1.Initialize";
mostCurrent._map1.Initialize();
 //BA.debugLineNum = 500;BA.debugLine="Map1.Clear";
mostCurrent._map1.Clear();
 //BA.debugLineNum = 502;BA.debugLine="B4XComboBox1.cmbBox.Clear";
mostCurrent._b4xcombobox1._cmbbox /*anywheresoftware.b4a.objects.SpinnerWrapper*/ .Clear();
 //BA.debugLineNum = 513;BA.debugLine="Dim Cursor As JdbcResultSet";
_cursor = new anywheresoftware.b4j.objects.SQL.ResultSetWrapper();
 //BA.debugLineNum = 514;BA.debugLine="Dim unSP As String = \"EXEC SP_MAESTRO_COLECCIONES";
_unsp = "EXEC SP_MAESTRO_COLECCIONES_LISTAR";
 //BA.debugLineNum = 516;BA.debugLine="Cursor = mod_Conexion.sql1.ExecQuery(unSP)";
_cursor = mostCurrent._mod_conexion._sql1 /*anywheresoftware.b4j.objects.SQL*/ .ExecQuery(_unsp);
 //BA.debugLineNum = 518;BA.debugLine="Do While Cursor.NextRow";
while (_cursor.NextRow()) {
 //BA.debugLineNum = 519;BA.debugLine="Map1.Put(Cursor.GetString(\"idcolec\"), Cursor.Get";
mostCurrent._map1.Put((Object)(_cursor.GetString("idcolec")),(Object)(_cursor.GetString("desccolec")));
 //BA.debugLineNum = 520;BA.debugLine="B4XComboBox1.cmbBox.Add(Cursor.GetString(\"descco";
mostCurrent._b4xcombobox1._cmbbox /*anywheresoftware.b4a.objects.SpinnerWrapper*/ .Add(_cursor.GetString("desccolec"));
 }
;
 //BA.debugLineNum = 523;BA.debugLine="Cursor.Close";
_cursor.Close();
 //BA.debugLineNum = 528;BA.debugLine="End Sub";
return "";
}
public static String  _cargarlistado() throws Exception{
anywheresoftware.b4j.objects.SQL.ResultSetWrapper _cursor = null;
String _unsp = "";
String _unaplanilla = "";
String _uncodart = "";
String _undescart = "";
String _uncolor = "";
String _unafecha = "";
String _unresultado = "";
anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
 //BA.debugLineNum = 247;BA.debugLine="Private Sub CargarListado()";
 //BA.debugLineNum = 249;BA.debugLine="Log(\"Entro a la FX --> CargarListado\")";
anywheresoftware.b4a.keywords.Common.LogImpl("53080194","Entro a la FX --> CargarListado",0);
 //BA.debugLineNum = 251;BA.debugLine="ProgressDialogShow2(\"Cargando por favor espere...";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Cargando por favor espere..."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 252;BA.debugLine="FxGlobales.DesactivarModoEstricto";
mostCurrent._fxglobales._desactivarmodoestricto /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 253;BA.debugLine="mod_Conexion.Conexion()";
mostCurrent._mod_conexion._conexion /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 255;BA.debugLine="ClvResultado.Clear";
mostCurrent._clvresultado._clear();
 //BA.debugLineNum = 260;BA.debugLine="Dim unIndice As Int = 0";
_unindice = (int) (0);
 //BA.debugLineNum = 261;BA.debugLine="Dim Cursor As JdbcResultSet";
_cursor = new anywheresoftware.b4j.objects.SQL.ResultSetWrapper();
 //BA.debugLineNum = 262;BA.debugLine="Dim unSP As String = \"EXEC SP_MOBILE_GESTION_DE_D";
_unsp = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_LISTAR_PLANILLAS "+BA.NumberToString(mostCurrent._datosglobales._idcoleccion /*int*/ );
 //BA.debugLineNum = 263;BA.debugLine="Cursor = mod_Conexion.sql1.ExecQuery(unSP)";
_cursor = mostCurrent._mod_conexion._sql1 /*anywheresoftware.b4j.objects.SQL*/ .ExecQuery(_unsp);
 //BA.debugLineNum = 265;BA.debugLine="Do While Cursor.NextRow";
while (_cursor.NextRow()) {
 //BA.debugLineNum = 266;BA.debugLine="Dim unaPlanilla As String = Cursor.GetString(\"nr";
_unaplanilla = _cursor.GetString("nroplanilla");
 //BA.debugLineNum = 267;BA.debugLine="Dim unCodArt As String = Cursor.GetString(\"codar";
_uncodart = _cursor.GetString("codart");
 //BA.debugLineNum = 268;BA.debugLine="Dim unDescArt As String = Cursor.GetString(\"desc";
_undescart = _cursor.GetString("descart");
 //BA.debugLineNum = 269;BA.debugLine="Dim unColor As String = Cursor.GetString(\"Color\"";
_uncolor = _cursor.GetString("Color");
 //BA.debugLineNum = 270;BA.debugLine="Dim unaFecha As String = Cursor.GetString(\"fecha";
_unafecha = _cursor.GetString("fechaalta");
 //BA.debugLineNum = 271;BA.debugLine="Dim unResultado As String = \"Pl: \" & unaPlanilla";
_unresultado = "Pl: "+_unaplanilla+" - Art: "+_uncodart+" - "+_undescart+" - Col: "+_uncolor+" - Fe: "+_unafecha;
 //BA.debugLineNum = 274;BA.debugLine="Dim xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 275;BA.debugLine="Dim p As B4XView = xui.CreatePanel(\"\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = _xui.CreatePanel(processBA,"");
 //BA.debugLineNum = 277;BA.debugLine="p.SetLayoutAnimated(100,0,0,100%x,40dip)";
_p.SetLayoutAnimated((int) (100),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 278;BA.debugLine="p.LoadLayout(\"itemRemito\")";
_p.LoadLayout("itemRemito",mostCurrent.activityBA);
 //BA.debugLineNum = 280;BA.debugLine="ItemLblRemito.Text = unResultado";
mostCurrent._itemlblremito.setText(BA.ObjectToCharSequence(_unresultado));
 //BA.debugLineNum = 282;BA.debugLine="ClvResultado.Add(p,\"\")";
mostCurrent._clvresultado._add(_p,(Object)(""));
 //BA.debugLineNum = 285;BA.debugLine="ItemLblRemito.Tag = unIndice";
mostCurrent._itemlblremito.setTag((Object)(_unindice));
 //BA.debugLineNum = 286;BA.debugLine="ItemBtnRemito.Tag = unIndice";
mostCurrent._itembtnremito.setTag((Object)(_unindice));
 //BA.debugLineNum = 288;BA.debugLine="unIndice = unIndice + 1";
_unindice = (int) (_unindice+1);
 }
;
 //BA.debugLineNum = 292;BA.debugLine="Cursor.Close";
_cursor.Close();
 //BA.debugLineNum = 297;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 299;BA.debugLine="Log(\"Finalizó de ejecutar la FX --> CargarListado";
anywheresoftware.b4a.keywords.Common.LogImpl("53080244","Finalizó de ejecutar la FX --> CargarListado",0);
 //BA.debugLineNum = 301;BA.debugLine="End Sub";
return "";
}
public static String  _cinputboxbtn_click() throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _b = null;
 //BA.debugLineNum = 439;BA.debugLine="Sub cInputBoxBtn_click";
 //BA.debugLineNum = 440;BA.debugLine="Try";
try { //BA.debugLineNum = 441;BA.debugLine="Dim b As Button";
_b = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 442;BA.debugLine="b=Sender";
_b = (anywheresoftware.b4a.objects.ButtonWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ButtonWrapper(), (android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 443;BA.debugLine="EditBox.Button_click(b.Tag,eb,Activity)";
mostCurrent._editbox._button_click /*String*/ (mostCurrent.activityBA,BA.ObjectToString(_b.getTag()),mostCurrent._eb,mostCurrent._activity);
 } 
       catch (Exception e6) {
			processBA.setLastException(e6); //BA.debugLineNum = 445;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 446;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("53473415",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 449;BA.debugLine="End Sub";
return "";
}
public static String  _escaner_scan_error() throws Exception{
 //BA.debugLineNum = 238;BA.debugLine="Private Sub Escaner_scan_error()";
 //BA.debugLineNum = 240;BA.debugLine="End Sub";
return "";
}
public static String  _escaner_scanner_started() throws Exception{
 //BA.debugLineNum = 207;BA.debugLine="Private Sub Escaner_scanner_started()";
 //BA.debugLineNum = 209;BA.debugLine="End Sub";
return "";
}
public static String  _escaner_scanner_stopped() throws Exception{
 //BA.debugLineNum = 234;BA.debugLine="Private Sub Escaner_scanner_stopped()";
 //BA.debugLineNum = 236;BA.debugLine="End Sub";
return "";
}
public static String  _escaner_scanner_touched() throws Exception{
 //BA.debugLineNum = 242;BA.debugLine="Private Sub Escaner_scanner_touched";
 //BA.debugLineNum = 244;BA.debugLine="End Sub";
return "";
}
public static String  _escaner_scanresult() throws Exception{
String _unresultado = "";
 //BA.debugLineNum = 212;BA.debugLine="Private Sub Escaner_scanresult()";
 //BA.debugLineNum = 213;BA.debugLine="Try";
try { //BA.debugLineNum = 214;BA.debugLine="Escaner.Visible = False";
mostCurrent._escaner.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 215;BA.debugLine="Escaner.stopScanner";
mostCurrent._escaner.stopScanner();
 //BA.debugLineNum = 217;BA.debugLine="Dim unResultado As String";
_unresultado = "";
 //BA.debugLineNum = 218;BA.debugLine="unResultado = Escaner.ScanResult";
_unresultado = mostCurrent._escaner.getScanResult();
 //BA.debugLineNum = 220;BA.debugLine="ProcesarEscaneo(unResultado)";
_procesarescaneo(_unresultado);
 } 
       catch (Exception e8) {
			processBA.setLastException(e8); //BA.debugLineNum = 223;BA.debugLine="Escaner.Visible = False";
mostCurrent._escaner.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 224;BA.debugLine="Escaner.stopScanner";
mostCurrent._escaner.stopScanner();
 //BA.debugLineNum = 225;BA.debugLine="ToastMessageShow(\"#ERROR: ha ocurrido un error n";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("#ERROR: ha ocurrido un error no controlado. - "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 232;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 24;BA.debugLine="Dim Awake As PhoneWakeState";
mostCurrent._awake = new anywheresoftware.b4a.phone.Phone.PhoneWakeState();
 //BA.debugLineNum = 26;BA.debugLine="Private PanelListadoPrincipal As Panel";
mostCurrent._panellistadoprincipal = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private ClvResultado As CustomListView";
mostCurrent._clvresultado = new b4a.example3.customlistview();
 //BA.debugLineNum = 29;BA.debugLine="Private ItemLblRemito As B4XView";
mostCurrent._itemlblremito = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private ItemBtnRemito As B4XView";
mostCurrent._itembtnremito = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private LblUsuarioDato As Label";
mostCurrent._lblusuariodato = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private Escaner As zxScannerLiveView";
mostCurrent._escaner = new zxscanwrapper.zxScanWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private BtnRefrescar As Button";
mostCurrent._btnrefrescar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private BtnIngMan As Button";
mostCurrent._btningman = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Dim eb As cInputBox";
mostCurrent._eb = new JHS.zxScannerLiveView.editbox._cinputbox();
 //BA.debugLineNum = 42;BA.debugLine="Private PnlColeccion As Panel";
mostCurrent._pnlcoleccion = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private B4XComboBox1 As B4XComboBox";
mostCurrent._b4xcombobox1 = new JHS.zxScannerLiveView.b4xcombobox();
 //BA.debugLineNum = 44;BA.debugLine="Private BtnColecOk As Button";
mostCurrent._btncolecok = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private BtnColecCancel As Button";
mostCurrent._btncoleccancel = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Dim Map1 As Map";
mostCurrent._map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 50;BA.debugLine="Private LblColeccionDato As Label";
mostCurrent._lblcolecciondato = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private LblNevegacion As Label";
mostCurrent._lblnevegacion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static String  _itembtnremito_click() throws Exception{
String _unresultado = "";
anywheresoftware.b4a.objects.ButtonWrapper _unbtn = null;
int _unindiceseleccionado = 0;
anywheresoftware.b4a.objects.B4XViewWrapper _pnl = null;
Object _v = null;
anywheresoftware.b4a.objects.B4XViewWrapper _b4xv = null;
int _undesde = 0;
int _unhasta = 0;
int _unaplanillasel = 0;
 //BA.debugLineNum = 134;BA.debugLine="Private Sub ItemBtnRemito_Click";
 //BA.debugLineNum = 135;BA.debugLine="Try";
try { //BA.debugLineNum = 138;BA.debugLine="Dim unResultado As String";
_unresultado = "";
 //BA.debugLineNum = 139;BA.debugLine="Dim unBtn As Button = Sender ' para obtener el i";
_unbtn = new anywheresoftware.b4a.objects.ButtonWrapper();
_unbtn = (anywheresoftware.b4a.objects.ButtonWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ButtonWrapper(), (android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 140;BA.debugLine="Dim unIndiceSeleccionado As Int";
_unindiceseleccionado = 0;
 //BA.debugLineNum = 141;BA.debugLine="unIndiceSeleccionado = unBtn.Tag";
_unindiceseleccionado = (int)(BA.ObjectToNumber(_unbtn.getTag()));
 //BA.debugLineNum = 143;BA.debugLine="Dim pnl As B4XView";
_pnl = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 144;BA.debugLine="pnl = ClvResultado.GetPanel(unIndiceSeleccionado";
_pnl = mostCurrent._clvresultado._getpanel(_unindiceseleccionado).getParent();
 //BA.debugLineNum = 146;BA.debugLine="For Each v As Object In pnl.GetAllViewsRecursive";
{
final anywheresoftware.b4a.BA.IterableList group8 = _pnl.GetAllViewsRecursive();
final int groupLen8 = group8.getSize()
;int index8 = 0;
;
for (; index8 < groupLen8;index8++){
_v = group8.Get(index8);
 //BA.debugLineNum = 147;BA.debugLine="If V Is Label Then";
if (_v instanceof android.widget.TextView) { 
 //BA.debugLineNum = 148;BA.debugLine="Dim B4xV As B4XView = V";
_b4xv = new anywheresoftware.b4a.objects.B4XViewWrapper();
_b4xv = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_v));
 //BA.debugLineNum = 149;BA.debugLine="unResultado = B4xV.Text";
_unresultado = _b4xv.getText();
 //BA.debugLineNum = 150;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 155;BA.debugLine="Dim unDesde As Int = (unResultado.IndexOf(\":\") +";
_undesde = (int) ((_unresultado.indexOf(":")+1));
 //BA.debugLineNum = 156;BA.debugLine="Dim unHasta As Int = (unResultado.IndexOf(\"-\") -";
_unhasta = (int) ((_unresultado.indexOf("-")-1));
 //BA.debugLineNum = 157;BA.debugLine="Dim unaPlanillaSel As Int = unResultado.SubStrin";
_unaplanillasel = (int)(Double.parseDouble(_unresultado.substring(_undesde,_unhasta)));
 //BA.debugLineNum = 160;BA.debugLine="Select Msgbox2(\"¿Desea editar la Planilla N° \" &";
switch (BA.switchObjectToInt(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("¿Desea editar la Planilla N° "+BA.NumberToString(_unaplanillasel)+"?"),BA.ObjectToCharSequence("ATENCIÓN"),"SI","","NO",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE)) {
case 0: {
 //BA.debugLineNum = 163;BA.debugLine="If ValidarPlanilla(unaPlanillaSel) = True Then";
if (_validarplanilla(_unaplanillasel)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 165;BA.debugLine="DatosGlobales.PlanillaSeleccionada = 0";
mostCurrent._datosglobales._planillaseleccionada /*int*/  = (int) (0);
 //BA.debugLineNum = 166;BA.debugLine="DatosGlobales.PlanillaSeleccionada = unaPlani";
mostCurrent._datosglobales._planillaseleccionada /*int*/  = _unaplanillasel;
 //BA.debugLineNum = 168;BA.debugLine="If (Escaner.Visible = True) Then";
if ((mostCurrent._escaner.getVisible()==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 169;BA.debugLine="Escaner.Visible = False";
mostCurrent._escaner.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 170;BA.debugLine="Escaner.stopScanner";
mostCurrent._escaner.stopScanner();
 };
 //BA.debugLineNum = 173;BA.debugLine="StartActivity(PlanillaDetalle)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._planilladetalle.getObject()));
 }else {
 };
 break; }
}
;
 } 
       catch (Exception e32) {
			processBA.setLastException(e32); //BA.debugLineNum = 184;BA.debugLine="Msgbox(\"#ERROR: \" & LastException, \"Mensaje del";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA))),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 185;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("52621491",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 188;BA.debugLine="End Sub";
return "";
}
public static String  _lblcoleccionbtn_click() throws Exception{
 //BA.debugLineNum = 457;BA.debugLine="Private Sub LblColeccionBtn_Click";
 //BA.debugLineNum = 461;BA.debugLine="If PnlColeccion.Visible = True Then";
if (mostCurrent._pnlcoleccion.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 462;BA.debugLine="PnlColeccion.Visible = False";
mostCurrent._pnlcoleccion.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 464;BA.debugLine="PnlColeccion.Visible = True";
mostCurrent._pnlcoleccion.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 468;BA.debugLine="End Sub";
return "";
}
public static String  _procesarescaneo(String _unaplanilla) throws Exception{
boolean _unresu = false;
 //BA.debugLineNum = 304;BA.debugLine="Private Sub ProcesarEscaneo(unaPlanilla As String)";
 //BA.debugLineNum = 307;BA.debugLine="Dim unResu As Boolean = False";
_unresu = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 310;BA.debugLine="If unaPlanilla = \"\" Then";
if ((_unaplanilla).equals("")) { 
 //BA.debugLineNum = 311;BA.debugLine="Msgbox(\"#ERROR: La planilla no es válida o no se";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: La planilla no es válida o no se encuentra en un estado correcto"),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 //BA.debugLineNum = 312;BA.debugLine="Return unResu";
if (true) return BA.ObjectToString(_unresu);
 };
 //BA.debugLineNum = 315;BA.debugLine="Select Msgbox2(\"Se va a controlar la Planilla N°";
switch (BA.switchObjectToInt(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Se va a controlar la Planilla N° "+_unaplanilla+""),BA.ObjectToCharSequence("ATENCIÓN"),"SI","","NO",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE)) {
case 0: {
 //BA.debugLineNum = 318;BA.debugLine="If ValidarPlanilla(unaPlanilla) = True Then";
if (_validarplanilla((int)(Double.parseDouble(_unaplanilla)))==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 321;BA.debugLine="DatosGlobales.PlanillaSeleccionada = 0";
mostCurrent._datosglobales._planillaseleccionada /*int*/  = (int) (0);
 //BA.debugLineNum = 322;BA.debugLine="DatosGlobales.PlanillaSeleccionada = unaPlanil";
mostCurrent._datosglobales._planillaseleccionada /*int*/  = (int)(Double.parseDouble(_unaplanilla));
 //BA.debugLineNum = 327;BA.debugLine="If (Escaner.Visible = True) Then";
if ((mostCurrent._escaner.getVisible()==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 328;BA.debugLine="Escaner.Visible = False";
mostCurrent._escaner.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 329;BA.debugLine="Escaner.stopScanner";
mostCurrent._escaner.stopScanner();
 };
 //BA.debugLineNum = 333;BA.debugLine="StartActivity(PlanillaDetalle)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._planilladetalle.getObject()));
 //BA.debugLineNum = 335;BA.debugLine="unResu = True";
_unresu = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 339;BA.debugLine="Msgbox(\"#ERROR: La planilla no es válida o no";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("#ERROR: La planilla no es válida o no se encuentra en un estado correcto"),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 };
 break; }
}
;
 //BA.debugLineNum = 344;BA.debugLine="Return unResu";
if (true) return BA.ObjectToString(_unresu);
 //BA.debugLineNum = 346;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 11;BA.debugLine="Private unaLista As List";
_unalista = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 12;BA.debugLine="Private unIndice As Int";
_unindice = 0;
 //BA.debugLineNum = 13;BA.debugLine="Private unTextoSel As String";
_untextosel = "";
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
public static boolean  _validarplanilla(int _unaplanilla) throws Exception{
String _unresu = "";
String _unmensaje = "";
boolean _unresultado = false;
anywheresoftware.b4j.objects.SQL.ResultSetWrapper _cursor = null;
String _unsp = "";
 //BA.debugLineNum = 348;BA.debugLine="Sub ValidarPlanilla(unaPlanilla As Int)As Boolean";
 //BA.debugLineNum = 352;BA.debugLine="Dim unResu As String";
_unresu = "";
 //BA.debugLineNum = 353;BA.debugLine="Dim unMensaje As String";
_unmensaje = "";
 //BA.debugLineNum = 354;BA.debugLine="Dim unResultado As Boolean";
_unresultado = false;
 //BA.debugLineNum = 356;BA.debugLine="Dim Cursor As JdbcResultSet";
_cursor = new anywheresoftware.b4j.objects.SQL.ResultSetWrapper();
 //BA.debugLineNum = 357;BA.debugLine="Dim unSP As String = \"EXEC SP_MOBILE_GESTION_DE_D";
_unsp = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_CABECERA_GUARDAR "+BA.NumberToString(_unaplanilla)+", "+BA.NumberToString(mostCurrent._datosglobales._idusuario /*int*/ )+", '"+mostCurrent._datosglobales._nombredispositivo /*String*/ +"'";
 //BA.debugLineNum = 359;BA.debugLine="Cursor = mod_Conexion.sql1.ExecQuery(unSP)";
_cursor = mostCurrent._mod_conexion._sql1 /*anywheresoftware.b4j.objects.SQL*/ .ExecQuery(_unsp);
 //BA.debugLineNum = 361;BA.debugLine="Do While Cursor.NextRow";
while (_cursor.NextRow()) {
 //BA.debugLineNum = 363;BA.debugLine="unResu = Cursor.GetString(\"RESULTADO\")";
_unresu = _cursor.GetString("RESULTADO");
 //BA.debugLineNum = 364;BA.debugLine="unMensaje = Cursor.GetString(\"MENSAJE\")";
_unmensaje = _cursor.GetString("MENSAJE");
 }
;
 //BA.debugLineNum = 367;BA.debugLine="Cursor.Close";
_cursor.Close();
 //BA.debugLineNum = 372;BA.debugLine="If (unResu = 1) Or (unResu = 3) Then";
if (((_unresu).equals(BA.NumberToString(1))) || ((_unresu).equals(BA.NumberToString(3)))) { 
 //BA.debugLineNum = 373;BA.debugLine="unResultado = True";
_unresultado = anywheresoftware.b4a.keywords.Common.True;
 }else if(((_unresu).equals(BA.NumberToString(2))) || ((_unresu).equals(BA.NumberToString(0))) || ((_unresu).equals(BA.NumberToString(4))) || ((_unresu).equals(BA.NumberToString(5)))) { 
 //BA.debugLineNum = 375;BA.debugLine="unResultado = False";
_unresultado = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 376;BA.debugLine="Msgbox(unMensaje, \"Mensaje del sistema\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_unmensaje),BA.ObjectToCharSequence("Mensaje del sistema"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 379;BA.debugLine="Return unResultado";
if (true) return _unresultado;
 //BA.debugLineNum = 381;BA.debugLine="End Sub";
return false;
}
}
