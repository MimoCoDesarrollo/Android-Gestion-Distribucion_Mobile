B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=11
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

	
	Private unaLista As List
	Private unIndice As Int
	Private unTextoSel As String




End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Dim Awake As PhoneWakeState

	Private PanelListadoPrincipal As Panel
	
	Private ClvResultado As CustomListView
	Private ItemLblRemito As B4XView
	
	Private ItemBtnRemito As B4XView
	
	Private LblUsuarioDato As Label

	Private Escaner As zxScannerLiveView
	
	Private BtnRefrescar As Button
	Private BtnIngMan As Button
	Dim eb As cInputBox
	
	
	Private PnlColeccion As Panel
	Private B4XComboBox1 As B4XComboBox
	Private BtnColecOk As Button
	Private BtnColecCancel As Button
	
	Dim Map1 As Map
	
	
	Private LblColeccionDato As Label
	
	Private LblNevegacion As Label

End Sub

Sub Activity_Create(FirstTime As Boolean)
	Try
		'Do not forget to load the layout file created with the visual designer. For example:
		'Activity.LoadLayout("Layout1")
		ProgressDialogShow2("Cargando por favor espere...", False)
	
		'CARGO EL ACTIVITY
		Activity.LoadLayout("planillaslistado")

		Escaner.HudVisible = True
		Escaner.PlaySound = True
		Escaner.Visible = False
		
		CargarColecciones 'cargar colecciones dsd la bbdd

		PnlColeccion.Visible = True

		ProgressDialogHide

		FxGlobales.DesactivarModoEstricto
	
		Awake.KeepAlive(True) 'para q no se apague
			
		
	Catch
		Msgbox("#ERROR: " & LastException, "Mensaje del sistema")
		Log(LastException)
	End Try

End Sub


Sub Activity_Resume
	Try
		Log("Pase por --> Activity_Resume")
	
		LblUsuarioDato.Text = DatosGlobales.DescUsuario
	
		ClvResultado.Clear
		CargarListado

		If DatosGlobales.EsProduccion = False Then
			LblNevegacion.Text = "    Navegación" & " - AMBIENTE: TEST"
		Else
			LblNevegacion.Text = "    Navegación"
		End If
			
	Catch
		Msgbox("#ERROR: " & LastException, "Mensaje del sistema")
		Log(LastException)
	End Try
	
	Log("Termino de ejecutar --> Activity_Resume")
	
End Sub

Sub Activity_Pause (UserClosed As Boolean)

	Log("El activity entro en Pausa")
	
End Sub


Private Sub BtnSalir_Click
	
	Select Msgbox2("¿Está seguro que desea salir de la aplicación?" , "ATENCIÓN", "SI",  "" , "NO", Null)
		Case DialogResponse.POSITIVE
		'Activity.Finish
		ExitApplication		
	End Select	
		

	
End Sub




Private Sub ItemBtnRemito_Click
	Try
		'PARA OBTENER EL TAG DEL BOTON SELECCIONADO EN EL LISTADO
	
		Dim unResultado As String
		Dim unBtn As Button = Sender ' para obtener el indice seleccionado
		Dim unIndiceSeleccionado As Int
		unIndiceSeleccionado = unBtn.Tag
	
		Dim pnl As B4XView
		pnl = ClvResultado.GetPanel(unIndiceSeleccionado).Parent
	
		For Each v As Object In pnl.GetAllViewsRecursive
			If V Is Label Then
				Dim B4xV As B4XView = V
				unResultado = B4xV.Text
				Exit
			End If
		Next
	
			
		Dim unDesde As Int = (unResultado.IndexOf(":") + 1)
		Dim unHasta As Int = (unResultado.IndexOf("-") - 1)
		Dim unaPlanillaSel As Int = unResultado.SubString2(unDesde, unHasta)
	
	
		Select Msgbox2("¿Desea editar la Planilla N° " & unaPlanillaSel & "?", "ATENCIÓN", "SI",  "" , "NO", Null)
			Case DialogResponse.POSITIVE
			
				If ValidarPlanilla(unaPlanillaSel) = True Then
			
					DatosGlobales.PlanillaSeleccionada = 0
					DatosGlobales.PlanillaSeleccionada = unaPlanillaSel
						
					If (Escaner.Visible = True) Then
						Escaner.Visible = False
						Escaner.stopScanner
					End If
				
					StartActivity(PlanillaDetalle)
		
				Else
					'ToastMessageShow("#ERROR: La planilla no es válida o no se encuentra en un estado correcto", True)
					'Msgbox("#ERROR: La planilla no es válida o no se encuentra en un estado correcto", "Mensaje del sistema")
				End If
			
		
		End Select
			
	Catch
		Msgbox("#ERROR: " & LastException, "Mensaje del sistema")
		Log(LastException)
	End Try
	
End Sub

