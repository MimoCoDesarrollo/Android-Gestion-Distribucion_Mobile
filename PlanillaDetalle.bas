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

End Sub



Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Dim Awake As PhoneWakeState

	Private Planilla_Detalle_Label_Nro_Planilla As Label
	Private Planilla_Detalle_ClvResultado As CustomListView
	
	
	Private ItemLblRemito As B4XView
	
	Private ItemBtnRemito As B4XView
	
	
	Private BtnVolver As Button
	
	'Private BtnSalir As Button
	Private LabelCab As Label

	Private LabelCabObs As Label
	Private LabelCab2 As Label
	Private LblUsuarioDato As Label
		

	Private B4XTable1 As B4XTable
	

	Private xui As XUI

 	'Dim ObjB4XTableSelections As B4XTableSelections


	Private PaginaActual As Int = 1
	Private CantFilas As Int = 0
	Dim CantCol As Int
	
		
	Private BtnPlanillaFinalizar As Button
	
	Private FinalizoPlanilla As Boolean = False
	
	
	Private BtnReset As Button
	Private LblNevegacion As Label
	Private ScvEncabezado As ScrollView
	Private PanelContenido As Panel
	
	Private LbContenido As Label
	Private WvContenido As WebView
End Sub



#Region "Eventos"


Sub Activity_Create(FirstTime As Boolean)
	Try
			'Do not forget to load the layout file created with the visual designer. For example:
			'Activity.LoadLayout("Layout1")
			B4XTable1.MaximumRowsPerPage = 5000
			Activity.LoadLayout("planilladetalle")
			
			FxGlobales.DesactivarModoEstricto
			
			Awake.KeepAlive(True) 'para q no se apague
			
		   ScvEncabezado.Panel.LoadLayout("ContenidoEncabezado")
		
		Catch 
			Msgbox("#ERROR: " & LastException, "Mensaje del sistema")
			Log(LastException)
		End Try
	End Sub

Sub Activity_Resume
	Try
		LblUsuarioDato.Text = DatosGlobales.DescUsuario
		FxGlobales.DesactivarModoEstricto
		Planilla_Detalle_Label_Nro_Planilla.Text = " Planilla N°: " & DatosGlobales.PlanillaSeleccionada
		
		CargarCabecera(DatosGlobales.PlanillaSeleccionada)
		
		B4XTable1.Clear
		
		CargarDetalle(DatosGlobales.PlanillaSeleccionada)

		B4XTable1_DataUpdated

		If DatosGlobales.EsProduccion = False Then
			LblNevegacion.Text = "    Navegación" & " - AMBIENTE: TEST"
		Else
			LblNevegacion.Text = "    Navegación"
		End If
				
		Catch
			Msgbox("#ERROR: " & LastException, "Mensaje del sistema")
			Log(LastException)
		End Try
	End Sub
	

Sub Activity_Pause (UserClosed As Boolean)
	Try
		Dim unResu As String = ""
		Dim unMensaje As String = ""
		Dim EstNuevo As Int
		Dim EstAnt As Int

		If FinalizoPlanilla = False Then
			Log("Paso por --> Activity_Pause --> NO FINALIZO")
			EstAnt = 1 ' 1 - PLANILLA EN PROCESO
			EstNuevo= 2 ' 2 - PLANILLA EN PAUSA
		Else
			Log("Paso por --> Activity_Pause --> SI FINALIZO")
			EstAnt = 1 ' 1 - PLANILLA EN PROCESO
			EstNuevo= 3 ' 3 - PLANILLA FINALIZADA
		End If
		
		'##############################################################################################################################################
		' LLAMADO A SERVIDOR DE BASE DE DATOS
		Dim Cursor As JdbcResultSet
		Dim unSP As String = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_CABECERA_CAMBIAR_ESTADO " & DatosGlobales.PlanillaSeleccionada & ", " & DatosGlobales.IdUsuario & ", " & EstAnt & ", " & EstNuevo
		
		Log(unSP)
		
