using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ExternalConfigExample.View;
using ExternalConfigExample.Model;

namespace ExternalConfigExample.Presenter
{
    public class HelloPresenter
    {
        private IHelloView HelloView;
        private BusinessClass Business;

        public HelloPresenter(IHelloView HelloView)
        {
            this.HelloView = HelloView;
            this.Business = new BusinessClass();
        }

        public void Send()
        {
            HelloView.UpdateView(Business.DoSomething());
        }

    }
}