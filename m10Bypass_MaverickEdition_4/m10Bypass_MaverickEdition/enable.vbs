Option Explicit 
Dim WSHShell, strSetting
Set WSHShell = WScript.CreateObject("WScript.Shell")


If True Then
Proxy
End If



Sub Proxy 
WSHShell.regwrite "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings\ProxyEnable", 1, "REG_DWORD"
WSHShell.regwrite "HKCU\Software\Microsoft\Windows\CurrentVersion\Internet Settings\ProxyServer","socks=localhost:8080", "REG_SZ"
End Sub