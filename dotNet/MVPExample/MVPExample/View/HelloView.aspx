<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="HelloView.aspx.cs" Inherits="MVPExample.View.WebForm1" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head id="Head1" runat="server">
    <title></title>
</head>
<body>
    <form id="myForm"  runat="server">
        Name: <asp:textbox id="myName" runat="server" />
        <p>
            <asp:button id="myButton" text="Click here" OnClick="SubmitBtn_Click" runat="server" />
        </p>
        <span id="mySpan" runat="server"  />
    </form>
</body>
</html>

