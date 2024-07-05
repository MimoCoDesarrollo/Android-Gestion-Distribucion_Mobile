package JHS.zxScannerLiveView.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_contenidoencabezado{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[ContenidoEncabezado/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="PanelContenido.Width=100%x"[ContenidoEncabezado/General script]
views.get("panelcontenido").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 4;BA.debugLine="PanelContenido.Height=150%y"[ContenidoEncabezado/General script]
views.get("panelcontenido").vw.setHeight((int)((150d / 100 * height)));
//BA.debugLineNum = 5;BA.debugLine="PanelContenido.Top=0"[ContenidoEncabezado/General script]
views.get("panelcontenido").vw.setTop((int)(0d));
//BA.debugLineNum = 6;BA.debugLine="PanelContenido.Left=0"[ContenidoEncabezado/General script]
views.get("panelcontenido").vw.setLeft((int)(0d));

}
}