Option Explicit 
Dim WSHShell, strSetting
Set WSHShell = WScript.CreateObject("WScript.Shell")


If True Then
NoProxy
End If


Sub NoProxy 
WSHShell.regwrite "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings\ProxyEnable", 0, "REG_DWORD"
WSHShell.regwrite "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings\ProxyServer"," ", "REG_SZ"
End Sub