Private Sub BtnEscanear_Click

	If (Escaner.Visible = True) Then
		Escaner.Visible = False
		Escaner.stopScanner
		Else
		Escaner.Visible = True
		Escaner.startScanner
	End If
	
	'ProcesarEscaneo("0000170005008745103") 'para prueba sin scanner
	
End Sub




Private Sub Escaner_scanner_started()
	
End Sub


Private Sub Escaner_scanresult()
	Try
		Escaner.Visible = False
		Escaner.stopScanner
		
		Dim unResultado As String
		unResultado = Escaner.ScanResult
		
		ProcesarEscaneo(unResultado)
			
	Catch
		Escaner.Visible = False
		Escaner.stopScanner
		ToastMessageShow("#ERROR: ha ocurrido un error no controlado. - " & LastException  , True)
	End Try
	
	
	
	
	
End Sub

Private Sub Escaner_scanner_stopped()
	
End Sub

Private Sub Escaner_scan_error()
	
End Sub

Private Sub Escaner_scanner_touched
	
End Sub


Private Sub CargarListado()
	
	Log("Entro a la FX --> CargarListado")
	
	ProgressDialogShow2("Cargando por favor espere...", False)
	FxGlobales.DesactivarModoEstricto
	mod_Conexion.Conexion()
	
	ClvResultado.Clear
	
	'##############################################################################################################################################
	' LLAMADO A SERVIDOR DE BASE DE DATOS

	Dim unIndice As Int = 0
	Dim Cursor As JdbcResultSet
	Dim unSP As String = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_LISTAR_PLANILLAS " & DatosGlobales.IdColeccion
	Cursor = mod_Conexion.sql1.ExecQuery(unSP)
		
	Do While Cursor.NextRow
		Dim unaPlanilla As String = Cursor.GetString("nroplanilla")
		Dim unCodArt As String = Cursor.GetString("codart")
		Dim unDescArt As String = Cursor.GetString("descart")
		Dim unColor As String = Cursor.GetString("Color")
		Dim unaFecha As String = Cursor.GetString("fechaalta")
		Dim unResultado As String = "Pl: " & unaPlanilla & " - Art: " & unCodArt & " - " & unDescArt & " - Col: " & unColor & " - Fe: " & unaFecha
			
		'SE AGREGA AL LISTADO SECUENCIALMENTE
		Dim xui As XUI
		Dim p As B4XView = xui.CreatePanel("")
				
		p.SetLayoutAnimated(100,0,0,100%x,40dip)
		p.LoadLayout("itemRemito")
				
		ItemLblRemito.Text = unResultado
							
		ClvResultado.Add(p,"")
				
		'LE AGREGO UN INDICE AL LABEL Y AL BOTON PARA LUEGO OBTENER LOS DATOS ASOCIADOS AL INDICE EN EL LISTADO
		ItemLblRemito.Tag = unIndice
		ItemBtnRemito.Tag = unIndice
					
		unIndice = unIndice + 1
									
	Loop

	Cursor.Close

	' FIN DEL LLAMADO A SERVIDOR DE BASE DE DATOS
	'##############################################################################################################################################

	ProgressDialogHide

	Log("Finalizó de ejecutar la FX --> CargarListado")

End Sub


Private Sub ProcesarEscaneo(unaPlanilla As String)

	'Dim unResu As String
	Dim unResu As Boolean = False

	
	If unaPlanilla = "" Then
		Msgbox("#ERROR: La planilla no es válida o no se encuentra en un estado correcto", "Mensaje del sistema")
		Return unResu
	End If
	
	Select Msgbox2("Se va a controlar la Planilla N° " & unaPlanilla & "" , "ATENCIÓN", "SI",  "" , "NO", Null)
		Case DialogResponse.POSITIVE
			
			If ValidarPlanilla(unaPlanilla) = True Then
				

				DatosGlobales.PlanillaSeleccionada = 0
				DatosGlobales.PlanillaSeleccionada = unaPlanilla
								
				'ToastMessageShow("Se va a controlar la planilla N° " & unaPlanilla, True)
				
				
				If (Escaner.Visible = True) Then
					Escaner.Visible = False
					Escaner.stopScanner
				End If
				
				
				StartActivity(PlanillaDetalle)
				
				unResu = True
			
			Else
				'ToastMessageShow("#ERROR: La planilla no es válida o no se encuentra en un estado correcto", True)
				Msgbox("#ERROR: La planilla no es válida o no se encuentra en un estado correcto", "Mensaje del sistema")
			End If
					
	End Select

	Return unResu

End Sub

