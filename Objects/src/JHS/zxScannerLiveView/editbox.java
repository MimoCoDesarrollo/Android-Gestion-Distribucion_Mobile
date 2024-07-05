package JHS.zxScannerLiveView;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class editbox {
private static editbox mostCurrent = new editbox();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static boolean _buttonclicked = false;
public b4a.example.dateutils _dateutils = null;
public JHS.zxScannerLiveView.main _main = null;
public JHS.zxScannerLiveView.mod_conexion _mod_conexion = null;
public JHS.zxScannerLiveView.datosglobales _datosglobales = null;
public JHS.zxScannerLiveView.fxglobales _fxglobales = null;
public JHS.zxScannerLiveView.starter _starter = null;
public JHS.zxScannerLiveView.planillaslistado _planillaslistado = null;
public JHS.zxScannerLiveView.planilladetalle _planilladetalle = null;
public JHS.zxScannerLiveView.b4xcollections _b4xcollections = null;
public JHS.zxScannerLiveView.xuiviewsutils _xuiviewsutils = null;
public JHS.zxScannerLiveView.httputils2service _httputils2service = null;
public static class _cinputbox{
public boolean IsInitialized;
public anywheresoftware.b4a.objects.ButtonWrapper cInputBoxOKBtn;
public anywheresoftware.b4a.objects.ButtonWrapper cInputBoxCancelBtn;
public anywheresoftware.b4a.objects.EditTextWrapper cInputBoxEditText;
public anywheresoftware.b4a.objects.LabelWrapper cInputBoxLabel;
public anywheresoftware.b4a.objects.PanelWrapper HolderPnl;
public anywheresoftware.b4a.objects.PanelWrapper FrontPnl;
public boolean Visible;
public String Result;
public int Response;
public void Initialize() {
IsInitialized = true;
cInputBoxOKBtn = new anywheresoftware.b4a.objects.ButtonWrapper();
cInputBoxCancelBtn = new anywheresoftware.b4a.objects.ButtonWrapper();
cInputBoxEditText = new anywheresoftware.b4a.objects.EditTextWrapper();
cInputBoxLabel = new anywheresoftware.b4a.objects.LabelWrapper();
HolderPnl = new anywheresoftware.b4a.objects.PanelWrapper();
FrontPnl = new anywheresoftware.b4a.objects.PanelWrapper();
Visible = false;
Result = "";
Response = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static String  _button_click(anywheresoftware.b4a.BA _ba,String _b,JHS.zxScannerLiveView.editbox._cinputbox _inp,anywheresoftware.b4a.objects.ActivityWrapper _activity) throws Exception{
anywheresoftware.b4a.phone.Phone _p = null;
 //BA.debugLineNum = 75;BA.debugLine="Sub Button_click(b As String, inp As cInputBox, ac";
 //BA.debugLineNum = 76;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 77;BA.debugLine="p.HideKeyboard(activity)";
_p.HideKeyboard(_activity);
 //BA.debugLineNum = 78;BA.debugLine="ButtonClicked=True";
_buttonclicked = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 79;BA.debugLine="Select b";
switch (BA.switchObjectToInt(_b,"Positive","Cancel")) {
case 0: {
 //BA.debugLineNum = 80;BA.debugLine="Case \"Positive\": inp.Response=DialogResponse.POS";
_inp.Response /*int*/  = anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE;
 break; }
case 1: {
 //BA.debugLineNum = 81;BA.debugLine="Case \"Cancel\": inp.Response=DialogResponse.CANCE";
_inp.Response /*int*/  = anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL;
 break; }
}
;
 //BA.debugLineNum = 83;BA.debugLine="End Sub";
return "";
}
public static String  _hide(anywheresoftware.b4a.BA _ba,JHS.zxScannerLiveView.editbox._cinputbox _inp) throws Exception{
 //BA.debugLineNum = 67;BA.debugLine="Sub Hide(Inp As cInputBox)";
 //BA.debugLineNum = 68;BA.debugLine="Inp.Result=Inp.cInputBoxEditText.Text";
_inp.Result /*String*/  = _inp.cInputBoxEditText /*anywheresoftware.b4a.objects.EditTextWrapper*/ .getText();
 //BA.debugLineNum = 69;BA.debugLine="If Inp.Visible Then";
if (_inp.Visible /*boolean*/ ) { 
 //BA.debugLineNum = 70;BA.debugLine="Inp.HolderPnl.RemoveView";
_inp.HolderPnl /*anywheresoftware.b4a.objects.PanelWrapper*/ .RemoveView();
 //BA.debugLineNum = 71;BA.debugLine="Inp.Visible=False";
_inp.Visible /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Type cInputBox (cInputBoxOKBtn As Button, cInputB";
;
 //BA.debugLineNum = 10;BA.debugLine="Dim ButtonClicked As Boolean";
_buttonclicked = false;
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static int  _show(anywheresoftware.b4a.BA _ba,JHS.zxScannerLiveView.editbox._cinputbox _inp,anywheresoftware.b4a.objects.ActivityWrapper _activity,String _message,String _hint,String _positive,String _cancel,int _inputtype) throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Show(Inp As cInputBox, Activity As Activity, M";
 //BA.debugLineNum = 14;BA.debugLine="Inp.Initialize";
_inp.Initialize();
 //BA.debugLineNum = 16;BA.debugLine="Inp.cInputBoxOKBtn.Initialize(\"cInputBoxBtn\")";
_inp.cInputBoxOKBtn /*anywheresoftware.b4a.objects.ButtonWrapper*/ .Initialize(_ba,"cInputBoxBtn");
 //BA.debugLineNum = 17;BA.debugLine="Inp.cInputBoxOKBtn.Text=Positive";
_inp.cInputBoxOKBtn /*anywheresoftware.b4a.objects.ButtonWrapper*/ .setText(BA.ObjectToCharSequence(_positive));
 //BA.debugLineNum = 18;BA.debugLine="Inp.cInputBoxOKBtn.Tag=\"Positive\"";
_inp.cInputBoxOKBtn /*anywheresoftware.b4a.objects.ButtonWrapper*/ .setTag((Object)("Positive"));
 //BA.debugLineNum = 20;BA.debugLine="Inp.cInputBoxCancelBtn.Initialize(\"cInputBoxBtn\")";
_inp.cInputBoxCancelBtn /*anywheresoftware.b4a.objects.ButtonWrapper*/ .Initialize(_ba,"cInputBoxBtn");
 //BA.debugLineNum = 21;BA.debugLine="Inp.cInputBoxCancelBtn.Text=Cancel";
_inp.cInputBoxCancelBtn /*anywheresoftware.b4a.objects.ButtonWrapper*/ .setText(BA.ObjectToCharSequence(_cancel));
 //BA.debugLineNum = 22;BA.debugLine="Inp.cInputBoxCancelBtn.Tag=\"Cancel\"";
_inp.cInputBoxCancelBtn /*anywheresoftware.b4a.objects.ButtonWrapper*/ .setTag((Object)("Cancel"));
 //BA.debugLineNum = 24;BA.debugLine="Inp.cInputBoxEditText.Initialize(\"cInputBoxEditTe";
_inp.cInputBoxEditText /*anywheresoftware.b4a.objects.EditTextWrapper*/ .Initialize(_ba,"cInputBoxEditText");
 //BA.debugLineNum = 25;BA.debugLine="Inp.cInputBoxEditText.Hint=Hint";
_inp.cInputBoxEditText /*anywheresoftware.b4a.objects.EditTextWrapper*/ .setHint(_hint);
 //BA.debugLineNum = 26;BA.debugLine="Inp.cInputBoxEditText.InputType=InputType";
_inp.cInputBoxEditText /*anywheresoftware.b4a.objects.EditTextWrapper*/ .setInputType(_inputtype);
 //BA.debugLineNum = 28;BA.debugLine="Inp.cInputBoxLabel.Initialize(\"\")";
_inp.cInputBoxLabel /*anywheresoftware.b4a.objects.LabelWrapper*/ .Initialize(_ba,"");
 //BA.debugLineNum = 29;BA.debugLine="Inp.cInputBoxLabel.Text=Message";
_inp.cInputBoxLabel /*anywheresoftware.b4a.objects.LabelWrapper*/ .setText(BA.ObjectToCharSequence(_message));
 //BA.debugLineNum = 30;BA.debugLine="Inp.cInputBoxLabel.TextSize=14dip";
_inp.cInputBoxLabel /*anywheresoftware.b4a.objects.LabelWrapper*/ .setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (14))));
 //BA.debugLineNum = 31;BA.debugLine="Inp.cInputBoxLabel.Typeface=Typeface.DEFAULT_BOLD";
_inp.cInputBoxLabel /*anywheresoftware.b4a.objects.LabelWrapper*/ .setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 32;BA.debugLine="Inp.cInputBoxLabel.TextColor=Colors.RGB(0,0,139)";
_inp.cInputBoxLabel /*anywheresoftware.b4a.objects.LabelWrapper*/ .setTextColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (0),(int) (0),(int) (139)));
 //BA.debugLineNum = 33;BA.debugLine="Inp.cInputBoxLabel.Gravity=Gravity.CENTER_HORIZON";
_inp.cInputBoxLabel /*anywheresoftware.b4a.objects.LabelWrapper*/ .setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 34;BA.debugLine="Inp.cInputBoxlabel.Gravity=Gravity.CENTER_VERTICA";
_inp.cInputBoxLabel /*anywheresoftware.b4a.objects.LabelWrapper*/ .setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_VERTICAL);
 //BA.debugLineNum = 35;BA.debugLine="Inp.HolderPnl.Initialize(\"\")";
_inp.HolderPnl /*anywheresoftware.b4a.objects.PanelWrapper*/ .Initialize(_ba,"");
 //BA.debugLineNum = 36;BA.debugLine="Inp.FrontPnl.Initialize(\"\")";
_inp.FrontPnl /*anywheresoftware.b4a.objects.PanelWrapper*/ .Initialize(_ba,"");
 //BA.debugLineNum = 39;BA.debugLine="Inp.HolderPnl.Color=Colors.Gray";
_inp.HolderPnl /*anywheresoftware.b4a.objects.PanelWrapper*/ .setColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 //BA.debugLineNum = 41;BA.debugLine="Inp.FrontPnl.Color=Colors.ARGB(135,240,248,255)";
_inp.FrontPnl /*anywheresoftware.b4a.objects.PanelWrapper*/ .setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (135),(int) (240),(int) (248),(int) (255)));
 //BA.debugLineNum = 45;BA.debugLine="Activity.AddView(Inp.holderpnl,10dip,100%y/2-150d";
