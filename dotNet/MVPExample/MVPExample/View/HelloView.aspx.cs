using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MVPExample.Presenters;

namespace MVPExample.View
{
    public partial class WebForm1 : System.Web.UI.Page, IHelloView
    {
        private HelloPresenter HelloPresenter;

        protected void Page_Load(object sender, EventArgs e)
        {
            this.HelloPresenter = new HelloPresenter(this);
        }

        public void SubmitBtn_Click(object sender, System.EventArgs e)
        {
            HelloPresenter.Send();
        }

        public string Id
        {
            get { return ((TextBox)myForm.FindControl("myName")).Text; }
        }

        public void UpdateView(String value)
        {
            mySpan.InnerText = value;
        }

    }
}