Group=Default Group
ModulesStructureVersion=1
Type=StaticCode
Version=2.71
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Type cInputBox (cInputBoxOKBtn As Button, cInputBoxCancelBtn As Button, _
					cInputBoxEditText As EditText, cInputBoxLabel As Label, _
					HolderPnl As Panel, FrontPnl As Panel, Visible As Boolean, _
					Result As String, Response As Int)
	Dim ButtonClicked As Boolean
End Sub

Sub Show(Inp As cInputBox, Activity As Activity, Message As String, Hint As String, Positive As String, Cancel As String, InputType As Int) As Int
	Inp.Initialize
	
	Inp.cInputBoxOKBtn.Initialize("cInputBoxBtn")
	Inp.cInputBoxOKBtn.Text=Positive
	Inp.cInputBoxOKBtn.Tag="Positive"
	
	Inp.cInputBoxCancelBtn.Initialize("cInputBoxBtn")
	Inp.cInputBoxCancelBtn.Text=Cancel
	Inp.cInputBoxCancelBtn.Tag="Cancel"	
	
	Inp.cInputBoxEditText.Initialize("cInputBoxEditText")
	Inp.cInputBoxEditText.Hint=Hint
	Inp.cInputBoxEditText.InputType=InputType
	
	Inp.cInputBoxLabel.Initialize("")
	Inp.cInputBoxLabel.Text=Message
	Inp.cInputBoxLabel.TextSize=14dip
	Inp.cInputBoxLabel.Typeface=Typeface.DEFAULT_BOLD
	Inp.cInputBoxLabel.TextColor=Colors.RGB(0,0,139)
	Inp.cInputBoxLabel.Gravity=Gravity.CENTER_HORIZONTAL
	Inp.cInputBoxlabel.Gravity=Gravity.CENTER_VERTICAL
	Inp.HolderPnl.Initialize("")
	Inp.FrontPnl.Initialize("")
	
	'Inp.HolderPnl.Color=Colors.ARGB(135,0,0,0)
	Inp.HolderPnl.Color=Colors.Gray
	
	Inp.FrontPnl.Color=Colors.ARGB(135,240,248,255)
	'Inp.FrontPnl.Color=Colors.ARGB(135,240,248,255)

	
	Activity.AddView(Inp.holderpnl,10dip,100%y/2-150dip,100%x-20dip,270dip)
	Inp.HolderPnl.AddView(Inp.FrontPnl,5dip,5dip,Inp.HolderPnl.width-10dip,Inp.HolderPnl.Height-10dip)
	
	Inp.FrontPnl.AddView(Inp.cInputBoxLabel,15dip,15dip,Inp.FrontPnl.Width-30dip,100dip)
	Inp.FrontPnl.AddView(Inp.cInputBoxEditText,15dip,125dip,Inp.FrontPnl.Width-30dip,60dip)
	Inp.FrontPnl.AddView(Inp.cInputBoxOKBtn,15dip,190dip,90dip,60dip)
	Inp.FrontPnl.AddView(Inp.cInputBoxCancelBtn,Inp.FrontPnl.Width-15dip-90dip,190dip,90dip,60dip)
	
	Inp.cInputBoxEditText.SingleLine=True
	Inp.cInputBoxEditText.ForceDoneButton=True
	
	Inp.Visible=True
	ButtonClicked=False
	Do While Not(ButtonClicked)
		DoEvents
	Loop
	
	Inp.result=Inp.cInputBoxEditText.Text
	Hide(Inp)
	Return Inp.Response
End Sub

Sub Hide(Inp As cInputBox)
	Inp.Result=Inp.cInputBoxEditText.Text
	If Inp.Visible Then
		Inp.HolderPnl.RemoveView
		Inp.Visible=False
	End If
End Sub

Sub Button_click(b As String, inp As cInputBox, activity As Activity)
	Dim p As Phone
	p.HideKeyboard(activity)
	ButtonClicked=True
	Select b
		Case "Positive": inp.Response=DialogResponse.POSITIVE
		Case "Cancel": inp.Response=DialogResponse.CANCEL
	End Select
End Sub