'		If (mod_Conexion.sql1.IsInitialized) = True Then
'			Msgbox("")
'		End If
		
		Cursor = mod_Conexion.sql1.ExecQuery(unSP)
		Do While Cursor.NextRow
			Dim unResu As String = Cursor.GetString("RESULTADO")
			Dim unMensaje As String = Cursor.GetString("MENSAJE")
		Loop
		Cursor.Close
		' FIN DEL LLAMADO A SERVIDOR DE BASE DE DATOS
		'##############################################################################################################################################
		
		If unResu = False Then
			Log(unMensaje)
			Msgbox(unMensaje, "Mensaje del sistema")
		End If
			
			
	Catch
		Msgbox("#ERROR: " & LastException, "Mensaje del sistema")
		Log(LastException)
	End Try

	End Sub
	


Private Sub ItemBtnRemito_Click
	Try
		Dim unBtn As Button = Sender ' para obtener el indice seleccionado
		Dim unIndiceSeleccionado As Int
		unIndiceSeleccionado = unBtn.Tag
		
		Dim pnl As B4XView
		pnl = Planilla_Detalle_ClvResultado.GetPanel(unIndiceSeleccionado).Parent
		
		For Each v As Object In pnl.GetAllViewsRecursive
			If V Is Label Then
				Dim B4xV As B4XView = V
				B4xV.Color = Colors.Gray
				Exit
			End If
		Next

		For Each v As Object In pnl.GetAllViewsRecursive
			If V Is Button Then
				Dim B4xV As B4XView = V
				Dim bmp As Bitmap
				bmp.Initialize(File.DirAssets,"plus.png")
				B4xV.SetBitmap(bmp)
				Exit
			End If
		Next
					
			Catch
				Msgbox("#ERROR: " & LastException, "Mensaje del sistema")
				Log(LastException)
			End Try
	End Sub



	Private Sub BtnVolver_Click
		Try
			Select Msgbox2("¿Está seguro que desea pausar la planilla?" , "ATENCIÓN", "SI",  "" , "NO", Null)
				Case DialogResponse.POSITIVE
					Activity.Finish
			End Select
		Catch
			Msgbox("#ERROR: " & LastException, "Mensaje del sistema")
			Log(LastException)
		End Try
	End Sub

	

	Private Sub B4XTable1_CellClicked(ColumnId As String, RowId As Long)
		Try
			Log("Evento 'B4XTable1_CellClicked' ---> Culumna: " & ColumnId  & " - Fila: " & RowId)

			'Para obtener el contenido de la celda seleccionada
			Dim row  As Map = B4XTable1.GetRow(RowId)
			Dim ValorCelda As String = row.Get(ColumnId) 'Obtengo el contenido de la Columna/Fila
			Log("Valor celda: " & ValorCelda)
			
			If ValorCelda <> 0 Then
				PintarCelda(ColumnId,RowId, Colors.Red) 'pinto celda de rojo
			End If
		Catch
			Msgbox("#ERROR: " & LastException, "Mensaje del sistema")
			Log(LastException)
		End Try
			
	End Sub


	Private Sub B4XTable1_CellClicked2(ColumnId As String, RowId As Long)
		Try
			'Log("Evento 'B4XTable1_CellClicked' ---> Culumna: " & ColumnId  & " - Fila: " & RowId)

			'Para obtener el contenido de la celda seleccionada
			Dim row  As Map = B4XTable1.GetRow(RowId)
			Dim ValorCelda As String = row.Get(ColumnId) 'Obtengo el contenido de la Columna/Fila
			
			PintarCeldaTransparente(ColumnId,RowId)
				
		Catch
			Msgbox("#ERROR: " & LastException, "Mensaje del sistema")
			Log(LastException)
		End Try
	End Sub
	

	Private Sub B4XTable1_DataUpdated
		Try
			If PaginaActual <> B4XTable1.CurrentPage Then
				PaginaActual = B4XTable1.CurrentPage
				LimpiarColorTabla 'blanquea la seleccion de colores de la tabla a Transparente
				CargarTalleGuardado
			End If
		Catch
			Msgbox("#ERROR: " & LastException, "Mensaje del sistema")
			Log(LastException)
		End Try
	End Sub
	
		
		
	Private Sub BtnPlanillaFinalizar_Click
		Try
			
		Dim unResultado As String = ""
			
		Select Msgbox2("¿Está seguro que desea finalizar la planilla?" , "ATENCIÓN", "SI",  "" , "NO", Null)
			Case DialogResponse.POSITIVE
				
				'##############################################################################################################################################
				' LLAMADO A SERVIDOR DE BASE DE DATOS
				Dim Cursor As JdbcResultSet
				Dim unSP As String = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_FINALIZAR_PLANILLA_DEFINITIVO_FINAL " & DatosGlobales.PlanillaSeleccionada & ", " & DatosGlobales.IdUsuario
				Cursor = mod_Conexion.sql1.ExecQuery(unSP)
				
				Do While Cursor.NextRow
					Log("Paso por este sp --> SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_FINALIZAR_PLANILLA_DEFINITIVO_FINAL")
					'Log(Cursor.GetString("RESULTADO"))
					'Dim unResultado As String = Cursor.GetString2(0) 'GetString("RESULTADO")
				Loop
				
				Cursor.Close
				' FIN DEL LLAMADO A SERVIDOR DE BASE DE DATOS
				'##############################################################################################################################################
				
				
				
				'##############################################################################################################################################
				' LLAMADO A SERVIDOR DE BASE DE DATOS
				Dim Cursor As JdbcResultSet
				Dim unSP As String = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_FINALIZAR_MENSAJE " & DatosGlobales.PlanillaSeleccionada 
				Cursor = mod_Conexion.sql1.ExecQuery(unSP)
				
				Do While Cursor.NextRow
					Log(Cursor.GetString("RESULTADO"))
					unResultado = ""
					unResultado = Cursor.GetString("RESULTADO")
				Loop
				
				Cursor.Close
				' FIN DEL LLAMADO A SERVIDOR DE BASE DE DATOS
				'##############################################################################################################################################
								
				
				If unResultado <> "OK" Then
					Msgbox("#ERROR: " & unResultado, "Mensaje del sistema")
					Return
				Else
					FinalizoPlanilla = True
					Activity.Finish
				End If
					
		End Select


		Catch
			Msgbox("#ERROR: " & LastException, "Mensaje del sistema")
			Log(LastException)
		End Try
			
	End Sub



	Private Sub BtnReset_Click
		Try
			Dim unaRespuesta As String = ""
			Select Msgbox2("¿Está seguro que desea resetear la planilla (borrar todo lo pintado)?" , "ATENCIÓN", "SI",  "" , "NO", Null)
				Case DialogResponse.POSITIVE
					'SE BORRA EL CONTENIDO DE LA PLANILLA
					'##############################################################################################################################################
					' LLAMADO A SERVIDOR DE BASE DE DATOS
					Dim Resu As Boolean = False
					Dim Cursor As JdbcResultSet
					Dim unSP As String = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_DETALLE_RESETEAR " & DatosGlobales.PlanillaSeleccionada & ", " & DatosGlobales.IdUsuario
					
					Cursor = mod_Conexion.sql1.ExecQuery(unSP)
					Do While Cursor.NextRow
						If (Cursor.GetInt("RESULTADO") > 0) Then
							'SE VUELVE A LISTA LA GRILLA
							B4XTable1.Clear
							CargarDetalle(DatosGlobales.PlanillaSeleccionada)
						End If
						unaRespuesta = Cursor.GetString("MENSAJE")
					Loop
					Cursor.Close
					' FIN DEL LLAMADO A SERVIDOR DE BASE DE DATOS
					'##############################################################################################################################################

					Msgbox(unaRespuesta, "Mensaje del sistema")

			End Select
		Catch
			Msgbox("#ERROR: " & LastException, "Mensaje del sistema")
			Log(LastException)
		End Try
	End Sub
		
	
	

