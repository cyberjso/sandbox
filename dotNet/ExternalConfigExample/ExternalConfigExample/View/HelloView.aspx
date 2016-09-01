<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="HelloView.aspx.cs" Inherits="ExternalConfigExample.View.HelloView" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head id="Head1" runat="server">
    <title></title>
</head>
<body>
    <form id="myForm"  runat="server">
        <p>
            <asp:button id="myButton" text="Click here" OnClick="ShowProperties" runat="server" />
        </p>
        <ul id="properties" runat="server">Application Properties </ul>
        <ul id="connectionStrings" runat="server">Connection Strings </ul>

        
    </form>
</body>
</html>

