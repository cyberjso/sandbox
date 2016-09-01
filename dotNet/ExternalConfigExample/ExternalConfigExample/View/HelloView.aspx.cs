using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using ExternalConfigExample.Presenter;
using System.Web.UI.HtmlControls;
using ExternalConfigExample.Model;

namespace ExternalConfigExample.View
{
    public partial class HelloView : System.Web.UI.Page, IHelloView
    {
        private HelloPresenter Presenter;

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Presenter = new HelloPresenter(this);
        }

        public void ShowProperties(object sender, System.EventArgs e)
        {
            Presenter.Send();
        }

        public void Update(Output output)
        {
            WriteAppSettings(output);
            WriteConnectionStrings(output);
        }

        private void WriteConnectionStrings(Output output)
        {
            foreach (String value in output.ConnectionStrings)
            {
                HtmlGenericControl li = new HtmlGenericControl("li");
                li.InnerText = value;
                connectionStrings.Controls.Add(li);
            }
        }

        private void WriteAppSettings(Output output)
        {
            foreach (String value in output.AppSettings)
            {
                HtmlGenericControl li = new HtmlGenericControl("li");
                li.InnerText = value;
                properties.Controls.Add(li);
            }
        }


    }
}