#End Region



#Region "Funciones"


	Public Sub CargarCabecera(unaPlanilla As Int)

		'SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_CABECERA
		'DesactivarModoEstricto
		FxGlobales.DesactivarModoEstricto
		
		mod_Conexion.Conexion()

		'##############################################################################################################################################
		' LLAMADO A SERVIDOR DE BASE DE DATOS
		Dim Cursor As JdbcResultSet
		Dim unSP As String = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_CABECERA " & unaPlanilla
					
		Cursor = mod_Conexion.sql1.ExecQuery(unSP)
	
		Dim sb As StringBuilder
		sb.Initialize
		sb.Append("<html><body style='background-color: rgb(213, 213, 249);'>")
	
		Do While Cursor.NextRow
			Dim unaPrioridad As String = Cursor.GetString("PRIORIDAD")
			Dim unColeccion As String = Cursor.GetString("COLECCION")
			Dim unEstado As String = Cursor.GetString("ESTADO")
			Dim unArticulo As String = Cursor.GetString("ARTICULO")
			Dim unColor As String = Cursor.GetString("COLOR")
			Dim unaObservacion As String = Cursor.GetString("OBSERVACION")
			Dim unaFechaCreacion As String = Cursor.GetString("CREACION")
			Dim unaFechaDistri As String = Cursor.GetString("A_DISTRI")
			Dim unUsuarioConfeccion As String = Cursor.GetString("CONFECCION")
			