Sub ValidarPlanilla(unaPlanilla As Int)As Boolean
	'##############################################################################################################################################
	' LLAMADO A SERVIDOR DE BASE DE DATOS
	
	Dim unResu As String
	Dim unMensaje As String
	Dim unResultado As Boolean
	
	Dim Cursor As JdbcResultSet
	Dim unSP As String = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_CABECERA_GUARDAR " & unaPlanilla & ", " & DatosGlobales.IdUsuario & ", '" & DatosGlobales.NombreDispositivo & "'"
				
	Cursor = mod_Conexion.sql1.ExecQuery(unSP)
		
	Do While Cursor.NextRow
			
		unResu = Cursor.GetString("RESULTADO")
		unMensaje = Cursor.GetString("MENSAJE")
	Loop

	Cursor.Close

	' FIN DEL LLAMADO A SERVIDOR DE BASE DE DATOS
	'##############################################################################################################################################
	
	If (unResu = 1) Or (unResu = 3) Then
		unResultado = True
	Else if (unResu = 2) Or (unResu = 0) Or (unResu = 4) Or (unResu = 5)  Then
		unResultado = False
		Msgbox(unMensaje, "Mensaje del sistema")
	End If
	
	Return unResultado
	
End Sub



Private Sub BtnRefrescar_Click
	Try
		ClvResultado.Clear
		CargarListado
	Catch
		Msgbox("#ERROR: " & LastException, "Mensaje del sistema")
		Log(LastException)
	End Try

End Sub




#Region "InputBox"

Private Sub BtnIngMan_Click
	Try
		'Llamado al EditBox
		
		If eb.Visible = True Then
			EditBox.Hide(eb)
			Return True
		Else
			Dim r As Int
			r=EditBox.Show(eb,Activity,"Mensaje del sistema","Ingrese número de planilla aquí","OK","Cancel",eb.cInputBoxEditText.INPUT_TYPE_NUMBERS)  'eb.cInputBoxEditText.INPUT_TYPE_TEXT)
			If r=DialogResponse.POSITIVE Then
				Log("Va a procesar manualmente a la planilla N°: " & eb.Result)
				If ProcesarEscaneo(eb.Result) = True Then
					ClvResultado.Clear
					CargarListado
				End If
			End If
		End If
			
	Catch
		Msgbox("#ERROR: " & LastException, "Mensaje del sistema")
		Log(LastException)
	End Try
			
	End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
		Try
			If KeyCode=KeyCodes.KEYCODE_BACK And eb.Visible=True Then
				EditBox.Hide(eb)
				Return True
			End If
		Catch
			Msgbox("#ERROR: " & LastException, "Mensaje del sistema")
			Log(LastException)
		End Try
End Sub

Sub cInputBoxBtn_click
	Try
		Dim b As Button
		b=Sender
		EditBox.Button_click(b.Tag,eb,Activity)
	Catch
		Msgbox("#ERROR: " & LastException, "Mensaje del sistema")
		Log(LastException)
	End Try

End Sub

#End Region



#Region "Coleccion"

Private Sub LblColeccionBtn_Click
	
	'CargarColecciones
	
	If PnlColeccion.Visible = True Then
		PnlColeccion.Visible = False
	Else
		PnlColeccion.Visible = True
	End If
	
	
End Sub


Private Sub BtnColecOk_Click
	Try
		Dim IdColeccSel As Int = Map1.GetKeyAt(B4XComboBox1.cmbBox.SelectedIndex)
		DatosGlobales.IdColeccion = IdColeccSel
	
		If DatosGlobales.IdColeccion > 0 Then
		
			CargarListado
		
		End If
		
		'Msgbox("Coleccion seleccionada: " & B4XComboBox1.cmbBox.SelectedItem & " - Indice: " & B4XComboBox1.cmbBox.SelectedIndex & " - IdColeccion: " & Map1.GetKeyAt(B4XComboBox1.cmbBox.SelectedIndex), "Mensaje del sistema")
		LblColeccionDato.Text = B4XComboBox1.cmbBox.SelectedItem
		PnlColeccion.Visible = False
			
	Catch
		Msgbox("#ERROR: " & LastException, "Mensaje del sistema")
		Log(LastException)
	End Try
	
End Sub

Private Sub BtnColecCancel_Click
	PnlColeccion.Visible = False
End Sub


Private Sub CargarColecciones
	Map1.Initialize
	Map1.Clear

	B4XComboBox1.cmbBox.Clear

	
	
	'##############################################################################################################################################
	' LLAMADO A SERVIDOR DE BASE DE DATOS
	
'	Dim unResu As String
'	Dim unMensaje As String
'	Dim unResultado As Boolean
	
	Dim Cursor As JdbcResultSet
	Dim unSP As String = "EXEC SP_MAESTRO_COLECCIONES_LISTAR"
				
	Cursor = mod_Conexion.sql1.ExecQuery(unSP)
		
	Do While Cursor.NextRow
		Map1.Put(Cursor.GetString("idcolec"), Cursor.GetString("desccolec"))
		B4XComboBox1.cmbBox.Add(Cursor.GetString("desccolec"))
	Loop

	Cursor.Close

	' FIN DEL LLAMADO A SERVIDOR DE BASE DE DATOS
	'##############################################################################################################################################

End Sub



#End Region