_activity.AddView((android.view.View)(_inp.HolderPnl /*anywheresoftware.b4a.objects.PanelWrapper*/ .getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),_ba)/(double)2-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150))),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),_ba)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (270)));
 //BA.debugLineNum = 46;BA.debugLine="Inp.HolderPnl.AddView(Inp.FrontPnl,5dip,5dip,Inp.";
_inp.HolderPnl /*anywheresoftware.b4a.objects.PanelWrapper*/ .AddView((android.view.View)(_inp.FrontPnl /*anywheresoftware.b4a.objects.PanelWrapper*/ .getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),(int) (_inp.HolderPnl /*anywheresoftware.b4a.objects.PanelWrapper*/ .getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),(int) (_inp.HolderPnl /*anywheresoftware.b4a.objects.PanelWrapper*/ .getHeight()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 48;BA.debugLine="Inp.FrontPnl.AddView(Inp.cInputBoxLabel,15dip,15d";
_inp.FrontPnl /*anywheresoftware.b4a.objects.PanelWrapper*/ .AddView((android.view.View)(_inp.cInputBoxLabel /*anywheresoftware.b4a.objects.LabelWrapper*/ .getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),(int) (_inp.FrontPnl /*anywheresoftware.b4a.objects.PanelWrapper*/ .getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)));
 //BA.debugLineNum = 49;BA.debugLine="Inp.FrontPnl.AddView(Inp.cInputBoxEditText,15dip,";
_inp.FrontPnl /*anywheresoftware.b4a.objects.PanelWrapper*/ .AddView((android.view.View)(_inp.cInputBoxEditText /*anywheresoftware.b4a.objects.EditTextWrapper*/ .getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (125)),(int) (_inp.FrontPnl /*anywheresoftware.b4a.objects.PanelWrapper*/ .getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 50;BA.debugLine="Inp.FrontPnl.AddView(Inp.cInputBoxOKBtn,15dip,190";
_inp.FrontPnl /*anywheresoftware.b4a.objects.PanelWrapper*/ .AddView((android.view.View)(_inp.cInputBoxOKBtn /*anywheresoftware.b4a.objects.ButtonWrapper*/ .getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (190)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 51;BA.debugLine="Inp.FrontPnl.AddView(Inp.cInputBoxCancelBtn,Inp.F";
_inp.FrontPnl /*anywheresoftware.b4a.objects.PanelWrapper*/ .AddView((android.view.View)(_inp.cInputBoxCancelBtn /*anywheresoftware.b4a.objects.ButtonWrapper*/ .getObject()),(int) (_inp.FrontPnl /*anywheresoftware.b4a.objects.PanelWrapper*/ .getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (15))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (190)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 53;BA.debugLine="Inp.cInputBoxEditText.SingleLine=True";
_inp.cInputBoxEditText /*anywheresoftware.b4a.objects.EditTextWrapper*/ .setSingleLine(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 54;BA.debugLine="Inp.cInputBoxEditText.ForceDoneButton=True";
_inp.cInputBoxEditText /*anywheresoftware.b4a.objects.EditTextWrapper*/ .setForceDoneButton(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 56;BA.debugLine="Inp.Visible=True";
_inp.Visible /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 57;BA.debugLine="ButtonClicked=False";
_buttonclicked = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 58;BA.debugLine="Do While Not(ButtonClicked)";
while (anywheresoftware.b4a.keywords.Common.Not(_buttonclicked)) {
 //BA.debugLineNum = 59;BA.debugLine="DoEvents";
anywheresoftware.b4a.keywords.Common.DoEvents();
 }
;
 //BA.debugLineNum = 62;BA.debugLine="Inp.result=Inp.cInputBoxEditText.Text";
_inp.Result /*String*/  = _inp.cInputBoxEditText /*anywheresoftware.b4a.objects.EditTextWrapper*/ .getText();
 //BA.debugLineNum = 63;BA.debugLine="Hide(Inp)";
_hide(_ba,_inp);
 //BA.debugLineNum = 64;BA.debugLine="Return Inp.Response";
if (true) return _inp.Response /*int*/ ;
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return 0;
}
}