'			sb.Append("Colección: ").Append(unColeccion).Append(CRLF)
'			sb.Append("Prioridad: ").Append(unaPrioridad).Append(CRLF)
'			sb.Append("Artículo: ").Append(unArticulo).Append(CRLF)
'			sb.Append("Color: ").Append(unColor).Append(CRLF)
'			sb.Append("Estado: ").Append(unEstado).Append(CRLF)
'			sb.Append("Creación: ").Append(unaFechaCreacion).Append(CRLF)
'			sb.Append("A Distribución: ").Append(unaFechaDistri).Append(CRLF)
'			sb.Append("Confección: ").Append(unUsuarioConfeccion).Append(CRLF)
'			sb.Append("Observación: ").Append(unaObservacion).Append(CRLF).Append(CRLF)
			
			' Construye el texto con formato HTML
			
			unaObservacion = unaObservacion.Replace(CRLF, "<br/>") 'control salto de linea
		
			sb.Append("<b>Colección:</b> ").Append(unColeccion).Append("<br/>")
			sb.Append("<b>Prioridad:</b> ").Append(unaPrioridad).Append("<br/>")
			sb.Append("<b>Artículo:</b> ").Append(unArticulo).Append("<br/>")
			sb.Append("<b>Color:</b> ").Append(unColor).Append("<br/>")
			sb.Append("<b>Estado:</b> ").Append(unEstado).Append("<br/>")
			sb.Append("<b>Creación:</b> ").Append(unaFechaCreacion).Append("<br/>")
			sb.Append("<b>A Distribución:</b> ").Append(unaFechaDistri).Append("<br/>")
			sb.Append("<b>Confección:</b> ").Append(unUsuarioConfeccion).Append("<br/>")
			sb.Append("<b>Observación:</b> ").Append(unaObservacion).Append("<br/><br/>")
		
		Loop
		Cursor.Close
	    
		sb.Append("</body></html>")
	
		' FIN DEL LLAMADO A SERVIDOR DE BASE DE DATOS
	'##############################################################################################################################################

	Dim lblInfo As Label ' Crear un control Label
	lblInfo.Initialize("") ' Inicializar el Label
	lblInfo.Text = sb.ToString ' Asignar el texto

	WvContenido.LoadHtml(sb.ToString)
	
		LabelCab.Text = " Colección: " & unColeccion & CRLF & " Prioridad: " & unaPrioridad & CRLF & CRLF & " Artículo: " & unArticulo & CRLF & " Color: " & unColor & CRLF & " Estado: " & unEstado

		LabelCab2.Text = "Creación: " & unaFechaCreacion & CRLF & CRLF & "A Distrib: " & unaFechaDistri & CRLF & CRLF & "Confecc.: " & unUsuarioConfeccion

		LabelCabObs.text= " Observación: " & unaObservacion

	End Sub



	Public Sub CargarDetalle(unaPlanilla As Int)

		Dim Data As List
		Data.Initialize


		'DesactivarModoEstricto
		FxGlobales.DesactivarModoEstricto
		
		mod_Conexion.Conexion()

		Dim CantTalles As Int = 0
		Dim ListaTalles As List

		'##############################################################################################################################################
		' LLAMADO A SERVIDOR DE BASE DE DATOS -> ARMADO PREVIO DE LA GRILLA

		Dim Cursor As JdbcResultSet
		Dim unSP As String = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_DETALLE_ARMADO_GRILLA " & unaPlanilla
					
		Cursor = mod_Conexion.sql1.ExecQuery(unSP)
		
		ListaTalles.Initialize
		
		Do While Cursor.NextRow
				
			'Log("T" & Cursor.GetString("TALLE"))
			ListaTalles.Add(Cursor.GetString("TALLE"))
										
		Loop

		Cursor.Close
		
		CantTalles = ListaTalles.Size
		'Log(ListaTalles)
		
		
		'CREACION DE LA TABLA (MANUAL)
		'B4XTable1.AddColumn("CLIENTE", B4XTable1.COLUMN_TYPE_TEXT)
		Dim ColCliente As B4XTableColumn = B4XTable1.AddColumn("CLIENTE", B4XTable1.COLUMN_TYPE_TEXT)
		ColCliente.Id = 0
		ColCliente.Width = 160dip
		
		Dim CantColumnas As Int

		For	i= 0 To CantTalles-1
			'B4XTable1.AddColumn(ListaTalles.Get(i), B4XTable1.COLUMN_TYPE_TEXT)
			Dim ColCantidad As B4XTableColumn = B4XTable1.AddColumn(ListaTalles.Get(i), B4XTable1.COLUMN_TYPE_TEXT)
			ColCantidad.Id = i + 1
			ColCantidad.Width = 40dip
				
			CantColumnas = ColCantidad.Id
		Next
			
		'B4XTable1.AddColumn("TOT", B4XTable1.COLUMN_TYPE_TEXT)
			
		Dim ColTot As B4XTableColumn = B4XTable1.AddColumn("TOT", B4XTable1.COLUMN_TYPE_TEXT)
		ColTot.Id = CantColumnas + 1
		ColTot.Width = 40dip
			
		'FIN DE CREACION DE LA TABLA (MANUAL)
		



		' FIN DEL LLAMADO A SERVIDOR DE BASE DE DATOS -> ARMADO PREVIO DE LA GRILLA
		'##############################################################################################################################################



		'##############################################################################################################################################
		' LLAMADO A SERVIDOR DE BASE DE DATOS


		Dim Cursor As JdbcResultSet
		Dim unSP As String = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_DETALLE " & unaPlanilla
					
		Cursor = mod_Conexion.sql1.ExecQuery(unSP)

		Dim Data As List
		Data.Initialize

		Dim CantElementos As Int = CantTalles + 2 'Todos los talles mas la columna de Clientes y la columa de Totales
			
		CantCol = CantElementos
			
		'Log("Cantidad de elementos: " & CantElementos)
			
		Do While Cursor.NextRow
			Dim row(CantElementos) As Object

			For	i= 0 To CantElementos-1
				row(i) = Cursor.GetString(Cursor.GetColumnName(i))
				'Log(row(i))
			Next
				
			Data.Add(row)
			
			CantFilas = CantFilas +1
										
		Loop

		Cursor.Close
		
		
		B4XTable1.SetData(Data)


		'B4XTable1.CurrentPage = 0

		'B4XTable1.MaximumRowsPerPage = 1000

		'B4XTable1.BuildLayoutsCache(B4XTable1.MaximumRowsPerPage)
		
		B4XTable1.Refresh

		'Log("Cantidad maxima de filas por pagina: " & B4XTable1.MaximumRowsPerPage)
		'Log("Cantidad de filas por pagina: " & B4XTable1.RowsPerPage)




		'Log("Pagina Actual: " & B4XTable1.CurrentPage)
		
		

		' FIN DEL LLAMADO A SERVIDOR DE BASE DE DATOS
		'##############################################################################################################################################




		'Log("Cantidad de filas: " & CantFilas)

	End Sub



		
	Private Sub PintarCelda(ColumnId As String, RowId As Long, unColor As Int)
		Dim RowData As Map = B4XTable1.GetRow(RowId)
		Dim cell As String = RowData.Get(ColumnId)
		Activity.Title = cell
	    
		Dim cs As CSBuilder
		cs.Initialize.Color(Colors.RGB(0,128,0)).Size(20).Bold.Append(cell).PopAll
		
		Log("Voy a pintar la celda de rojo: FILA -> " & RowId & " - COLUMNA: "  & ColumnId)


	'	Log("Cantidad de columnas: " & B4XTable1.Columns.Size)
	'	Log("Cantidad de filas: " & B4XTable1.Size)
		
		   
		Dim column As B4XTableColumn = B4XTable1.GetColumn(ColumnId)
		
		Dim UnaFilaPag As Int
		If B4XTable1.CurrentPage = 1 Then
			UnaFilaPag = RowId
		Else
			'UnaFilaPag = RowId - (5 * (B4XTable1.CurrentPage-1)) 'LE RESTO UNO SIEMPRE A LA PAGINA ACTUAL PARA QUE EL ROWID SIEMPRE QUEDE EN FUNCION COMO SI FUERA DE LA PAGINA UNO
			UnaFilaPag = RowId - (B4XTable1.RowsPerPage * (B4XTable1.CurrentPage-1)) 'LE RESTO UNO SIEMPRE A LA PAGINA ACTUAL PARA QUE EL ROWID SIEMPRE QUEDE EN FUNCION COMO SI FUERA DE LA PAGINA UNO
		End If
		
		Dim pnl As B4XView = column.CellsLayouts.Get(UnaFilaPag) 'column.CellsLayouts.Get(RowId) 'column.CellsLayouts.Get(1)
		Dim l As Label    = pnl.GetView(0) 'pnl.GetView(0)


		


		If (pnl.GetView(0).Color = unColor) Then 'Si lo seleccionado en la grilla es igual al color con el cual voy a pintarlo lo borro de la bbdd
				
							

			'Para obtener el contenido de la celda seleccionada
			Dim row  As Map = B4XTable1.GetRow(RowId)
			Dim CodCte As String = ""
			Dim CteCompleto As String = row.Get("0") 'Obtengo el contenido de la Columna/Fila --> El cliente siempre esta en la primer columna
			Dim ColSel As B4XTableColumn = B4XTable1.GetColumn(ColumnId)
			CodCte = FxGlobales.Left(CteCompleto, CteCompleto.IndexOf("-")-1 )
								
			Select Msgbox2("¿Desea anular la selección para el cliente: " & CodCte & " y el talle: " & ColSel.Title & "?", "ATENCIÓN", "SI",  "" , "NO", Null)
				Case DialogResponse.POSITIVE
					'				'guardar des-seleccion en base de datos
						
					If (GuardarSeleccion(ColumnId, RowId, False)) = True Then
						l.Color= Colors.Transparent
					Else
						Msgbox("#ERROR: no se ha podido guardar la des-seleccion. ", "Mensaje del sistema")
					End If
						
					
			End Select
					
				
				
		Else If (pnl.GetView(0).Color = Colors.Transparent) Then 'Si el color de la celda es distinto al q voy a usar para pintar grabo en la bbdd
			
			'guardar seleccion aca
			If (GuardarSeleccion(ColumnId, RowId, True)) = True Then
				l.Color = unColor
	'			Else
	'				Msgbox("#ERROR: no se ha podido guardar lo seleccionado. ", "Mensaje del sistema")
			End If
				
		Else
		
			'guardar seleccion aca
			If (GuardarSeleccion(ColumnId, RowId, True)) = True Then
				l.Color = unColor
	'			Else
	'				Msgbox("#ERROR: no se ha podido guardar lo seleccionado. ", "Mensaje del sistema")
			End If
				
		End If
		

		
		
		'Colores de grilla original:
		'Even row Color: #FFFFFFFF
		'Odd row Color: #FFEAEAEA
	End Sub




	Private Sub PintarCeldaTransparente(ColumnId As String, RowId As Long)
		Dim RowData As Map = B4XTable1.GetRow(RowId)
		Dim cell As String = RowData.Get(ColumnId)
		Activity.Title = cell
	    
		Dim cs As CSBuilder
		cs.Initialize.Color(Colors.RGB(0,128,0)).Size(20).Bold.Append(cell).PopAll
		
		    
		Dim column As B4XTableColumn = B4XTable1.GetColumn(ColumnId)
		Dim pnl As B4XView = column.CellsLayouts.Get(RowId) 'column.CellsLayouts.Get(1)
		Dim l As Label    = pnl.GetView(0) 'pnl.GetView(0)


		l.Color= Colors.Transparent

		
		'Colores de grilla original:
		'Even row Color: #FFFFFFFF
		'Odd row Color: #FFEAEAEA
	End Sub




	Private Sub LimpiarColorTabla

		CantFilas = B4XTable1.RowsPerPage '5

		Log("Filas: " & CantFilas & " - Clumnas: " & CantCol)

		For	i = 1 To CantFilas
		
			For j = 0 To CantCol + 1
		
				B4XTable1_CellClicked2(j, i)

			Next

		Next

	End Sub





	Private Sub GuardarSeleccion(ColumnId As String, RowId As Long, Activo As Boolean) As Boolean
		
		Log("Entro a --> GuardarSeleccion")
		
		Dim Resu As Boolean = False
		'Para obtener el contenido de la celda seleccionada
		Dim row  As Map = B4XTable1.GetRow(RowId)
		Dim CodCte As String = ""
		Dim CteCompleto As String = row.Get("0") 'Obtengo el contenido de la Columna/Fila --> El cliente siempre esta en la primer columna
		Dim ColSel As B4XTableColumn = B4XTable1.GetColumn(ColumnId)
		

		'Log("Se guarda cliente: " & CteCompleto & " y talle: " & ColSel.Title)
		'Log("CodCliente: " &  FxGlobales.Left(CteCompleto, CteCompleto.IndexOf("-")-1 ))

		CodCte = FxGlobales.Left(CteCompleto, CteCompleto.IndexOf("-")-1 )

		
		'##############################################################################################################################################
		' LLAMADO A SERVIDOR DE BASE DE DATOS
				
		Dim Cursor As JdbcResultSet
		Dim unSP As String = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_DETALLE_GUARDAR_SELECCION " & DatosGlobales.PlanillaSeleccionada & ", '" & CodCte & "', '" & ColSel.Title & "', " & DatosGlobales.IdUsuario & ", " & Activo
				
		Cursor = mod_Conexion.sql1.ExecQuery(unSP)
		Do While Cursor.NextRow
			If (Cursor.GetInt("RESULTADO") > 0) Then
				Resu = True
			End If
		Loop
			
		Cursor.Close
			
		' FIN DEL LLAMADO A SERVIDOR DE BASE DE DATOS
		'##############################################################################################################################################
		
		Return Resu
	End Sub



	Private Sub CargarTalleGuardado()
		Dim FilaReal As Int
		For	i = 1 To B4XTable1.RowsPerPage
			If (B4XTable1.CurrentPage) = 1 Then
				Dim row  As Map = B4XTable1.GetRow(i)
				FilaReal = i
			Else
				FilaReal = i + (B4XTable1.RowsPerPage * (B4XTable1.CurrentPage-1))
				'Log("total grilla:" & B4XTable1.Size)
				'Log("Fila: " & FilaReal)
				If FilaReal <= B4XTable1.Size Then
					Dim row  As Map = B4XTable1.GetRow(i + (B4XTable1.RowsPerPage * (B4XTable1.CurrentPage-1)))
				End If
			End If
				
			If FilaReal <= B4XTable1.Size Then
				Dim CodCte As String = ""
				Dim CteCompleto As String = row.Get("0") 'Obtengo el contenido de la Columna/Fila --> El cliente siempre esta en la primer columna
				For	j = 1 To CantCol -2 'para no mostrar el total
				
					Dim ColSel As B4XTableColumn = B4XTable1.GetColumn(j)
					CodCte = FxGlobales.Left(CteCompleto, CteCompleto.IndexOf("-")-1 )
				
					Log("Se va a cargar la seleccion para el cliente: " & CodCte & " y talle: " & ColSel.Title)
								
					'llamado al SP --> si resultado positivo pinto sino no hago nada
					If ConsultarTalleParaCliente(CodCte, ColSel.Title) = True Then
						PintarCelda(j, FilaReal, Colors.Red)
					End If
				
				Next
			End If
				
		Next

	End Sub



	Private Sub ConsultarTalleParaCliente(unCodCliente As String, unCodTalle As String) As Boolean

		'##############################################################################################################################################
		' LLAMADO A SERVIDOR DE BASE DE DATOS
		Dim Resu As Boolean = False
		Dim Cursor As JdbcResultSet
		Dim unSP As String = "EXEC SP_MOBILE_GESTION_DE_DISTRIBUCION_PLANILLA_DETALLE_CONSULTAR_TALLE " & DatosGlobales.PlanillaSeleccionada & ", '" & unCodCliente & "', '" & unCodTalle & "'"
				
		Cursor = mod_Conexion.sql1.ExecQuery(unSP)
		Do While Cursor.NextRow
			If (Cursor.GetInt("RESULTADO") > 0) Then
				Resu = True
			End If
		Loop
		
		Cursor.Close
		' FIN DEL LLAMADO A SERVIDOR DE BASE DE DATOS
		'##############################################################################################################################################
		
		Return Resu

	End Sub



#End Region










